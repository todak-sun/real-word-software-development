package io.todak.realworldsoftware.chapter06.web_adapter;

import io.todak.realworldsoftware.chapter06.RegistrationStatus;
import io.todak.realworldsoftware.chapter06.TwootRepository;
import io.todak.realworldsoftware.chapter06.Twootr;
import io.todak.realworldsoftware.chapter06.database.DatabaseTwootRepository;
import io.todak.realworldsoftware.chapter06.database.DatabaseUserRepository;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class TwootServer extends WebSocketServer {
    public static final int STATIC_PORT = 8000;
    public static final int WEBSOCKET_PORT = 9000;

    private static final String USER_NAME = "Joe";
    private static final String PASSWORD = "ahc5ez2aiV";
    private static final String OTHER_USER_NAME = "John";

    private final TwootRepository twootRepository = new DatabaseTwootRepository();
    private final Twootr twootr = new Twootr(new DatabaseUserRepository(), twootRepository);
    private final Map<WebSocket, WebSocketEndPoint> socketToEndPoint = new HashMap<>();

    public TwootServer(InetSocketAddress address) {
        super(address, 1);
        twootr.onRegisterUser(USER_NAME, PASSWORD);
        twootr.onRegisterUser(OTHER_USER_NAME, PASSWORD);
    }

    @Override
    public void onOpen(final WebSocket webSocket, final ClientHandshake clientHandshake) {
        socketToEndPoint.put(webSocket, new WebSocketEndPoint(twootr, webSocket));
    }

    @Override
    public void onClose(final WebSocket webSocket, final int i, final String s, final boolean b) {
        socketToEndPoint.remove(webSocket);
    }

    @Override
    public void onMessage(final WebSocket webSocket, final String message) {
        var endPoint = socketToEndPoint.get(webSocket);
        try {
            endPoint.onMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        e.printStackTrace();
    }

    public static void main(String[] args) throws Exception {
        var websocketAddress = new InetSocketAddress("localhost", WEBSOCKET_PORT);
        var twootServer = new TwootServer(websocketAddress);
        twootServer.start();

        System.setProperty("org.eclipse.jetty.LEVEL", "INFO");

        var context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setResourceBase(System.getProperty("user.dir") + "/src/main/webapp");
        context.setContextPath("/");

        ServletHolder staticContentServlet = new ServletHolder(
                "staticContentServlet",
                DefaultServlet.class
        );
        staticContentServlet.setInitParameter("dirAllowed", "true");
        context.addServlet(staticContentServlet, "/");

        var jettyServer = new Server(STATIC_PORT);
        jettyServer.setHandler(context);
        jettyServer.start();
        jettyServer.dumpStdErr();
        ;
        jettyServer.join();
        //TODO: 200페이지부터 다시 읽기
    }

}
