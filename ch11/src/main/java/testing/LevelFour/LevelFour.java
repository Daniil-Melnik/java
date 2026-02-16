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

// 0 разделить проект на несколько файлов (возможны классы-фабрики)

package testing.LevelFour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

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
        private int fontSize = 26;
        private int fontOutline = Font.BOLD;
        private Font font;
        private Color color = Color.BLACK;

        public TextComponent(){
            font = new Font(fontName, fontOutline, fontSize);
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setFont(font);
            g.setColor(color);

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

        public void setColor(Color c) {
            color = c;
        }
    }

    /*
    - Файл
    - режим (взаимоисключающие)
      -- меню
      -- панель

    // случай режима-меню
    + Шрифт
      ++ гарнитура
      ++ начертание
    - цвет
      -- наборный
      -- константный

    размер - элемент на панели

    панель с информацией от текущих настройках + размеры строки в пикселях
    панель с настройками
    - Содержание (- всегда через панель)
    */

    private static class MainMenuBar extends JMenuBar{
        private static final boolean PALETTE = false;
        private static final boolean ADDITIVE = true;

        public MainMenuBar(){
            JMenu fileMenu = new JMenu("Файл");
            JMenu fontMenu = new JMenu("Шрифт");
            JMenu colorMenu = new JMenu("Цвет");

            JMenu outlineMenu = new JMenu("Начертание");

            JMenuItem shriftItem = new JMenuItem("Гарнитура");
            ButtonGroup radioGroup = new ButtonGroup();
            JRadioButtonMenuItem outlineItemPlain = new JRadioButtonMenuItem(new OutlineAction("Plain"));
            JRadioButtonMenuItem outlineItemBold = new JRadioButtonMenuItem(new OutlineAction("Bold"));
            JRadioButtonMenuItem outlineItemItalic = new JRadioButtonMenuItem(new OutlineAction("Italic"));
            radioGroup.add(outlineItemPlain);
            radioGroup.add(outlineItemBold);
            radioGroup.add(outlineItemItalic);

            outlineMenu.add(outlineItemPlain);
            outlineMenu.add(outlineItemBold);
            outlineMenu.add(outlineItemItalic);

            outlineItemPlain.addActionListener((e) -> {
                textComponent.setFontOutline(Font.PLAIN);
            });

            shriftItem.addActionListener((e) -> {
                try {
                    DialogWindows.TextAdder tA = DialogWindows.getTextAddedDialog(mainFrame);
                    if (tA.showDialog()){
                        textComponent.setFontName(tA.getText());
                        textComponent.setFont();
                        textComponent.repaint();
                    }
                } catch (IOException ex) {System.out.println(ex.getMessage());}

            });

            JMenuItem colorAddsItem = new JMenuItem("Наборный");
            JMenuItem colorPaletteItem = new JMenuItem("Плитка");

            colorAddsItem.addActionListener((e) -> {
                DialogWindows.TextColorChooser tC = DialogWindows.getTextColorChooser(mainFrame, ADDITIVE);
                if (tC.showDialog()){
                    System.out.println("Text changed");
                    textComponent.setColor(tC.getColor());
                    textComponent.repaint();
                }
            });

            colorPaletteItem.addActionListener((e) -> {
                DialogWindows.TextColorChooser tC = DialogWindows.getTextColorChooser(mainFrame, PALETTE);
                if (tC.showDialog()){
                    textComponent.setColor(tC.getColor());
                    textComponent.repaint();
                }
            });

            colorMenu.add(colorAddsItem);
            colorMenu.add(colorPaletteItem);

            fontMenu.add(shriftItem);
            fontMenu.add(outlineMenu);

            add(fileMenu);
            add(fontMenu);
            add(colorMenu);
        }
    }

    private static class OutlineAction extends AbstractAction{
        HashMap<String, Integer> outlineMap = new HashMap<>(3);

        public OutlineAction(String name){
            putValue(Action.NAME, name);
            outlineMap.put("Plain", Font.PLAIN);
            outlineMap.put("Bold", Font.BOLD);
            outlineMap.put("Italic", Font.ITALIC);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = this.getValue(Action.NAME).toString();
            int outline = outlineMap.get(name);
            textComponent.setFontOutline(outline);
            textComponent.setFont();
            textComponent.repaint();
        }
    }
}
