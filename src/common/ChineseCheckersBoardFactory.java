package common;

import client.FileManager;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
        if (board[x][y] == null)
          continue;
        if (board[x][y].getOwner() != PlayerTag.PLAYER_1
                && board[x][y].getOwner() != PlayerTag.PLAYER_4) {
          board[x][y].setOwner(PlayerTag.NONE);
          continue;
        }
      }
    }

    return new Board(board);
  }

  public static Board createThreePlayersBoard() {
    Cell[][] board = createBoard();

    for (int y = 0; y < Board.ROWS; y++) {
      for (int x = 0; x < Board.COLUMNS; x++) {
        if (board[x][y] == null)
          continue;
        if (board[x][y].getOwner() != PlayerTag.PLAYER_1
                && board[x][y].getOwner() != PlayerTag.PLAYER_3 && board[x][y].getOwner() != PlayerTag.PLAYER_5) {
          board[x][y].setOwner(PlayerTag.NONE);
          continue;

        }
      }
    }

    return new Board(board);
  }

  public static Board createFourPlayersBoard() {
    Cell[][] board = createBoard();

    for (int y = 0; y < Board.ROWS; y++) {
      for (int x = 0; x < Board.COLUMNS; x++) {
        if (board[x][y] == null)
          continue;
        if (board[x][y].getOwner() != PlayerTag.PLAYER_2
                && board[x][y].getOwner() != PlayerTag.PLAYER_3
                && board[x][y].getOwner() != PlayerTag.PLAYER_5
                && board[x][y].getOwner() != PlayerTag.PLAYER_6) {
          board[x][y].setOwner(PlayerTag.NONE);
          continue;

        }
      }
    }

    return new Board(board);
  }

  public static Board createSixPlayersBoard() {
    Cell[][] board = createBoard();

    return new Board(board);

  }
}

