package game.dino;

import game.utility.components.GameObject;
import game.utility.input.ArduinoSingleInput_Digital;
import game.utility.input.KeyInput;

import java.awt.event.KeyEvent;

public class Dinosaur extends GameObject {

    KeyInput kIP;
    ArduinoSingleInput_Digital aIP;
    
    public Dinosaur() {
        setSprite("res/dino/Dino.png");
        sizeInWorld[0]=0.6f;
        sizeInWorld[1]=0.4f;
        sizeInWorld[2]=0.6f;
        sizeInWorld[3]=0.4f;
        setCollider(0.3f, 0.35f, 0.1f, 0.3f);
        x=-2.5f;
        y=0.3f;
        velY=0;
    }

    float ground = -0.3f;
    float accY = -9.81f;
    float velY;
    public void update()
    {
        if(y<ground)
        {y=ground;velY=0;}
        else if(y>ground)
        {
            y+=velY*clock.delT;
            velY+=accY*clock.delT;
        }
        if(kIP.keyState[KeyEvent.VK_SPACE] || aIP.state)
        {
            if(y<=ground+0.01f)
            {y=0.0001f;velY=3.5f; }
        }
    }

}
