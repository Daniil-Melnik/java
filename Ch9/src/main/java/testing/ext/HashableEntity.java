package testing.ext;

public class HashableEntity extends Entity{

    private String str;

    public HashableEntity(int n, String s){
        super(n);
        str = s;
    }

    public HashableEntity(){
        this(0, "");
    }

    // РАЗНЫЕ ПРИНЦИПЫ ПОИСКА (из-за разной структуры хранения)
    // hashCode() -> equals() обязательны для поиска в HashSet (хештаблица) - оба метода должны выдавать эквивалентные друг другу результаты
    // compareTo() - обязателен для поиска в TreeSet (хеш-дерево)

    public boolean equals(Object o) { // ОБЯЗАТЕЛЕН для корректной работы  HashSet.contains()
        return super.getNum() == ((HashableEntity) o).getNum() &&
                this.str.equals(((HashableEntity) o).str);
    }

    @Override
    public int hashCode(){ // ОБЯЗАТЕЛЕН для корректной работы HashSet.contains()
        return this.str.length() * super.getNum() + super.getNum() - this.str.length();
    }

    // переопределние compareTo() из родительского Entity - недопустимо, если попробовать определить,
    // то получится только по num (так как он есть в суперклассе)

    @Override
    public int compareTo(Entity e) { // требуется для поиска в TreeSet
        return super.compareTo(e);
    }

    @Override
    public String toString(){
        return super.getNum() + " " + str + " " + this.hashCode();
    }
}
