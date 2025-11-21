package Interfaces;

public interface TestInt2 extends TestInt {
    int P = 234;

    String getInfo2();

    default String getInfo3(){
        return this.getInfo2();
    }
}
