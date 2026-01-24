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
                                                       // экранная кнопка для следующего фото (через Action)
        JButton rBtn = new JButton(new PhotoAction(component, "назад", "Предыдущее фото", true));
                                                       // экранная кнопка для предыдущего фото (через Action)

        lBtn.setBounds(502, 625, 492, 43);
        rBtn.setBounds(10, 625, 492, 43);

        add(lBtn);
        add(rBtn);
    }
}

class ComponentSeven extends JComponent{
    String fileName; // хранение имени файла с текущей фотографией

    public ComponentSeven(){
        fileName = ""; // первоначально область под фото пустая

        InputMap imap = getInputMap(WHEN_IN_FOCUSED_WINDOW); // получение отображения ввода для компонента
        ActionMap amap = getActionMap(); // получение отображения действий для компонента

        imap.put(KeyStroke.getKeyStroke("RIGHT"), "next"); // привязка п-стрелки кнопки на ввод
                                                                          // по ключу next
        imap.put(KeyStroke.getKeyStroke("LEFT"), "previous"); // привязка  кнопки на ввод
                                                                             // по ключу previous

        amap.put("next", new PhotoAction(this, false)); // привязка деймтвия выбора следующего по next
        amap.put("previous", new PhotoAction(this, true)); // привязка деймтвия выбора предыдущего по previous
    }

    @Override
    public void paintComponent(Graphics g) {
        float k; // коэффициент сжатия изображения

        Image image = new ImageIcon( // получение текущей фотографии по пути их параметра fileName
                Objects.requireNonNull(
                        this.getClass().getResource(String.format("/photos/%s", fileName))
                )).getImage();

        k = (float) image.getWidth(null) / 980; // коэффициент - пропорция по ширине
        int imgW = (int) (image.getWidth(null) / k); // высота фото для окна по коэффициенту
        int imgH = (int) (image.getHeight(null) / k); // ширина фото для окна по коэффициенту
        int imgX = (FrameSeven.WIDTH - imgW) / 2; // расчёт координаты X для фото

        g.drawImage(image, imgX, 0, imgW, imgH, null); // отрисовка изображения
        g.setFont(new Font("Arial", Font.BOLD, 15)); // шрифт для названия фото
        g.setColor(Color.BLACK); // цвет для названия фото
        g.drawString(fileName, imgX, 600); // отрисовка названия фото
    }

    public void setFileName(String fN){ // метод установки нового имени текущего фото
        fileName = fN;
    }
}

class PhotoAction extends AbstractAction{ // класс-описание-перехватчик действия по смене фото
    ComponentSeven component; // компонент для которого экземпляр перехватчика
    private static String [] fileNames = {"Ozerki_5.jpg", "Ligovsky_prospect_24.jpg", "Leninsky_prospect_4.jpg", "Electrosila_15.jpg"};
                           // параметр класса - массив имён файлов с фото
    private int i; // счётчик номера текущего фото
    private boolean revers; // параметр направления перелистывания

    public PhotoAction(ComponentSeven c, String n, String d, boolean r){ // конструктор под экранную кнопку
        component = c;
        putValue(Action.NAME, n);
        putValue(Action.SHORT_DESCRIPTION, d);
        i = 0;
        revers = r;
    }

    public PhotoAction(ComponentSeven c, boolean r){ // конструктор под привязку к клавишам
        component = c;
        putValue(Action.NAME, "default");
        putValue(Action.SHORT_DESCRIPTION, "default");
        i = 0;
        revers = r;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        component.setFileName(fileNames[i % fileNames.length]); // установка нового текущего фото в компонент
        i = revers ? i == 0 ? fileNames.length - 1 : i - 1 : i + 1; // расчёт нового индекса для следующего
                                                                    // (после этого) фото
        component.repaint(); // перерисовка компонента
    }
}