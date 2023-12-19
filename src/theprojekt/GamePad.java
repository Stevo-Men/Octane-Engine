package theprojekt;

import doctrina.MovementController;

import java.awt.event.KeyEvent;

public class GamePad extends MovementController {

    private final int quitKey = KeyEvent.VK_Q;
    private final int attackKey = KeyEvent.VK_SPACE;
    private final int pauseKey = KeyEvent.VK_P;
    private final int resumeKey = KeyEvent.VK_O;
    private final int shiftKey = KeyEvent.VK_SHIFT;



    public GamePad() {
        bindKey(quitKey);
        bindKey(attackKey);
        bindKey(pauseKey);
        bindKey(resumeKey);
        bindKey(shiftKey);
    }

    public boolean isAttackPressed() {
        return isKeyPressed(attackKey);
    }

    public boolean isQuitPressed() {
        return isKeyPressed(quitKey);
    }

    public boolean isPausePressed() {
        return isKeyPressed(pauseKey);
    }


    public boolean isResumePressed() {
        return isKeyPressed(resumeKey);
    }

    public boolean isShiftPressed() {
        return isKeyPressed(shiftKey);
    }


}
