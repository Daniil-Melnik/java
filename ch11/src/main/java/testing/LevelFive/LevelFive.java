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
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LevelFive {
    private static  JFrame mainFrame;

    private static final int FRAME_W = 1000;
    private static final int FRAME_H = 700;

    private static final int PHOTO_PANEL_H = 500;

    private static final int SLIDE_PANEL_H = 150;

    private static final int BUTTON_PANEL_H = 50;

    private static PhotoHolder holder = new PhotoHolder();

    public static void main (String [] args){

        EventQueue.invokeLater(() -> {
            mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });

    }

    private static class MainFrame extends JFrame{
        public MainFrame(){
            setLayout(new GridBagLayout());
            setSize(FRAME_W, FRAME_H);
            setIconImage( new ImageIcon(
                    Objects.requireNonNull(LevelFive.class.getResource("/photo.png"))).getImage()
            );
            setTitle("Фотосмотр");
            setResizable(false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            add(new PhotoPanel(), new GBC(0, 0, 1, 1));
            add(new PhotoSlidePanel(), new GBC(0, 1, 1, 1));
            add(new ButtonPanel(), new GBC(0, 2, 1, 1));
            setJMenuBar(new MainMenuBar());
            pack();
        }
    }


    private static class PhotoPanel extends JPanel{

        public PhotoPanel(){
            setBackground(Color.BLUE);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(FRAME_W, PHOTO_PANEL_H);
        }
    }

    private static class PhotoSlidePanel extends JPanel{
        public PhotoSlidePanel(){
            setBackground(Color.RED);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(FRAME_W, SLIDE_PANEL_H);
        }
    }

    private static class ButtonPanel extends JPanel{
        public ButtonPanel(){
            setBackground(Color.GREEN);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(FRAME_W, BUTTON_PANEL_H);
        }
    }

    private static class MainMenuBar extends JMenuBar{
        public MainMenuBar(){

            JMenu mainMenu = new JMenu("Файл");

            JMenuItem setFolderItem = new JMenuItem("Папка");

            mainMenu.add(setFolderItem);
            setFolderItem.addActionListener((e) -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (fileChooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION){
                    holder.setImageList(fileChooser.getSelectedFile().listFiles());
                }
            });

            add(mainMenu);
        }
    }

    private static class PhotoHolder{
        private CursorList<Image> imageList;

        public PhotoHolder(){
            imageList = new CursorList<>(100);
        }

        public void setImageList(File [] files){
            imageList = new CursorList<>(getImagesFromFileArray(files));
            imageList.setCurrentIndex(0);
        }

        public CursorList<Image> getImageList(){
            return imageList;
        }

        private List<Image> getImagesFromFileArray(File [] files){
            String fileName;
            List<Image> result = new ArrayList<>();
            for (File f : files){
                fileName = f.toString().toLowerCase();
                if (f.isFile() &&
                        fileName.endsWith("png") || fileName.endsWith("jpg") || fileName.endsWith("jpeg")) {
                    result.add(Toolkit.getDefaultToolkit().getImage(f.toString()));
                }
            }
            return result;
        }
    }
}
