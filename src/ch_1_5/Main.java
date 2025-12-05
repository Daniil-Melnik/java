//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import Interfaces.TestInt5;
import Interfaces.FunctInt1;
import Interfaces.TestInt6;

//import static jdk.internal.org.jline.utils.Colors.s;

void main() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException, NoSuchFieldException {
    /* Oper1 [] operArr = new Oper1[3];

    operArr[0] = new Oper1();
    operArr[1] = new Oper1();
    operArr[2] = new Oper2();


    for (Oper1 op : operArr){
        System.out.println(op.doOper(1,2));
    }

    System.out.println(((Oper2) operArr[2]).doOper2(1,2));*/

    /*Person p0 = new Person();
    Person p1 = new Person("Lena", (byte) 23);
    Person p2 = new Person("Lena", (byte) 23);

    // System.out.printf("p = %d; p1 = %d; p2 = %d", Objects.hash(p0), Objects.hash(p1), Objects.hash(p2));

    System.out.println(p0.toString());
    System.out.println(Integer.valueOf(4).toString());*/

    // System.out.println(Person.summ(1,2,3,4,5,6,7,8,9,0));

    // System.out.println(Person.class.getConstructor().newInstance().getName());

    // RecorceTest.readJsonFile();

    // for (Method m : Person.class.getDeclaredMethods()) System.out.println(Modifier.toString(m.getModifiers()));

    /* Person [] newPerson = new Person[2];
    newPerson[0] = new Person("Popuass", (byte) 36);
    newPerson[1] = new Person("Ded", (byte) 6); */

    /* Field f = Person.class.getDeclaredField("name");
    f.setAccessible(true);
    System.out.println(f.get(newPerson[0]));*/

    /*for (Person p : newPerson)
        StringUtils.universalToString(p);*/

    // Method m1 = Person.class.getDeclaredMethod("getName");
    // System.out.println(m1.invoke(newPerson[0]));

    // Method m3 = Person.class.getDeclaredMethod("summ", int[].class);
    // Method m3 = Person.class.getDeclaredMethod("summ", int[].class);

    // System.out.println(m3.invoke(null, (Object) new int [] {1, 2, 3, 4, 5, 6, 7, 8, 9}));

    /* Person p = Person.class.getConstructor(String.class, byte.class).newInstance("Nikich", (byte) 67);
    System.out.println(p.getName()); */
    /*InterTest [] iTestArr = new InterTest[3];

    iTestArr[0] = new InterTest();
    iTestArr[1] = new InterTest("Andrey", "Andreev");
    iTestArr[2] = new InterTest("Sveta", "Svenkova");*/

    //Arrays.sort(iTestArr);

    // for (InterTest i : iTestArr) System.out.printf("%s %s\n", i.getName(), i.getName2());

    // System.out.println(InterTest.P);

    //System.out.println(iTestArr[0].getInfo3());
    //(new InterChildTest2()).getSumm();

    /*Person [] pesArr = new Person[4];
    pesArr[0] = new Person("Galla", (byte) 16);
    pesArr[1] = new Person("Halla", (byte) 10);
    pesArr[2] = new Person("Ialla", (byte) 12);
    pesArr[3] = new Person("Jalla", (byte) 11);

    Arrays.sort(pesArr, new ObjComparator());

    for (Person p : pesArr) System.out.printf("%s %s\n", p.getName(), p.getAge());*/

    /*BiConsumer<String, String> wqe = (String s1, String s2) -> System.out.println(((new StringBuilder()).append(s1).append(s2)).toString());
    wqe.accept("Hello, ", "world!");*/

    // testL((str) -> System.out.println(str + "SSS"), "mm");
    /*String [] arr = {"A", "B", "C"};
    prnt(arr, (str) -> System.out.printf("%s -- a\n", str));

    int [][] arrI = {{1,2}, {2,1}, {1,1}};
    comparate(arrI, (i1, i2) -> i1 > i2 ? 1 : i1 == i2 ? 0 : -1);*/

    /* int [][] arrI = {{1,2}, {2,1}, {1,1}};
    functInt1 fI = new functInt1();
    comparateThis(arrI, fI::comparate3); */
    // comparateThis(arrI, (i1, i2) -> -1);

    // prepExample2("QQQ", 2);

    // Внутренние классы
    /*Person p1 = new Person(); //
    Person p2 = new Person("Dedus", (byte) 5);

    Person.InterClass intCl1 = p1.new InterClass();
    Person.InterClass intCl2 = p2.new InterClass();

    System.out.printf("%s %s\n", intCl1.getPersName(), intCl2.getPersName());*/

    //getCompare(4);

    TestInt6.TestInt6Cl t6 = new TestInt6.TestInt6Cl();

    IntTest6 i6 = new IntTest6();
    System.out.println(TestInt6.getStr1()); // к внутреннему классу в инт-се и статическому методу только от имени онтерфейса

    Person.InterClass ip = (new Person()).new InterClass();
}

private void testL(TestInt5 t, String sm){
    t.runn(sm);
}

private void prnt(String [] arr, TestInt5 t){
    for (String s : arr) t.runn(s);
}

private void prnt2 (BiConsumer<String, String> b, String s1, String s2){
    b.accept(s1, s2);
}

private void comparateThis(int [][] arr, FunctInt1 f){
    for (int [] a : arr) System.out.println(f.comparate(a[0], a[1]));
}

private class functInt1 implements  FunctInt1{
    @Override
    public int comparate(int i1, int i2){
        return i1 > i2 ? 5 : i1 == i2 ? 0 : -5;
    }

    public int comparate2(int i1, int i2){
        return i1 > i2 ? 1 : i1 == i2 ? 0 : -1;
    }

    /*public int comparate2(int i1, int i2, int i3){
        return i1 > i2 ? 1 : i1 == i2 ? 0 : -1; // другой вариант comparate2 (иная сигнатура), ОШИБКА, сигнатура должна быть одинаковой с объявленным методом в интерфейсе.
    }*/

    public int comparate3(int i1, int i2){
        return i1 > i2 ? 3 : i1 == i2 ? 0 : -3;
    }
};



public void prepExample(String Text, int a){
    String Text2 = "WWW";
    Runnable r = () -> {
        System.out.println(Text2);
        System.out.println(a);
    };
    r.run();
}

public void prepExample2(String Text, int a){
    String Text2 = "WWW";
    IntConsumer r = (t) -> {
        System.out.println(t);
    };
    //r.accept(Text2);
    for (int i = 0; i < a; i++) r.accept(i);
}

private void getCompare(int b){ // с внутренним локальным классом
    int a = 1; // локальная переменная
    class functInt2 implements FunctInt1
    {
        @Override
        public int comparate(int i1, int i2){
            return 1 + a + b; // Локальный Внут. класс имеет доступ к локальным переменным и параметрам метода которые не могут быть изменены (действительно конечные)
        }
    }
    int [][] arr = {{1,2}, {2,1}, {1,1}};
    functInt2 fI2 = new functInt2();
    comparateThis(arr, fI2::comparate);
}

private void getCompare2(int b){ // с внутренним локальным анонимным классом
    int a = 1; // локальная переменная
    FunctInt1 f = new FunctInt1() {
        @Override
        public int comparate(int a, int b) {
            return 0;
        }
    };

    int [][] arr = {{1,2}, {2,1}, {1,1}};
    comparateThis(arr, f::comparate);
}



