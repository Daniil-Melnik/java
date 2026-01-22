/*
5. Освоить перехват событий от мыши. Часть 1. Добавление квадратов/окружностей по клику
- создать фрейм, выделить в нём панель с отличным по цвету фоном
- реализовать внутри панели компонент, отрисовывающий квадраты/окружности в произвольном количестве
  -- ввести в компонент параметры-массивы отвечающие за фигуры
    --- создать внутренний класс, хранящий тип фигуры, координаты угла, ширину и высоту, цвет
    --- присвоить массиву в компоненте этот тип
  -- ввести в метод отрисовки рисование по массивам
- привязать к панели действия мыши: л-щелчок - квадрат, п-щелчок - окружность на место клика
*/

// доделать окружности
// ввести комментарии

package testing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.Random;

public class LevelFive {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 300;

    public static void main(String ... args){
        EventQueue.invokeLater(() -> {
            FrameFive frame = new FrameFive(WIDTH, HEIGHT);
            frame.setVisible(true);
        });
    }
}

class FrameFive extends JFrame{

    private static final int MARGIN_TOP = 5;
    private static final int MARGIN_LEFT = 5;

    public FrameFive(int w, int h){
        setLayout(null);
        setSize(w, h);
        addWindowListener(new WindowTerminator());

        ComponentFive comp = new ComponentFive();
        comp.setBounds(MARGIN_LEFT, MARGIN_TOP, w - MARGIN_LEFT*5, h - MARGIN_TOP*10);
        comp.addMouseListener(new MouseClicker(comp));
        add(comp);
    }
}

class ComponentFive extends JComponent{
    private LinkedList<RectangleMod> rectangles;

    public ComponentFive(){
        rectangles = new LinkedList<>();
    }

    public void appendRectangle(double h, double w, Point2D p, Color c, boolean t){
        rectangles.add(new RectangleMod(p.getX(), p.getY(), h, w, c, t));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.RED);
        for (RectangleMod fO : rectangles){
            g2.setColor(fO.getColor());
            g2.draw(fO);
        }
    }
}

class RectangleMod extends Rectangle2D.Double{
    private Color color;
    private boolean type;

    public RectangleMod(double h, double w, double x, double y, Color c, boolean t){
        super(h, w, x, y);
        color = c;
        type = t;
    }

    public RectangleMod(){
        super();
        color = Color.BLACK;
        type = false;
    }

    public Color getColor(){
        return color;
    }

    public boolean getType(){
        return type;
    }
}

class EllipsMod extends Ellipse2D.Double{
    private Color color;

    public EllipsMod(double h, double w, double x, double y, Color c){
        super(h, w, x, y);
        color = c;
    }

    public EllipsMod(){
        super();
        color = Color.BLACK;
    }
}

class MouseClicker extends MouseAdapter{

    private ComponentFive component;

    public MouseClicker(ComponentFive c){
        component = c;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Random random = new Random();

        int iR = Math.abs((e.getX() + e.getY() + 100) % 255);
        int iG = Math.abs((e.getX() + e.getY() + 200) % 255);
        int iB = Math.abs((e.getX() + e.getY()) % 255);

        Color randColor = new Color(iR, iG, iB);

        switch (e.getButton()){
            case 1:
                component.appendRectangle(10, 10, e.getPoint(), randColor, false);
                break;
            case 3:
                component.appendRectangle(10, 10, e.getPoint(), randColor, true);
                break;
            default:
                System.out.println("middle pressed");
                break;
        }

        component.repaint();
    }
}
