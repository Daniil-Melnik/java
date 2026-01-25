/*
8. Расширить приложение из LevelSix до переноса нескольких квадратов
- создать фрейм, создать в нём компонент для рисования
- расположить в компоненте 5 квадратов разного цвета, внедрить возможности для динамического изменения их координат
- привязать к компоненту с квадратами перехватчик, который должен:
  - обрабатывать движения переноса мышью
  - перетаскивать каждый квадрат в отдельности
  - квадраты могут накладываться друг на друга
*/

package testing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

public class LevelEight {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 300;

    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            FrameEight frame = new FrameEight();
            frame.setVisible(true);
        });
    }
}

class FrameEight extends JFrame{
    public FrameEight(){
        setLayout(null);
        setSize(LevelEight.WIDTH, LevelEight.HEIGHT);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowTerminator());
        setTitle("LevelEight");

        ComponentEight component = new ComponentEight();
        component.setBounds(0, 0, LevelEight.WIDTH, LevelEight.HEIGHT);
        add(component);
    }
}

class ComponentEight extends JComponent{ // компонент с квадратами
    public final static float RECT_D = 50; // длина стороны квадрата

    public ComponentEight(){
        addMouseMotionListener(new RectangleMouse(this)); // привязка перехватчика движений мыши
    }

    private ArrayList<TimedRectangle> rectangles = new ArrayList<>(List.of( // поле комопнента с инициализацией при
            new TimedRectangle(0, 0, RECT_D, RECT_D, Color.BLUE),     // создании экземпляра
            new TimedRectangle(55, 0, RECT_D, RECT_D, Color.GREEN),   // 5 разноцветных квадратов с координатами,
            new TimedRectangle(110, 0, RECT_D, RECT_D, Color.RED),    // размерами и цветом
            new TimedRectangle(165, 0, RECT_D, RECT_D, Color.MAGENTA),
            new TimedRectangle(120, 25, RECT_D, RECT_D, Color.ORANGE)
    ));

    // - Квараты хранятся в списочном массиве. Расширенный квадрат представляет собой квадрат с цветом и временной маркой.
    // - Далее берётся точка, на которой была зажата и перенесена мышь. По этой точке определяется кварат, которому может
    // принадлежать точка.
    // - Когда квадрат определён, происходит перерасчёт его новых екоординат и выполняется установка координат в массиве
    // квадратов. Вместе координатами устанавливается новая временная марка.
    // - После установки квадрата происходит сортировка по убыванию временной марки.
    // - Из отсортированного массива для отрисовки вынимаются квараты в таком порядке, чтобы соблюдалось наложение квадратов
    // наложение соблюдается через порядок рисования квадратов (для этого нужна сортировка по временной марке)
    // - Также, порядок сортировки необходим для быстрого поиска квадратов (поиск начинается с "верхнего слоя")
    @Override
    protected void paintComponent(Graphics g) { // отрисовка компонента
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 4; i >= 0; i--){ // отрисовка квадатов в обратном порядке для соблюдения слоёв
            TimedRectangle r = rectangles.get(i); // выемка квадрата
            g2.setColor(r.getColor()); // принятие его цвета
            g2.draw(r); // рисование квадрата в границах
            g2.fill(r); // наполнение квадрата
        }
    }

    public TimedRectangle getRectanleWithPoint(Point p){ // метод для нахождения квадрата с этой точкой
        TimedRectangle result = null; // пре-инициализация через null
        Iterator<TimedRectangle> iterator = rectangles.iterator(); // взятие итератора от массива квадратов
        TimedRectangle t = null;
        while ((result == null) && iterator.hasNext()){ // перебор коллекции пока не найден результат или не достигнут
                                                        // предел коллекции
            t = iterator.next(); // переход по итератору
            if (t.isInRectangle(p)) result = t; // проверка квадрата на содержание точки
        }
        return result;
    }

    public void sortRecnangels(){ // сортировка коллекции квадратов
        // сортировка с определением компаратора через лямбда-выражение
        rectangles.sort((o1, o2) -> {
            long tSt1 = o1.getTimeStamp(); // сортировка по времени доступа
            long tSt2 = o2.getTimeStamp();
            return tSt2 - tSt1 < 0 ? -1 : tSt1 - tSt2 > 0 ? 1 : 0;
        });
    }
}

class TimedRectangle extends Rectangle2D.Float{ // расширенный квадрат
    private long timeStamp; // временная метка
    private Color color; // цвет

    public TimedRectangle(float x, float y, float w, float h, Color c){
        super(x, y, w, h); // конструктор суперкласса
        timeStamp = System.currentTimeMillis(); // установка времени создания квадрата
        color = c;
    }

    @Override
    public String toString() {
        return String.format("%s", color);
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getTimeStamp(){
        return timeStamp;
    }

    public Color getColor(){
        return color;
    }

    public boolean isInRectangle(Point p){ // проверка принадлежности точки квадрату
        float pX = (float) p.getX(); // координаты точки
        float pY = (float) p.getY();

        float rX = (float) this.getX(); // координаты ЛВ угла квадрата
        float rY = (float) this.getY();

        return ((pX >= rX) && (pX <= rX + getWidth()) && (pY >= rY) && (pY <= rY + getHeight()));
    }

    public void setCoordinates(Point p){ // установка квадрата
        float newX = (float) (p.getX() - ComponentEight.RECT_D / 2); // расчёт новых координат квадрата
        float newY = (float) (p.getY() - ComponentEight.RECT_D / 2);
        super.setRect(newX, newY, ComponentEight.RECT_D, ComponentEight.RECT_D); // установка "чистого" квадрата
        setTimeStamp(System.currentTimeMillis()); // обновление временной метки
    }
}

class RectangleMouse extends MouseMotionAdapter{ // перехватчик движений мыши
    private ComponentEight component;

    public RectangleMouse(ComponentEight c){
        component = c;
    }

    @Override
    public void mouseDragged(MouseEvent e) { // движение с задажатой кнопкой
        Point point = e.getPoint(); // получить точку
        TimedRectangle t = component.getRectanleWithPoint(point); // поиск квадрата с точкой
        if (t != null){ // если квадрат найден
            t.setCoordinates(point); // установить новые координаты и временную метку квадрата
            component.sortRecnangels(); // сортировка квадратов по временной марке
            component.repaint(); // перерисовка компонента с квадратами
        }
    }
}
