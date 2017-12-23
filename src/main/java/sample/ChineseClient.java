package main.java.sample;
import java.util.logging.Logger;

public class ChineseClient extends GameClient {
    private static final Logger log = Logger.getLogger( ChineseClient.class.getName() );
    private ChineseClient consoleReader;

    public static void main(String args[] ) {
        // setup log4j
        if (args.length < 2) {
            System.out.println(" <host> <player_name> \n");
            System.exit(0);
        }
        ChineseClient gc = new ChineseClient();
        gc.init(args);
        gc.start();
        log.info("Polaczono z serwerem");

    }

    protected void shutdown() {
        consoleReader.shutdown();
        super.shutdown();
    }

    public void init(String args[]) {
        super.init(args);
    }


    protected void processIncomingEvents() {
        GameEvent inEvent;
        while (inQueue.size() > 0) {
        }
    }


    public String getGameName() {
        return "Chinese Checkers";
    }

    public GameEvent createGameEvent() {
        return new GameEventDefault();
    }



    public GameEvent createDisconnectEvent(String reason) {
        return null;
    }

}