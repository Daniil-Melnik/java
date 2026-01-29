/*
1. Освоить построение (на низком уровне) простого фрейма с компонентом с несколькими строками разного цвета
- создать главный поток с созданием и инициализацией фрейма
- установить имя фрейма, иконку, изменяемость размера
- установить для фрейма размер основываясь на Toolkit getDefaultToolkis.getScreenSize

- создать компонент в виде внутреннего класса
- дать компоненту getPrefferedSize, добавить в компонент 4 строки различного цвета через paintComponent()
*/

// фрейм = окно

package testing;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LevelOne {
    public static void main(String[] args) { // основной поток выполнения
        EventQueue.invokeLater(() -> // запуск потоке (очереди событий) выполнения Swing
                {
                    FrameOne frO = new FrameOne();// создание экземпляра фрейма
                    frO.setVisible(true); // установка видимости фрейма
                }
        );
    }


    static class FrameOne extends JFrame { // внутренний класс-описание фрейма

        public FrameOne() { // конструктор фрейма, базовые его настройки

            setTitle("LevelOne"); // установка имени фрейма (окна)

            setIconImage(new ImageIcon(Objects.requireNonNull( // установка иконки
                    this.getClass().getResource("/images/100.png")) // в реалиях maven - через ресурсы
            ).getImage());

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // при закрытии окна - прекращение работы программы

            setResizable(false); // запрет на изменение размера окна

            add(new Component1()); // добавление компонента со строками в окно
            pack(); // упаковка окна на основании размеров по умолчанию от getPreferredSize() компонента(ов)
        }
    }

    static class Component1 extends JComponent { // внутренний класс-описание компонента

        private static final int PREF_HEIGHT = 80; // высота по умолчанию
        private static final int PREF_WIDTH = 500; // ширина по умолчанию

        // РЕАЛИЗАЦИЯ КАСТОМНОЙ ОТРИСОВКИ КОМПОНЕНТОВ
        @Override
        public void paintComponent(Graphics g) { // метод, отвечающий за рисование в компоненте
            // g - инструмент для пользовательского рисования

            g.setColor(Color.BLUE); // смена цвета на синий
            g.drawString("Hello!", 0, 20); // нанесение строки синим цветом
            g.setColor(Color.RED); // смена цвета на красный
            g.drawString("Hello!", 50, 20); // нанесение строки красным цветом
            g.setColor(new Color(18, 184, 123)); // установка цвета через кнструктор цвета
            g.drawString("Hello!", 100, 20); // нанесение строки сконструированным цветом
        }

        @Override
        public Dimension getPreferredSize() { // переопределение метода выдачи размеров по умолчанию
            return new Dimension(PREF_WIDTH, PREF_HEIGHT);
        }
    }
}