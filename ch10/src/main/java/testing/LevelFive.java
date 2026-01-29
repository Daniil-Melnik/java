/*
5. Освоить перехват событий от мыши. Часть 1. Добавление квадратов/окружностей по клику
- создать фрейм, выделить в нём панель с отличным по цвету фоном
- реализовать внутри панели компонент, отрисовывающий квадраты/окружности в произвольном количестве
  -- ввести в компонент параметры-массивы отвечающие за фигуры
    --- создать внутренний класс, хранящий тип фигуры, координаты угла, ширину и высоту, цвет
    --- присвоить массиву в компоненте этот тип
  -- ввести в метод отрисовки рисование по массивам
- привязать к панели действия мыши: л-щелчок - квадрат, п-щелчок - окружность на место клика
*/

package testing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public class LevelFive {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 300;

    public static void main(String... args) {
        EventQueue.invokeLater(() -> {
            FrameFive frame = new FrameFive(WIDTH, HEIGHT);
            frame.setVisible(true);
        });
    }


    static class FrameFive extends JFrame {

        private static final int MARGIN_TOP = 5;
        private static final int MARGIN_LEFT = 5;

        public FrameFive(int w, int h) { // фрейм приложения, принимает высоту и ширину
            setLayout(null);
            setSize(w, h);
            addWindowListener(new LevelThree.WindowTerminator()); // установка перехватчика оконных событий
            setTitle("LevelFive");
            setIconImage(new ImageIcon(
                    Objects.requireNonNull(
                            this.getClass().getResource("/images/5.png")
                    )
            ).getImage());

            ComponentFive comp = new ComponentFive(); // введение в фрейм компонента для рисования фигур
            comp.setBounds(MARGIN_LEFT, MARGIN_TOP, w - MARGIN_LEFT * 5, h - MARGIN_TOP * 10);
            comp.addMouseListener(new MouseClicker(comp)); // добавление к компоненту перехватчика событий от мыши
            add(comp);
        }
    }

    static class ComponentFive extends JComponent { // формирования компонента для отрисовки
        private LinkedList<ShapeMod> shapes; // связный список спец. объектов (квадратов и окружностей) наносимых на компонент

        public ComponentFive() {
            shapes = new LinkedList<>();
        }

        public void appendShape(Shape s, boolean t) {
            shapes.add(new ShapeMod(s, t)); // метод для добавления фигуры в список фигур, все фигуры этого списка отрисовываются по циклу
        }

        @Override
        protected void paintComponent(Graphics g) { // отрисовка компонента
            RectangleMod rect; // переменные для хранения рисуемого квадрата/окружности
            EllipsMod ells;

            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3));
            g2.setColor(Color.RED);
            for (ShapeMod fO : shapes) { // перебор экземпляров квадратов/окружностей
                if (!fO.getType()) { // если тип - false, то это квадрат
                    rect = (RectangleMod) fO.getShape(); // преобразование более общего Shape в оболочку для Rectangle2D
                    // через приведение типов
                    ells = null; // переменная для окружности зануляется
                } else { // если тип фигуры - true, то это окружность
                    ells = (EllipsMod) fO.getShape(); // получение из более общего Shape кокретной оболочки для Ellipse2D
                    // через приведение типов
                    rect = null; // параметр квадрата зануляется
                }

                // одна из фигур будет нулевой
                g2.setColor(rect == null ? ells.getColor() : rect.getColor()); // установка цвета по ненулевой фигуре
                g2.draw(rect == null ? ells : rect); // рисование ненулевой фигуры
            }
        }
    }

    static class ShapeMod { // оболочка для хранения фигуры по более общему интерфейсу Shape
        private Shape shape; // интерфейсный параметр Shape, пригоден для хранения расширений Rectangle2D и Ellipse2D
        private boolean type; // параметр типа фигуры true - окружность
        //                      false - квадрат

        public ShapeMod(Shape s, boolean t) {
            shape = s;
            type = t;
        }

        public ShapeMod() {
            this(null, false);
        }

        public boolean getType() {
            return type;
        }

        public Shape getShape() {
            return shape;
        }
    }

    static class RectangleMod extends Rectangle2D.Double { // оболочка для хранения Rectangle2D.Double вместе с цветом
        private Color color; // параметр цвета

        public RectangleMod(double x, double y, double h, double w, Color c) {
            super(h, w, x, y);
            color = c;
        }

        public RectangleMod() {
            super();
            color = Color.BLACK;
        }

        public Color getColor() {
            return color;
        }
    }

    static class EllipsMod extends Ellipse2D.Double { // оболочка для хранения Ellipse2D.Double вместе с цветом
        private Color color;

        public EllipsMod(double x, double y, double h, double w, Color c) {
            super(h, w, x, y);
            color = c;
        }

        public EllipsMod() {
            super();
            color = Color.BLACK;
        }

        public Color getColor() {
            return color;
        }
    }

    static class MouseClicker extends MouseAdapter { // класс перехватчика событий мыши на основе адаптера

        private ComponentFive component; // параметр, содержащий ссылку на компонент, для которого был создан

        public MouseClicker(ComponentFive c) {
            component = c;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            Random random = new Random();

            int iR = Math.abs((e.getX() + e.getY() + 100) % 255);
            int iG = Math.abs((e.getX() + e.getY() + 200) % 255);
            int iB = Math.abs((e.getX() + e.getY()) % 255);

            Color randColor = new Color(iR, iG, iB); // создание соответсвующего цвета

            switch (e.getButton()) { // разбиение по нажатой кнопке мыши
                case 1: // нажата ЛКМ - добавить квадрат
                    component.appendShape(
                            new RectangleMod(10, 10, e.getX(), e.getY(), randColor), false); // добавление квадрата в список фигур компонента
                    break;
                case 3: // нажата ПКМ - добавить окружность
                    component.appendShape(
                            new EllipsMod(10, 10, e.getX(), e.getY(), randColor), true); // добавление окружности в список фигур компонента
                    break;
                default: // по умолчанию (СКМ не учитывается) - вывести отладочное сообщение
                    System.out.println("middle pressed");
                    break;
            }

            component.repaint(); // перерисовка компонента
        }
    }
}
