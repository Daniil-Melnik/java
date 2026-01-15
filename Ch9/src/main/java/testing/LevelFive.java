/*
4. Попробовать одно- и двух- направленные очереди (набить и перебрать элементы)
+ создать по два объекта (один - фикс. длины через ArrayBlockingDeque другой через ArrayDeque) очередей Queue<>
  и Dequeue<> (LinkedBlockingList и ArrayDeque) для хранения сущностей Entity
+ заполнить Queue через add() и offer() вплоть до заполнения, посмотреть результат
  + посмотреть элемент в голове заполненной очереди через element() и peek()
  + удалить все элементы из очереди методами remove() и poll() с печатью возвращенных значений из возврата, посмотреть результат
  + посмотреть элемент в голове пустой очереди через element() и peek()
+ заполнить Dequeue 10-ю элементами до полного состояния любым методом и попробовать добавить излишний через addFirst() и offerLast()
  + попробовать посмотреть не удаляя элемент через peekFirst() getLast()
  + удалить с печатью возврата все элементы любым способом, после удаления попробовать методы pollFirst() и removeLast()
  + посмотреть голову/хвост пустой очереди getFirst() и peekLast()
*/

package testing;

import testing.ext.Entity;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class LevelFive {

    private static final int SIZE = 10;

    public static void main(String [] args){

        System.out.println("ОДНОСТОРОННЯЯ ОЧЕРЕДЬ\n\n"); // начало тестов 1-очереди

        Queue<Entity> queueEntity1 = new ArrayBlockingQueue<>(SIZE); // создание ограниченной в размере 1-очереди
        Queue<Entity> queueEntity2 = new ArrayDeque<>(); // создание неограниченной в размере очереди 1-очереди
        // также есть LinkedBlockingQueue<>()

        Deque<Entity> dequeEntity1 = new LinkedBlockingDeque<>(SIZE); // создание ограниченной в размере 2-очереди
        Deque<Entity> dequeEntity2 = new ArrayDeque<>(); // создание неограниченной в размере 2-очереди

        for (int i = 0; i < SIZE/2; i++){
            queueEntity1.add(new Entity(i)); // заполнение первой 1/2 1-очереди через генерирующего ошибку add()
            queueEntity2.add(new Entity(i));
        }

        for (int i = SIZE/2; i < SIZE; i++){
            queueEntity1.offer(new Entity(i)); // заполнение первой 1/2 1-очереди через НЕгенерирующего ошибку offer()
            queueEntity2.offer(new Entity(i));
        }

        System.out.println(queueEntity1.offer(new Entity(100))); // попытка добавить в полный ограниченный - false
        System.out.println(queueEntity2.offer(new Entity(100))); // попытка добавить в полный НЕограниченный - true

        System.out.println();

        try{
            System.out.println(queueEntity1.add(new Entity(100))); // попытка добавить в полный ограниченный - ОШИБКА
        } catch (IllegalStateException e){
            System.out.println("1-Очередь переполнена! - " + e.getMessage() + " - " + e.getClass());
        }

        System.out.println(queueEntity2.add(new Entity(100))); // попытка добавить в полный НЕограниченный - ничего

        System.out.println(queueEntity1.peek()); // взять голову ограниченной очереди
        System.out.println(queueEntity2.element()); // взять голову неограниченной очереди

        for (int i = 0; i < SIZE; i++){ // удаление с печатью всей очереди через генерирующий ошибку метод remove
            System.out.printf("%s ", queueEntity1.remove().toString());
        }

        System.out.println();

        System.out.println(queueEntity1.poll()); // попытка удалить голову из 1-очереди НЕгенерирующим методом poll()
                                                 // выдаст null

        try{ // попытка удалить голову из 1-очереди генерующим методом remove() - выдаст ОШИБКУ
            System.out.println(queueEntity1.remove());
        } catch (NoSuchElementException e){
            System.out.println("Ошибка удаления из 1-очереди (пусто)! - " + e.getMessage() + " - " + e.getClass());
        }

        System.out.println(queueEntity1.peek()); // попытка взять без удаления голову 1-очереди НЕгенерирующим методом peek() - null

        try{ // попытка взять без удаления голову 1-очереди генерирующим методом element() - выдаст ОШИБКУ
            System.out.println(queueEntity1.element());
        } catch (NoSuchElementException e){
            System.out.println("Ошибка получения головы 1-очереди (пусто)! - " + e.getMessage() + " - " + e.getClass());
        }

        System.out.println("\n\nДВУХСТОРОННЯЯ ОЧЕРЕДЬ\n"); // начало тестов 2-очереи

        for (int i = 0; i < SIZE/2; i++){ // заполнение 1/2 2-очередей генерирующим методом addFirst() и НЕгенерирующим методом offerFirst
            dequeEntity1.addFirst(new Entity(SIZE - i - 1));
            dequeEntity2.offerFirst(new Entity(SIZE - i));
        }

        for (int i = SIZE/2; i < SIZE; i++){ // заполнение 1/2 2-очередей генерирующим методом addLast() и НЕгенерирующим методом offerLast
            dequeEntity1.offerLast(new Entity(SIZE - i - 1));
            dequeEntity2.addLast(new Entity(SIZE - i));
        }

        System.out.println(dequeEntity1.offerLast(new Entity(1000))); // попытка добавить в хвост ограниченной 2-очереди
                                                                         // НЕгенерирующим методом offerLast() - false

        try {
            dequeEntity1.addLast(new Entity(1000)); // попытка добавить в хвост ограниченной 2-очереди
                                                       // генерирующим методом addLast() - ОШИБКА
        } catch (IllegalStateException e) {
            System.out.println("Ошибка добавления в 2-очередь (полная)! - " + e.getMessage() + " - " + e.getClass());
        }

        System.out.println(dequeEntity2.offerLast(new Entity(1000))); // попытка добавить в хвост НЕограниченной 2-очереди
                                                                         // НЕгенерирующим методром offerLast() - true
        System.out.println(dequeEntity2);

        try {
            dequeEntity2.addLast(new Entity(1000)); // попытка добавить в хвост НЕограниченной 2-очереди
                                                       // генерирующим методом addLast() - ничего не произойдёт
        } catch (IllegalStateException e) {
            System.out.println("Ошибка добавления в 2-очередь (полная)! - " + e.getMessage() + " - " + e.getClass());
        }

        System.out.printf("%s -- %s\n", dequeEntity1.getFirst(), dequeEntity1.peekLast()); // попытка взять границы полной 2-очереди
        System.out.printf("%s -- %s\n", dequeEntity2.peekFirst(), dequeEntity2.getLast());

        for (int i = 0; i < SIZE/2; i++ ){
            System.out.printf("%s ", dequeEntity1.removeFirst().toString()); // удаление первой 1/2 генерирующим методом removeFirst()
        }

        for (int i = SIZE/2; i < SIZE; i++ ){
            System.out.printf("%s ", dequeEntity1.removeLast().toString()); // удаление второй 1/2 генерирующим методом removeLast()
        }

        System.out.println();

        System.out.println(dequeEntity1.pollLast()); // попытка удалить хвост пустой 2-очереди НЕгенерирующим методом pollLast() - false

        try{ // попытка удалить голову пустой 2-очереди генерирующим методом removeFirst() - ОШИБКА
            System.out.println(dequeEntity1.removeFirst());
        } catch (NoSuchElementException e){
            System.out.println("Ошибка удаления из 2-очереди! - " + e.getMessage() + " - " + e.getClass());
        }

        System.out.println(dequeEntity1.peekFirst()); // попытка взять голову пустой 2-очереди НЕгенерирующим методом peekFirst() - false

        try{ // попытка взять хвост пустой 2-очереди генерирующим методом getFirst() - ОШИБКА
            System.out.println(dequeEntity1.getLast());
        } catch (NoSuchElementException e){
            System.out.println("Ошибка чтения конца 2-очереди - " + e.getMessage() + " - " + e.getClass());
        }

        System.out.println("\n\nТЕСТИРОВАНИЕ 2-ОЧЕРЕДИ\n");

        Deque<String> testDeque = new ArrayDeque<>();

        testDeque.addFirst("1"); // вставить элемент в голову

        System.out.println(testDeque);

        testDeque.addLast("10"); // вставить элемент в хвост

        System.out.println(testDeque);

        testDeque.offerLast("2"); // вставить элемент в хвост

        System.out.println(testDeque);

        testDeque.addLast("20"); // вставить элемент в хвост

        System.out.println(testDeque);

        testDeque.pollFirst(); // удалить голову

        System.out.println(testDeque);

        testDeque.removeFirst(); // удалить голову

        System.out.println(testDeque);

        System.out.printf("size = %d", testDeque.size()); // посмотреть размер списка
    }
}
