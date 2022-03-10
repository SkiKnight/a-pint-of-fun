package game.dino;


import game.utility.components.GameObject;
import game.utility.core.Scene;

public class Cactus extends GameObject {
    
    Dinosaur dinosaur;
    float velX = -4f;
    private float accX = -0.005f;
    Dino g;
    public Cactus()
    {
        setSprite("res/dino/cactus.png");
        sizeInWorld[0]=0.2f;
        sizeInWorld[1]=0.2f;
        sizeInWorld[2]=0.2f;
        sizeInWorld[3]=0.2f;
        setCollider(0.15f, 0.15f, 0.15f, 0.15f);
        y=-0.5f;
    }
    public void update()
    {
        if(x>-scene.width/2-0.3)
        x+=velX*clock.delT;

        velX+=accX*clock.delT;
        if(collidesWith(dinosaur))
        {
            scene.setState(Scene.PAUSE);
            g.isGameOver = true;
        }
    }
}
