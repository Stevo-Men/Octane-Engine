package doctrina;

import theprojekt.BlockadeMap;

import java.awt.*;

public class Blockade extends StaticEntity {

    private int TILE_SIZE = 16;
    public Blockade(int x, int y) {
        this.x = x;
        this.y = y;
        width = TILE_SIZE;
        height = TILE_SIZE;
        CollidableRepository.getInstance().registerEntity(this);

    }

    @Override
    public void draw(Canvas canvas) {
       // canvas.drawRectangle(x, y, TILE_SIZE, TILE_SIZE, Color.RED);
    }
}
