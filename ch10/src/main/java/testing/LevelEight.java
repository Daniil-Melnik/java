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

class ComponentEight extends JComponent{
    public final static float RECT_D = 50;

    public ComponentEight(){
        addMouseMotionListener(new RectangleMouse(this));
    }

    private ArrayList<TimedRectangle> rectangles = new ArrayList<>(List.of(
            new TimedRectangle(0, 0, RECT_D, RECT_D, Color.BLUE),
            new TimedRectangle(55, 0, RECT_D, RECT_D, Color.GREEN),
            new TimedRectangle(110, 0, RECT_D, RECT_D, Color.RED),
            new TimedRectangle(165, 0, RECT_D, RECT_D, Color.MAGENTA),
            new TimedRectangle(120, 25, RECT_D, RECT_D, Color.ORANGE)
    ));

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 4; i >= 0; i--){
            TimedRectangle r = rectangles.get(i);
            g2.setColor(r.getColor());
            g2.draw(r);
            g2.fill(r);
        }
    }

    public TimedRectangle getRectanleWithPoint(Point p){
        TimedRectangle result = null;
        Iterator<TimedRectangle> iterator = rectangles.iterator();
        TimedRectangle t = null;
        while ((result == null) && iterator.hasNext()){
            t = iterator.next();
            if (t.isInRectangle(p)) result = t;
        }
        return result;
    }

    public void sortRecnangels(){
        rectangles.sort(new Comparator<TimedRectangle>() {
            @Override
            public int compare(TimedRectangle o1, TimedRectangle o2) {
                long tSt1 = o1.getTimeStamp();
                long tSt2 = o2.getTimeStamp();
                return tSt2 - tSt1 < 0 ? -1 : tSt1 - tSt2 > 0 ? 1 : 0;
            }
        });
    }
}

class TimedRectangle extends Rectangle2D.Float{
    private long timeStamp;
    private Color color;

    public TimedRectangle(float x, float y, float w, float h, Color c){
        super(x, y, w, h);
        timeStamp = System.currentTimeMillis();
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

    public boolean isInRectangle(Point p){
        float pX = (float) p.getX();
        float pY = (float) p.getY();

        float rX = (float) this.getX();
        float rY = (float) this.getY();

        return ((pX >= rX) && (pX <= rX + getWidth()) && (pY >= rY) && (pY <= rY + getHeight()));
    }

    public void setCoordinates(Point p){
        float newX = (float) (p.getX() - ComponentEight.RECT_D / 2);
        float newY = (float) (p.getY() - ComponentEight.RECT_D / 2);
        super.setRect(newX, newY, ComponentEight.RECT_D, ComponentEight.RECT_D);
        setTimeStamp(System.currentTimeMillis());
    }
}

class RectangleMouse extends MouseMotionAdapter{
    private ComponentEight component;

    public RectangleMouse(ComponentEight c){
        component = c;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point point = e.getPoint();
        TimedRectangle t = component.getRectanleWithPoint(point);
        if (t != null){
            t.setCoordinates(point);
            component.sortRecnangels();
            component.repaint();
        }
    }
}
