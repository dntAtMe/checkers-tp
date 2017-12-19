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
        System.out.println("Poczatek");
        ChineseClient gc = new ChineseClient();
        System.out.println("Po utworzeniu klienta");
        gc.init(args)
        ;
        System.out.println("Po inicie");
        gc.start();
        System.out.println("Po starcie");
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
            System.out.println("jestem");
        }
    }


    public String getGameName() {
        return "Chinese Checkers";
    }

    public GameEvent createGameEvent() {
        return new GameEventDefault();
    }

    public GameEvent createLoginEvent() {
        return new GameEventDefault(GameEventDefault.C_LOGIN);
    }

    public GameEvent createDisconnectEvent(String reason) {
        return null;
    }

}