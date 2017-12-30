package common;

import java.io.Serializable;

public class Point implements Serializable {
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

  public static Point substract(Point a, Point b) {
    return new Point(a.getQ() - b.getQ(), a.getR() - b.getR());
  }

  public static Point multiply(Point point, double scalar) {
    return new Point(point.getQ() * scalar, point.getR() * scalar);
  }

  public static Point add(Point a, Point b) {
    return new Point (a.getQ() + b.getQ(), a.getR() + b.getR());
  }

  public static double distanceBetween (Point a, Point b) {
    return (Math.abs(a.getQ() - b.getQ()) + Math.abs(a.getR() - b.getR()) + Math.abs(a.getS() - b.getS())) / 2;
  }

  public boolean equals(Object o) {
    Point p = null;
    if (o == null)
      return false;
    if (o instanceof Point) {
      p = (Point) o;
      return (q == p.getQ() && r == p.getR() && s == p.getS());
    }
    return false;
  }
}
