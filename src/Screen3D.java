import javax.swing.*;
import java.awt.*;

public class Screen3D extends JPanel {

    int[] distances = new int[90];

    public Screen3D() {
        JFrame frame3D = new JFrame("3D");

        frame3D.setSize(400, 400);
        frame3D.setVisible(true);
        setLocationToTopRight(frame3D);

        frame3D.add(this);
        frame3D.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        setPreferredSize(new Dimension(360, 400));

        frame3D.pack();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, 400, 200);
        g.setColor(Color.WHITE);
        g.fillRect(0, 200, 400, 200);
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] >= 0) {
                int height = (int) (-0.25 * distances[i]) + 200;
                g.setColor(new Color(0, 41, 0));
                g.fillRect(i * 4, 100, 4, height);
            }
        }
    }

    private void setLocationToTopRight(JFrame frame) {
        GraphicsConfiguration config = frame.getGraphicsConfiguration();
        Rectangle bounds = config.getBounds();
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(config);

        int x = bounds.x + bounds.width - insets.right - frame.getWidth();
        int y = bounds.y + insets.top;
        frame.setLocation(x, y);
    }
}
