package org.example.classes;

public class TestClass2{
    private String desc;
    private int num;

    public TestClass2(int i, String str){
        num = i;
        desc = str;
    }

    public TestClass2(){this(0,"");}

    public int getNum() {
        return num;
    }

    public String getDesc(){
        return desc;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("testClass2").append(String.format("- %d - %s", num, desc));
        return result.toString();
    }
}
