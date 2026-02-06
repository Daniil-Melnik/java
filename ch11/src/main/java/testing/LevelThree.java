/*
3. Продолжить освоение граничной компоновки на примере игры "пятнашка"
- сформировать фрейм, поместить в него панель 3*3
- заполнить панель 8-ю кнопками, одну клетку оставить свободной
- реализовать логику функционирования игры
- добавить строку меню с возможностью генерации новой обстановки, загрузки и сохранения в файл
*/

// добавить кнопки и логику

package testing;

import javax.swing.*;
import java.awt.*;

public class LevelThree {
    private static final int FRAME_H = 400;
    private static final int FRAME_W = 360;

    private static final int GAME_PAN_H = 360;
    private static final int GAME_PAN_W = 360;

    private static MainFrame mainFrame;
    private static GamePanel gamePanel;

    public static void main(String... args){
        EventQueue.invokeLater(() -> {
            mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }

    private static class MainFrame extends JFrame{
        public MainFrame(){
            setLayout(new BorderLayout());
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(FRAME_W, FRAME_H);

            gamePanel = new GamePanel();
            add(gamePanel, BorderLayout.SOUTH);
        }
    }

    private static class GamePanel extends JPanel{
        public GamePanel(){
            setLayout(new GridLayout());
            setBackground(Color.GREEN);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(GAME_PAN_H, GAME_PAN_W);
        }
    }
}
