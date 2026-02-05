/*
2. Освоить средства граничной компоновки, создать простой оконный калькулятор
- сформировать фрейм, поделить его на 2-е части: экран калькулятора и сетка кнопок
- присвоить каждой кнопке в сетке действие по наполнению строки выражения
- продублировать эти же действия на нажатия клавиш
- проработать экран калькулятора на отрисовку строки
- реализовать очистку экрана и вывод операции
- при недопустимых операциях - вывод окна с ошибкой
*/

// строка 250

package testing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelTwo {
    private static MainFrame mainFrame; // главное окно
    private static BtnPanel btnPanel;   // панель с кнопками (сеточная компоновка)
    private static CalcPanel calcPanel; // панель с компонентом отрисовки выражения и результата
    private static ScreenComponent screenComponent; // компонент отрисовки выражения и результата

    private static final int FRAME_W = 300; // размеры панельных компонентов интерфейса
    private static final int FRAME_H = 295;

    private static final int BTN_PANEL_W = 300;
    private static final int BTN_PANEL_H = 140;

    private static final int CALC_PANEL_W = 300;
    private static final int CALC_PANEL_H = 90;

    private static final String[] BTN_S = {"1", "2" , "3", "+", "4", "5", "6", "-", // массив
                                           "7", "8", "9", "/" , "0", "C", "=", "*"};

    private static final String[] KEYSTROKES = {"NUMPAD1", "NUMPAD2", "NUMPAD3", "ADD", "NUMPAD4", "NUMPAD5",
                                                "NUMPAD6", "SUBTRACT", "NUMPAD7", "NUMPAD8", "NUMPAD9",
                                                "DIVIDE", "NUMPAD0", "BACK_SPACE", "ENTER", "MULTIPLY"
                                               }; // коды нажимаемых клавиш для формирования KeyStroke
                                                  // под ActionMap и InputMap

    private static final String[] BTN_SG = {"=", "/" , "-", "*", "+"}; // операции

    private static final int SYMBOLS_PER_STR = 13; // кол-во символов выражения в строке при 32-м кегле

    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }

    private static class MainFrame extends JFrame{
        public MainFrame(){
            setLayout(new BorderLayout());
            setSize(FRAME_W, FRAME_H);
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            setResizable(false);

            btnPanel = new BtnPanel(); // введение панели с кнопками
            add(btnPanel, BorderLayout.SOUTH); // внизу

            calcPanel = new CalcPanel(); // введение панели-экрана
            add(calcPanel, BorderLayout.NORTH); // вверху
            setJMenuBar(new MainMenuBar());
        }
    }

    private static class BtnPanel extends JPanel{ // класс панели с кнопками
        private static JButton[] btns = new JButton[16]; // 16 кнопок (цифры, операции)
        private static BtnAction[] actions = new BtnAction[16]; // 16 действий (для кнопок и клавиш)

        public BtnPanel(){
            setLayout(new GridLayout(4, 4)); // установка сеточной компоновки 4*4 секции
            int i = 0;

            for (String s : BTN_S) { // создание кнопок
                actions[i] = new BtnAction(s);
                btns[i] = new JButton(actions[i]);
                btns[i].setFont(new Font("Courier New", Font.BOLD, 16));
                add(btns[i]); // последовательная набивка сетки кнопками
                i++;
            }
            createKeyIAMap(); // установка соответсвия клавишам действий
            setEnableEqual(false); // логика
        }

        public void setEnableEqual(boolean b){ // логика-блокировка кнопки и действия равенства
            btns[14].setEnabled(b);
            actions[14].putValue("enabled", b);
        }

        public void setEnableBtns(boolean b){ // блокировка/разблокировка всех кроме равенства и очистки
            for (int i = 0; i < 16; i++){
                btns[i].setEnabled(b);
                actions[i].putValue("enabled", b);
            }
            btns[13].setEnabled(true);
            btns[14].setEnabled(true);

            actions[13].putValue("enabled", true); // отдельный ключ для "блокировки" - enabled
            actions[14].putValue("enabled", true);
        }

        private void createKeyIAMap(){ // метод формирования отображения действие-ключ-клавиша
            ActionMap amap = this.getActionMap();
            InputMap imap = this.getInputMap(WHEN_IN_FOCUSED_WINDOW);

            for (int i = 0; i < 16; i++){
                imap.put(KeyStroke.getKeyStroke(KEYSTROKES[i]), BTN_S[i]);
                amap.put(BTN_S[i], actions[i]);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(BTN_PANEL_W, BTN_PANEL_H);
        }
    }

    private static class CalcPanel extends JPanel{ // панель-экран вычислений
        public CalcPanel(){
            setLayout(new BorderLayout()); // граничная компоновка
            setBackground(Color.WHITE); // фон - белый
            screenComponent = new ScreenComponent(); // добаление компонента отрисовки выражения и результата
            add(screenComponent);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(CALC_PANEL_W, CALC_PANEL_H);
        }
    }

    private static class BtnAction extends AbstractAction{ // действие для кнопки
        public BtnAction(String s){
            putValue(Action.NAME, s);
            putValue(Action.SHORT_DESCRIPTION, s);
            putValue("enabled", true); // отдельный ключ для "блокированности"
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                if ((boolean) this.getValue("enabled")) // разблокировано => можно выполнять
                    screenComponent.setStr(this.getValue(Action.NAME).toString()); // обновить строку на экране
            }catch (MaxExpLengthExeption ex){
                JOptionPane.showMessageDialog(mainFrame, ex.getMessage()); // строка слишком длинная => выдача сообщения
                btnPanel.setEnableBtns(false); // и блокировка всего кроме равенства и удаления
                screenComponent.setK(2);
            }

        }
    }

    private static class ScreenComponent extends JComponent{ // компонент отрисовки цифр и зопераций
        private String str = ""; // строка выражения
        private String strRes = ""; // строка результата
        private boolean lastInSGwM; // флаг ввода знака операции
        private int k = 1; // делитель кегля для кол-ва символов
        private boolean eqOp = false; // флаг нажатия операции равенства
        private int radix = 10; // основание выводимой системы счисления

        @Override
        protected void paintComponent(Graphics g) { // отрисовка компонента
            g.setFont(new Font("Courier New", Font.BOLD, 32 / k)); // отрисовка выражения
            g.drawString(str, 0, 25);
            g.setFont(new Font("Courier New", Font.BOLD, 26)); // отрисовка результата
            g.drawString(strRes, 5, 65);
        }

        public void setStr(String s) throws MaxExpLengthExeption {
            validInsert(s);
        }

        public void setK(int nk){
            k = nk;
        }

        // примитивная логика формирования строки выражения
        // и строки результата
        private void validInsert(String s) throws MaxExpLengthExeption{

            k = str.length() / SYMBOLS_PER_STR + 1;

            if (eqOp) {
                str = "";
                strRes = "";
                eqOp = false;
                btnPanel.setEnableEqual(false);
                repaint();
            }

            if ((s.equals("C")) && (!str.isEmpty())) {
                if (k == 3) k = 2;
                str = str.substring(0, str.length()-1);
                btnPanel.setEnableBtns(true);
                repaint();
            }

            else if (k == 3) {
                throw new MaxExpLengthExeption();
            }

            else if (s.equals("=") && !str.isEmpty()){
                try {
                    switch (radix){
                        case 2:
                            strRes = "=b" + Integer.toBinaryString(calculateExpr(getSplitExpression()));
                            break;
                        case 8:
                            strRes = "=o" + Integer.toOctalString(calculateExpr(getSplitExpression()));
                            break;
                        case 10:
                            strRes = "=" + calculateExpr(getSplitExpression());
                            break;
                        case 16:
                            strRes = "=h" + Integer.toHexString(calculateExpr(getSplitExpression()));
                            break;
                    }
                    btnPanel.setEnableBtns(true);
                    eqOp = true;
                    repaint();
                } catch (IndexOutOfBoundsException e){
                    JOptionPane.showMessageDialog(mainFrame, "Некорректное выражение", "Допишите", JOptionPane.ERROR_MESSAGE);
                }

            } else if ((!s.equals("C")) && (str.isEmpty()) && (!Arrays.asList(BTN_SG).contains(s))){
                str += s;
                lastInSGwM = Arrays.asList(BTN_SG).contains(s);
                repaint();
            }
            else if ((!s.equals("C")) &&((!str.isEmpty())) && !(lastInSGwM && Arrays.asList(BTN_SG).contains(s))){
                str += s;
                lastInSGwM = Arrays.asList(BTN_SG).contains(s);
                repaint();
            }

            if (str.length() == 0){
                btnPanel.setEnableEqual(false);
            } else btnPanel.setEnableEqual(true);
        }

        private void setRadix(int r){
            radix = r;
        }

        // разбивка выражения по знакам арифметических операций
        private ArrayList<String> getSplitExpression(){
            String [] splittedStr = str.split("(?<=[-*/+])");
            System.out.println(Arrays.toString(splittedStr));
            return new ArrayList<>(List.of(splittedStr));
        }

        private int calculateExpr(ArrayList<String> sA){
            String current = "";
            String next = "";
            String nextOp = "";

            int currentI = 0;
            int nextI = 0;
            int res = 0;

            String regex = ".*[-+*/]";
            int l = sA.size();
            int i = 0;
            while (i < l){
                current = sA.get(i);
                char lastChar = current.charAt(current.length()-1);
                if (lastChar == '*' || lastChar == '/'){
                    next = sA.get(i+1);
                    currentI = Integer.parseInt(current.substring(0, current.length()-1));
                    nextI = Integer.parseInt(next.matches(regex) ? next.substring(0, next.length()-1) : next);
                    nextOp = (next.matches(regex) ? next.charAt(next.length()-1) + "" : "");
                    sA.remove(i+1);
                }
                switch (lastChar){
                    case '*':
                        res = currentI * nextI;
                        sA.set(i, res + nextOp);
                        l--;
                        i=0;
                        break;
                    case '/':
                        res = currentI / nextI;
                        sA.set(i, res + nextOp);
                        l--;
                        i=0;
                    default:
                        i++;
                }
                System.out.println(sA);
            }

            i = 0;
            l = sA.size();
            while (i < l){
                current = sA.get(i);
                char lastChar = current.charAt(current.length()-1);
                if (lastChar == '-' || lastChar == '+'){
                    next = sA.get(i+1);
                    currentI = Integer.parseInt(current.substring(0, current.length()-1));
                    nextI = Integer.parseInt(next.matches(regex) ? next.substring(0, next.length()-1) : next);
                    nextOp = (next.matches(regex) ? next.charAt(next.length()-1) + "" : "");
                    sA.remove(i+1);
                }
                switch (current.charAt(current.length()-1)){
                    case '-':
                        res = currentI - nextI;
                        sA.set(i, res + nextOp);
                        l--;
                        i=0;
                        break;
                    case '+':
                        res = currentI + nextI;
                        sA.set(i, res + nextOp);
                        l--;
                        i=0;
                        break;
                    default:
                        i++;
                }
                System.out.println(sA);
            }
            return Integer.parseInt(sA.get(0));
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(CALC_PANEL_H, CALC_PANEL_W);
        }
    }

    private static class MaxExpLengthExeption extends RuntimeException{
        @Override
        public String getMessage() {
            return "Превышена максимальная длина выражения";
        }
    }

    private static class MainMenuBar extends JMenuBar{

        private final int[] items = {2, 8, 10, 16};

        public MainMenuBar(){
            JMenuItem item = null;
            JRadioButtonMenuItem radioItem = null;
            ButtonGroup group = new ButtonGroup();

            JMenu exitMenu = new JMenu("Файл");
            JMenu numSysMenu = new JMenu("СисСчис");

            JMenuItem exitItem = new JMenuItem("Закрыть");
            JMenuItem aboutItem = new JMenuItem("О программе");

            for (int i : items){

                radioItem = new JRadioButtonMenuItem(Integer.toString(i));
                group.add(radioItem);
                if (i == 10) radioItem.setSelected(true);

                radioItem.addActionListener((e) -> {
                    screenComponent.setRadix(i);
                    System.out.println(i);
                });
                numSysMenu.add(radioItem);
            }

            exitItem.addActionListener((e) -> {
                if (JOptionPane.showConfirmDialog(mainFrame, "Выйти?", "Выход", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                    System.exit(0);
            });

            aboutItem.addActionListener((e) -> {
                JOptionPane.showMessageDialog(mainFrame,
                        "Демонстрация применения граничной компоновки на примере калькулятора с на" +
                                "выполненни арифметических операций в очередном порядке",
                        "О программе", JOptionPane.INFORMATION_MESSAGE);
            });

            exitMenu.add(exitItem);
            exitMenu.add(aboutItem);

            add(exitMenu);
            add(numSysMenu);
        }
    }
}