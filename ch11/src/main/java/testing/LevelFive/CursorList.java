package testing.LevelFive;

import java.util.ArrayList;
import java.util.List;

public class CursorList<T> extends ArrayList<T> {

    private final int NEAR_CAPACITY = 5;

    private int currentIndex = 0;

    public CursorList(){
        super();
    }

    public CursorList(int cap){
        super(cap);
    }

    public T getCurrent(){
        return this.get(currentIndex);
    }

    public void incrCurrentIndex(){
        if (currentIndex < this.size()) currentIndex++;
    }

    public void decrCurrentIndex(){
        if (currentIndex > 0) currentIndex--;
    }

    public void setCurrentIndex(int cI){
        if ((cI >=0) && (cI < this.size())){
            currentIndex = cI;
        }
    }

    public int getCurrentIndex(){
        return currentIndex;
    }

    public List<T> getNearElements(){
        List<T> result = new ArrayList<>(NEAR_CAPACITY);
        if ((currentIndex - NEAR_CAPACITY / 2 >= 0) && (currentIndex + NEAR_CAPACITY / 2 <= this.size())){
            result = this.subList(currentIndex - 2, currentIndex + 2 + 1);
        }
        else if((currentIndex == 0) || (currentIndex == 1)){
            if (this.size() < NEAR_CAPACITY){
                result = this;
            }
            else {
                result = this.subList(0, NEAR_CAPACITY);
            }
        }
        else if((currentIndex == this.size() - 1) || (currentIndex == this.size() - 2)){
            if (this.size() < NEAR_CAPACITY){
                result = this;
            }
            else {
                result = this.subList(this.size() - NEAR_CAPACITY, this.size());
            }
        }
        return result;
    }
}
