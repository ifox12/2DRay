import java.util.ArrayList;

public class ViewerPlane {
    Vector2D center;
    int radius;
    Vector2D direction;
    ArrayList<Ray> rays = new ArrayList<>();

    ViewerPlane(Vector2D center, int radius, Vector2D direction) {
        this.center = center;
        this.radius = radius;
        this.direction = direction;

        populateRays();
    }

    private void populateRays() {
        Vector2D left = direction.perpendicularCounterclockwise();
        left.normalize();
        Vector2D right = direction.perpendicularClockwise();
        right.normalize();

        for (int i = -radius; i < radius; i++) {
            Vector2D currentOrigin = right.scalarMultiply(i);
            currentOrigin.move(center);
            rays.add(new Ray(currentOrigin, direction));
        }
    }
}
