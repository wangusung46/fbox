package fbox;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BackgroundGIF extends JPanel {

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Animated GIF Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        BackgroundGIF gifPanel = new BackgroundGIF("/icon/welcome_page.gif", 100);
        frame.add(gifPanel);

        frame.setVisible(true);
    }

    private final Image[] images;
    private int currentFrame;
    private int delay; // Delay between frames (in milliseconds)

    public BackgroundGIF(String gifPath, int delay) {
        this.delay = delay;
        ImageIcon icon = new ImageIcon(getClass().getResource(gifPath));
        Image gifImage = icon.getImage();
        int numFrames = icon.getIconWidth() / icon.getIconHeight();

        images = new Image[numFrames];
        for (int i = 0; i < numFrames; i++) {
            images[i] = createFrame(gifImage, i, icon.getIconHeight());
        }

        currentFrame = 0;

        Timer timer = new Timer(delay, e -> {
            repaint();
            currentFrame = (currentFrame + 1) % numFrames;
        });
        timer.start();
    }

    private Image createFrame(Image gifImage, int frameIndex, int frameWidth) {
        BufferedImage frame = new BufferedImage(frameWidth, gifImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = frame.createGraphics();
        g2d.drawImage(gifImage, 0, 0, frameWidth, gifImage.getHeight(null), frameIndex * frameWidth, 0, (frameIndex + 1) * frameWidth, gifImage.getHeight(null), null);
        g2d.dispose();
        return frame;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image currentImage = images[currentFrame];
        g.drawImage(currentImage, 0, 0, getWidth(), getHeight(), this);
    }

}
