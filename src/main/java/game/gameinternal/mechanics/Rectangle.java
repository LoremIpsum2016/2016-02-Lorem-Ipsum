package game.gameinternal.mechanics;

import game.gameinternal.mechanics.Body;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
/**
 * Created by Данил on 30.04.2016.
 */
public class Rectangle extends Body {
    //Прямоугольник задаётся верхней левой точкой
    private double width;
    private double height;

    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }

    public void setWidth(double width) {
        this.width = width;
    }
    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public double top(){
        return this.yPos;//Ось 0Y канваса возрастает сверху вниз
    }
    @Override
    public double bottom() {
        return this.yPos + this.height;//Ось 0Y канваса возрастает сверху вниз
    }
    @Override
    public double right() {
        return this.xPos + this.width;
    }
    @Override
    public double left() {
        return this.xPos;
    }

    public Rectangle(){
        this.width = 0;
        this.height = 0;
    }
    public Rectangle(double x, double y, double vx, double vy, double w, double h){
        super(x,y,vx,vy);
        width = w;
        height = h;
    }
    public Rectangle(JSONObject rect){
        super(rect);
        try {
            width = rect.has("width")?rect.getDouble("width"):0;
            height = rect.has("height")?rect.getDouble("height"):0;
        }
        catch (Exception err) {
            LOGGER.debug(err.getMessage());
        }
    }
}
