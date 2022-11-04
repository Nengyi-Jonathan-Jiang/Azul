package Engine.Core;

import java.awt.*;
import java.util.Objects;

public final class Vec2 {
    public final double x;
    public final double y;

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vec2 fromDimension(Dimension d) {
        return new Vec2(d.width, d.height);
    }
    public Dimension toDimension(){
        return new Dimension((int) x, (int) y);
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Vec2) obj;
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

    public static Vec2 add(Vec2 a, Vec2 b) {
        return new Vec2(a.x + b.x, a.y + b.y);
    }

    public static Vec2 sub(Vec2 a, Vec2 b) {
        return new Vec2(a.x - b.x, a.y - b.y);
    }

    public static Vec2 scale(Vec2 p, double s) {
        return new Vec2(p.x * s, p.y * s);
    }

    public static Vec2 scale(Vec2 p, double x, double y) {
        return new Vec2(p.x * x, p.y * y);
    }

    public static double norm(Vec2 p) {
        return Math.sqrt(p.x * p.x + p.y * p.y);
    }

    /**
     * Returns true if a.x <= b.x and a.y <= b.y
     */
    public static boolean compareCoordinates(Vec2 a, Vec2 b){
        return a.x <= b.x && a.y <= b.y;
    }

    public static double distance(Vec2 a, Vec2 b) {
        return sub(a, b).norm();
    }

    public static double manhattanDistance(Vec2 a, Vec2 b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    public Vec2 plus(Vec2 other) {
        return add(this, other);
    }

    public Vec2 minus(Vec2 other) {
        return sub(this, other);
    }

    public Vec2 scaledBy(double scale) {
        return scale(this, scale);
    }

    public Vec2 scaledBy(double x, double y) {
        return scale(this, x, y);
    }

    public double norm() {
        return norm(this);
    }

    public double distanceTo(Vec2 other) {
        return distance(this, other);
    }

    public double manhattanDistanceTo(Vec2 other) {
        return manhattanDistance(this, other);
    }

    /**
     * Returns true if x <= other.x and y <= other.y
     */
    public boolean compareCoordinates(Vec2 other){
        return compareCoordinates(this, other);
    }

    public static final Vec2 zero = new Vec2(0, 0);
}