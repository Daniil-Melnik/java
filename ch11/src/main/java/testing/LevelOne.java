/*
1. Освоить поведение поточной и граничной компоновки
- создать окно приложения
- добавить в его верхнюю часть панель с меню, реализовать закрытие окна и переключение между панелями компоновок
- создать панель с поточной компоновкой, которая по нажатию кнопки внизу будет добавлять квадраты в поточном режиме
- создать панель с граничной компоновкой
  - один квадрат
  - кнопки-переключатели (5 шт) по количеству положений в окне
- приспособить меню к переключению окон
*/

package testing;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LevelOne {

    private static LayoutPanel layoutPanel; // статические переменные для хранения панелей
    private static FlowPanel flowPanel;
    private static BorderPanel borderPanel;
    private static ButtonPanel buttonPanel;

    static void main() {
        EventQueue.invokeLater(() -> {
            FrameOneCh11 frame = new FrameOneCh11();
            frame.setVisible(true);
        });
    }

    static class FrameOneCh11 extends JFrame {

        public FrameOneCh11(){
            setLayout(new BorderLayout());
            setSize(700, 450);
            setTitle("LevelOne Ch 11");
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            setResizable(false);

            layoutPanel = new LayoutPanel();
            buttonPanel = new ButtonPanel();
            buttonPanel.setAccessForButtons(true);
            setJMenuBar(new MainMenuBar()); // установка панели с меню

            add(buttonPanel, BorderLayout.SOUTH);
            add(layoutPanel, BorderLayout.NORTH);

        }
    }

    // панель с кнопкаму иправления: добавление и направление граничной компоновки
    static class ButtonPanel extends JPanel{
        private final JRadioButton southRadio; // радио-кнопки
        private final JRadioButton northRadio;
        private final JRadioButton westRadio;
        private final JRadioButton eastRadio;

        JButton addBtn; // кнопка добавления квадрата

        public ButtonPanel(){
            setLayout(new FlowLayout()); // установка поточного менеджера компоновки
            setBorder(new EtchedBorder()); // установка границы блока кнопок
            addBtn = new JButton("Добавить"); // кнопка добавления квадрата
            addBtn.addActionListener((e) -> { // действие к кнопке обавления
                flowPanel.addRectangle(); // метод из поточной панели
            });
            add(addBtn);

            ButtonGroup buttonGroup = new ButtonGroup(); // группа для радио-кнопок

            southRadio = new JRadioButton("SOUTH"); // формирование кнопок с подписями
            northRadio = new JRadioButton("NORTH");
            westRadio = new JRadioButton("WEST");
            eastRadio = new JRadioButton("EAST");

            buttonGroup.add(southRadio); // добавление радио-кнопок в группу
            buttonGroup.add(northRadio);
            buttonGroup.add(westRadio);
            buttonGroup.add(eastRadio);

            southRadio.addActionListener(new BorderActionListener(BorderLayout.SOUTH)); // присвоение перехватчиков радио-кнопкам
            northRadio.addActionListener(new BorderActionListener(BorderLayout.NORTH));
            westRadio.addActionListener(new BorderActionListener(BorderLayout.WEST));
            eastRadio.addActionListener(new BorderActionListener(BorderLayout.EAST));

            JPanel radioPanel = new JPanel(); // панель под радио-кнопки
            radioPanel.setSize(350, 90);

            radioPanel.add(southRadio); // добавление в пнель радио-кнопок
            radioPanel.add(northRadio);
            radioPanel.add(westRadio);
            radioPanel.add(eastRadio);

            add(radioPanel); // добавление панели с радио-кнопками на панель с кнопками
        }

        public void setAccessForButtons(boolean accessType){ // установка доступности для кнопок: радио/добавление
            southRadio.setEnabled(accessType);
            northRadio.setEnabled(accessType);
            westRadio.setEnabled(accessType);
            eastRadio.setEnabled(accessType);

            addBtn.setEnabled(!accessType);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(700, 90);
        }
    }

    static class LayoutPanel extends JPanel{ // панель с панелями граничной и поточной (попеременный выбор)
        public LayoutPanel(){
            borderPanel = new BorderPanel(); // грничная панель
            flowPanel = new FlowPanel(); // поточная панель

            setLayout(new BorderLayout());
            add(flowPanel, BorderLayout.NORTH); // помещение  номинально разные точки для того, чтобы отрисовывались обе
            add(borderPanel, BorderLayout.SOUTH);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(500, 300);
        }

        public void setPanel(boolean type){ // метод для переключения панелей
            flowPanel.setVisible(!type);
            borderPanel.setVisible(type);
        }
    }

    static class BorderActionListener implements ActionListener{ // перехватчик для изменения границы в граничной панели
        private final String layout;

        public BorderActionListener(String l){
            layout = l;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            borderPanel.setBorder(layout);
        }
    }

    static class BorderPanel extends JPanel{ // граничная панель
        RectangleComponent rectangle = new RectangleComponent();
        public BorderPanel(){
            setLayout(new BorderLayout()); // установка граничного компоновщика
            add(rectangle); // добавление первичного прямоугольника
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(500, 300);
        }

        public void setBorder(String newLayout){ // обновление положения фигуры при изменении границы
            remove(rectangle);
            add(rectangle, newLayout);
            revalidate();
        }
    }


    static class FlowPanel extends JPanel{ // поточная панель
        public FlowPanel(){
            setLayout(new FlowLayout()); // установка поточного компоновщика
        }

        public void addRectangle(){
            add(new RectangleComponent()); // добавления прямоугольника
            revalidate(); // перерасчет координат
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(500, 300);
        }
    }

    static class RectangleComponent extends JPanel{ // цветной прямоугольник
        public RectangleComponent(){
            setBackground(Color.BLUE);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(50, 50);
        }
    }

    static class MainMenuBar extends JMenuBar{ // класс-контейнер меню
        public MainMenuBar(){
            JMenu menuFile = new JMenu("Файл"); // меню "Файл"
            JMenu menuLayout = new JMenu("Компоновка"); // меню "Компоновка" - переключение панелей компоновки

            JMenuItem exitItem = new JMenuItem(new ExitAction()); // конструирование пункта по действию
            menuFile.add(exitItem);
            menuFile.addSeparator(); // добавление разделителей
            menuFile.add(new AboutAction()); // добавление пункта в виде сразу действия

            menuLayout.add(new JMenuItem(new LayoutAction(false))); // Поточная компоновка
            menuLayout.add(new JMenuItem(new LayoutAction(true))); // Граничная компоновка

            add(menuFile); // добавление меню в панель меню
            add(menuLayout);
        }
    }

    static class LayoutAction extends AbstractAction{ // действие для формирования пунктов компоновки
        boolean layoutType;

        public LayoutAction( boolean t){
            layoutType = t;
            putValue(Action.NAME, layoutType ? "Граничная" : "Поточная");
        }

        public LayoutAction(){
            this(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                layoutPanel.setPanel(layoutType); // переключение панелей
                buttonPanel.setAccessForButtons(layoutType); // установка доступности кнопок управления
            } catch (NullPointerException ex) {
                System.out.println(ex.getMessage());
            }

        }
    }

    static class ExitAction extends AbstractAction{ // действие по выходу из приложения

        public ExitAction(){
            putValue(Action.NAME, "Выход");
            putValue(Action.SHORT_DESCRIPTION, "Выйти из приложения");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());

            if (JOptionPane.showConfirmDialog // диалоговое окно с запросом
                    (frame, "Выйти?", "Выход", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_OPTION){
                System.exit(0);
            }
        }
    }

    static class AboutAction extends AbstractAction{ // действие по информации о программе
        public AboutAction(){
            putValue(Action.NAME, "О программе");
            putValue(Action.SHORT_DESCRIPTION, "Информация о программе");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog( // информационное окно
                    (JFrame) SwingUtilities.getWindowAncestor ((Component) e.getSource()),
                    "Программа тестирования поточной и граничной компоновки"
            );
        }
    }
}