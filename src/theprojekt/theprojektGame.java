package theprojekt;

import doctrina.Canvas;
import doctrina.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class theprojektGame extends Game {
    private Player player;
    private GamePad gamePad;
    private Map map;
    private Camera camera;
    private ArrayList<Npc> npcs;
    private ArrayList<Chest> chests;
    private List<StaticEntity> blockadeMaps;
    private HUD hud;
    private ArrayList<Knife> knives;
    private BlockadeMap blockadeMap;
    private final SoundEffect gameOverFx = SoundEffect.GAMEOVER_FX;
    private final SoundEffect dashFx = SoundEffect.DASH_FX;

    @Override
    protected void initialize() {
        gamePad = new GamePad();
        player = new Player(gamePad);
        player.teleport(100, 500);
        map = new Map();
        map.load();
        camera = new Camera();
        npcs = new ArrayList<>();
        npcs.add(new Npc(100, 300,0));
        npcs.add(new Npc(800, 400,1));
        npcs.add(new Npc(100, 100,1));
        npcs.add(new Npc(275, 75,2));
        npcs.add(new Npc(600, 75,2));
        npcs.add(new Npc(830, 70,2));
        npcs.add(new Npc(380, 450,0));
        npcs.add(new Npc(540, 440,0));
        npcs.add(new Npc(750, 170,2));
        npcs.add(new Npc(460, 160,0));
        chests = new ArrayList<>();
        chests.add(new Chest(100, 400, 10, 100));
        chests.add(new Chest(150, 160, 10, 100));
        chests.add(new Chest(450, 360, 10, 100));
        chests.add(new Chest(460, 60, 10, 100));
        hud = new HUD();
        knives = new ArrayList<>();
        blockadeMap = new BlockadeMap(0, 0);
        blockadeMaps = new ArrayList<>();




        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream stream = AudioSystem.getAudioInputStream(
                    this.getClass().getClassLoader().getResourceAsStream("audios/droneAmbient.wav"));
            clip.open(stream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


        RenderingEngine.getInstance().getScreen().toggleFullscreen();
        RenderingEngine.getInstance().getScreen().hideCursor();
    }


    @Override
    protected void update() {

        if (gamePad.isQuitPressed()) {
            stop();
        }
        if (!player.stillAlive()) {
           gameOverFx.play();
        }

        if (gamePad.isAttackPressed() && player.canThrow()) {
            knives.add(player.throwKnife());
        }
        if (gamePad.isShiftPressed() && player.canDash()) {
           player.dash();
           dashFx.play();
        }

        player.update();
        //camera.update(player);
        hud.update(player);

        ArrayList<StaticEntity> killedElements = new ArrayList<>();

        for (Npc npc : npcs) {
            if (npc.canAttack(player)) {
                npc.isAttacking = true;
                npc.attack(player);
            } else {
                npc.isAttacking = false;
            }
            if (npc.isChasing(player)) {
                player.detectedState = npc.isChasing(player);
            }
            npc.update(player);
        }

        for (Chest chest : chests) {
            if (player.getBounds().intersects(chest.getBounds()) && chest.isAlive) {
                chest.open(player);
            }

        }

        for (Knife knife : knives) {
            if (knife.isOutOfBounds() || !knife.isFlying()) {
                killedElements.add(knife);
            }
            knife.update();

            for (Npc npc : npcs) {
                if (knife.hitBoxIntersectWith(npc)) {
                    killedElements.add(knife);
                    npc.isTouched(knife);
                    npc.isTouched();

                    if (npc.getHealth() <= 0) {
                        killedElements.add(npc);
                    }
                }
            }
        }

        for (StaticEntity killedElement : killedElements) {
            if (killedElement instanceof Npc) {
                npcs.remove(killedElement);
                player.detectedState = false;
            }
            if (killedElement instanceof Knife) {
                knives.remove(killedElement);
            }
        }
        CollidableRepository.getInstance().unregisterEntities(killedElements);


    }


    @Override
    protected void draw(Canvas canvas) {


        map.draw(canvas,camera);
        for (StaticEntity blockade : blockadeMaps) {
            blockade.draw(canvas);
        }
        blockadeMap.draw(canvas);

        for (Chest chest : chests) {
            chest.draw(canvas);
            if (!chest.isAlive) {
                chest.draw(canvas);
            }
        }

        for (Npc npc : npcs) {
            npc.draw(canvas, npc.getBounds().intersects(player.playerVision.getBounds2D()));

            if (npc.isAttacking) {
                npc.drawAttackEffect(canvas,player);
            }
        }

        for (Knife knife : knives) {
            knife.draw(canvas);
        }

        if (player.stillAlive()) {
            player.draw(canvas);
            hud.draw(canvas);
            player.drawDebuginfo(canvas);
            hud.hudTexture(canvas, player);
        } else {
           hud.drawGameOver(canvas);
        }

    }
}
