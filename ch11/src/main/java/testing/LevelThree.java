/*
3. Продолжить освоение граничной компоновки на примере игры "пятнашка"
- сформировать фрейм, поместить в него панель 3*3
- заполнить панель 8-ю кнопками, одну клетку оставить свободной
- реализовать логику функционирования игры
- добавить строку меню с возможностью генерации новой обстановки, загрузки и сохранения в файл

= ЛОГИКА
- реализовать проверку возможности хода при нажатии на кнопку
- реализовать перемещение на массиве
- реализовать перерисовку по полю

*/

// добавить кнопки и логику

package testing;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class LevelThree {
    private static final int FRAME_H = 400;
    private static final int FRAME_W = 360;

    private static final int GAME_PAN_H = 360;
    private static final int GAME_PAN_W = 360;

    private static MainFrame mainFrame;
    private static GamePanel gamePanel;
    private static GameLogic gameLogic;

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

            gameLogic = new GameLogic();
            gamePanel = new GamePanel();
            add(gamePanel, BorderLayout.SOUTH);
        }
    }

    private static class GamePanel extends JPanel{

        public GamePanel(){
            int i = 0;
            setLayout(new GridLayout(3, 3));
            setBackground(Color.GREEN);
            for (String s : gameLogic.getField()){
                add(!s.equals("*") ? new GameButton(i / 3, i % 3, s) : new JPanel());
                i++;
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(GAME_PAN_H, GAME_PAN_W);
        }
    }

    private static class GameButton extends JButton{
        private int posX;
        private int posY;

        public GameButton(int x, int y, String val){
            super(val);
            posX = x;
            posY = y;
        }

        public void setPosition(int nX, int nY){
            posY = nY;
            posX = nX;
        }

        public String getVal(){
            return super.getText();
        }
    }

    private static class GameLogic{
        private String[] field = {"1", "2", "3", "4", "5", "6", "7", "8", "*"};
        private HashMap<String, HashSet<String>> enabledPositions = new HashMap<>();
        private ArrayList<String> currentEnPos;
        private int zeroIndex = 8;

        public GameLogic(){
            enabledPositions.put("0", new HashSet<>(List.of("1", "3")));
            enabledPositions.put("1", new HashSet<>(List.of("0", "2", "4")));
            enabledPositions.put("2", new HashSet<>(List.of("1", "5")));
            enabledPositions.put("3", new HashSet<>(List.of("0", "4", "6")));
            enabledPositions.put("4", new HashSet<>(List.of("1", "3", "5", "7")));
            enabledPositions.put("5", new HashSet<>(List.of("8", "4", "2")));
            enabledPositions.put("6", new HashSet<>(List.of("3", "7")));
            enabledPositions.put("7", new HashSet<>(List.of("6", "4", "8")));
            enabledPositions.put("8", new HashSet<>(List.of("7", "5")));
        }

        public void move(){

        }

        public String[] getField(){
            return field;
        }
    }
}
