package org.example.classes;

public class TestClass1 {
    private int num;

    public TestClass1(int i) {num = i;}
    public TestClass1(){this(0);}

    public int getNum() {
        return num;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("testClass1").append(String.format("- %d", num));
        return result.toString();
    }
}
