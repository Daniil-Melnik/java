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
import java.util.*;
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
        private ArrayList<GameButton> btns = new ArrayList<>(8);

        public GamePanel(){
            int i = 0;
            setLayout(new GridLayout(3, 3));
            setBackground(Color.GREEN);
            for (String s : gameLogic.getField()){
                if (!s.equals("*")){
                    btns.add(new GameButton(i / 3, i % 3, s));
                }
                i++;
            }

            rePaintButtons();
        }

        public void rePaintButtons(){
            for (int i = 0; i < 8; i++){
                remove(btns.get(i));
            }

            btns.sort((o1, o2) -> {
                int posObj = o2.posX * 3 + o2.posY;
                int posThis = o1.posX * 3 + o1.posY;
                return posThis - posObj;
            });

            for (int i = 0; i < 8; i++){
                add(btns.get(i));
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(GAME_PAN_H, GAME_PAN_W);
        }
    }

    private static class GameButton extends JButton implements Comparable<GameButton>{
        private int posX;
        private int posY;

        public GameButton(int x, int y, String val){
            super(val);
            posX = x;
            posY = y;
            addActionListener((e) -> gameLogic.move(this));
        }

        public void setPosition(int nX, int nY){
            posY = nY;
            posX = nX;
        }

        public int getIndex(){
            return posX*3 + posY;
        }

        public String getVal(){
            return super.getText();
        }

        @Override
        public int compareTo(GameButton o) {
            int posObj = o.posX * 3 + o.posY;
            int posThis = this.posX * 3 + this.posY;
            return posThis - posObj;
        }
    }

    private static class GameLogic{
        private String[] field = {"1", "2", "3", "4", "5", "6", "7", "8", "*"};
        private HashMap<Integer, HashSet<String>> enabledPositions = new HashMap<>();
        private ArrayList<String> currentEnPos;
        private int zeroIndex = 8;

        public GameLogic(){
            enabledPositions.put(0, new HashSet<>(List.of("1", "3")));
            enabledPositions.put(1, new HashSet<>(List.of("0", "2", "4")));
            enabledPositions.put(2, new HashSet<>(List.of("1", "5")));
            enabledPositions.put(3, new HashSet<>(List.of("0", "4", "6")));
            enabledPositions.put(4, new HashSet<>(List.of("1", "3", "5", "7")));
            enabledPositions.put(5, new HashSet<>(List.of("8", "4", "2")));
            enabledPositions.put(6, new HashSet<>(List.of("3", "7")));
            enabledPositions.put(7, new HashSet<>(List.of("6", "4", "8")));
            enabledPositions.put(8, new HashSet<>(List.of("7", "5")));
        }

        public void move(GameButton btn){
            int btnPos = btn.getIndex();
            if (enabledPositions.get(zeroIndex).contains(btnPos + "")){
                System.out.println("Можно!!!");
            } else {
                System.out.println("Нельзя");
            }
        }

        public String[] getField(){
            return field;
        }
    }
}
