package game.dino;

import game.utility.Game;
import game.utility.audio.AudioManager;
import game.utility.components.Background;
import game.utility.components.Text;
import game.utility.core.Camera;
import game.utility.core.Scene;
import game.utility.input.ArduinoSingleInput_Digital;
import game.utility.input.KeyInput;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Dino extends Game {

    Scene scene;
    Camera camera;
    Dinosaur player;
    Cactus[] cacti;
    KeyInput kIP;
    ArduinoSingleInput_Digital aIP;
    AudioManager audioManager;
    Text scoreBoard;
    boolean isGameOver = false;

    public Dino() {
        scene = new Scene(8, 6);
        camera = new Camera(800, 600, scene);

        camera.applyVignette(true);

        Background back = new Background("res/dino/Background.png", false);
        scene.setBackground(back);

        player = new Dinosaur();
        player.clock = clock;
        scene.addObject(player);
        cacti = new Cactus[5];
        for (int i = 0; i < cacti.length; i++) {
            cacti[i] = new Cactus();
            cacti[i].x = -scene.width / 2 - 0.4f;
            cacti[i].dinosaur = player;
            cacti[i].clock = clock;
            cacti[i].g = this;
            scene.addObject(cacti[i]);
        }

        window.setTitle("::Chrome Dino::");

        kIP = new KeyInput();
        addKeyListener(kIP);
        player.kIP = kIP;
        aIP = new ArduinoSingleInput_Digital();
        player.aIP = aIP;

        audioManager = new AudioManager();
        backMusic = audioManager.addToLibrary("res/dino/Epic-Chase.wav");
        audioManager.loop(backMusic);

        scoreBoard = new Text("VERDANA", Font.BOLD, 20);
        scoreBoard.x = 10;
        scoreBoard.y = 50;
        scoreBoard.text = "SCORE:";
        camera.overLayText.add(scoreBoard);

    }

    int backMusic = 0;

    // render stuff
    public void render() {
        camera.render();
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (camera != null)
            g.drawImage(camera.screen, 0, 0, null);
        g.dispose();
    }

    boolean Xkeypressed = false;

    public void update() {
        scene.update();

        if (kIP.keyState[KeyEvent.VK_ESCAPE])
            System.exit(0);
        if (kIP.keyState[KeyEvent.VK_X] && !Xkeypressed) {
            Xkeypressed = true;
            if (scene.getState() == Scene.PLAY) {
                scene.setState(Scene.PAUSE);
                audioManager.pause(backMusic);
            } else {
                scene.setState(Scene.PLAY);
                audioManager.loop(backMusic);
            }
        } else if (!kIP.keyState[KeyEvent.VK_X] && Xkeypressed)
            Xkeypressed = false;

        if (clock.time > lastCactusTime + baseCactusTiming) {
            nextCactus();
            lastCactusTime = clock.time;
            cactusTiming = (float) (baseCactusTiming + 0.7 * Math.random());
            if (cactusTiming < 0)
                cactusTiming = 0.1f;

            baseCactusTiming -= 0.005f;
        }

        if (scene.getState() == Scene.PLAY)
            distance += -(cacti[0].velX * clock.delT);

        scoreBoard.text = "DISTANCE: " + (int) distance + " meters";

        if (isGameOver) {
            //System.out.println("OVER");
            Scene ending = new Scene(8, 6);
            Camera sadCam = new Camera(800, 600, ending);
            Background oopsie = new Background("res/dino/X.png", false);
            ending.setBackground(oopsie);
            this.camera = sadCam;
            this.camera.overLayText = new ArrayList<Text>();
            this.camera.overLayText.add(scoreBoard);
            render();
            audioManager.pause(backMusic);
            stop();
        }

    }

    float distance = 0;
    float baseCactusTiming = 1.5f;
    float cactusTiming = 0;
    float lastCactusTime = 0;

    public void nextCactus() {
        for (Cactus c : cacti) {
            if (c.x < -scene.width / 2 - 0.3) {
                c.x = scene.width / 2 + 0.2f;
                return;
            }
        }
    }
}
