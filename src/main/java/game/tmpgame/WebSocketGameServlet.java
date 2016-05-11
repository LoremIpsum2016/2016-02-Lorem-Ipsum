package game.tmpgame;

import main.IAccountService;
import main.IGame;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

/**
 * Created by Installed on 18.04.2016.
 */
@WebServlet(name = "WebSocketGameServlet", urlPatterns = {"/gamesocket"})
public class WebSocketGameServlet extends WebSocketServlet {
    private static final int IDLE_TIME = 60 * 100000;
    private final IAccountService accountService;
    private final IGame dbService;

    @SuppressWarnings("SameParameterValue")
    public WebSocketGameServlet(
            IAccountService accountService,
            IGame dbService
    ) {
        this.accountService = accountService;
        this.dbService = dbService;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(IDLE_TIME);
        factory.setCreator(new GameWebSocketCreator(accountService, dbService));
    }
}