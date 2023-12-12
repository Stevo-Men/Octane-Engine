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
   public int translatedX;
   public int translatedY;
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

        translatedX = camera.translateX(map.getX());
        translatedY = camera.translateY(map.getY());


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

    public int getTranslatedX() {
        return translatedX;
    }

    public int getTranslatedY() {
        return translatedY;
    }
}
