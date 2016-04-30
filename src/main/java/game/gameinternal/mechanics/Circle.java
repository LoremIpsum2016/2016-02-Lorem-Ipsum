package game.gameinternal.mechanics;

import game.gameinternal.mechanics.Body;
/**
 * Created by Данил on 30.04.2016.
 */
public class Circle extends Body {
    private double radius;
    @Override
    public double top(){
        return this.yPos - this.radius;//Ось 0Y канваса возрастает сверху вниз
    }

    @Override
    public double bottom() {
        return this.yPos + this.radius;//Ось 0Y канваса возрастает сверху вниз
    }

    @Override
    public double right() {
        return this.xPos + this.radius;
    }

    @Override
    public double left() {
        return this.xPos - this.radius;
    }

}
