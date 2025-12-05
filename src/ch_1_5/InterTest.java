import Interfaces.TestInt2;
import Interfaces.TestInt3;

public class InterTest implements TestInt2, Comparable<InterTest>, TestInt3 {
    private String name;
    private String name2;

    public String getName() {
        return name;
    }

    public String getName2() {
        return name2;
    }

    public InterTest(String name, String name2){ this.name = name; this.name2 = name2; }

    public InterTest(){this("Ivan", "Ivanov");}

    @Override
    public String getInfo() {
        return String.format("%s %s", name, name2);
    }

    @Override
    public String getInfo2() {
        return String.format("%s %s", name2, name);
    }

    @Override
    public int compareTo(InterTest o) {

        return Integer.compare(this.name.length(), o.name.length());
    }

    @Override
    public String getInfo3() {
        return String.format("%s %s", "QQ", "QQ");
    }

    /* @Override
    public int compareTo(InterTest o) {

        return this.name.length() < o.name.length() ? -1 : this.name.length() == o.name.length() ? 0 : 1;
    } */
}
