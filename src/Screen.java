import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class Screen extends JPanel {

    ArrayList<Obstacle> obstacles = new ArrayList<>();
    ArrayList<Ray> rays = new ArrayList<>();
    int povX;
    int povY;
    Screen3D output3D;

    Screen() {

        for (int currentAngle = -45; currentAngle < 45; currentAngle++) {
            rays.add(new Ray(new Vector2D(50, 200), new Vector2D(currentAngle)));
        }

        obstacles.add(new Obstacle(new Point(300, 50), new Point(300, 500)));
        obstacles.add(new Obstacle(new Point(50, 100), new Point(200, 150)));

        new ViewerPlane(new Vector2D(50, 50), 40, new Vector2D(1, 0));

        JFrame window = new JFrame("2DRay");

        window.add(this);
        window.setSize(600, 600);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);

        output3D = new Screen3D();


        window.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                povX = e.getX();
                povY = e.getY();
                repaint();
                output3D.repaint();
            }
        });

        window.requestFocusInWindow();
    }
    // TODO center slices in the middle of the screen
    // TODO fish eye
    // TODO nearest wall

    static public void main(String[] args) {
        new Screen();
    }

    @Override
    public void paintComponent(Graphics g) {
        clearScreen(g);

        Point pov = new Point(povX, povY);
        fillCircle(g, pov, 10);

        for (Obstacle obstacle : obstacles) {
            drawObstacle(g, obstacle);
            int count = 0;
            for (Ray ray : rays) {
                Vector2D pt = ray.cast(obstacle);
                if (pt != null) {
                    g.drawLine(povX, povY, (int) pt.x, (int) pt.y);
                }
                if (pt != null) {
                    output3D.distances[count] = ray.distanceTo(pov, new Point((int) pt.x, (int) pt.y));
                } else {
                    output3D.distances[count] = -1;
                }
                count++;

            }
        }
    }

    private void drawObstacle(Graphics g, Obstacle obstacle) {
        g.drawLine(obstacle.first.x, obstacle.first.y, obstacle.second.x, obstacle.second.y);
    }

    private void fillCircle(Graphics g, Point center, int radius) {
        g.fillOval(center.x - 5, center.y - 5, radius, radius);
    }

    private void clearScreen(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 600, 600);
        g.setColor(Color.BLACK);
    }
}
