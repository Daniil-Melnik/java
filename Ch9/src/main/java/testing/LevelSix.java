/*
5. Попробовать очереди по приоритету
+ создать сущность, имеющую поле приоритета и информационное целочисленное поле
- создать очередь с приоритетом
- добавлять и удалять элементы очереди с просмотром её состояния
*/

package testing;

import testing.ext.PriorityEntity;

import java.util.PriorityQueue;

public class LevelSix {
    public static void main(String [] args){ // при выводах можно наблюдать постройку кучи
                                             // (самоорганизующегося двоичного дерева)

        // корень дерева - нулевой элемент
        // Левый потомок: 2*i + 1
        // Правый потомок: 2*i + 2

        // Минимальная куча - любой из двух потомков больше (по значению числа) родителя
        // Двоичное дерево поиска - левый потомок больше родителя
        //                        - правый потомок меньше родителя

        // поиск в минимальной куче дорогой (O(n))
        // поиск в BST - дешевле (O(log(n)) - O(n))

        PriorityQueue<PriorityEntity> priorityEntities = new PriorityQueue<>();

        priorityEntities.add(new PriorityEntity(0,121));
        System.out.println(priorityEntities);

        priorityEntities.add(new PriorityEntity(1,15));
        System.out.println(priorityEntities);

        priorityEntities.add(new PriorityEntity(3,25));
        System.out.println(priorityEntities);

        priorityEntities.add(new PriorityEntity(4,14));
        System.out.println(priorityEntities);

        priorityEntities.add(new PriorityEntity(5,25));
        System.out.println(priorityEntities);

        priorityEntities.remove();
        System.out.println(priorityEntities);

        priorityEntities.add(new PriorityEntity(0,16));
        System.out.println(priorityEntities);

        priorityEntities.add(new PriorityEntity(6,16));
        System.out.println(priorityEntities);

        priorityEntities.add(new PriorityEntity(2,16));
        System.out.println(priorityEntities);

        /*                        16(0)
                   15(1)                          16(2)
              25(5)      14(4)                16(6)    25(3)

         */

        // 16(0) 15(1) 16(2) 25(5) 14(4) 16(6) 25(3)
    }
}
