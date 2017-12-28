package common;

public class Cell {
  private Point point;
  private PlayerTag owner;

  public Cell(int x, int y, PlayerTag owner) {
    point = new Point(x, y);
    this.owner = owner;
  }

  public Cell(){

  }

  public Point getPoint() {
    return point;
  }

  public PlayerTag getOwner() {
    return owner;
  }

  public void setOwner(PlayerTag playerTag) {
    owner = playerTag;
  }
}
