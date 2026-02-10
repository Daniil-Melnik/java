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

package testing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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
        private Font font = new Font("Arial", Font.PLAIN, 16);

        @Override
        protected void paintComponent(Graphics g) {
            g.setFont(font);

            FontMetrics fm = getFontMetrics(font);
            int strWidth = fm.stringWidth(text);
            int strHeight = fm.getHeight();

            g.drawString(text, (TEXT_PANEL_W - strWidth) / 2,(TEXT_PANEL_H - strHeight) / 2);
        }

        @Override
        public void setFont(Font f) {
            font = f;
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
    - Содержание (кастомное диалоговое окно)
    - Шрифт
      -- гарнитура
      -- начертание
    - цвет
      -- наборный
      -- константный

    размер - элемент на панели

    панель с информацией от текущих настройках + размеры строки в пикселях
    */

    private static class MainMenuBar extends JMenuBar{
        public MainMenuBar(){
            JMenu fileMenu = new JMenu("Файл");
            JMenu textMenu = new JMenu("Содержание");
            JMenu fontMenu = new JMenu("Шрифт");
            JMenu colorMenu = new JMenu("Цвет");

            add(fileMenu);
            add(textMenu);
            add(fontMenu);
            add(colorMenu);
        }
    }
}
