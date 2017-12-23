package main.java.sample;

import java.nio.channels.*;
import java.util.*;
import java.net.*;
import java.io.*;
import java.util.logging.Logger;

/**
 *  Ta klasa jest odpowiedzialna za polaczenia z klientami
 *  oraz zarzadzanie obiektami GameController
 */
public class GameServer {

    private static final Logger log = Logger.getLogger( GameServer.class.getName() );
    private ServerSocketChannel sSockChan;
    private Selector selector;
    private Hashtable gameControllers;
    private static final String CONTROLLER_CLASS_PREFIX =  "server.controller.";
    private static Hashtable playersByPlayerId;
    private static Hashtable playersBySessionId;
    private boolean running;
    private SelectAndRead selectAndRead;
    private EventWriter eventWriter;

    private static long nextSessionId = 0;

    public static void main(String args[]) {

        GameServer gs = new GameServer();
        System.out.println("Serwer pracuje...");
        gs.run();
    }

    public GameServer() {
        gameControllers = new Hashtable();
        System.out.println("po");
        playersByPlayerId = new Hashtable();
        System.out.println("po");

        playersBySessionId = new Hashtable();
        System.out.println("po");
    }

    public void init() {
        log.info("GameServer initializing");

        loadGameControllers();
        initServerSocket();

        selectAndRead = new SelectAndRead(this);
        selectAndRead.start();
        eventWriter = new EventWriter(this, Globals.EVENT_WRITER_WORKERS);
    }


    private void initServerSocket() {
        try {
            // open a non-blocking server socket channel
            sSockChan = ServerSocketChannel.open();
            sSockChan.configureBlocking(false);

            // bind to localhost on designated port
            InetAddress addr = InetAddress.getLocalHost();
            log.info("binding to address: " + addr.getHostAddress());
            sSockChan.socket().bind(new InetSocketAddress(addr, Globals.PORT));
            System.out.println("cos");
            // get a selector
            selector = Selector.open();
            System.out.println("selector otwarty");
            // register the channel with the selector to handle accepts
            SelectionKey acceptKey = sSockChan.register(selector, SelectionKey.OP_ACCEPT);
        }
        catch (Exception e) {
            log.info("error initializing ServerSocket");
            System.exit(1);
        }
    }


    public void run() {
        init();
        log.info("******** GameServer running ********");
        running = true;
        int numReady = 0;

        while (running) {
            try {
                selector.select();
                Set readyKeys = selector.selectedKeys();

                Iterator i = readyKeys.iterator();
                while (i.hasNext()) {
                    SelectionKey key = (SelectionKey) i.next();
                    i.remove();

                    ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
                    SocketChannel clientChannel = ssChannel.accept();

                    selectAndRead.addNewClient(clientChannel);
                    log.info("got connection from: " + clientChannel.socket().getInetAddress());
                }
            }
            catch (IOException ioe) {
                log.warning("error during serverSocket select(): " + ioe.getMessage());
            }
            catch (Exception e) {
                log.info("exception in run()");
            }
        }
    }


    public void shutdown() {
        selector.wakeup();
    }


    public synchronized String nextSessionId() {
        return "" + nextSessionId++;
    }

    public GameController getGameController(String gameName) {
        return getGameControllerByHash(gameName.hashCode());
    }


    public GameController getGameControllerByHash(int gameNameHash) {
        GameController gc = (GameController) gameControllers.get("" + gameNameHash);
        if (gc == null)
            log.info("no gamecontroller for gameNameHash: " + gameNameHash);
        return gc;
    }


    private void loadGameControllers() {
        log.info("loading GameControllers");

        String baseClass = "C:\\Wzorce projektowe\\javatrainings\\designpatterns\\trunk\\Checkers\\src\\sample\\GameController.class";

        try {
            File f =new File(baseClass);
            //File f = new File(this.getClass().getClassLoader().getResource(baseClass).getPath());
            File[] files = f.getParentFile().listFiles();


            if (files == null) {
                log.info("error getting GameController directory");
                return;
            }

            for (int i = 0; (i < files.length); i++) {
                String file = files[i].getName();
                if (file.indexOf(".class") == -1)
                    continue;
                if (file.equals("GameController.class"))
                    continue;

                try {
                    String controllerClassName = CONTROLLER_CLASS_PREFIX + file.substring(0, file.indexOf(".class"));
                    log.info("loading class: " + controllerClassName);

                    Class cl = Class.forName(controllerClassName);

                    if (!GameController.class.isAssignableFrom(cl)) {
                        log.warning("class file does not extend GameController: " + file);
                        continue;
                    }

                    GameController gc = (GameController) cl.newInstance();
                    String gameName = gc.getGameName();
                    gc.init(this, getGameConfig(gameName));

                    gameControllers.put("" + gameName.hashCode(), gc);

                    log.info("loaded controller for gameName: " + gameName + ", hash: " + gameName.hashCode());
                } catch (Exception e) {
                    log.info("Error instantiating GameController from file: " + file);
                }
            }

        }catch (NullPointerException e){
            log.info("Null pointer expression in creating path");
        }
    }


    public void writeEvent(GameEvent e) {
        eventWriter.handleEvent(e);
    }

    public GameConfig getGameConfig(String gameName) {
        // todo: implement getGameConfig()
        return null;
    }

    public static Player getPlayerById( String id) {
        return (Player) playersByPlayerId.get(id);
    }

    public static Player getPlayerBySessionId(String id) {
        return (Player) playersBySessionId.get(id);
    }

    public static void addPlayer(Player p) {
        playersByPlayerId.put(p.getPlayerId(), p);
        playersBySessionId.put(p.getSessionId(), p);
    }

    public static void removePlayer(Player p) {
        playersByPlayerId.remove(p.getPlayerId());
        playersBySessionId.remove(p.getPlayerId());
    }

}