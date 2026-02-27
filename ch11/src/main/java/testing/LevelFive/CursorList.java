package testing.LevelFive;

import java.util.ArrayList;
import java.util.List;

public class CursorList extends ArrayList<String> {

    private int currentIndex = 0;

    public CursorList(){
        super();
    }

    public CursorList(int cap){
        super(cap);
    }

    public String getCurrent(){
        return this.get(currentIndex);
    }

    public void incrCurrentIndex(){
        if (currentIndex < this.size()) currentIndex++;
    }

    public void decrCurrentIndex(){
        if (currentIndex > 0) currentIndex--;
    }

    public void setCurrentIndex(int cI){
        currentIndex = cI;
    }

    public int getCurrentIndex(){
        return currentIndex;
    }

    public List<String> getNearElements(){
        List<String> result = new ArrayList<>(5);
        if ((currentIndex - 2 >= 0) && (currentIndex + 2 <= this.size())){
            result = this.subList(currentIndex - 2, currentIndex + 2 + 1);
        }
        else if((currentIndex == 0) || (currentIndex == 1)){
            if (this.size() < 5){
                result = this;
            }
            else {
                result = this.subList(0, 5);
            }
        }
        else if((currentIndex == this.size() - 1) || (currentIndex == this.size() - 2)){
            if (this.size() < 5){
                result = this;
            }
            else {
                result = this.subList(this.size() - 5, this.size());
            }
        }
        return result;
    }

    public static void main(String[] args){
        CursorList h = new CursorList();
        for (int i = 0; i < 100; i++){
            h.add(i + "");
        }
        System.out.println(h.getNearElements());

        h.incrCurrentIndex();

        System.out.println(h.getNearElements());

        h.incrCurrentIndex();

        System.out.println(h.getNearElements());

        h.incrCurrentIndex();

        System.out.println(h.getNearElements());

        h.setCurrentIndex(99);
    }
}
