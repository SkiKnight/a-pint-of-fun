package game.utility.components;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background {
    public BufferedImage image;
    public int sizeX;       //size of image in pixels along X nd Y
    public int sizeY;
    public boolean tiled;
    
    public Background(String path, boolean tiled){
        this.tiled = tiled;
        loadImage(path);
        sizeX = image.getWidth();
        sizeY = image.getHeight();
    }

    public void loadImage(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("ERROR: Failed loading background image!");
        }
    }
}
