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
            JMenuItem exitItem = new JMenuItem(new ExitAction(this));
            menuBar.add(exitItem);

            add(menuBar, BorderLayout.NORTH);
        }
    }

    static class ExitAction extends AbstractAction{

        FrameOneCh11 frame;

        public ExitAction(FrameOneCh11 f){
            frame = f;
            putValue(Action.NAME, "Выход");
            putValue(Action.SHORT_DESCRIPTION, "Выйти из приложения");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (JOptionPane.showConfirmDialog
                    (frame, "Выйти?", "Выход", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_OPTION){
                System.exit(0);
            }
        }
    }
}




