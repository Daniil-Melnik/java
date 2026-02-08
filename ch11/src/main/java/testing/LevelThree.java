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

// генерация случайной позиции
// с учетом позиции звезда и картой

// меню загрузки и сохранения игры

package testing;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class LevelThree {
    private static final int FRAME_H = 415;
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
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            setSize(FRAME_W, FRAME_H);

            gameLogic = new GameLogic();
            gamePanel = new GamePanel();
            add(gamePanel, BorderLayout.SOUTH);

            setJMenuBar(new MainMenuBar());
        }
    }

    private static class GamePanel extends JPanel{
        public static Map<String, Color> colors = Map.of(
                "1", new Color(85,85,255),
                "2", new Color(254,1,154),
                "3", new Color(57,255,20),
                "4", new Color(188,19,254),
                "5", new Color(4,217,255),
                "6", new Color(248,0,0),
                "7", new Color(204,255,0),
                "8", new Color(255,164,32)
        );

        public GamePanel(){
            int i = 0;
            setLayout(new GridLayout(3, 3));
            rePaintButtons();
        }

        public void rePaintButtons(){
            int i = 0;

            removeAll();
            revalidate();
            repaint();

            GameButton btn;

            for (String s : gameLogic.getField()){
                if (!s.equals("*")){
                    btn = new GameButton(i / 3, i % 3, s);
                    btn.setFont(new Font("Arial", Font.BOLD, 64));
                    btn.setBackground(colors.get(s));
                    add(btn);
                } else {
                    add(new JPanel());
                }

                i++;
            }
            revalidate();
            repaint();
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
            addActionListener((e) -> {
                gameLogic.move(this);
                gamePanel.rePaintButtons();
                if (gameLogic.isWin()){
                    JOptionPane.showMessageDialog(mainFrame, "Игра выиграна!");
                }
            });
            setBackground(Color.BLUE);
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
        private String[] field = {"1", "2", "3", "4", "5", "6", "7", "*", "8"};
        private String[] winField = {"1", "2", "3", "4", "5", "6", "7", "8", "*"};
        private final HashMap<Integer, HashSet<String>> enabledPositions = new HashMap<>();
        private int zeroIndex = 7;

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
            System.out.println(btn.getIndex());
            if (enabledPositions.get(zeroIndex).contains(btnPos + "")){
                System.out.println(zeroIndex + " " + btnPos);
                btn.setPosition(zeroIndex / 3, zeroIndex % 3);
                field[zeroIndex] = btn.getVal();
                field[btnPos] = "*";
                zeroIndex = btnPos;
            }
        }

        public void shuffleField(){
            zeroIndex = 0;

            Collections.shuffle(Arrays.asList(field));
            setZeroIndexByField();
        }

        public boolean isWin(){
            return Arrays.equals(field, winField);
        }

        public String[] getField(){
            return field;
        }

        public void setField(String[] f) {
            field = f;
        }

        public void setZeroIndexByField(){
            zeroIndex = 0;
            while (!field[zeroIndex].equals("*")) zeroIndex++;
        }
    }

    private static class FileUtil{

        public static String[] getFieldFromFile(File file) throws FileNotFoundException {

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String [] newField = new String[9];

            try {
                for (int i = 0; i < 9; i++){
                    newField[i] = reader.readLine();
            }
            } catch (IOException e){
                JOptionPane.showMessageDialog(mainFrame, "Некорректный файл", "Ошибка файла", JOptionPane.ERROR_MESSAGE);
            }

            return newField;
        }
    }

    private static class MainMenuBar extends JMenuBar{

        public MainMenuBar(){
            JMenu mainMenu = new JMenu("Файл");
            JMenu gameMenu = new JMenu("Игра");

            JMenu newGameMenu = new JMenu("Новая");

            JMenuItem exitItem = new JMenuItem("Выход");
            JMenuItem aboutItem = new JMenuItem("О программе");

            JMenuItem newGameItem = new JMenuItem("Случайная");
            JMenuItem saveGameItem = new JMenuItem("Сохранить");
            JMenuItem loadGameItem = new JMenuItem("Загрузить");

            newGameItem.addActionListener((e) -> {
                gameLogic.shuffleField();
                gamePanel.rePaintButtons();
            });

            exitItem.addActionListener((e) -> System.exit(0));
            aboutItem.addActionListener((e) -> JOptionPane.showMessageDialog(mainFrame, "Игра Пятнашка на 9 клеток"));

            saveGameItem.addActionListener((e) -> {

            });

            loadGameItem.addActionListener((e) -> {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setFileFilter(new FileNameExtensionFilter("game files", "txt"));
                if (chooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION){
                    try {
                        gameLogic.setField(FileUtil.getFieldFromFile(chooser.getSelectedFile()));
                        gameLogic.setZeroIndexByField();
                        gamePanel.rePaintButtons();
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(mainFrame, "Файл пропал!", "Ошибка файла", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            mainMenu.add(exitItem);
            mainMenu.add(aboutItem);

            newGameMenu.add(newGameItem);
            newGameMenu.add(loadGameItem);

            gameMenu.add(newGameMenu);
            gameMenu.addSeparator();
            gameMenu.add(saveGameItem);

            add(mainMenu);
            add(gameMenu);
        }
    }
}
