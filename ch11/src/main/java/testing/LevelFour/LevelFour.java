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

// 0 добавить изменение размера с прверкой размеров строки
// 1 добавить панель с информацией о шрифте

package testing.LevelFour;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;

public class LevelFour {
    private static MainFrame mainFrame;
    private static TextPanel textPanel;
    private static TextComponent textComponent;
    private static TextInfoPanel textInfoPanel;

    private static final int FRAME_W = 800;
    private static final int FRAME_H = 500;

    private static final int TEXT_PANEL_W = 800;
    private static final int TEXT_PANEL_H = 350;

    private static final int MAX_TEXT_W = 750;

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
            //setResizable(false);
            setTitle("Просмотр текста");
            setLayout(new BorderLayout());
            setIconImage(
                    new ImageIcon(
                            Objects.requireNonNull(this.getClass().getResource("/text.png"))
                    ).getImage());

            textPanel = new TextPanel();
            textInfoPanel = new TextInfoPanel();

            add(textPanel, BorderLayout.CENTER);
            add(textInfoPanel, BorderLayout.SOUTH);

            setJMenuBar(new MainMenuBar());
        }
    }

    private static class TextPanel extends JPanel{
        public TextPanel(){
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);
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

        public FontMetrics getFM(){
            return getFontMetrics(font);
        }

        public void setFont() {
            Font newFont = new Font(fontName, fontOutline, fontSize);
            if (getFontMetrics(newFont).stringWidth(text) <= MAX_TEXT_W){
                font = newFont;
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Слишком длинная строка, уменьшите пожалуйста её длину", "Длинная строка", JOptionPane.ERROR_MESSAGE);
            }
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

        public void setText(String t) throws BadLocationException {
            FontMetrics fm = getFontMetrics(font);

            if (fm.stringWidth(t) > MAX_TEXT_W){
                textInfoPanel.getInputTextField().setDocument(new LimitDocument(t.length() - 1));
                textInfoPanel.getInputTextField().setText(text);
            }
            else{
                text = t;
                ((LimitDocument) textInfoPanel.getInputTextField().getDocument()).updateDocument(text.length() + 1);
            }
        }

        public String getText(){
            return text;
        }

        public void setComponent(String t, Font f) throws BadLocationException {
            setText(t);
            setFont(f);
        }

        public void setColor(Color c) {
            color = c;
        }

        public Color getColor(){
            return color;
        }
    }

    /*
    - Файл

    + Шрифт
      ++ гарнитура
      ++ начертание
      -- размер

    + цвет
      ++ наборный
      ++ константный

    панель с информацией от текущих настройках + размеры строки в пикселях

    - Содержание (- всегда через панель)
    */

    private static class TextInfoPanel extends JPanel{
        private JTextField inputTextField;
        private LimitDocument limitDocument;

        public TextInfoPanel(){
            limitDocument = new LimitDocument(100);
            setBorder(new EtchedBorder());
            setLayout(new GridBagLayout());
            inputTextField = new JTextField();
            inputTextField.setFont(new Font("Arial", Font.PLAIN, 20));
            inputTextField.setText(textComponent.getText());
            inputTextField.setDocument(limitDocument);

            JLabel textLabel = new JLabel("Строка:");
            textLabel.setFont(new Font("Arial", Font.PLAIN, 20));

            add(textLabel, new GBC(0, 0, 2, 1).setWeight(0, 0.1).setAnchor(GBC.EAST).setInsets(0, 0, 10, 0));
            add(inputTextField, new GBC(2, 0, 14, 1).setWeight(1, 0.1).setFill(1).setInsets(10));

            JPanel fillPanel = new JPanel();

            add(fillPanel, new GBC(0, 1, 16, 2).setWeight(1, 1).setFill(1));
        }

        public JTextField getInputTextField(){
            return inputTextField;
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(TEXT_PANEL_W, FRAME_H - TEXT_PANEL_H);
        }
    }

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
                DialogWindows.TextColorChooser tC = DialogWindows.getTextColorChooser(mainFrame, ADDITIVE, textComponent.getColor());
                if (tC.showDialog()){
                    textComponent.setColor(tC.getColor());
                    textComponent.repaint();
                }
            });

            colorPaletteItem.addActionListener((e) -> {
                DialogWindows.TextColorChooser tC = DialogWindows.getTextColorChooser(mainFrame, PALETTE, textComponent.getColor());
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

    private static class LimitDocument extends PlainDocument{
        private int limit;

        public LimitDocument(int l){
            super();
            limit = l;
            addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    try {
                        textComponent.setText(getText(0, getLength()));
                        textComponent.repaint();
                    } catch (BadLocationException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    try {
                        textComponent.setText(getText(0, getLength()));
                        textComponent.repaint();
                    } catch (BadLocationException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                }
            });
        }

        public void updateDocument(int l){
            limit = l;
        }

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) return;

            if ((getLength() + str.length() <= limit)){
                super.insertString(offs, str, a);
            }
        }
    }
}
