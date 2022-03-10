package game.utility.input;

import java.awt.event.*;

public class KeyInput implements KeyListener {

    public boolean[] keyState;

    public KeyInput() {
        keyState = new boolean[150];
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        
        keyState[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyState[e.getKeyCode()] = false;
    }

}
