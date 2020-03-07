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

    // TODO make this return a new Vector
    public void normalize() {
        double magnitude = magnitude();
        // TODO zero check
        x /= magnitude;
        y /= magnitude;
    }

    public void move(Vector2D newOrigin) {
        x += newOrigin.x;
        y += newOrigin.y;
    }

    public double distanceTo(Vector2D other) {
        double x = other.x - this.x;
        double y = other.y - this.y;
        return new Vector2D(x, y).magnitude();
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(other.x - this.x, other.y - this.y);
    }

    public int thetaInDegrees() {
        int result;
        if (x != 0) {
            result = (int) Math.toDegrees(Math.atan(y / x));
            if (result < 0) {
                result = 180 + result;
            }
        } else {
            if (y > 0) {
                result = 90;
            } else {
                result = 270;
            }
        }
        return result;
    }
}

