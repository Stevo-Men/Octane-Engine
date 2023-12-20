package doctrina;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public abstract class CSVReader {

    private static final int GRID_SIZE = 64;
    private static final int BLOCKADE_VALUE = 1;
    private static final int EMPTY_VALUE = 0;





    public void readBlockadeFromCSV(String csvFile,ArrayList<ArrayList<Integer>> blockade2DGrid) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                createBlockade(attributes, blockade2DGrid);
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


        printBlockade(blockade2DGrid);
    }

    private void createBlockade(String[] metadata, ArrayList<ArrayList<Integer>> blockade2DGrid) {
        ArrayList<Integer> row = new ArrayList<>();
        for (int i = 0; i < GRID_SIZE; i++) {

            if (metadata[i].equals("42")) {
                row.add(BLOCKADE_VALUE);
            } else if (metadata[i].equals("-1")) {
                row.add(EMPTY_VALUE);
            }
        }
        blockade2DGrid.add(row);
    }

    private void printBlockade(ArrayList<ArrayList<Integer>> blockade2DGrid) {
        for (int i = 0; i < blockade2DGrid.size(); i++) {
            for (int j = 0; j < blockade2DGrid.get(i).size(); j++) {
                System.out.print(blockade2DGrid.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }


}
