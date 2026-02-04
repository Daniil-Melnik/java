/*
2. Освоить средства граничной компоновки, создать простой оконный калькулятор
- сформировать фрейм, поделить его на 2-е части: экран калькулятора и сетка кнопок
- присвоить каждой кнопке в сетке действие по наполнению строки выражения
- продублировать эти же действия на нажатия клавиш
- проработать экран калькулятора на отрисовку строки
- реализовать очистку экрана и вывод операции
- при недопустимых операциях - вывод окна с ошибкой
*/

// очистка результата после нажатия равенства

package testing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class LevelTwo {
    private static MainFrame mainFrame;
    private static BtnPanel btnPanel;
    private static CalcPanel calcPanel;
    private static ScreenComponent screenComponent;

    private static final int FRAME_W = 300;
    private static final int FRAME_H = 250;

    private static final int BTN_PANEL_W = 300;
    private static final int BTN_PANEL_H = 140;

    private static final int CALC_PANEL_W = 300;
    private static final int CALC_PANEL_H = 70;

    private static final String[] BTN_S = {"1", "2" , "3", "+", "4", "5", "6", "-",
                                           "7", "8", "9", "/" , "0", "C", "=", "*"};

    private static final String[] KEYSTROKES = {"NUMPAD1", "NUMPAD2", "NUMPAD3", "ADD", "NUMPAD4", "NUMPAD5",
                                                "NUMPAD6", "SUBTRACT", "NUMPAD7", "NUMPAD8", "NUMPAD9",
                                                "DIVIDE", "NUMPAD0", "BACK_SPACE", "ENTER", "MULTIPLY"
                                               };

    private static final String[] BTN_SG = {"=", "/" , "-", "*", "+"};

    private static final String[] BTN_SG_W_MINUS = {"=", "/" , "*", "-", "+"};

    private static final int SYMBOLS_PER_STR = 13;

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
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setResizable(false);

            btnPanel = new BtnPanel();
            add(btnPanel, BorderLayout.SOUTH);

            calcPanel = new CalcPanel();
            add(calcPanel, BorderLayout.NORTH);
        }
    }

    private static class BtnPanel extends JPanel{
        private static JButton[] btns = new JButton[16];
        private static BtnAction[] actions = new BtnAction[16];

        public BtnPanel(){
            setLayout(new GridLayout(4, 4));
            int i = 0;

            for (String s : BTN_S) {
                actions[i] = new BtnAction(s);
                btns[i] = new JButton(actions[i]);
                btns[i].setFont(new Font("Courier New", Font.BOLD, 16));
                add(btns[i]);
                i++;
            }

            createKeyIAMap();
        }

        public void setEnableBtns(boolean b){
            for (int i = 0; i < 16; i++){
                btns[i].setEnabled(b);
                actions[i].putValue("enabled", b);
            }
            btns[13].setEnabled(true);
            btns[14].setEnabled(true);

            actions[13].putValue("enabled", true);
            actions[14].putValue("enabled", true);
        }

        private void createKeyIAMap(){
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

    private static class CalcPanel extends JPanel{
        public CalcPanel(){
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);
            screenComponent = new ScreenComponent();
            add(screenComponent);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(CALC_PANEL_W, CALC_PANEL_H);
        }
    }

    private static class BtnAction extends AbstractAction{
        public BtnAction(String s){
            putValue(Action.NAME, s);
            putValue(Action.SHORT_DESCRIPTION, s);
            putValue("enabled", true);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                if ((boolean) this.getValue("enabled"))
                    screenComponent.setStr(this.getValue(Action.NAME).toString());
            }catch (MaxExpLengthExeption ex){
                JOptionPane.showMessageDialog(mainFrame, ex.getMessage());
                btnPanel.setEnableBtns(false);
                screenComponent.setK(2);
            }

        }

        public ArrayList<String> splitMathExpr(String s){

            String [] arrMult = s.split("(?<=[-*/+])");
            System.out.println(Arrays.toString(arrMult));
            return null;
        }
    }

    private static class ScreenComponent extends JComponent{
        private String str = "";
        private String strRes = "";
        private boolean lastInSGwM;
        private int k = 1;

        @Override
        protected void paintComponent(Graphics g) {
            g.setFont(new Font("Courier New", Font.BOLD, 32 / k));
            g.drawString(str, 0, 20);
            g.setFont(new Font("Courier New", Font.BOLD, 26));
            g.drawString(strRes, 5, 60);
        }

        public void setStr(String s) throws MaxExpLengthExeption {
            validInsert(s);
        }

        public void setK(int nk){
            k = nk;
        }

        private void validInsert(String s) throws MaxExpLengthExeption{

            k = str.length() / SYMBOLS_PER_STR + 1;

            if ((s.equals("C")) && (!str.isEmpty())) {
                if (k == 3) k = 2;
                str = str.substring(0, str.length()-1);
                btnPanel.setEnableBtns(true);
                repaint();
            }

            else if (k == 3) {
                throw new MaxExpLengthExeption();
            }

            else if (s.equals("=")){
                try {
                    strRes = "=" + calculateExpr(getSplitExpression());
                    btnPanel.setEnableBtns(true);
                    repaint();
                } catch (IndexOutOfBoundsException e){
                    JOptionPane.showMessageDialog(mainFrame, "Некорректное выражение", "Допишите", JOptionPane.ERROR_MESSAGE);
                }

            } else if ((!s.equals("C")) && (str.isEmpty()) && (!Arrays.asList(BTN_SG).contains(s))){
                str += s;
                lastInSGwM = Arrays.asList(BTN_SG_W_MINUS).contains(s);
                repaint();
            }
            else if ((!s.equals("C")) &&((!str.isEmpty())) && !(lastInSGwM && Arrays.asList(BTN_SG_W_MINUS).contains(s))){
                str += s;
                lastInSGwM = Arrays.asList(BTN_SG_W_MINUS).contains(s);
                repaint();
            }
        }

        private ArrayList<String> getSplitExpression(){
            String [] splittedStr = str.split("(?<=[-*/+])");
            System.out.println(Arrays.toString(splittedStr));
            return new ArrayList<>(List.of(splittedStr));
        }

        private String calculateExpr(ArrayList<String> sA){
            Iterator<String> iter = sA.iterator();

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
                    default:
                        i++;
                }
                System.out.println(sA);
            }
            return sA.get(0);
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
}