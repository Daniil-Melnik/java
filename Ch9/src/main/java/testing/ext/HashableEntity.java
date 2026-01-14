package testing.ext;

public class HashableEntity extends Entity{

    private String str;

    HashableEntity(int n, String s){
        super(n);
        str = s;
    }

    HashableEntity(){
        this(0, "");
    }

    @Override
    public int hashCode(){
        return this.str.hashCode() * super.getNum() + super.getNum() - this.str.length();
    }

    @Override
    public int compareTo(Entity e) {
        return super.compareTo(e);
    }
}
