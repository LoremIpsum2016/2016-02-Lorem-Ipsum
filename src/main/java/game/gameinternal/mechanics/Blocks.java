package game.gameinternal.mechanics;

import game.gameinternal.mechanics.Rectangle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

/**
 * Created by Данил on 30.04.2016.
 */
public class Blocks extends Rectangle {
    private int[][] hitPointMatrix;
    private int rowsCount;
    private int columnsCount;

    private boolean isCorrectRow(int row){
        return ( row >= 0 && row < rowsCount);
    }
    private boolean isCorrectColumn (int column){
        return ( column >= 0 && column < columnsCount);
    }
    public void destroyBlock(int row,int column){
        if (isCorrectRow(row) && isCorrectColumn(column)){
            hitPointMatrix[row][column] = 0;
        }
        else{
            LOGGER.debug("Invalid row or column");
        }

    }
    public void attackBlock(int row,int column){
        if (isCorrectRow(row) && isCorrectColumn(column)){
            if(hitPointMatrix[row][column]>0) {
                hitPointMatrix[row][column]--;
            }
        }
        else{
            LOGGER.debug("Invalid row or column");
        }
    }
    public void setHitPoints( int[][] matrix){
        hitPointMatrix = matrix.clone();
    }

    public Blocks(double x, double y, double vx, double vy, double w, double h, int r, int c){
        super(x,y,vx,vy,w,h);
        rowsCount = r;
        columnsCount = c;
        hitPointMatrix = new int[rowsCount][columnsCount];
        for (int i=0; i < rowsCount; i++){
            for (int j=0; j < columnsCount; j++){
                hitPointMatrix[i][j] = 1;
            }
        }

    }

    public Blocks (JSONObject blocks){
        super(blocks);
        try {
            rowsCount = blocks.has("rows") ? blocks.getInt("rows") : 0;
            columnsCount = blocks.has("columns") ? blocks.getInt("columns") : 0;

        }
        catch(Exception err){
            LOGGER.debug(err.getMessage());
        }
    }



}
