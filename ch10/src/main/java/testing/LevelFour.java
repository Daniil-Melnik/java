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
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            FrameFour frame = new FrameFour();
            frame.setVisible(true);
        });
    }


    static class FrameFour extends JFrame {
        private int width;
        private int height;

        public FrameFour() {
            Preferences node = Preferences.userRoot().node("/testing/levelFour");

            setLayout(null);
            width = node.getInt("width", 500); // принятие совйств из Preferences
            height = node.getInt("height", 150);

            setSize(width, height);

            addWindowListener(new LevelThree.WindowTerminator()); // определён как внутренний класс в LevelThree
            setTitle(node.get("title", "default"));

            setIconImage(new ImageIcon(Objects.requireNonNull(
                    this.getClass().getResource(node.get("iconFile", "/images/default.png"))
            )).getImage());

            PanelFour panel = new PanelFour(width, height); // создание панели
            panel.setBounds(0, 0, width, height); // установка положения и размеров панели во фрейме

            ActionMap aMap = panel.getActionMap(); // получение ОТОБРАЖЕНИЯ ДЕЙСТВИЙ
            InputMap iMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW); // получение ОТОБРАЖЕНИЯ ПРИВЯЗКИ ВВОДА
            // самая широкая область фокусировки

            iMap.put(KeyStroke.getKeyStroke("ctrl R"), "rAction"); // добавление нажатия Ctrl+R в отображение
            // привязки ввода
            // ключ rAction - связка между вводом и
            // действием в aMap

            iMap.put(KeyStroke.getKeyStroke("ctrl T"), "tAction"); // добавление нажатия Crtl+R
            // ключ tAction - связка между вводом и
            // действием в aMap

            aMap.put("rAction", new TextAction(panel.getComponent(), true, "цвет", "поменять цвет"));
            // установка связки перехватчика и ключа для замены цвета
            // перехватчик -> ключ <- ввод
            aMap.put("tAction", new TextAction(panel.getComponent(), false, "гарнитура", "поменять гарнитуру"));
            // установка связки перехватчика и ключа для замены гарнитуры

            add(panel);
        }
    }

    static class TextAction extends AbstractAction { // описание дествия (привязывается к клавишам и кнопкам)

        private static String[] fontFamilyNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        // поле класса, содержащее все применимые в системе гарнитуры шрифтов

        private ComponentFour sComponent; // ссылка на компонент, для которого создаётся экземпляр действия
        private boolean mode; // true - действие для изменения цвета
        // false - действие для изменения гарнитуры

        public TextAction(ComponentFour comp, boolean m, String name, String descr) { // конструктор действия
            putValue(Action.NAME, name);
            putValue(Action.SHORT_DESCRIPTION, descr);
            sComponent = comp;
            mode = m;
        }

        @Override
        public void actionPerformed(ActionEvent e) { // определение метода-перехватчика
            Random random = new Random(); // генератор случайного
            int iFn; // случайны целочисленные для параметров
            int iR;
            int iG;
            int iB;

            if (mode) {
                iR = Math.abs(random.nextInt() % 255); // случайные компоненты цвета по RGB от 0 до 254
                iG = Math.abs(random.nextInt() % 255);
                iB = Math.abs(random.nextInt() % 255);
                sComponent.setColor(new Color(iR, iG, iB)); // установка в компонент с шрифтом требуемого цвета
                // начертанияя букв
            } else {
                iFn = Math.abs(random.nextInt() % fontFamilyNames.length); // выбор гарнитуры шрифта по случайному индексу

                sComponent.setFontFamilyName(fontFamilyNames[iFn]); // установка в компонент с шрифтом требуемой
                // гарнитуры шрифта
            }

            sComponent.repaint(); // перерисовка компонента с изменёнными цветом или гарнитурой
        }
    }

    static class PanelFour extends JPanel { // панель-прокладка (?)
        private ComponentFour component; // компонент, хранимый на экземпляре панели

        public PanelFour(int w, int h) {
            setLayout(null);
            component = new ComponentFour();
            component.setBounds(0, 0, w, h - 50); // добавление на панель компонента
            add(component);

            TextAction rAction = new TextAction(component, true, "цвет", "поменять цвет"); // создание экземпляра
            // действия для экранной кнопки

            TextAction tAction = new TextAction(component, false, "гарнитура", "поменять гарнитуру");// создание экземпляра
            // действия для экранной кнопки

            JButton rBtn = new JButton(rAction); // создание экземпляра кнопки по экземпляру действия
            JButton tBtn = new JButton(tAction);


            rBtn.setBounds(0, h - 70, 105, 30);
            tBtn.setBounds(110, h - 70, 105, 30);

            add(rBtn);
            add(tBtn);
        }

        public ComponentFour getComponent() {
            return component;
        }
    }

    static class ComponentFour extends JComponent { // компонент с текстом
        private Color color; // хранение цвета букв для перерисовки под разные цвета
        private String fontFamilyName; // хранение названия гарнитуры для возможности перерисовки

        public ComponentFour() {
            setLayout(null);
            color = Color.BLACK; // при конструировани задаются первые свойства
            fontFamilyName = "Algerian";
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setFont(new Font(fontFamilyName, Font.PLAIN, 28)); // шрифт из установленного извне свойства
            g2.setColor(color); // цвет из установленного извне свойства
            g2.drawString("abcdefghijklmnopqrstuvwxyz", 10, 40);

            g2.setFont(new Font("Arial", Font.PLAIN, 14)); // неизменяемое задание параметров рисования
            g2.setColor(Color.BLACK);
            g2.drawString(fontFamilyName, 10, 60);
        }

        public void setColor(Color c) { // сеттеры необходимые для изменения свойств отрисовки компонента
            color = c;
        }

        public void setFontFamilyName(String s) {
            fontFamilyName = s;
        }
    }
}