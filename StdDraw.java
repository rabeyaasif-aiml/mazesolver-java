/******************************************************************************
 *  Compilation:  javac StdDraw.java
 *  Execution:    java StdDraw
 *
 *  Standard drawing library.
 *
 ******************************************************************************/

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public final class StdDraw implements MouseListener, MouseMotionListener, KeyListener {

    // canvas size
    private static final int DEFAULT_SIZE = 512;
    private static int width = DEFAULT_SIZE;
    private static int height = DEFAULT_SIZE;

    // user's coordinate system
    private static double xmin, ymin, xmax, ymax;

    // double buffering
    private static BufferedImage offscreenImage, onscreenImage;
    private static java.awt.Graphics2D offscreen, onscreen;

    // singleton for listeners
    private static StdDraw std = new StdDraw();

    // mouse events
    private static volatile double mouseX = 0;
    private static volatile double mouseY = 0;
    private static volatile boolean mousePressed = false;

    // keyboard events
    private static volatile int lastKeyTyped = -1;

    static { init(); }

    private static void init() {
        // create the drawing window
        JFrame frame = new JFrame("StdDraw");
        onscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        offscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        onscreen = onscreenImage.createGraphics();
        offscreen = offscreenImage.createGraphics();

        offscreen.setColor(Color.WHITE);
        offscreen.fillRect(0, 0, width, height);

        frame.setContentPane(new javax.swing.JLabel(new javax.swing.ImageIcon(onscreenImage)));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addMouseListener(std);
        frame.addMouseMotionListener(std);
        frame.addKeyListener(std);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        setXscale();
        setYscale();
    }

    // scale
    public static void setXscale() { setXscale(0.0, 1.0); }
    public static void setYscale() { setYscale(0.0, 1.0); }
    public static void setXscale(double min, double max) { xmin = min; xmax = max; }
    public static void setYscale(double min, double max) { ymin = min; ymax = max; }

    private static double scaleX(double x) { return width  * (x - xmin) / (xmax - xmin); }
    private static double scaleY(double y) { return height * (y - ymin) / (ymax - ymin); }

    // clear
    public static void clear() { clear(Color.WHITE); }
    public static void clear(Color color) {
        offscreen.setColor(color);
        offscreen.fillRect(0, 0, width, height);
    }

    // set pen properties
    public static void setPenColor(Color color) { offscreen.setColor(color); }
    public static final Color BLACK = Color.BLACK;

    // draw line
    public static void line(double x0, double y0, double x1, double y1) {
        offscreen.drawLine((int) scaleX(x0), (int) scaleY(y0),
                           (int) scaleX(x1), (int) scaleY(y1));
    }

    // draw filled circle
    public static void filledCircle(double x, double y, double r) {
        int xs = (int) scaleX(x);
        int ys = (int) scaleY(y);
        int rs = (int) (r * width / (xmax - xmin));
        offscreen.fillOval(xs - rs, ys - rs, 2*rs, 2*rs);
    }

    // show
    public static void show() { onscreen.drawImage(offscreenImage, 0, 0, null); }

    // double buffering
    public static void enableDoubleBuffering() { }

    // ============================
    // mouse + key listeners
    // ============================

    public void mousePressed(MouseEvent e) {
        mouseX = e.getX(); mouseY = height - e.getY();
        mousePressed = true;
    }

    public void mouseReleased(MouseEvent e) { mousePressed = false; }
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX(); mouseY = height - e.getY();
    }
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX(); mouseY = height - e.getY();
    }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) {}

    // key
    public void keyTyped(KeyEvent e) {
        lastKeyTyped = e.getKeyChar();
    }
    public void keyPressed(KeyEvent e) { }
    public void keyReleased(KeyEvent e) { }

    // query keyboard and mouse
    public static boolean isMousePressed() { return mousePressed; }

    public static double mouseX() { return mouseX; }

    public static double mouseY() { return mouseY; }

    public static char nextKeyTyped() {
        char c = (char) lastKeyTyped;
        lastKeyTyped = -1;
        return c;
    }
}
