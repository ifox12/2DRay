import java.awt.*;
import java.awt.geom.Point2D;

public class Vector2D {
    double x;
    double y;

    public Vector2D(double x, double y) {
        // TODO zero check
        this.x = x;
        this.y = y;
    }

    public Vector2D(double angleInDegrees) {
        // already normalized
        this.x = Math.cos(Math.toRadians(angleInDegrees));
        this.y = Math.sin(Math.toRadians(angleInDegrees));
    }

    public double magnitude() {
        return Math.sqrt(magnitudeSquared());
    }

    public double magnitudeSquared() {
        return x * x + y * y;
    }

    public void normalize() {
        double magnitude = magnitude();
        // TODO zero check
        x /= magnitude;
        y /= magnitude;
    }

    public Point tmpConvertToPoint() {
        return new Point((int) x, (int) y);
    }

    public Point2D.Double tmpConvertToPoint2DDouble() {
        return new Point2D.Double(x, y);
    }

    public Vector2D perpendicularClockwise() {
        return new Vector2D(y, -x);
    }

    public Vector2D perpendicularCounterclockwise() {
        return new Vector2D(-y, x);
    }

    public Vector2D scalarMultiply(int scalar) {
        return new Vector2D(x * scalar, y * scalar);
    }

    public void move(Vector2D newOrigin) {
        x += newOrigin.x;
        y += newOrigin.y;
    }

    double distanceTo(Vector2D other) {
        double x = other.x - this.x;
        double y = other.y - this.y;
        return magnitude();
    }
}

