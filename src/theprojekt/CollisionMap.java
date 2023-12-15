package theprojekt;

import doctrina.Blockade;
import doctrina.CSVReader;
import doctrina.Canvas;

import java.util.ArrayList;
import java.util.List;

public class CollisionMap extends CSVReader {
    private static final String csvFile = "ressources/images/sewer_map_collision.csv";
    private int x;
    private int y;

    private ArrayList<ArrayList<Integer>> blockade2DGrid = new ArrayList<>();
    private final int TILE_SIZE = 16;





    public CollisionMap() {
        readBlockadeFromCSV(csvFile, blockade2DGrid);
        x = 0;
        y = 0;

    }



    public ArrayList<ArrayList<Integer>> getBlockade2DGrid() {
        return blockade2DGrid;
    }

}
