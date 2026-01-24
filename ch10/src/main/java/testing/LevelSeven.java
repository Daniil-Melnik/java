/*
8. Освоить вставку изображений в оконное приложение
- создать фрейм, добавить в него компонент для рисования
- присопособить компонент для отрисовки одной из нескольких картинок
- добавить панель с двумя эеранными кнопками
- добавить класс-перехватчик собыития исмены картинки в компоненте
- присвоить действие смены рисунка экранным кнопкам и двум стрелкам клавиатуры
*/

package testing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class LevelSeven {
    public static void main(String ... args){
        EventQueue.invokeLater(() -> {
            FrameSeven frame = new FrameSeven();
            frame.setVisible(true);
        });
    }
}

class FrameSeven extends JFrame{

    public static final int WIDTH = 1020;
    public static final int HEIGHT = 710;

    public FrameSeven(){
        setSize(WIDTH, HEIGHT);
        setLayout(null);
        setTitle("LevelSeven");
        addWindowListener(new WindowTerminator());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        ComponentSeven component = new ComponentSeven();
        component.setBounds(0, 10, 1020, 620);
        add(component);

        JButton lBtn = new JButton(new PhotoAction(component, "далее", "Следующее фото", false));
        JButton rBtn = new JButton(new PhotoAction(component, "назад", "Предыдущее фото", true));

        lBtn.setBounds(502, 625, 492, 43);
        rBtn.setBounds(10, 625, 492, 43);

        add(lBtn);
        add(rBtn);
    }
}

class ComponentSeven extends JComponent{
    String fileName;

    public ComponentSeven(){
        fileName = "";
        InputMap imap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap amap = getActionMap();

        imap.put(KeyStroke.getKeyStroke("RIGHT"), "next");
        imap.put(KeyStroke.getKeyStroke("LEFT"), "previous");

        amap.put("next", new PhotoAction(this, false));
        amap.put("previous", new PhotoAction(this, true));
    }

    @Override
    public void paintComponent(Graphics g) {
        float k;

        Image image = new ImageIcon(
                Objects.requireNonNull(
                        this.getClass().getResource(String.format("/photos/%s", fileName))
                )).getImage();

        k = (float) image.getWidth(null) / 980;
        int imgW = (int) (image.getWidth(null) / k);
        int imgH = (int) (image.getHeight(null) / k);
        int imgX = (FrameSeven.WIDTH - imgW) / 2;

        System.out.println(imgH + " " + imgW);

        g.drawImage(image, imgX, 0, imgW, imgH, null);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.setColor(Color.BLACK);
        g.drawString(fileName, imgX, 600);
    }

    public void setFileName(String fN){
        fileName = fN;
    }
}

class PhotoAction extends AbstractAction{
    ComponentSeven component;
    private static String [] fileNames = {"Ozerki_5.jpg", "Ligovsky_prospect_24.jpg", "Leninsky_prospect_4.jpg", "Electrosila_15.jpg"};
    private int i;
    private boolean revers;

    public PhotoAction(ComponentSeven c, String n, String d, boolean r){
        component = c;
        putValue(Action.NAME, n);
        putValue(Action.SHORT_DESCRIPTION, d);
        i = 0;
        revers = r;
    }

    public PhotoAction(ComponentSeven c, boolean r){
        component = c;
        putValue(Action.NAME, "default");
        putValue(Action.SHORT_DESCRIPTION, "default");
        i = 0;
        revers = r;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        component.setFileName(fileNames[i % fileNames.length]);
        i = revers ? i == 0 ? i = fileNames.length - 1 : i - 1 : i + 1;
        component.repaint();
    }
}