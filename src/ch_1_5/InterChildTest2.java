import Interfaces.TestInt4;

public class InterChildTest2 extends InterSuperTest2 implements TestInt4 {
    public void getSumm(){
        System.out.println(getSum(1,2));
    }
}
