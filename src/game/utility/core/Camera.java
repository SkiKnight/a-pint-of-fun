package game.utility.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Graphics2D;

import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.utility.components.GameObject;
import game.utility.components.Text;

public class Camera {

    public BufferedImage screen; // Conceptualy similar to a led screen on digital camera
    Graphics2D g2d; // Graphics2D obj

    public float x, y; // position of camera in the game world/scene
    public int zoom = 100; // number of pixels for 1 unit in game world i.e, "scene"
    private int width, height; // resolution of the camera screen
    Scene scene; // the actual scene to which the camera is tied

    private BufferedImage vignette;

    public Camera(int width, int height, Scene scene) {
        this.width = width;
        this.height = height;
        this.scene = scene;
        screen = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        g2d = screen.createGraphics();

        try {
            BufferedImage temp = ImageIO.read(new File("res/camUtility/Vignette3.png"));
            vignette = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
            float xScale = this.width / temp.getWidth();
            float yScale = this.height / temp.getHeight();
            Graphics2D grph = (Graphics2D) vignette.getGraphics();
            grph.scale(xScale, yScale);
            grph.drawImage(temp, 0, 0, null);
            grph.dispose();

        } catch (IOException e) {
            e.printStackTrace();
        }

        overLayText = new ArrayList<Text>();
    }

    public void render() {
        // This is where each gameObjects will be asked to render themselves
        float camX = width / (2 * zoom);
        float camY = height / (2 * zoom);

        if (scene.background == null) {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, width, height);
        } else {
            if (!scene.background.tiled) {
                BufferedImage img = scene.cropBackgroud(x - camX, y + camY, 2 * camX, 2 * camY);
                g2d.drawImage(img, 0, 0, width, height, null);
            }
        }

        ArrayList<GameObject> gObjects = scene.getRenderableObjects(x - camX, y + camY, 2 * camX, 2 * camY);
        if (gObjects.size() != 0) {
            for (GameObject gObj : gObjects) {
                int screenX = width / 2 + (int) (zoom * (gObj.x - x));
                int screenY = height / 2 - (int) (zoom * (gObj.y - y));
                int rightBound = (int) (gObj.sizeInWorld[0] * zoom);
                int upBound = (int) (gObj.sizeInWorld[1] * zoom);
                int leftBound = (int) (gObj.sizeInWorld[2] * zoom);
                int lowBound = (int) (gObj.sizeInWorld[3] * zoom);
                gObj.render(g2d, screenX - leftBound, screenY - upBound, rightBound + leftBound, upBound + lowBound);

            }
        }

        // special effects
        if (isVignette)
            addVignette();

        // overlay texts
        //System.out.println(overLayText.size());
        for (Text Tobj : overLayText) {
            renderText(Tobj);
            // System.out.println("text remdered");
        }

    }

    public void renderText(Text Tobj) {
        g2d.setColor(Tobj.color);
        g2d.setFont(Tobj);
        g2d.drawString(Tobj.text, Tobj.x, Tobj.y);
    }

    private void addVignette() {
        // g2d.drawImage(vignette, 0, 0, width, height, null);
        g2d.drawImage(vignette, 0, 0, null);
    }

    private boolean isVignette = false;

    public void applyVignette(boolean flag) {
        isVignette = flag;
    }

    public ArrayList<Text> overLayText;

}
