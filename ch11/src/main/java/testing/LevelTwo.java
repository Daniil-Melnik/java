/*
2. Освоить средства граничной компоновки, создать простой оконный калькулятор
- сформировать фрейм, поделить его на 2-е части: экран калькулятора и сетка кнопок
- присвоить каждой кнопке в сетке действие по наполнению строки выражения
- продублировать эти же действия на нажатия клавиш
- проработать экран калькулятора на отрисовку строки
- реализовать очистку экрана и вывод операции
- при недопустимых операциях - вывод окна с ошибкой
*/

// настроить разбор выражения на составные части и вычисление его результата

package testing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

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
                                           "7", "8", "9", "/" , "0", "%", "=", "*"};

    private static final String[] BTN_SG = {"=", "/" , "%", "*", "+"};

    private static final String[] BTN_SG_W_MINUS = {"=", "/" , "%", "*", "-", "+"};

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
        public BtnPanel(){
            setLayout(new GridLayout(4, 4));
            for (String s : BTN_S){
                JButton btn = new JButton(new BtnAction(s));
                btn.setFont(new Font("Courier New", Font.BOLD, 16));
                add(btn);
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
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            screenComponent.setStr(this.getValue(Action.NAME).toString());
        }
    }

    private static class ScreenComponent extends JComponent{
        private String str = "";
        private boolean lastInSGwM;

        @Override
        protected void paintComponent(Graphics g) {
            g.setFont(new Font("Courier New", Font.BOLD, 16));
            g.drawString(str, 0, 20);
        }

        public void setStr(String s) {
            validInsert(s);
        }

        private void validInsert(String s){
            if ((str.isEmpty()) && (!Arrays.asList(BTN_SG).contains(s))){
                str = str + s;
                lastInSGwM = Arrays.asList(BTN_SG_W_MINUS).contains(s);
                repaint();
            }
            else if (((!str.isEmpty())) && !(lastInSGwM && Arrays.asList(BTN_SG_W_MINUS).contains(s))){
                System.out.println(lastInSGwM + " " + Arrays.asList(BTN_SG_W_MINUS).contains(s));
                str = str + s;
                lastInSGwM = Arrays.asList(BTN_SG_W_MINUS).contains(s);
                repaint();
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(CALC_PANEL_H, CALC_PANEL_W);
        }
    }
}