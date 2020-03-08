import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class Screen extends JPanel {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    ArrayList<Obstacle> obstacles = new ArrayList<>();
    RayCollection rays;
    Vector2D pov;
    Screen3D output3D;
    Vector2D povDirectionInDegrees;
    int mouseX;
    int mouseY;
    private Vector2D subtract = new Vector2D(0, 0);

    Screen() {
        pov = new Vector2D(50, 200);

        povDirectionInDegrees = new Vector2D(0, 1);
        rays = new RayCollection(pov, povDirectionInDegrees);

        obstacles.add(new Obstacle(new Point(300, 50), new Point(300, 500)));
        obstacles.add(new Obstacle(new Point(50, 100), new Point(200, 150)));

        obstacles.add(new Obstacle(new Point(0, 0), new Point(WIDTH, 0)));
        obstacles.add(new Obstacle(new Point(0, 0), new Point(0, HEIGHT)));
        obstacles.add(new Obstacle(new Point(WIDTH, HEIGHT), new Point(WIDTH, 0)));
        obstacles.add(new Obstacle(new Point(WIDTH, HEIGHT), new Point(0, HEIGHT)));

        JFrame window = new JFrame("2DRay");
        output3D = new Screen3D();

        window.add(this);
        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                mouseX = e.getX();
                mouseY = e.getY();
                Vector2D povDirection = pov.subtract(new Vector2D(e.getX(), e.getY()));
                rays = new RayCollection(pov, povDirection);
                repaint();
                output3D.repaint();
            }
        });

        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    pov.x -= 10;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    pov.x += 10;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    pov.y += 10;
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    pov.y -= 10;
                }
                repaint();
                output3D.repaint();
            }
        });

        repaint();

        window.requestFocusInWindow();
    }
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

        g.drawString("pov: (" + pov.x + "," + pov.y + ")", 10, 15);
        g.drawString("pov theta: " + povDirectionInDegrees, 10, 30);
        g.drawString("ray angles upper: " + rays.upperBound, 10, 45);
        g.drawString("ray angles lower: " + rays.lowerBound, 10, 60);
        g.drawString("mouse: (" + mouseX + "," + mouseY + ")", 10, 75);
        subtract.normalize();
        g.drawString("direction vector: (" + subtract.x + "," + subtract.y + ")", 10, 90);
        fillCircle(g, new Vector2D(mouseX, mouseY), 10);

        int count = 0;
        for (Ray ray : rays.rays) {
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
//                output3D.distances[count] = (int) (distanceToClosestIntersection * Math.cos(Math.toRadians(ray.angleToPovDirection)));
                output3D.distances[count] = (int) (closestIntersection.x * Math.cos(Math.toRadians(0)) + closestIntersection.y * Math.sin(Math.toRadians(0)));
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
