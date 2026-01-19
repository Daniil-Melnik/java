/*
2. Освоить рисование геометрических фигур. Также освоить средства ToolKit и Preferences API
- получить корень дерева Preferences под отдельного пользователя (userRoot)
- настроить значения в узле дерева для последующей настройки фрейма
- экспортировать дерево настроек в отдельный файл

- создать фрейм, настроить его по настройкам, полученным из дерева
- создать компонент, в нём создать четыре графические элемента: прямоугольник, эллипс, прямую и точку
-
 */

package testing;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.prefs.Preferences;

public class LevelTwo {

    public static void main(String [] args){
        Preferences root = Preferences.userRoot();

        Preferences node2 = root.node("/testing/levelTwo");

        // формирование локального дерева настроек preferencies
        // С экспортом в файл preferencies.xml

        // node2.put("height", "350");
        /*node2.put("width", "500");
        node2.put("resizeble", "false");
        node2.put("title", "LevelTwo");
        node2.put("iconFile", "/images/100.png");

        try {
            String userHome = System.getProperty("user.home");
            root.exportSubtree(new FileOutputStream(userHome + "\\test\\preferencies.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (BackingStoreException e) {
            throw new RuntimeException(e);
        }*/

        EventQueue.invokeLater(() -> {
            JFrame frame = new FrameTwo();
            frame.setVisible(true);
        });

        System.out.println(node2.getInt("width", 0)); // проверка наличия значений
        System.out.println(node2.getInt("height", 0));


    }
}

class FrameTwo extends JFrame{
    public FrameTwo(){

        Preferences node = Preferences.userRoot().node("/testing/levelTwo");
        Toolkit toolKit = Toolkit.getDefaultToolkit();

        setTitle(node.get("title", "default"));
        setSize(node.getInt("width", (int) (toolKit.getScreenSize().getWidth() / 2)),
                              node.getInt("height", (int) (toolKit.getScreenSize().getHeight()) / 2));

        setIconImage(new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource(node.get("iconFile", "/images/default.png"))) // в реалиях maven - через ресурсы
        ).getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(node.getBoolean("resizeble", true));

        add(new ComponentTwo());
        pack();
    }
}

class ComponentTwo extends JComponent{

    private static final int PREF_HEIGHT = 350;
    private static final int PREF_WIDTH = 500;

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        System.out.println(Arrays.toString(GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames()));

        float lextX = 100.0F;
        float lextY = 80.0F;
        float height = 190.0F;
        float width = 300.0F;

        g2.setStroke(new BasicStroke(4));

        g2.setColor(Color.BLUE);
        Rectangle2D rectangle = new Rectangle2D.Float(lextX, lextY, width, height);
        g2.draw(rectangle);

        g2.setColor(Color.RED);
        Ellipse2D ellipse = new Ellipse2D.Double(lextX, lextY, width, height);
        g2.draw(ellipse);
        g2.setColor(new Color(255, 255, 255));
        g2.fill(ellipse);

        g2.setColor(Color.CYAN);
        Line2D line = new Line2D.Double(
                rectangle.getMinX(),
                rectangle.getMinY(),
                rectangle.getMaxX(),
                rectangle.getMaxY());
        g2.draw(line);

        drawLines(g2, (int) rectangle.getMinX(), (int) rectangle.getMaxY());

        System.out.println();
    }

    private static void drawLines( Graphics g, int startX, int startY){
        Graphics2D g2 = (Graphics2D) g;

        Font [] fonts = new Font[4];
        Map<TextAttribute, Object> attribs = new HashMap<>();

        fonts[0] = new Font("SansSerif", Font.BOLD, 14);

        attribs.put(TextAttribute.FAMILY, "Dialog");
        attribs.put(TextAttribute.SIZE, 16);
        attribs.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        attribs.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
        attribs.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

        fonts[1] = new Font(attribs);

        g2.setColor(new Color(3, 168, 0));
        g2.setFont(fonts[0]);
        g2.drawString("Фигуры", startX, startY + 25.0F);

        g2.setFont(fonts[1]);
        g2.drawString("Фигуры", startX, startY + 50.0F);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREF_WIDTH, PREF_HEIGHT);
    }
}
