package theprojekt;

import doctrina.CSVReader;
import doctrina.Canvas;
import doctrina.CollidableRepository;
import doctrina.StaticEntity;

import java.awt.*;
import java.util.ArrayList;

public class BlockadeMap extends StaticEntity {
    private static final String csvFile = "sewer_map_collision.csv";
    private CollisionMap collisionMap;
    private final int TILE_SIZE = 16;
    private ArrayList<ArrayList<Integer>> blockade2DGrid = new ArrayList<>();
    private Camera camera;
    public BlockadeMap(int x , int y) {
        this.x = x;
        this.y = y;


    }

    public void load() {
        collisionMap = new CollisionMap();
        blockade2DGrid = collisionMap.getBlockade2DGrid();
    }

    public void update() {

    }

    private void drawBlockade(Canvas canvas) {
        for (int i = 0; i < blockade2DGrid.size(); i++) {
            for (int j = 0; j < blockade2DGrid.get(i).size(); j++) {
                if (blockade2DGrid.get(i).get(j) == 1) {
                    canvas.drawRectangle(x + j * TILE_SIZE, y + i * TILE_SIZE, TILE_SIZE, TILE_SIZE, new Color(255, 0, 0, 100));
                    CollidableRepository.getInstance().registerEntity(this);
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        drawBlockade(canvas);
    }
}