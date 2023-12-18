package theprojekt;

import doctrina.*;
import doctrina.Canvas;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BlockadeMap extends StaticEntity {
    private CollisionMap collisionMap;
    private final int TILE_SIZE = 16;
    private ArrayList<ArrayList<Integer>> blockade2DGrid = new ArrayList<>();
    private List<StaticEntity> blockadeMaps = new ArrayList<>();

    public BlockadeMap(int x , int y) {
        this.x = x;
        this.y = y;
        load();

    }

    public void load() {
        collisionMap = new CollisionMap();
        blockade2DGrid = collisionMap.getBlockade2DGrid();
        addBlockade();
    }

    public void update() {

    }

    private void addBlockade() {
        for (int i = 0; i < blockade2DGrid.size(); i++) {
            for (int j = 0; j < blockade2DGrid.get(i).size(); j++) {
                if (blockade2DGrid.get(i).get(j) == 1) {
                    blockadeMaps.add(new Blockade(x + j * TILE_SIZE, y + i * TILE_SIZE));

                }
            }
        }
    }


    @Override
    public void draw(Canvas canvas) {
        for (StaticEntity blockade : blockadeMaps) {
            blockade.draw(canvas);
        }

    }
}
