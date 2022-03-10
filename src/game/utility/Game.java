package game.utility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

import game.utility.core.Clock;
import game.utility.input.KeyInput;

public class Game extends JPanel implements Runnable {

    protected JFrame window;
    Thread gameThread;

    int screenWidth = 800, screenHeight = 600;
    boolean running = false;

    public Clock clock;
    public KeyInput kIP;

    public Game() {

        window = new JFrame("GAME: Title NOT set");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        window.add(this);
        window.pack();
        window.setVisible(true);

        gameThread = new Thread(this);
        gameThread.setPriority(1);
        clock = new Clock();

        kIP = new KeyInput();
        addKeyListener(kIP);
        requestFocus();
    }

    public void start() {
        running = true;
        gameThread.start();
    }

    public void stop() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ;
    }

    public void run() {

        clock.tick();       //first clock tick

        int targetFPS = 40;
        float targetFrameRanderTime = 1f / targetFPS;
        float nextRenderTime = clock.time;
        float nextFPSresetTime = clock.time;
        int fps = 0;

        long sleepDuration = 0;

        while (running) {

            clock.tick();
            if (clock.time > nextRenderTime) {

                nextRenderTime += targetFrameRanderTime;

                
                render();
                update();

                fps++;

                // we got to sleep if we have rendered a frame and there is time left for the
                // next
                sleepDuration = (long) (0.95 * 1000 * (nextRenderTime - clock.time));
                if (sleepDuration > 0) {
                    try {
                        Thread.sleep(sleepDuration);
                        // System.out.println("Slept for "+sleepDuration+" milis");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (clock.time > nextFPSresetTime) {
                nextFPSresetTime += 1;
                System.out.println("FPS = " + fps);
                fps = 0;
            }

        }
    }

    public void update() {
    }

    public void render(){
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g2d.dispose();
    }

}
