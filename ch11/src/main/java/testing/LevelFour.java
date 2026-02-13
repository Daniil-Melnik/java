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

// 1 переделать главную модель под расширенный компоновщик
// 0 пересмотреть компоновку диалогового окна TextColorChooser чтобы всё было ровно

package testing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    private static final Font font20 = new Font("Arial", Font.BOLD, 20);
    private static final Font font12 = new Font("Arial", Font.BOLD, 12);

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
        public MainMenuBar(){
            JMenu fileMenu = new JMenu("Файл");
            JMenu textMenu = new JMenu("Режим");
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
                    TextAdder tA = new TextAdder(mainFrame);
                    if (tA.showDialog()){
                        textComponent.setFontName(tA.getText());
                        textComponent.setFont();
                        textComponent.repaint();
                    }
                } catch (IOException ex) {System.out.println("Problems with fonts.txt");}

            });

            JMenuItem colorAddsItem = new JMenuItem("Наборный");
            colorAddsItem.addActionListener((e) -> {
                TextColorChooser tC = new TextColorChooser(mainFrame);
                if (tC.showDialog()){
                    System.out.println("Text changed");
                    textComponent.setColor(tC.getColor());
                    textComponent.repaint();
                }
            });

            colorMenu.add(colorAddsItem);

            fontMenu.add(shriftItem);
            fontMenu.add(outlineMenu);

            add(fileMenu);
            add(textMenu);
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

    private static class TextAdder extends JPanel{
        private boolean ok = false;
        private JFrame owner = null;
        private JDialog dialog = null;
        private JComboBox<String> fontCombo = null;

        public TextAdder(JFrame o) throws IOException {
            owner = o;

            fontCombo = new JComboBox<>(getFontsFromFile().toArray(new String[0]));


            JPanel btnPanel = new JPanel();
            CustomBtn okBtn = new CustomBtn("ок", font12, (e) -> {ok = true; dialog.setVisible(false);});
            CustomBtn cancelBtn = new CustomBtn("отмена", font12, (e) -> {dialog.setVisible(false);});
            setLayout(new BorderLayout());
            btnPanel.setLayout(new FlowLayout());

            fontCombo.setFont(font20);

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

    private static class TextColorChooser extends JPanel{
        private JSlider rSlider = new JSlider(0, 255, 100);
        private JSlider gSlider = new JSlider(0, 255, 100);
        private JSlider bSlider = new JSlider(0, 255, 100);

        private JDialog dialog = null;
        private JFrame owner = null;
        private boolean ok = false;

        private HashMap<String, JSlider> sliders = new HashMap<>(3);
        private LinkedHashMap<String, JLabel> valLabels = new LinkedHashMap<>(3);
        private HashMap<String, JLabel> colorNameLabels = new HashMap<>(3);

        {
            valLabels.put("r", new JLabel("100"));
            valLabels.put("g", new JLabel("100"));
            valLabels.put("b", new JLabel("100"));

            colorNameLabels.put("r", new JLabel("Красный"));
            colorNameLabels.put("g", new JLabel("Зелёный"));
            colorNameLabels.put("b", new JLabel("Синий"));

            sliders.put("r", new JSlider(0, 255, 100));
            sliders.put("g", new JSlider(0, 255, 100));
            sliders.put("b", new JSlider(0, 255, 100));
        }

        public TextColorChooser(JFrame o){
            setLayout(new GridBagLayout());
            JPanel colorProbe = new JPanel();
            colorProbe.setBackground(this.getColor());

            owner = o;
            CustomBtn okBtn = new CustomBtn("ок", font12, (e) -> {ok = true; dialog.setVisible(false);});
            CustomBtn cancelBtn = new CustomBtn("отмена", font12, (e) -> {dialog.setVisible(false);});

            JPanel sliderPanel = new JPanel();
            sliderPanel.setLayout(new GridBagLayout());

            int i = 0;

            System.out.println(valLabels.keySet());
            for (String s : valLabels.keySet()){
                colorNameLabels.get(s).setFont(font12);
                valLabels.get(s).setFont(font12);
                sliders.get(s).addChangeListener((e) -> {
                    valLabels.get(s).setText(sliders.get(s).getValue() + "");
                    colorProbe.setBackground(this.getColor());
                });

                sliderPanel.add(colorNameLabels.get(s), new GBC(0, i, 1, 1)
                        .setWeight(0.2, 0.3)
                        .setAnchor(GBC.EAST)
                        .setInsets(0,0,0,20));
                sliderPanel.add(sliders.get(s), new GBC(1, i, 7, 1)
                        .setWeight(0.6,0.3)
                        .setFill(GBC.HORIZONTAL));
                sliderPanel.add(valLabels.get(s), new GBC(8, i, 2, 1)
                        .setWeight(0.2, 0.3));

                i++;
            }

            JPanel btnPanel = new JPanel();
            btnPanel.add(okBtn);
            btnPanel.add(cancelBtn);

            add(sliderPanel, new GBC(0, 0, 10, 3)
                    .setWeight(1, 0.8).setFill(GridBagConstraints.BOTH));
            add(colorProbe, new GBC(0, 3, 10, 1)
                    .setWeight(1, 0.1).setFill(GBC.BOTH));
            add(btnPanel, new GBC(0, 4, 10, 1)
                    .setWeight(1, 0.1));
        }

        public boolean showDialog(){
            if (dialog == null){
                dialog = new JDialog(owner, true);
                dialog.add(this);
                dialog.pack();
                dialog.setResizable(false);
                dialog.setTitle("Выбор цвета");
                dialog.setSize(400, 200);
                dialog.setIconImage(new ImageIcon(
                        Objects.requireNonNull(
                                this.getClass().getResource("/palette.png")
                        )
                ).getImage());
            }
            dialog.setVisible(true);
            return ok;
        }

        public Color getColor(){
            return new Color(
                    sliders.get("r").getValue(),
                    sliders.get("g").getValue(),
                    sliders.get("b").getValue()
            );
        }
    }

    private static class CustomBtn extends JButton{
        public CustomBtn(String name, Font font, ActionListener listener){
            setText(name);
            addActionListener(listener);
            setFont(font);
        }
    }

    private static class GBC extends GridBagConstraints{
        public GBC(int gx, int gy){
            this.gridx = gx;
            this.gridy = gy;
        }

        public GBC(int gx, int gy, int w, int h){
            this.gridx = gx;
            this.gridy = gy;
            this.gridwidth = w;
            this.gridheight = h;
        }

        public GBC setAnchor(int a){
            this.anchor = a;
            return this;
        }

        public GBC setFill(int f){
            this.fill = f;
            return this;
        }

        public GBC setWeight(double wx, double wy){
            this.weightx = wx;
            this.weighty = wy;
            return this;
        }

        public GBC setInsets(int dist){
            this.insets = new Insets(dist, dist, dist, dist);
            return this;
        }

        public GBC setInsets(int t, int b, int l, int r){
            this.insets = new Insets(t, l, b, r);
            return this;
        }
    }
}
