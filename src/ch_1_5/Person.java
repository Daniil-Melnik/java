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

    public String getPersName2(){ // способы создания экземпляров внутреннего класса в экземпляре внешнего класса
        InterClass i1 = this.new InterClass();
        InterClass i2 = new InterClass(); // без неявного параметра this

        return  String.format("%s %s", i1.getPersName(), i2.getPersName());
    }

     public class InterClass {
        public String getPersName(){ return Person.this.name;}
    }
}
