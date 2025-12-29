package ch_7;

import ch_7.Exeptions.TestExepption1;
import ch_7.Exeptions.TestExeption0;
import ch_7.Exeptions.TestExeption2;

public class TestClass {
    public static void printMessage(String msg) throws TestExeption0 {
        if (msg.equals("")) throw new TestExeption0("Пустое сообщение");
        System.out.println(msg);
    }

    public void printMessage1(String msg) {
        if (msg.equals(""));
        System.out.println(msg);
    }

    public static void TestChainOfExeptions(String message){
        try{
            if (message.length() < 5){
                TestExeption0 e0 = new TestExeption0("less than 5");
                throw e0;
            }
            else System.out.println("OK");
        } catch (TestExeption0 e0m) {
            printExeptionTrace(e0m);
            try {
                if (message.length() < 3) {
                    TestExepption1 e1 = new TestExepption1("less than 3");
                    e1.initCause(e0m);
                    printExeptionTrace(e0m, e1);
                    throw e1;
                }
                else System.out.println("OK1");
            } catch (TestExepption1 e1m) {
                printExeptionTrace(e1m.getCause(), e1m);
                try {
                    if (message.length() == 0) {
                        TestExeption2 e2 = new TestExeption2("poor");
                        e2.initCause(e1m);
                        throw e2;
                    }
                    else System.out.println("OK2");
                } catch (TestExeption2 e2m) {
                    printExeptionTrace(e2m.getCause().getCause(), e2m.getCause(), e2m);
                    // System.out.printf("%s -> %s -> %s", e2m.getCause().getCause(), e2m.getCause(), e2m); наглядная цепь
                }
                finally {
                    System.out.println("Всё равно выполняестя");
                }
            }
        }
    }

    private static void printExeptionTrace (Throwable ... exceptions){ // получение цепи через метод с произвольным набором ошибок
                                                                       // применение StringBuilder
                                                                       // статический метод TestChainOfExeptions может только статический printExeptionTrace
        StringBuilder exceptionsStr = new StringBuilder();
        for (Throwable e : exceptions) exceptionsStr.append(String.format("%s\n", e.getMessage()));
        System.out.println(String.format("%s\n", exceptionsStr.toString()));
    }
}
