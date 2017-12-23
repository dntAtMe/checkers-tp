package main.java.sample;
import javafx.scene.paint.Color;

public class CheckerCell {
    private  int q;
    private  int r;
    private  int s;
    boolean isfree;

    private CheckerCellEnum type;
    public Color color;

    public CheckerCell(int x, int y, CheckerCellEnum type, PlayerTagEnum ownerTag, Color color,boolean isfree) {
        this.q = x;
        this.r = y;
        this.s = -q-r;
        this.color = color;
        this.isfree=isfree;
    }

   public CheckerCell(int q, int r){
        this.q=q;
        this.r=r;
        s=-q-r;

   }

   public CheckerCell(){

   }

    public boolean isOnBoard() {
        return isfree;
    }


    public int getQ() {
        return q;
    }

    public int getR() {
        return r;
    }

    public int getS() {
        return s;
    }

}
