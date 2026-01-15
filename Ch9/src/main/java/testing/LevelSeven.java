/*
6. Сравнить работу TreeMap и HashMap
- создать по два экземпляра (по одному с 10 элементами и по одному с 500*10^3) отображений
  - TreeMap - простой конструктор
  - HashMap - конструктор с объёмом и коэффициентом заполнения
- заполнить отображения значениями HasableEntity (HashMap с у четом коэффициента заполнения),
  замерить время выполнения
- выполнить вывод каждого малого отображения в консоль через forEach()
- замерить время нахождения элемента по ключу в обоих больших через get(), getOrDefault()
- проверить существование ключа в отображении через containsKey()
- замерить время обновления записи в отображении тремя способами
  - smth.put(word, newEntity) - можно ли так?
  - smth.put(word, smth.get(word) + 1)
  - smth.put(word, smth.getOrDefault(word) + 1)
  - smth.putIfAbsent(word, 0)
    smth.put(word, smth.get(word)+1)
*/

package testing;

public class LevelSeven {
}
