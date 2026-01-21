/*
3. Освоить источник события - экранные кнопки
- создать фрейм, добавить в него компоненты:
  - с текстом определённого цвета
  - с экранной кнопкой 1
  - с экранной кнопкой 2
- фрейм расширяет класс WindowAdapter с переопределением метода закрытия окна на вопрос

- реализовать несколько приемников событий от экранной кнопки 1
  - отдельный внутренний класс реализующий ActionListener
  - локальный анонимный класс
  - лямбда выражение
  - привязкой объекта Action

- реализовать действие по смене цвета написанного текста

*/

package testing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;

public class LevelThree {
    public static void main(String[] args){
        EventQueue.invokeLater(FrameThree::new
        );
    }
}

class FrameThree extends JFrame{
    public FrameThree(){
        addWindowListener(new WindowTerminator()); // подключение класса с действиями
                                                   // на основе адаптера (далёкое расширение WindowListener)

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // чтобы не закрывался только от нажатия крестика
                                                              // закрывается программно из windowClosing при
                                                              // получении утвердительного ответа в диалоговом окне

        setSize(500, 200);

        setLayout(null); // включение абсолютного позиционирования

        RectangleComponent rComponent = new RectangleComponent(); // подключение компонента с прямоугольником
        rComponent.setBounds(0, 0, 500, 100); // установка абсолютной позиции в окне
        add(rComponent); // добавление компонента в окно

        ButtonPanel bPanel = new ButtonPanel(rComponent);
        bPanel.setBounds(0, 100, 500, 100);
        add(bPanel);

        setResizable(true);
        setVisible(true);
    }
}

class RectangleComponent extends JComponent{ // компонент с прямоугольником
                                             // расширение JComponent

    // параметры класса компонента. Применяются для изменения состояния элементов компонента
    // (задание новых свойств компонентов через эти параметры) -> применение repaint()
    private Color color; // параметр цвета окантовки
    private Color fillColor; // параметр цвета заливки
    private Stroke stroke; // параметр толщины пера

    public RectangleComponent(Color c, Color fillC, Stroke s){
        // конструктор компонента с параметрами
        color = c;
        fillColor = fillC;
        stroke = s;
    }

    public  RectangleComponent(){ // конструктор без параметров
        this(Color.BLACK, Color.BLUE, new BasicStroke(5));
    }

    // в свою очередь repaint() вызывает paintComponent()
    @Override
    public void paintComponent(Graphics g){ // определение порядка отрисовки окна
        Graphics2D g2 = (Graphics2D) g;
        Rectangle2D rectangle = new Rectangle2D.Float(10, 10, 460, 76);
        g2.setColor(color); // принятие цвета окантовки по параметру
        g2.setStroke(stroke); // принятие толщины пера по параметру
        g2.draw(rectangle);
        g2.setColor(fillColor); // принятие цвета заливки из параметров класса
        g2.fill(rectangle);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500,100);
    }

    public void setColor(Color color) { // сеттеры для изменения состояния параметров цветов
        this.color = color;
    }

    public void setFillColor(Color fillColor){
        this.fillColor = fillColor;
    }
}

class ButtonPanel extends JPanel{ // панель с кнопками
    public ButtonPanel(RectangleComponent rComponent){

        JButton fillButton = new JButton("Поменять заливку"); // создание кнопки изменения заливки

        fillButton.addActionListener(new FillActionListener(rComponent)); // применение в качестве перехватчика
                                                                          // событий экземпляра отдельного
                                                                          // класса-реализации ActionListener

        fillButton.addActionListener(new ActionListener() { // примененение в качестве перехватчика
                                                            // локального анонимного класса
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Изменить заливку");
            }
        });


        JButton edgButton = new JButton(new EdgAction
                ("Изменить окантовку", rComponent)); // создание кнопки с передачей
                                                           // экземпляра AbstractAction с настройками

        edgButton.addActionListener((e) -> { // добавление перехватчика через лямбда выражение
            System.out.println("Изменить окантовку");
        });

        add(fillButton); // добавление кнопок на панель
        add(edgButton);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 100);
    }
}

class WindowTerminator extends WindowAdapter{ // адаптер для окна
                                              // переопределены действия на закрытие и открытие окна,
                                              // остальные реализованы пустыми в суперклассе WindowAdapter
    @Override
    public void windowClosing(WindowEvent e){ // перехват закрытия окна
        if (JOptionPane.showConfirmDialog(e.getWindow(), "Закрыть?") == 0){
            System.exit(0); // прекратить работу при положительном ответе (0)
        }
    }

    @Override
    public void windowOpened(WindowEvent e){ // перехват открытия окна
        System.out.println("Window opened!");
        // JOptionPane.showMessageDialog(e.getWindow(), "Окно открыто!");
    }
}

class FillActionListener implements ActionListener{ // отдельный класс-реализация ActionListener для передачи
                                                    // экземпляра при добавлении перехватчика события

    private static Color [] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE, Color.MAGENTA};

    private RectangleComponent rComponent; // хранит в себе ссылку на конкретный объект компонента
                                           // на который будет воздействовать конкретный экземпляр перехватчика

    private int i; // поле класса отвечающее за выбор цвета

    public FillActionListener(RectangleComponent rComp){
        rComponent = rComp;
        i = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        rComponent.setFillColor(colors[i%colors.length]); // установка в компонент нового цвета заливки
        rComponent.repaint(); // перерисовка с новым цветом завливки
        i++;
    }
}

class EdgAction extends AbstractAction{ // создание класса-расширения Action для построения по его экземпляру кнопки
    private static Color [] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE, Color.MAGENTA}; // поле класса
                                                                                                        // цвета

    private int i;
    private RectangleComponent rComp; // хранит ссылку на компонент, на который влияет

    public EdgAction(String name, RectangleComponent rC){
        putValue(Action.NAME, name); // добавление имени кнопки (текст кнопки)
        putValue(Action.SHORT_DESCRIPTION, "Подсказка"); // добавление всплывающей подсказки
        rComp = rC;
        i = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        rComp.setColor(colors[i % colors.length]); // установка в компонент нового цвета окантовки
        i++;
        rComp.repaint(); // перерисовка с новым цветом окантовки
    }
}
