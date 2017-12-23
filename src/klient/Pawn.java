package klient;


import javafx.scene.paint.Color;

public class Pawn {
    int x;
    int y;
    PlayerTagEnum owner;
    Color color;


    public Pawn(int x, int y, PlayerTagEnum owner, Color color){
        this.x = x;
        this.y = y;
        this.owner = owner;
        this.color =color;

    }
}
