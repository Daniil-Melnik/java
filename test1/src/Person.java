public class Person {
    private String name;
    private byte age;

    public byte getAge() {return age;}
    public String getName() {return name;}

    public Person (String name, byte age){
        this.age = age;
        this.name = name;
    }

    public  Person(){
        this("Ivan", (byte) 25);
    }

    private int privateMeth(){ return 8; }

    @Override
    public int hashCode(){
        return name.hashCode() + age;
    }

    public int plusMinus(int a, int b) {return  a + b;}

    // освоение переменного числа аргументов
     public static int summ (int... args){
        int s = 0;
        for (int i : args){
            s += i;
        }
        return s;
    }
}
