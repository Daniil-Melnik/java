/*
4. Освоить обработку нажатия клавиш клавиатуры
- создать окно для тестирования нажатий:
  - создать фрейм, в фрейме создать панель
  - поместить в панель компонент цветного текста определённого шрифта
  - предусмотреть возможность перерисовки компонента
- реализовать перехват нажатия комбинаций клавиш Ctrl+R и Ctrl+T
  - создать отдельный класс расширения AbstractAction и определить 2 экземпляра дейтсви
    по смене шрифта
  - получить отображение привязки ввода панели
  - получить отображение привязки действия панели
  - привязать (добавлением в отображение) ввод 2-х комбинаций клавиш
  - привязать (добавлением в отображение) 2 действия (1 комбинация = 1 действие)
*/

package testing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;
import java.util.Random;
import java.util.prefs.Preferences;

public class LevelFour {
    public static void main(String[] args){
        EventQueue.invokeLater(() ->{
            FrameFour frame = new FrameFour();
            frame.setVisible(true);
        });
    }
}

class FrameFour extends JFrame{
    private int width;
    private int height;

    public FrameFour(){
        Preferences node = Preferences.userRoot().node("/testing/levelFour");

        setLayout(null);
        width = node.getInt("width", 500);
        height = node.getInt("height", 150);

        setSize(width, height);

        addWindowListener(new WindowTerminator()); // определён как внутренний класс в LevelThree
        setTitle(node.get("title", "default"));

        setIconImage(new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource(node.get("iconFile", "/images/default.png"))
        )).getImage());

        PanelFour panel = new PanelFour(width, height);
        panel.setBounds(0, 0, width, height);

        ActionMap aMap = panel.getActionMap();
        InputMap iMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        iMap.put(KeyStroke.getKeyStroke("ctrl R"), "rAction");
        iMap.put(KeyStroke.getKeyStroke("ctrl T"), "tAction");
        aMap.put("rAction", new TextAction(panel.getComponent(), true, "цвет", "поменять цвет"));
        aMap.put("tAction", new TextAction(panel.getComponent(), false, "гарнитура", "поменять гарнитуру"));

        add(panel);
    }
}

class TextAction extends AbstractAction{

    private static String [] fontFamilyNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    private ComponentFour sComponent;
    private boolean mode;

    public TextAction(ComponentFour comp, boolean m, String name, String descr){
        putValue(Action.NAME, name);
        putValue(Action.SHORT_DESCRIPTION, descr);
        sComponent = comp;
        mode = m;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Random random = new Random();
        int iFn;
        int iR;
        int iG;
        int iB;

        if (mode){
            iR = Math.abs(random.nextInt() % 255);
            iG = Math.abs(random.nextInt() % 255);
            iB = Math.abs(random.nextInt() % 255);
            sComponent.setColor(new Color(iR, iG, iB));
        } else {
            iFn = Math.abs(random.nextInt() % fontFamilyNames.length);
            sComponent.setFontFamilyName(fontFamilyNames[iFn]);
        }

        sComponent.repaint();
    }
}

class PanelFour extends JPanel{
    private ComponentFour component;

    public PanelFour(int w, int h){
        setLayout(null);
        component = new ComponentFour();
        component.setBounds(0, 0, w, h-50);
        add(component);

        TextAction rAction = new TextAction(component, true, "цвет", "поменять цвет");
        TextAction tAction = new TextAction(component, false, "гарнитура", "поменять гарнитуру");

        JButton rBtn = new JButton(rAction);
        JButton tBtn = new JButton(tAction);


        rBtn.setBounds(0, h-70, 105, 30);
        tBtn.setBounds(110, h-70, 105, 30);

        add(rBtn);
        add(tBtn);
    }

    public ComponentFour getComponent(){
        return component;
    }
}

class ComponentFour extends JComponent{
    private Color color;
    private String fontFamilyName;

    public ComponentFour(){
        setLayout(null);
        color = Color.BLACK;
        fontFamilyName = "Algerian";
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font(fontFamilyName, Font.PLAIN,28));
        g2.setColor(color);
        g2.drawString("abcdefghijklmnopqrstuvwxyz", 10, 40);

        g2.setFont(new Font("Arial", Font.PLAIN,14));
        g2.setColor(Color.BLACK);
        g2.drawString(fontFamilyName, 10, 60);
    }

    public void setColor(Color c) {
        color = c;
    }

    public void setFontFamilyName(String s){
        fontFamilyName = s;
    }
}