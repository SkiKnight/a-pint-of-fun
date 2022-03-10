package game.utility.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.utility.components.Background;
import game.utility.components.GameObject;

public class Scene {

    /**
     * The scene is the game world.
     * The coordinate system used is simple cartesian coordinates NOT screen
     * coordinates
     * The range of x axis is [-width/2, width/2]
     * The range of y axis is [-height/2, height/2]
     * Coordinate (0,0) / the Origin is the centre of the scene
     * (x,y) represnts a point : x and y are floats(4 bytes)
     */

    public static final int PLAY = 0;
    public static final int PAUSE = 1;

    private int sceneState;     //either play or pause

    public float width, height;
    private ArrayList<GameObject> gameObjects;
    Background background;

    public Scene(float width, float height) {
        this.width = width;
        this.height = height;
        gameObjects = new ArrayList<GameObject>(0);
        tempGObjects = new ArrayList<GameObject>(0);
        sceneState = PLAY;
    }

    public void setState(int state)
    {
        sceneState = state;
    }
    public int getState()
    {
        return sceneState;
    }

    public void addObject(GameObject obj) {
        gameObjects.add(obj);
        obj.setScene(this);
    }
    public void removeObject(GameObject obj) {
        gameObjects.remove(obj);
    }

    public void setBackground(Background back) {
        background = back;
    }

    ArrayList<GameObject> tempGObjects;

    public ArrayList<GameObject> getRenderableObjects(float xLeft, float yUp, float w, float h) {
        // the params defines a rectangular section in which we search for objects
        tempGObjects.clear();

        float X = 0, Y = 0, W = 0, H = 0;

        if (gameObjects != null) {
            for (GameObject gObj : gameObjects) {
                // check if gObj is inside the rectangular section

                X = gObj.x - gObj.sizeInWorld[2];
                Y = gObj.y + gObj.sizeInWorld[1];
                W = gObj.sizeInWorld[0] + gObj.sizeInWorld[2];
                H = gObj.sizeInWorld[1] + gObj.sizeInWorld[3];

                if (X > xLeft + w || Y < yUp - h ||
                        xLeft > X + W || yUp < Y - H) {
                    // not inside rectangular section
                    continue;
                }
                
                
                tempGObjects.add(gObj);
            }
        }

        return tempGObjects;
    }

    private BufferedImage camError;

    public BufferedImage cropBackgroud(float xLeft, float yUp, float w, float h) {
        int X = (int) (background.sizeX * (0.5f + xLeft / width));
        int Y = (int) (background.sizeY * (0.5f - yUp / height));
        int H = (int) (background.sizeY * (h / height));
        int W = (int) (background.sizeX * (w / width));
        if (X < 0 || Y < 0 || X + W > background.sizeX || Y + H > background.sizeY) {
            if (camError == null) {
                try {
                    camError = ImageIO.read(new File("res/camUtility/camERROR.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return camError;
        }
        return background.image.getSubimage(X, Y, W, H);
    }

    public void update()
    {
        if(sceneState==PAUSE)
        return;

        for(GameObject gObj : gameObjects){
            gObj.update();
        }
    }

}
