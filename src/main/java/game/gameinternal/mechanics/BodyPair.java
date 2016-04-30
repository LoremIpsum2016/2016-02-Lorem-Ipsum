package game.gameinternal.mechanics;

import game.gameinternal.mechanics.Body;


/**
 * Created by Данил on 30.04.2016.
 */
public class BodyPair {
    private Body first;
    private Body second;
    private CollisionHandler handler;

    public BodyPair(Body first, Body second, CollisionHandler handler){
        this.first = first;
        this.second = second;
        this.handler = handler;
    }

    private boolean checkCollisionVertical(){
        boolean result = false;
        if( (first.top() <= second.bottom()) &&
            (first.top() >= second.top()) ) {
             result = true;
        }
         if( (first.bottom() >= second.top()) &&
             (first.top() <= second.top()) ) {
             result = true;
        }
        return  result;
        
        
    }
    private boolean checkCollisionHorizontal(){
        boolean result = false;
        if ( (first.left() <= second.right()) &&
                (first.left() >= second.left()) ){
            result = true;
        }
        if( (first.right() >= second.left()) &&
                (first.right() <= second.right()) ){
            result = true;
        }
        return  result;
    }
    public boolean checkCollision(){
        return this.checkCollisionHorizontal() &&
               this.checkCollisionVertical();
    }

}
