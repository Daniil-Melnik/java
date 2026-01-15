package testing.ext;

public class PriorityEntity implements Comparable<PriorityEntity>{ // можно не задавть признак приритетности
                                                                   // при создании очереди

    private byte priority; // приоритет для очереди с приоритетом

    private int number;

    public PriorityEntity(int p, int n){
        priority = (byte) p;
        number = n;
    }

    public PriorityEntity(){
        this(0, 0);
    }

    @Override
    public String toString() {
        return priority + " - " + number;
    }

    @Override
    public int compareTo(PriorityEntity o) {
        return this.priority - o.priority;
    }
}
