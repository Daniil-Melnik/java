/*
5.  Улучшить навыки работы с сеточно-контейнерной компоновкой.
    Разработать и реализовать приложение для просмотра изображений в выбранной дериктории
  - сформировать главное окно
  - добавить в главное окно изображение
  - добавить в окно панель с лентой окружающих изображений
  - добавить в окно имя просматриваемо файла, его метаинформацию
  - добавить кнопки пролистывания, привязать к ним нажатия клавиш
  - выбор лиректории провести через главное меню
*/

package testing.LevelFive;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LevelFive {
    private static  JFrame mainFrame;

    private static final int FRAME_W = 1000;
    private static final int FRAME_H = 700;

    public static void main (String [] args){

        EventQueue.invokeLater(() -> {
            mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });

    }

    private static class MainFrame extends JFrame{
        public MainFrame(){
            setSize(FRAME_W, FRAME_H);
            setIconImage( new ImageIcon(
                    Objects.requireNonNull(LevelFive.class.getResource("/photo.png"))).getImage()
            );
            setTitle("Фотосмотр");
            setResizable(false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
    }
}
