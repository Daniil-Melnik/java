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
import java.util.Objects;

public class LevelFour {
    private static MainFrame mainFrame;
    private static final int FRAME_W = 800;
    private static final int FRAME_H = 500;

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
            setIconImage(
                    new ImageIcon(
                            Objects.requireNonNull(this.getClass().getResource("/text.png"))
                    ).getImage());
        }
    }
}
