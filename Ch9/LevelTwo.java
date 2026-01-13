/*
2. Проверить работу нескольких итераторов
- на созданных ArrayList, LinkedList создать два итератора
- создать метод печати спика на основе forEachRemaning
- попробовать выполнить remove раньше next
- выполнить remove после next, посмотреть результат на списке
- выполнить add через ListIterator, посмотреть результат на списке
- выполнить remove после previous, посмотреть результат на списке
- выполнить set после next, посмотреть результат на списке
- создать два итератора:
- - первый пустить вперед, изменить одно значение (удалить/ваставить/установить)
- - запустить второй вдогонку, проверить что будет ArrayList
- - выполнить перхват исключения модификации списка LinkedList
*/

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.StringJoiner;

public class LevelTwo {
    public static void main(){
        List<String> linkedList = new LinkedList<>(List.of("1", "2", "3", "4", "5", "6", "7", "8","9", "10")); // формировнаие изменяемых списков через маленькие 
        List<String> arrayList = new ArrayList<>(List.of("1", "2", "3", "4", "5", "6", "7"));   // неизменяемые коллекции (не более 10 элементов)

        ListIterator<String> linkedIterator1 = linkedList.listIterator(); // два лист-итератора для связного списка
        ListIterator<String> linkedIterator2 = linkedList.listIterator();

        ListIterator<String> arrayIterator1 = arrayList.listIterator(); // два лист-итератора для списочного массива
        ListIterator<String> arrayIterator2 = arrayList.listIterator();

        // попытка удаления элемента перед проходом
        // removeBeforeNext(linkedIterator1); // при попытке удалить перед проходом хотябы одного элемента - ОШИБКА

        // удаление элемента после прохода
        // removeAfterNext(arrayList);
        // removeAfterNext(linkedList); // будет удалён элемент со значением "1"

        // добавление элемента в список через ListIterator (подчинение Iterator)
        // addToListByListIterator(arrayList); // будет добавлен элемент в начало списка
        
        // удаление после previous
        // removeAfterPrevious(linkedList); // будут пройдены вперёд 1 2 3 4, пройден назд 4, удалится 4

        // установка значения после перехода
        // setAfterNext(linkedList); // будут пройдены 1 2 3 4 будет установлено 99 на место 4

        // проверка двух итераторов
        // ============== проверка на ArrayList ==============
        /* try {
            System.out.println(arrayIterator1.next()); // при попытке чтения итератором2 после измененения длины списка итератором1 (через REMOVE)
            System.out.println(arrayIterator2.next()); // выходит ошибка ConcurrentModificationException
            System.out.println(arrayIterator1.next());
            arrayIterator1.remove();
            System.out.println(arrayIterator2.next());
            System.out.println(arrayIterator1.next());
            System.out.println(arrayIterator2.next());
        } catch (ConcurrentModificationException e){
            System.out.printf("Ошибка конкуренции! %s %s", e.getMessage(), e.getClass().toString());
        }*/ 

        // ============== проверка на LinkedList ==============
        try {
            System.out.println(linkedIterator1.next()); // при попытке чтения итератором2 после измененения длины списка итератором1 (через ADD)
            System.out.println(linkedIterator2.next()); // выходит ошибка ConcurrentModificationException
            System.out.println(linkedIterator1.next());
            linkedIterator1.add("00");
            System.out.println(linkedIterator2.next());
            System.out.println(linkedIterator1.next());
            System.out.println(linkedIterator2.next());
        } catch (Exception e){
            System.out.printf("Ошибка конкуренции! %s %s", e.getMessage(), e.getClass().toString());
        }

    }

    private static void printList(List<String> list){
        StringJoiner sJoiner = new StringJoiner(" ");
        list.iterator().forEachRemaining(str -> sJoiner.add(str)); // печать всего списка через forEachRemaining
        System.out.println(sJoiner.toString());
    }

    private static void removeBeforeNext(Iterator iterator){ // попытка удаления до вызова next
        iterator.remove();
    }

    private static void removeAfterNext(List<String> list){ // удаление после вызова next
        Iterator<String> iterator = list.iterator();

        printList(list); // печать начального состояния списка

        iterator.next(); // переход на один элемент к концу списка
        iterator.remove(); // удаление пройденного элемента

        printList(list); // печать конечного состояния списка
    }

    private static void addToListByListIterator(List<String> list){
        ListIterator<String> listIterator = list.listIterator(); // получение объекта ListIterator

        printList(list);
        listIterator.add("XX"); // добавление элемента на нулевую позицию
        printList(list);
    }

    private static void removeAfterPrevious(List<String> list){
        ListIterator<String> listIterator = list.listIterator();

        printList(list);
        
        System.out.println(listIterator.next()); // печать пройденных прямо элементов
        System.out.println(listIterator.next());
        System.out.println(listIterator.next());
        System.out.println(listIterator.next());

        System.out.println(listIterator.previous()); // печать пройденного назад элемента

        listIterator.remove(); // удаление элемента
        
        printList(list);
    }

    private static void setAfterNext(List<String> list){
        ListIterator<String> listIterator = list.listIterator();

        printList(list);
        
        System.out.println(listIterator.next()); // печать пройденных прямо элементов
        System.out.println(listIterator.next());
        System.out.println(listIterator.next());
        System.out.println(listIterator.next());

        listIterator.set("99"); // установка значения

        printList(list);
    }
}
