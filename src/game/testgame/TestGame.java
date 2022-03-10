package game.testgame;

import game.utility.Game;
import game.utility.components.Background;
import game.utility.components.Player;
import game.utility.core.Camera;
import game.utility.core.Scene;

import java.awt.event.KeyEvent;

import java.awt.Graphics;

public class TestGame extends Game{
    
    Player player;
    Scene scene;
    Camera cam;

    public TestGame()
    {
        player = new Player();
        player.clock = clock;
        player.kIP = kIP;
        scene = new Scene(12, 6);
        cam = new Camera(800, 600, scene);
        Background background = new Background("res/test/Background1.png",false);
        scene.setBackground(background);
        scene.addObject(player);
        window.setTitle("engine test : debug");
    }
    
    public void render()
    {
        cam.render();
        repaint();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(cam!=null)
        g.drawImage(cam.screen, 0, 0, null);
    }

    int updateZoom = 1;
    public void update()
    {
        
        player.update();
        //cam.zoom+=updateZoom;
        if(cam.zoom>200 || cam.zoom < 50)
        updateZoom = - updateZoom;  
        
        if(kIP.keyState[KeyEvent.VK_W])
        cam.y+=0.2;
        if(kIP.keyState[KeyEvent.VK_S])
        cam.y-=0.2;
        if(kIP.keyState[KeyEvent.VK_A])
        cam.x-=0.2;
        if(kIP.keyState[KeyEvent.VK_D])
        cam.x+=0.2;
    }

}
