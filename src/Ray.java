import java.awt.*;

public class Ray {
    Vector2D base;
    Vector2D direction;

    Ray(Vector2D base, Vector2D direction) {
        this.base = base;
        this.direction = direction;
    }

    public Vector2D cast(Obstacle obstacle) {
        Vector2D result = null;

        double x1 = obstacle.first.x;
        double x2 = obstacle.second.x;
        double x3 = base.x;
        double x4 = base.x + direction.x;
        double y1 = obstacle.first.y;
        double y2 = obstacle.second.y;
        double y3 = base.y;
        double y4 = base.y + direction.y;

        double divisor = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / divisor;
        double u = -(((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / divisor);

        if (t >= 0 && t <= 1 && u >= 0) {
            result = new Vector2D(x1 + t * (x2 - x1), y1 + t * (y2 - y1));
        }
        return result;
    }

    int distanceTo(Point base, Point intersection) {
        int x = intersection.x - base.x;
        int y = intersection.y - base.y;
        return (int) Math.sqrt(x * x + y * y);
    }
}
