package common;

import client.FileManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO: CLEAN UP AND SET TAGS PROPERLY
public class ChineseCheckersBoardFactory {

  private static ChineseCheckersBoardFactory instance = null;

  private ChineseCheckersBoardFactory() {

  }

  public static ChineseCheckersBoardFactory getInstance() {
    if (instance == null)
      instance = new ChineseCheckersBoardFactory();
    return instance;
  }

  public Board createBoard(int numberOfPlayers) {
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

  private Cell[][] createBoard() {
    char currentChar;
    PlayerTag owner;
    Cell[][] board = new Cell[Board.COLUMNS][Board.ROWS];

    ArrayList<String> output = FileManager.getInstance().readFile("src/board.txt");
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

  private Board createTwoPlayersBoard() {
    Cell[][] board = createBoard();
    Map<PlayerTag, List<Point>> endingCells = new HashMap<>();
    endingCells.put(PlayerTag.PLAYER_2, new ArrayList<>());
    endingCells.put(PlayerTag.PLAYER_1, new ArrayList<>());



    for (int y = 0; y < Board.ROWS; y++) {
      for (int x = 0; x < Board.COLUMNS; x++) {
        Cell cell = board[x][y];
        if (cell == null)
          continue;
        if (cell.getOwner() == PlayerTag.PLAYER_4) {
          cell.setOwner(PlayerTag.PLAYER_2);
          endingCells.get(PlayerTag.PLAYER_1).add(cell.getPoint());
        }
        else if (cell.getOwner() == PlayerTag.PLAYER_1) {
          endingCells.get(PlayerTag.PLAYER_2).add(cell.getPoint());
        }
        else
          cell.setOwner(PlayerTag.NONE);

      }
    }
    return new Board(board, endingCells);

  }

  private Board createThreePlayersBoard() {
    Cell[][] board = createBoard();
    Map<PlayerTag, List<Point>> endingCells = new HashMap<>();
    endingCells.put(PlayerTag.PLAYER_3, new ArrayList<>());
    endingCells.put(PlayerTag.PLAYER_2, new ArrayList<>());
    endingCells.put(PlayerTag.PLAYER_1, new ArrayList<>());


    for (int y = 0; y < Board.ROWS; y++) {
      for (int x = 0; x < Board.COLUMNS; x++) {
        Cell cell = board[x][y];
        if (cell == null)
          continue;
        switch (cell.getOwner()) {
          case PLAYER_1:
            cell.setOwner(PlayerTag.NONE);
            break;
          case PLAYER_2:
            endingCells.get(PlayerTag.PLAYER_1).add(cell.getPoint());
            cell.setOwner(PlayerTag.PLAYER_1);
            break;
          case PLAYER_3:
            cell.setOwner(PlayerTag.NONE);
            break;
          case PLAYER_4:
            endingCells.get(PlayerTag.PLAYER_2).add(cell.getPoint());
            cell.setOwner(PlayerTag.PLAYER_2);
            break;
          case PLAYER_5:
            cell.setOwner(PlayerTag.NONE);
            break;
          case PLAYER_6:
            endingCells.get(PlayerTag.PLAYER_3).add(cell.getPoint());
            cell.setOwner(PlayerTag.PLAYER_3);
            break;
        }
      }
    }
    return new Board(board, endingCells);
  }

  private Board createFourPlayersBoard() {
    Cell[][] board = createBoard();
    Map<PlayerTag, List<Point>> endingCells = new HashMap<>();
    endingCells.put(PlayerTag.PLAYER_4, new ArrayList<>());
    endingCells.put(PlayerTag.PLAYER_3, new ArrayList<>());
    endingCells.put(PlayerTag.PLAYER_2, new ArrayList<>());
    endingCells.put(PlayerTag.PLAYER_1, new ArrayList<>());

    for (int y = 0; y < Board.ROWS; y++) {
      for (int x = 0; x < Board.COLUMNS; x++) {
        Cell cell = board[x][y];
        if (cell == null)
          continue;
        switch (cell.getOwner()) {
          case PLAYER_2:
            endingCells.get(PlayerTag.PLAYER_3).add(cell.getPoint());
            cell.setOwner(PlayerTag.PLAYER_1);
            break;
          case PLAYER_3:
            endingCells.get(PlayerTag.PLAYER_4).add(cell.getPoint());
            cell.setOwner(PlayerTag.PLAYER_2);
            break;
          case PLAYER_5:
            endingCells.get(PlayerTag.PLAYER_1).add(cell.getPoint());
            cell.setOwner(PlayerTag.PLAYER_3);
            break;
          case PLAYER_6:
            endingCells.get(PlayerTag.PLAYER_2).add(cell.getPoint());
            cell.setOwner(PlayerTag.PLAYER_4);
            break;
          default:
            cell.setOwner(PlayerTag.NONE);
            break;
        }
      }
    }

    return new Board(board, endingCells);
  }

  private Board createSixPlayersBoard() {
    Cell[][] board = createBoard();
    Map<PlayerTag, List<Point>> endingCells = new HashMap<>();
    endingCells.put(PlayerTag.PLAYER_6, new ArrayList<>());
    endingCells.put(PlayerTag.PLAYER_5, new ArrayList<>());
    endingCells.put(PlayerTag.PLAYER_4, new ArrayList<>());
    endingCells.put(PlayerTag.PLAYER_3, new ArrayList<>());
    endingCells.put(PlayerTag.PLAYER_2, new ArrayList<>());
    endingCells.put(PlayerTag.PLAYER_1, new ArrayList<>());

    for (int y = 0; y < Board.ROWS; y++) {
      for (int x = 0; x < Board.COLUMNS; x++) {
        Cell cell = board[x][y];
        if (cell == null)
          continue;
        switch (cell.getOwner()) {
          case PLAYER_1:
            endingCells.get(PlayerTag.PLAYER_4).add(cell.getPoint());
            break;
          case PLAYER_2:
            endingCells.get(PlayerTag.PLAYER_5).add(cell.getPoint());
            break;
          case PLAYER_3:
            endingCells.get(PlayerTag.PLAYER_6).add(cell.getPoint());
            break;
          case PLAYER_4:
            endingCells.get(PlayerTag.PLAYER_1).add(cell.getPoint());
            break;
          case PLAYER_5:
            endingCells.get(PlayerTag.PLAYER_2).add(cell.getPoint());
            break;
          case PLAYER_6:
            endingCells.get(PlayerTag.PLAYER_3).add(cell.getPoint());
            break;
          default:

            break;
        }
      }
    }

      return new Board(board, endingCells);
  }

}

