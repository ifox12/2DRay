import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class Screen extends JPanel {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    ArrayList<Obstacle> obstacles = new ArrayList<>();
    ArrayList<Ray> rays = new ArrayList<>();
    int povX;
    int povY;
    Vector2D pov;
    Screen3D output3D;
    ViewerPlane viewerPlane;

    Screen() {
        pov = new Vector2D(50, 200);

        for (int currentAngle = -45; currentAngle < 45; currentAngle++) {
            rays.add(new Ray(pov, new Vector2D(currentAngle)));
        }

        obstacles.add(new Obstacle(new Point(300, 50), new Point(300, 500)));
        obstacles.add(new Obstacle(new Point(50, 100), new Point(200, 150)));

        viewerPlane = new ViewerPlane(pov, 40, new Vector2D(1, 0));

        JFrame window = new JFrame("2DRay");
        output3D = new Screen3D();

        window.add(this);
        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);

        window.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                povX = e.getX();
                povY = e.getY();
                pov.update(e.getX(), e.getY());
                viewerPlane.update(pov);
                repaint();
                output3D.repaint();
            }
        });

        window.requestFocusInWindow();
    }
    // TODO center slices in the middle of the screen
    // TODO fish eye
    // TODO turning
    // TODO pov greater y than mouse cursor

    static public void main(String[] args) {
        new Screen();
    }

    @Override
    public void paintComponent(Graphics g) {
        // TODO move logic to mouseMoved()
        clearScreen(g);

        for (Obstacle obstacle : obstacles) {
            drawObstacle(g, obstacle);
        }
        fillCircle(g, pov, 10);
        ArrayList<Integer> distances = new ArrayList<>();

        int count = 0;
        for (Ray ray : rays) {
            Vector2D closestIntersection = null;
            double distanceToClosestIntersection = -1;
            for (Obstacle obstacle : obstacles) {
                Vector2D currentIntersection = ray.cast(obstacle);
                if (currentIntersection != null) {
                    double currentDistance = pov.distanceTo(currentIntersection);
                    if (closestIntersection == null || distanceToClosestIntersection > currentDistance) {
                        closestIntersection = currentIntersection;
                        distanceToClosestIntersection = currentDistance;
                    }
                }
            }
            if (closestIntersection != null) {
                g.drawLine((int) pov.x, (int) pov.y, (int) closestIntersection.x, (int) closestIntersection.y);
                output3D.distances[count] = (int) distanceToClosestIntersection;
            } else {
                output3D.distances[count] = -1;
            }
            count++;
        }
    }

    private void drawObstacle(Graphics g, Obstacle obstacle) {
        g.drawLine(obstacle.first.x, obstacle.first.y, obstacle.second.x, obstacle.second.y);
    }

    private void fillCircle(Graphics g, Vector2D center, int radius) {
        g.fillOval((int) center.x - radius / 2, (int) center.y - radius / 2, radius, radius);
    }

    private void clearScreen(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);
    }
}
