package game.utility.components;

import java.awt.event.KeyEvent;

import game.utility.core.Clock;
import game.utility.input.KeyInput;

/**
 * this is a test class and will be removed after debugging
 */
public class Player extends GameObject{
    public Clock clock;
    public KeyInput kIP;

    public Player() {
        setSprite("res/test/Dino.png");
        x =0;
        y = -0.5f;
        rotation =0;
        isVisisble = true;
        sizeInWorld[0] = 1; 
        sizeInWorld[1] = 1;
        sizeInWorld[2] = 1;
        sizeInWorld[3] = 1;
    }

    public void update() {
        //System.out.println("Pos update player");
        if(kIP.keyState[KeyEvent.VK_UP])
        y+=0.2;
        if(kIP.keyState[KeyEvent.VK_DOWN])
        y-=0.2;
        if(kIP.keyState[KeyEvent.VK_LEFT])
        x-=0.2;
        if(kIP.keyState[KeyEvent.VK_RIGHT])
        x+=0.2;

    }

}
