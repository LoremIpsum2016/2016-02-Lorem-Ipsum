package game.gameinternal.mechanics;

import game.gameinternal.mechanics.CollisionHandler;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.Arrays;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
/**
 * Created by Данил on 30.04.2016.
 */
//abstract class BodyPair extends  Pair<Body,Body>{}
public class CollisionChecker {
//    public static void checkCollisions(Pair<Body,Body>[] pairs , Map<BodyPair,CollisionHandler> handlers){
//        boolean is_colision = false;
//        Arrays.sort(bodies);
//        for (int i = 0; i < bodies.length; i++){
//            for (int j = i + 1; j < bodies.length && (bodies[i].right() > bodies[j].left()) ; j++) {
//                is_colision =false;
//                if( (bodies[i].top() <= bodies[j].bottom()) &&
//                    (bodies[i].top() >= bodies[j].top()) )
//                {
//                    is_colision = true;
//                }
//                else if( (bodies[i].bottom() >= bodies[j].top()) &&
//                         (bodies[i].top() <= bodies[j].top()) ) {
//                    is_colision = true;
//                }
//                if (is_colision && handlers.containsKey(new ImmutablePair<Body,Body>(bodies[i],bodies[j]))){
//
//                }
//
//            }
//
//        }
//    }
//    private static boolean containHandler (int i,int j ,Body[] bodies , Map<BodyPair,CollisionHandler> handlers){
//
//    }
}
