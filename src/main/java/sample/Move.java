package main.java.sample;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

public class Move {

    CheckerBoard checkerBoard;
    Board board;
    Color color;
    Polygon shapeCell;
    Polygon shapeDestination;
    CheckerCell cell;
    CheckerCell destination;
    CheckerCell findedCell;
    int r, q, s;

    public Move(Board board) {
        cell = new CheckerCell();
        destination = new CheckerCell();
        shapeCell = new Polygon();
        shapeDestination = new Polygon();
        this.board = board;
    }


    public boolean properMove(CheckerCell cell, Polygon shapeCell) {

        if (cell.isfree == false) {
            this.shapeCell = shapeCell;
            this.cell = cell;
            return true;
        }

        return false;
    }

    public void move(CheckerCell destination, Polygon shapeDestination) {

        if (destination.isfree) {
            this.destination = destination;
            this.shapeDestination = shapeDestination;

            if (cellsDistance(cell, destination) <= 4) {
                if (((cellsDistance(cell, destination) == 4) && isBetween(cell, destination)) || cellsDistance(cell, destination) == 2) {
                    cell.isfree = true;
                    destination.isfree = false;
                    shapeDestination.setFill(cell.color);
                    destination.color = cell.color;
                    cell.color = Color.WHITE;
                    shapeCell.setFill(Color.WHITE);
                }
            }
        }
    }

    public boolean isBetween(CheckerCell a, CheckerCell b) {
        if (a.getQ() + 2 == b.getQ())
            q = a.getQ();
        else if (a.getQ() - 2 == b.getQ())
            q = b.getQ();
        else if (a.getQ() == b.getQ())
            q = a.getQ();
        else
            return false;

        if (a.getR() + 2 == b.getR())
            r = a.getR();
        else if (a.getR() - 2 == b.getR())
            r = b.getR();
        else if (a.getR() == b.getR())
            r = a.getR();
        else
            return false;

        if (findCell(r, q + 1))
            return true;

        if (findCell(r + 1, q))
            return true;

        if (findCell(r + 1, q + 1))
            return true;

        return false;
    }

    public boolean findCell(int r, int q) {
            findedCell = new CheckerCell(q, r);
            for (int j = 0; j < board.cells.size(); j++) {
                if (board.cells.get(j) != null) {
                    if (findedCell.getQ() == board.cells.get(j).getQ() && findedCell.getR() == board.cells.get(j).getR() && board.cells.get(j).isfree == false)
                        if (cellsDistance(findedCell, destination) == 2)
                            return true;
                }
            }

        return false;
    }



    public double cellsDistance(CheckerCell a, CheckerCell b) {
        return (Math.abs(a.getQ() - b.getQ()) + Math.abs(a.getR() - b.getR()) + Math.abs(a.getS() - b.getS()));
    }
}



