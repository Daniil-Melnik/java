package Interfaces;

public interface TestInt4 {
    default int getSum(int a, int b){
        return a + b + a + b;
    }
}
