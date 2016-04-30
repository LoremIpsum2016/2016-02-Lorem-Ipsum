package game.gameinternal.mechanics;

import game.gameinternal.mechanics.Body;

/**
 * Created by Данил on 30.04.2016.
 */
public interface CollisionHandler {
    public void handleCollision(Body first, Body second);
}
