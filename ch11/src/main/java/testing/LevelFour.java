/*
4. Освоить примененение компонентов для выбора разных вариантов
- сформировать фрейм, поместить в него компонент с текстом
- добавить в фрейм панели с выбором: шрифта, начертания, размера
  -- шрифт - компобокс
  -- размер - регулируемый ползунок
  -- начертание - чекбоксы
  -- цвет - меню радио кнопок - наборный через комбо
                              - из констант через компбо
  -- изменение текста через поля для ввода
- все панели изменений провести через строку меню
*/

// переделать главную модель под расширенный компоновщик

package testing;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

public class LevelFour {
    private static MainFrame mainFrame;
    private static TextPanel textPanel;
    private static TextComponent textComponent;

    private static final int FRAME_W = 800;
    private static final int FRAME_H = 500;

    private static final int TEXT_PANEL_W = 800;
    private static final int TEXT_PANEL_H = 350;

    public static void main(String... args){
        EventQueue.invokeLater(() -> {
            mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }

    private static class MainFrame extends JFrame{
        public MainFrame(){
            setSize(FRAME_W, FRAME_H);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setResizable(false);
            setTitle("Просмотр текста");
            setLayout(new BorderLayout());
            setIconImage(
                    new ImageIcon(
                            Objects.requireNonNull(this.getClass().getResource("/text.png"))
                    ).getImage());

            textPanel = new TextPanel();

            add(textPanel, BorderLayout.NORTH);

            setJMenuBar(new MainMenuBar());
        }
    }

    private static class TextPanel extends JPanel{
        public TextPanel(){
            setBackground(Color.WHITE);
            setLayout(new BorderLayout());
            textComponent = new TextComponent();

            add(textComponent, BorderLayout.CENTER);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(TEXT_PANEL_W, TEXT_PANEL_H);
        }
    }

    private static class TextComponent extends JComponent{
        private String text = "Hello, it's text component";
        private String fontName = "Arial";
        private int fontSize = 16;
        private int fontOutline = Font.PLAIN;
        private Font font;

        public TextComponent(){
            font = new Font(fontName, fontOutline, fontSize);
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setFont(font);

            FontMetrics fm = getFontMetrics(font);
            int strWidth = fm.stringWidth(text);
            int strHeight = fm.getHeight();

            g.drawString(text, (TEXT_PANEL_W - strWidth) / 2,(TEXT_PANEL_H - strHeight) / 2);
        }

        public void setFont() {
            font = new Font(fontName, fontOutline, fontSize);
        }

        public void setFontName(String fN) {
            fontName = fN;
        }

        public void setFontOutline(int fO) {
            fontOutline = (fO >= 0 && fO <=2) ? fO : Font.PLAIN; // проверка на принадлежность [PLAIN; ITALIC]
        }

        public void setFontSize(int fS) {
            fontSize = fS;
        }

        public void setText(String t) {
            text = t;
        }

        public void setComponent(String t, Font f){
            setText(t);
            setFont(f);
        }
    }

    /*
    - Файл
    - режим (взаимоисключающие)
      -- меню
      -- панель

    // случай режима-меню
    - Шрифт
      -- гарнитура
      -- начертание
    - цвет
      -- наборный
      -- константный

    размер - элемент на панели

    панель с информацией от текущих настройках + размеры строки в пикселях
    панель с настройками
    - Содержание (- всегда через панель)
    */

    private static class MainMenuBar extends JMenuBar{
        public MainMenuBar(){
            JMenu fileMenu = new JMenu("Файл");
            JMenu textMenu = new JMenu("Режим");
            JMenu fontMenu = new JMenu("Шрифт");
            JMenu colorMenu = new JMenu("Цвет");

            JMenuItem shriftItem = new JMenuItem("Гарнитура");
            shriftItem.addActionListener((e) -> {
                try {
                    TextAdder tA = new TextAdder(mainFrame);
                    if (tA.showDialog()){
                        textComponent.setFontName(tA.getText());
                        textComponent.setFont();
                        textComponent.repaint();
                    }
                } catch (IOException ex) {System.out.println("Problems with fonts.txt");}

            });

            fontMenu.add(shriftItem);

            add(fileMenu);
            add(textMenu);
            add(fontMenu);
            add(colorMenu);
        }
    }

    private static class TextAdder extends JPanel{
        private boolean ok = false;
        private JFrame owner = null;
        private JDialog dialog = null;
        private JComboBox<String> fontCombo = null;

        public TextAdder(JFrame o) throws IOException {
            owner = o;

            fontCombo = new JComboBox<>(getFontsFromFile().toArray(new String[0]));

            Font font20 = new Font("Arial", Font.BOLD, 20);
            Font font12 = new Font("Arial", Font.BOLD, 12);
            JPanel btnPanel = new JPanel();
            JButton okBtn = new JButton("ок");
            JButton cancelBtn = new JButton("отмена");
            setLayout(new BorderLayout());
            btnPanel.setLayout(new FlowLayout());

            okBtn.setFont(font12);
            cancelBtn.setFont(font12);
            fontCombo.setFont(font20);

            okBtn.addActionListener((e) -> {
                ok = true;
                dialog.setVisible(false);
            });

            cancelBtn.addActionListener((e) -> dialog.setVisible(false));

            btnPanel.add(okBtn);
            btnPanel.add(cancelBtn);

            add(btnPanel, BorderLayout.SOUTH);
            add(fontCombo, BorderLayout.CENTER);
        }

        private LinkedList<String> getFontsFromFile() throws IOException {
            String line = null;
            LinkedList<String> items = new LinkedList<>();
            BufferedReader reader = new BufferedReader(new FileReader(this.getClass().getResource("/fonts.txt").getFile()));
            System.out.println(this.getClass().getResource("/fonts.txt").toString());

            while ((line = reader.readLine()) != null){
                items.add(line);
            }

            return items;
        }

        public boolean showDialog(){
            if (dialog == null){
                dialog = new JDialog(owner, true); // JDialog - база всех диалоговых окно, подобна JFrame
                dialog.add(this); // добавление в базу диалогового окна наполнения из панели
                dialog.setSize(new Dimension(380, 110));
                dialog.setTitle("Выбор шрифта");
                dialog.setIconImage(
                        new ImageIcon(
                                Objects.requireNonNull(
                                        this.getClass().getResource("/fonts.png"))
                        ).getImage());
                dialog.setResizable(false);
            }
            dialog.setVisible(true);
            return ok;
        }

        public String getText(){
            return Objects.requireNonNull(fontCombo.getSelectedItem()).toString();
        }
    }
}
