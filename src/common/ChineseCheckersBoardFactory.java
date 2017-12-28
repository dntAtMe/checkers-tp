package common;

import client.FileManager;

import java.util.ArrayList;

public class ChineseCheckersBoardFactory {

  public static Board createBoard(int numberOfPlayers) {
    switch(numberOfPlayers) {
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
        System.out.println("Doing (" + x + ", " + y + ")");
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
        System.out.println("Doing (" + x + ", " + y + ")");
        if(board[x][y] == null)
          continue;
        if(board[x][y].getOwner() != PlayerTag.PLAYER_1
                && board[x][y].getOwner() != PlayerTag.PLAYER_4) {
          board[x][y].setOwner(PlayerTag.NONE);
          continue;
        } else if (board[x][y].getOwner() == PlayerTag.PLAYER_4) {
          board[x][y].setOwner(PlayerTag.PLAYER_2);
          continue;
        }
      }
    }

    return new Board(board);
  }

  //TODO: finish
  public static Board createThreePlayersBoard() {
    return null;
  }

  //TODO: finish
  public static Board createFourPlayersBoard() {
    return null;
  }

  //TODO: finish
  public static Board createSixPlayersBoard() {
    return null;
  }

}
