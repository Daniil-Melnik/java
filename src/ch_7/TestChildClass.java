package ch_7;

public class TestChildClass extends TestClass{

    @Override
    // public void printMessage1(String msg) throws TestExeption{ // нельзя дочернему кидать исключение, если родительский не кидает
    public void printMessage1(String msg){
        if (msg.equals(""));
        System.out.println(msg);
    }
}
