package common;

public class Point {
  private final double q, r, s;

  public Point(double q, double r) {
    this.q = q;
    this.r = r;
    this.s = -q-r;
  }

  public double getQ() {
    return q;
  }

  public double getR() {
    return r;
  }

  public double getS() {
    return s;
  }
}
