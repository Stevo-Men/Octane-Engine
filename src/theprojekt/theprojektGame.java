package theprojekt;

import doctrina.Canvas;
import doctrina.*;
import tank.Brick;
import tank.Missile;

import java.util.ArrayList;

public class theprojektGame extends Game {
    private Player player;
    private GamePad gamePad;
    private Map map;
    private Camera camera;
    private Npc npc;
    private ArrayList<Npc> npcs;
    private HUD hud;
    private ArrayList<Knife> knives;
    @Override
    protected void initialize() {
        gamePad = new GamePad();
        player = new Player(gamePad);
        player.teleport(200, 200);
        map = new Map();
        map.load();
        camera = new Camera();
        npcs = new ArrayList<>();
        npc = new Npc(400,200);
        npcs.add(new Npc(300,400));
        hud = new HUD();
        knives = new ArrayList<>();


        RenderingEngine.getInstance().getScreen().toggleFullscreen();
        RenderingEngine.getInstance().getScreen().hideCursor();
    }



    @Override
    protected void update() {
        if (gamePad.isQuitPressed()) {
            stop();
        }

        if (gamePad.isAttackPressed() && player.canThrow()) {
            knives.add(player.throω());
        }

        camera.update();
        player.update();


        ArrayList<StaticEntity> killedElements = new ArrayList<>();

            for (Npc npc : npcs) {
                npc.update();
            }

            for (Knife knife : knives) {
                knife.update();
            }


        CollidableRepository.getInstance().unregisterEntities(killedElements);
    }

    @Override
    protected void draw(Canvas canvas) {


        int translatedX = camera.translateX(map.getX());
        int translatedY = camera.translateY(map.getY());

        map.draw(canvas, translatedX, translatedY);

        for (Npc npc : npcs) {
            npc.draw(canvas, translatedX, translatedY);
        }

        for (Knife knife : knives) {
            knife.draw(canvas);
        }

        player.draw(canvas);
        hud.draw(canvas);



    }
}
