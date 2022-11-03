package Engine.Core;

import java.util.Objects;

public final class Point2D {
    private final float x;
    private final float y;

    public Point2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Point2D) obj;
        return this.x == that.x && this.y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ')';
    }

    public static Point2D add(Point2D a, Point2D b) {
        return new Point2D(a.x + b.x, a.y + b.y);
    }

    public static Point2D sub(Point2D a, Point2D b) {
        return new Point2D(a.x - b.x, a.y - b.y);
    }

    public static Point2D scale(Point2D p, float s) {
        return new Point2D(p.x * s, p.y * s);
    }

    public static Point2D scale(Point2D p, float x, float y) {
        return new Point2D(p.x * x, p.y * y);
    }

    public static float norm(Point2D p) {
        return (float) Math.sqrt(p.x * p.x + p.y * p.y);
    }

    public static float distance(Point2D a, Point2D b) {
        return sub(a, b).norm();
    }

    public static float manhattanDistance(Point2D a, Point2D b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    public Point2D plus(Point2D other) {
        return add(this, other);
    }

    public Point2D minus(Point2D other) {
        return sub(this, other);
    }

    public Point2D times(float scale) {
        return scale(this, scale);
    }

    public Point2D scaledBy(float x, float y) {
        return scale(this, x, y);
    }

    public float norm() {
        return norm(this);
    }

    public float distanceTo(Point2D other) {
        return distance(this, other);
    }

    public float manhattanDistanceTo(Point2D other) {
        return manhattanDistance(this, other);
    }
}