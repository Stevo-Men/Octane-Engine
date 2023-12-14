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
    private CollisionMap collisionMap;
    private ArrayList<Blockade> blockades;
    private BlockadeMap blockadeMap;

    @Override
    protected void initialize() {
        gamePad = new GamePad();
        player = new Player(gamePad);
        player.teleport(200, 200);
        map = new Map();
        map.load();
        blockadeMap = new BlockadeMap(0,0);
        blockadeMap.load();
        camera = new Camera();
        npcs = new ArrayList<>();
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


        player.update();
        camera.update();
        blockadeMap.update();


        ArrayList<StaticEntity> killedElements = new ArrayList<>();

            for (Npc npc : npcs) {
                npc.update(player);
            }

            for (Knife knife : knives) {
                knife.update();
                for (Npc npc : npcs) {
                   if (knife.hitBoxIntersectWith(npc)) {
                       killedElements.add(knife);
                       npc.isTouched(knife);
                       if (npc.getHealth() <= 0) {
                           killedElements.add(npc);
                       }
                   }
                }

            }



        for (StaticEntity killedElement : killedElements) {
            if (killedElement instanceof Npc) {
                npcs.remove(killedElement);
            }
            if (killedElement instanceof Knife) {
                knives.remove(killedElement);
            }
        }

        CollidableRepository.getInstance().unregisterEntities(killedElements);
    }

    @Override
    protected void draw(Canvas canvas) {

        int translatedX = camera.translateX(map.getX());
        int translatedY = camera.translateY(map.getY());


        map.draw(canvas, translatedX, translatedY);
        blockadeMap.draw(canvas);


        for (Npc npc : npcs) {
            npc.draw(canvas,camera);
        }

        for (Knife knife : knives) {
            knife.draw(canvas);
        }

        player.draw(canvas);
        hud.draw(canvas);



    }
}
