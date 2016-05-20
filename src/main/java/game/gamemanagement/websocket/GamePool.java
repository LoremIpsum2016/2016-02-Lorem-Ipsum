package game.gamemanagement.websocket;

import game.GameException;
import main.IGame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.json.JSONArray;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Installed on 17.04.2016.
 */
public class GamePool {

    public static final Logger LOGGER = LogManager.getLogger("GameLogger");
    private final Map<Long, GameWebSocket> connectedUsers = new ConcurrentHashMap<>();
    private final Map<String, GameWebSocket> freeUsers = new ConcurrentHashMap<>();
    private final Map<Long, GameSession> games = new ConcurrentHashMap<>();
    private final IGame dbService;

    public GamePool(IGame dbService) {
        this.dbService = dbService;
    }

    public void connectUser(GameWebSocket gameWebSocket) throws GameException {
        Long userId = null;
        if( gameWebSocket != null
                && (userId = gameWebSocket.getMyId()) != null)
        {
            connectedUsers.put(userId, gameWebSocket);
            freeUsers.put(gameWebSocket.getMyLogin(), gameWebSocket);
            gameWebSocket.setGamePool(this);
        }
        else {
            throw new GameException("Cannot connect user, improper condition.");
        }
    }

    public void disconnectUser(GameWebSocket webSocket) throws GameException {
        if(webSocket != null) {
            Long userId = webSocket.getMyId();
            String login = webSocket.getMyLogin();

            if(connectedUsers.containsKey(userId))
                connectedUsers.remove(userId);

            if(freeUsers.containsKey(login))
                freeUsers.remove(login);

            if(games.containsKey(userId)) {
                games.get(userId).stop();
                games.remove(userId);
            }
        }
        else {
            throw new GameException("Cannot disconnect user, improper condition.");
        }
    }

    public void stopGame(GameWebSocket firstSocket, GameWebSocket secondSocket) throws GameException {
        if(firstSocket != null
                && secondSocket != null) {

            Long userIdFirst = firstSocket.getMyId();
            Long userIdSecond = secondSocket.getMyId();

            Long starterId = null;
            if (games.containsKey(userIdFirst))
                starterId = userIdFirst;
            else if (games.containsKey(userIdSecond))
                starterId = userIdSecond;

            if (starterId != null) {
                GameSession gameSession = games.get(starterId);
                games.remove(starterId);
                freeUsers.put(firstSocket.getMyLogin(), firstSocket);
                freeUsers.put(secondSocket.getMyLogin(), secondSocket);
                gameSession.stop();
            }
        }

    }

    public void startGame(GameWebSocket firstSocket, GameWebSocket secondSocket) throws GameException {
        if(firstSocket != null
                && secondSocket != null) {

            Long userIdStarter = firstSocket.getMyId();
            Long userIdSecond = secondSocket.getMyId();

            //noinspection OverlyComplexBooleanExpression
            if (connectedUsers.containsKey(userIdStarter)
                    && connectedUsers.containsKey(userIdSecond)
                    && freeUsers.containsKey(firstSocket.getMyLogin())
                    && freeUsers.containsKey(secondSocket.getMyLogin())
                    && !userIdStarter.equals(userIdSecond)) {


                GameSession gameSession = new GameSession(
                        this,
                        firstSocket,
                        secondSocket
                );
                gameSession.start();
                freeUsers.remove(firstSocket.getMyLogin());
                freeUsers.remove(secondSocket.getMyLogin());
                games.put(userIdStarter, gameSession);
            } else {
                throw new GameException("Unable to start game, improper condition.");
            }
        }
    }

    public JSONArray getFreeUsersArray() {
        JSONArray res = new JSONArray();
        for( String login : freeUsers.keySet() ) {
            res.put(login);
        }
        return res;
    }

    public GameWebSocket getFreeUserByLogin(String login) {
        if(freeUsers.containsKey(login))
            return freeUsers.get(login);
        else
            return null;
    }

}