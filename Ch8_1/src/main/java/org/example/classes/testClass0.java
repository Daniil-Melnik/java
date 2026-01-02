package org.example.classes;

public class TestClass0<T> {
    private T el1;
    private T el2;

    public TestClass0(){ // конструктор без параметров
        this(null, null);
    }

    public TestClass0(T el1, T el2){ // конструктор с параметрами
        this.el1 = el1;
        this.el2 = el2;
    }

    public void prntOfPair(){
        System.out.printf("%s - %s\n", el1.toString(), el2.toString());
    }
}
