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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

public class LevelOne {
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

            LayoutPanel layoutPanel = new LayoutPanel();
            ButtonPanel buttonPanel = new ButtonPanel(layoutPanel);
            setJMenuBar(new MainMenuBar(layoutPanel));

            add(buttonPanel, BorderLayout.SOUTH);
            add(layoutPanel, BorderLayout.NORTH);

        }
    }

    static class ButtonPanel extends JPanel{
        public ButtonPanel(LayoutPanel p){
            setLayout(new FlowLayout());
            setBackground(Color.MAGENTA);
            JButton addBtn = new JButton("Добавить");
            addBtn.addActionListener((e) -> {
                p.getFlowPanel().addRectangle();
                p.getFlowPanel().repaint();
                System.out.println("Asssa");
            });
            add(addBtn);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(700, 90);
        }
    }

    static class LayoutPanel extends JPanel{
        BorderPanel borderPanel = new BorderPanel();
        FlowPanel flowPanel = new FlowPanel();

        public LayoutPanel(){
            setLayout(new BorderLayout());
            setBackground(Color.BLACK);
            add(flowPanel, BorderLayout.NORTH);
            add(borderPanel, BorderLayout.SOUTH);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(500, 300);
        }

        public void setPanel(boolean type){
            flowPanel.setVisible(!type);
            borderPanel.setVisible(type);
            System.out.println(type + " " + !type);
        }

        public BorderPanel getBorderPanel(){
            return borderPanel;
        }

        public FlowPanel getFlowPanel(){
            return flowPanel;
        }
    }

    static class BorderPanel extends JPanel{
        public BorderPanel(){
            setLayout(new BorderLayout());
            setBackground(Color.RED);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(500, 300);
        }
    }


    static class FlowPanel extends JPanel{
        private ArrayList<RectangleComponent> rectangles;
        public FlowPanel(){
            setLayout(new FlowLayout());
            rectangles = new ArrayList<>();
            rectangles.add(new RectangleComponent());
            rectangles.add(new RectangleComponent());
            rectangles.add(new RectangleComponent());
            Iterator<RectangleComponent> rectIterator = rectangles.iterator();
            while (rectIterator.hasNext()){
                add(rectIterator.next());
            }
            setBackground(Color.GREEN);
        }

        public void addRectangle(){
            rectangles.add(new RectangleComponent());
            System.out.println("added");
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(500, 300);
        }
    }

    static class RectangleComponent extends JPanel{
        public RectangleComponent(){
            setBackground(Color.BLUE);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(50, 50);
        }
    }

    static class MainMenuBar extends JMenuBar{
        public MainMenuBar(LayoutPanel panel){
            JMenu menuFile = new JMenu("Файл");
            JMenu menuLayout = new JMenu("Компоновка");

            JMenuItem exitItem = new JMenuItem(new ExitAction());
            menuFile.add(exitItem);
            menuFile.addSeparator();
            menuFile.add(new AboutAction());

            menuLayout.add(new JMenuItem(new LayoutAction(panel, false)));
            menuLayout.add(new JMenuItem(new LayoutAction(panel, true)));

            add(menuFile);
            add(menuLayout);
        }
    }

    static class LayoutAction extends AbstractAction{
        LayoutPanel panel; // панель с двумя компонентами разной видимости
        boolean layoutType;

        public LayoutAction(LayoutPanel c, boolean t){
            panel = c;
            layoutType = t;
            putValue(Action.NAME, layoutType ? "Граничная" : "Поточная");
        }

        public LayoutAction(){
            this(null, false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                panel.setPanel(layoutType);
                // panel.revalidate();
            } catch (NullPointerException ex) {
                System.out.println(ex.getMessage());
            }

        }
    }

    static class ExitAction extends AbstractAction{

        public ExitAction(){
            putValue(Action.NAME, "Выход");
            putValue(Action.SHORT_DESCRIPTION, "Выйти из приложения");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());

            if (JOptionPane.showConfirmDialog
                    (frame, "Выйти?", "Выход", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_OPTION){
                System.exit(0);
            }
        }
    }

    static class AboutAction extends AbstractAction{
        public AboutAction(){
            putValue(Action.NAME, "О программе");
            putValue(Action.SHORT_DESCRIPTION, "Информация о программе");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(
                    (JFrame) SwingUtilities.getWindowAncestor ((Component) e.getSource()),
                    "Программа тестирования поточной и граничной компоновки"
            );
        }
    }
}




