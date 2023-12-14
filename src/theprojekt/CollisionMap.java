package theprojekt;

import doctrina.CSVReader;

import java.util.ArrayList;
import java.util.Map;

public class CollisionMap extends CSVReader {
    private static final String csvFile = "ressources/images/sewer_map_collision.csv";
    private int x;
    private int y;
    private theprojekt.Map map;
    private ArrayList<ArrayList<Integer>> blockade2DGrid = new ArrayList<>();


    public CollisionMap() {
        readBlockadeFromCSV(csvFile, blockade2DGrid);
        x = 0;
        y = 0;
    }

    public ArrayList<ArrayList<Integer>> getBlockade2DGrid() {
        return blockade2DGrid;
    }


}
