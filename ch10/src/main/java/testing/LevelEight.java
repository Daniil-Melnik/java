package testing;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Comparator;
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
    private final static float RECT_D = 50;

    private ArrayList<TimedRectangle> rectangles = new ArrayList<>(List.of(
            new TimedRectangle(0, 0, RECT_D, RECT_D, Color.BLUE),
            new TimedRectangle(55, 0, RECT_D, RECT_D, Color.GREEN),
            new TimedRectangle(110, 0, RECT_D, RECT_D, Color.RED),
            new TimedRectangle(165, 0, RECT_D, RECT_D, Color.MAGENTA),
            new TimedRectangle(58, 102, RECT_D, RECT_D, Color.ORANGE)
    ));

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        rectangles.forEach((r) -> {
            g2.setColor(r.getColor());
            g2.draw(r);
            g2.fill(r);
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
        return String.format("%s %s %d", super.getX(), super.getY(), timeStamp);
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
}
