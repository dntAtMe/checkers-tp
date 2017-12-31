package common;

import client.FileManager;
import client.PlayerTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//TODO: CLEAN UP AND SET TAGS PROPERLY
public class ChineseCheckersBoardFactory {


  public static Board createBoard(int numberOfPlayers) {
    switch (numberOfPlayers) {
      case 2:
        return createTwoPlayersBoard();
      case 3:
        return createThreePlayersBoard();
      case 4:
        return createFourPlayersBoard();
      case 6:
        return createSixPlayersBoard();
    }
    return null;
  }

  private static Cell[][] createBoard() {
    char currentChar;
    PlayerTag owner;
    Cell[][] board = new Cell[Board.COLUMNS][Board.ROWS];

    ArrayList<String> output = FileManager.readFile("src/board.txt");
    String[] lines = output.toArray(new String[0]);

    for (int y = 0; y < Board.ROWS; y++) {
      for (int x = 0; x < Board.COLUMNS; x++) {
        currentChar = lines[y].charAt(x);
        PlayerTag[] owners = PlayerTag.values();


        if (currentChar == ' ')
          continue;
        else {
          owner = owners[Integer.parseInt(String.valueOf(currentChar))];
          board[x][y] = new Cell(x, y, owner);
        }
      }
    }

    return board;
  }

  public static Board createTwoPlayersBoard() {
    Cell[][] board = createBoard();

    for (int y = 0; y < Board.ROWS; y++) {
      for (int x = 0; x < Board.COLUMNS; x++) {
        Cell cell = board[x][y];
        if (cell == null)
          continue;
        if (cell.getOwner() == PlayerTag.PLAYER_4)
          cell.setOwner(PlayerTag.PLAYER_2);
        else if (cell.getOwner() != PlayerTag.PLAYER_1)
          cell.setOwner(PlayerTag.NONE);

      }
    }
    return new Board(board);

  }

  public static Board createThreePlayersBoard() {
    Cell[][] board = createBoard();

    for (int y = 0; y < Board.ROWS; y++) {
      for (int x = 0; x < Board.COLUMNS; x++) {
        Cell cell = board[x][y];
        if (cell == null)
          continue;
        if (cell.getOwner() == PlayerTag.PLAYER_2)
          cell.setOwner(PlayerTag.PLAYER_1);
        else if (cell.getOwner() == PlayerTag.PLAYER_4)
          cell.setOwner(PlayerTag.PLAYER_2);
        else if (cell.getOwner() == PlayerTag.PLAYER_6)
          cell.setOwner(PlayerTag.PLAYER_3);
        else
          cell.setOwner(PlayerTag. NONE);

      }
    }
    return new Board(board);
  }

  public static Board createFourPlayersBoard() {
    Cell[][] board = createBoard();

    for (int y = 0; y < Board.ROWS; y++) {
      for (int x = 0; x < Board.COLUMNS; x++) {
        Cell cell = board[x][y];
        if (cell == null)
          continue;
        if (cell.getOwner() == PlayerTag.PLAYER_2)
          cell.setOwner(PlayerTag.PLAYER_1);
        else if (cell.getOwner() == PlayerTag.PLAYER_3)
          cell.setOwner(PlayerTag.PLAYER_2);
        else if (cell.getOwner() == PlayerTag.PLAYER_5)
          cell.setOwner(PlayerTag.PLAYER_3);
        else if (cell.getOwner() == PlayerTag.PLAYER_6)
          cell.setOwner(PlayerTag.PLAYER_4);
        else
          cell.setOwner(PlayerTag.NONE);
      }
    }

    return new Board(board);
  }

  public static Board createSixPlayersBoard() {
    Cell[][] board = createBoard();

    for (int y = 0; y < Board.ROWS; y++) {
      for (int x = 0; x < Board.COLUMNS; x++) {
        if (board[x][y] == null)
          continue;
      }
    }

      return new Board(board);
  }

  public static int cellsLength(Cell[] cell){
    int length=0;
    for(int i=0;i<10;i++){
      if(cell[i]!=null)
        length++;
    }
    return length;
  }

  public static Cell[] cleanArray(){
    Cell[] c = new Cell[10];
    for (int i=0;i<10;i++){
      c[i]=null;
    }
    return c;
  }
}

