package theprojekt;

import doctrina.MovementController;

import java.awt.event.KeyEvent;

public class GamePad extends MovementController {

    private final int quitKey = KeyEvent.VK_Q;
    private final int attackKey = KeyEvent.VK_SPACE;

    public GamePad() {
        bindKey(quitKey);
        bindKey(attackKey);
    }

    public boolean isAttackPressed() {
        return isKeyPressed(attackKey);
    }

    public boolean isQuitPressed() {
        return isKeyPressed(quitKey);
    }
}
