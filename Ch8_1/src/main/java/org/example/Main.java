package org.example;

import org.example.classes.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        // ============= тестирование простого обобщенного класса
        /* TestClass0<TestClass1> tester1 = new TestClass0<>(new TestClass1(5), new TestClass1(6));
        tester1.prntOfPair();

        TestClass0<TestClass2> tester2 = new TestClass0<>(new TestClass2(5, "test1"), new TestClass2(6, "test2"));
        tester2.prntOfPair();*/

        // ============= тестирование обобщенного метода

        /*getStringOfObject(new TestClass1(6)); // применение обобщенного метода (не обязательно метод д. б. static)
        getStringOfObject2(new TestClass21()); // применение обобщенного метода с ограничениями сверху*/

        // ============= тестирование обобщенного класса с ограничением

        /*TestClass3<TestClass21> testClass21_1 = new TestClass3<>(new TestClass21());
        System.out.println(testClass21_1.toString());*/

        // ============= тестирование подстановочного типа с ограничением сверху
        /* TestClass21 t1 = new TestClass21(1, "test_1");
        TestClass21 t2 = new TestClass21(2, "test_2");
        testWildcardsExt(new TestClass0<TestClass21>(t1,t2));*/

        // ============= тестирование подстановочного типа с ограничением снизу
        TestClass21 t1 = new TestClass21(1, "test_1");
        TestClass21 t2 = new TestClass21(2, "test_2");
        testWildcardsSup(new TestClass0<TestClass21>(t1, t2));
        
    }

    public static  <T> void getStringOfObject(T el){ // обобщенный метод
        System.out.println(el.toString());
    }

    public static <T extends TestClass2> void getStringOfObject2(T el){ // обобщенный метод с ограничением extends
        System.out.println(el.toString());
    }

    public static void testWildcardsExt(TestClass0<? extends TestClass2> t){ // тестирование подстановочного типа с ограничением сверху
        t.prntOfPair();
    }

    public static void testWildcardsSup(TestClass0<? super TestClass211> t){ // тестирование подстановочного типа с ограничением снизу
        t.prntOfPair();
    }
}
