//package ch_7;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String [] args) {


        /*try { // обёртка кода кидающего ошибку
            TestClass.printMessage("");
        } catch (TestExeption e){ // приём кидаемой ошибки
            System.out.println(e);
        }*/
        /*TestChildClass tCh = new TestChildClass(); // если родитель не кидает, то и наследник не может
        tCh.printMessage1("qq");*/

        // TestClass.TestChainOfExeptions("55555"); // Проверки цепи
        // TestClass.TestChainOfExeptions("4444");
        // TestClass.TestChainOfExeptions("333");
        // TestClass.TestChainOfExeptions("1");
        // TestClass.TestChainOfExeptions("");

        /* try (var in = new Scanner(new FileInputStream("C:\\text\\1.txt"), StandardCharsets.UTF_8)){ // try с ресурсами
            while (in.hasNext()) System.out.println(in.next());
            //Throwable t = new Throwable();
            //t.printStackTrace(); // в стеке только этот main
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        StackWalker walker = StackWalker.getInstance();
        walker.forEach(frame -> System.out.println(frame)); // в списке будет только main */

        int a = 8; // проверка утверждений
        //assert a <= 0;// assert условие;
                        // закомментировать имя пакета
                        // javac Main.java
                        // java -ea Main

        assert a <=0 : new AssertionError("Ошибка!!!"); // assert условие:выражение;
    }
}
