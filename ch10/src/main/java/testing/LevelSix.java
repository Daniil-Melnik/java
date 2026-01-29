/*
6. Освоить перехват событий от движения мыши
- создать фрейм, поместить в него компонент - поле
- ввести для компонента класс-перехватчик событий:
  -- способен менять курсор при спмене наведения
  -- перетаскивает объект в компоненте при зажатой ПКМ
*/

package testing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;

public class LevelSix {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 300;

    public static void main(String ... args){
        EventQueue.invokeLater(() -> {
            FrameSix frame = new FrameSix(WIDTH, HEIGHT);
            frame.setVisible(true);
        });
    }
}

class FrameSix extends JFrame{
    public FrameSix(int w, int h){
        setLayout(null);
        setSize(w, h);
        setTitle("LevelSix");
        addWindowListener(new LevelThree.WindowTerminator());

        ComponentSix component = new ComponentSix(0, 0);
        component.setBounds(10, 10, 465, 240);
        add(component);
    }
}

class ComponentSix extends JComponent{ // компонент с перетаскиваемым прямоугольником

    private float rectX; // параметр Х левого верхнего угла прямоугольника
    private float rectY; // параметр Y левого верхнего угла прямоугольника

    public static int RECTANGLE_WIDTH = 50; // ширина прямоугольника
    public static int RECTANGLE_HEIGHT = 50; // высота прямоугольника

    public ComponentSix(float x, float y){
        addMouseMotionListener(new MotionMouse(this)); // привязка перехватчика движения пышью
        rectX = x;
        rectY = y;
    }
    @Override
    protected void paintComponent(Graphics g) { // отрисовка компонента
        Graphics2D g2 = (Graphics2D) g;

        Rectangle2D rect = new Rectangle2D.Float(rectX, rectY, RECTANGLE_WIDTH, RECTANGLE_HEIGHT); // создание прямоугольника
                                                     // основываясь на параметрах ЛВ угла из параметров экземпляра компонента
        g2.setColor(Color.BLUE);
        g2.fill(rect);
        g2.draw(rect); // рисование обновленного прямоугольника
    }

    public float getRectX(){
        return rectX;
    }

    public float getRectY(){
        return rectY;
    }


    public void setRectCoord(float x, float y){ // метод установки новых координат ЛВ угла прямоугольника
        rectX = x;
        rectY = y;
    }
}

class MotionMouse extends MouseMotionAdapter{ // класс перехватчика основаный на адаптере
    ComponentSix component; // ссылка на компонент, для которого экземпляр перехватчика

    public MotionMouse(ComponentSix c){
        component = c;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int pX = e.getX(); // координаты положения курсора при зажатом движении
        int pY = e.getY();
        if ((pY <= component.getRectY() + ComponentSix.RECTANGLE_HEIGHT) // проверка положение курсора на попадание в область квадрата
                && (pY >= component.getRectY())
                && (pX <= component.getRectX() + ComponentSix.RECTANGLE_WIDTH)
                && (pX >= component.getRectX())){

            component.setCursor(new Cursor(Cursor.MOVE_CURSOR)); // смена курсора на "перетаскиватель" если попадает
        }
        else component.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // смена на обычный когда не попадает
    }

    @Override
    public void mouseDragged(MouseEvent e) { // перехватчик зажатого движения
        int pX = e.getX(); // координаты положения курсора при зажатом движении
        int pY = e.getY();

        if ((pY <= component.getRectY() + ComponentSix.RECTANGLE_HEIGHT) // проверка положение курсора на попадание в область квадрата
                && (pY >= component.getRectY())
                && (pX <= component.getRectX() + ComponentSix.RECTANGLE_WIDTH)
                && (pX >= component.getRectX())){

            component.setRectCoord(pX - (float) ComponentSix.RECTANGLE_WIDTH / 2,
                    pY - (float) ComponentSix.RECTANGLE_HEIGHT / 2); // установка новых координат ЛВ угла квадрата
                                                                        // (точка зажатия считается центром квадрата)
            component.repaint(); // перерисовка компонента
        }
    }
}