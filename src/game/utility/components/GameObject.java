package game.utility.components;

import game.utility.core.Clock;
import game.utility.core.Scene;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameObject {

    public float x, y;
    public float rotation;
    AffineTransform transform;

    public Collider collider;

    public Scene scene;
    public boolean isVisisble = false;
    public Clock clock;

    public BufferedImage sprite;

    public float sizeInWorld[];
    /*
     * {rightBound, topBound, leftBound, lowBound} *"distance"(magnitude) from
     * Origin of the gameObject
     * also is the approx boundary
     */

    public GameObject() {
        isVisisble = false;
        sizeInWorld = new float[4];
        transform = new AffineTransform();
        rotation = 0;
    }

    public void setScene(Scene scene) {
        if (this.scene == null)
            this.scene = scene;
    }

    public void setSprite(String path) {
        try {
            sprite = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCollider(Collider collider) {
        this.collider = collider;
    }

    public void setCollider(float rightBound, float topBound, float leftBound, float lowBound) {
        collider = new Collider(rightBound, topBound, leftBound, lowBound);
    }

    public void update() {
        System.out.println("Update method notr defined : " + this.getClass().getName());
    }

    public void render(Graphics2D g2d, int x0, int y0, int w, int h) {
        g2d.drawImage(sprite, x0, y0, w, h, null);
        // System.out.print(this.getClass().getName()+"\t");
        // g2d.setColor(Color.GREEN);
        // g2d.drawRect(x0, y0, w, h);
        // float scale = w / (sizeInWorld[0]+sizeInWorld[2]);
        // int W = (int) (scale*(collider.bounds[0] + collider.bounds[2]));
        // int H = (int) (scale*(collider.bounds[1] + collider.bounds[3]));
        // int X = x0 + (int)(scale * (sizeInWorld[0]-collider.bounds[0]));
        // int Y = y0 + (int)(scale * (sizeInWorld[1]-collider.bounds[1]));
        // g2d.setColor(Color.RED);
        // g2d.drawRect(X, Y, W, H);
        
    }

    public boolean collidesWith(GameObject obj2) {
        if (collider == null || obj2.collider == null)
            return false;
        //no collision under the following condition
        if ((this.x - this.collider.bounds[2]) > (obj2.x + obj2.collider.bounds[0]) ||
                (obj2.x - obj2.collider.bounds[2]) > (this.x + this.collider.bounds[0]) ||
                (this.y - this.collider.bounds[3]) > (obj2.y + obj2.collider.bounds[1]) ||
                (obj2.y - obj2.collider.bounds[3]) > (this.y + this.collider.bounds[1]))
            return false;
        //else collision has occured
        return true;
    }

}
