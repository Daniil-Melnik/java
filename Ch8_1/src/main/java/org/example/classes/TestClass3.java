package org.example.classes;

public class TestClass3<T extends TestClass2>{ // ограничение сверху
    private T el;
    public TestClass3(T el){this.el = el;}
    public TestClass3(){this(null);}

    @Override
    public String toString() {
        return el.toString();
    }
}
