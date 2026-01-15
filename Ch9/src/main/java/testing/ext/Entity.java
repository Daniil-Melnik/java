package testing.ext;

public class Entity implements Comparable<Entity>{
    private int num;

    public int compareTo(Entity e){
        return this.num - e.num;
    }

    public Entity(int n){
        num = n;
    }

    public Entity(){
        this(0);
    }

    public int getNum(){ return num; }

    @Override
    public String toString() {
        return num + "";
    }
}
