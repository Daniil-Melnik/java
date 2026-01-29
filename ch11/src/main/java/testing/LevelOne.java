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
            setSize(500, 300);
            setTitle("LevelOne Ch 11");
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

            JMenuBar menuBar = new JMenuBar();

            JMenu menuFile = new JMenu("Файл");
            JMenu menuLayout = new JMenu("Компоновка");

            JMenuItem exitItem = new JMenuItem(new ExitAction());
            menuFile.add(exitItem);
            menuFile.addSeparator();
            menuFile.add(new AboutAction());

            menuLayout.add(new JMenuItem(new LayoutAction(null, false)));
            menuLayout.add(new JMenuItem(new LayoutAction(null, true)));

            menuBar.add(menuFile);
            menuBar.add(menuLayout);

            setJMenuBar(menuBar);
        }
    }

    static class LayoutAction extends AbstractAction{
        JComponent component; // панель с двумя компонентами разной видимости
        boolean layoutType;

        public LayoutAction(JComponent c, boolean t){
            component = c;
            layoutType = t;
            putValue(Action.NAME, layoutType ? "Граничная" : "Поточная");
        }

        public LayoutAction(){
            this(null, false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                component.setLayout(layoutType ? new BorderLayout() : new FlowLayout());
                component.revalidate();
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




