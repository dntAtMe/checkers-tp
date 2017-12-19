package main.java.sample;

import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.io.*;
import java.util.logging.Logger;

public abstract class GameClient extends Thread{
    private static final Logger log = Logger.getLogger( GameClient.class.getName() );

    protected InetAddress serverAddress;
    protected SocketChannel channel;
    protected EventQueue inQueue;
    protected EventQueue outQueue;
    protected NIOEventReader netReader;
    protected ByteBuffer writeBuffer;
    protected String playerId;
    protected String opponentId;
    protected boolean inGame = false;
    protected boolean running = true;

    public void init(String args[]) {
        inQueue = new EventQueue("GameClient-in");
        outQueue = new EventQueue("GameClient-out");
        writeBuffer = ByteBuffer.allocate(Globals.MAX_EVENT_SIZE );

        try {
            serverAddress = InetAddress.getByName(args[0]);
        }
        catch (UnknownHostException uhe) {
            log.info("unknown host: " + args[0]);
            System.exit(1);
        }
        this.playerId = args[1];
        System.out.println("Czy polaczony"+connect());

        if (!connect())
            System.exit(1);

        netReader = new NIOEventReader(this, channel, inQueue);
        netReader.start();

    }

    public void run() {
        login();
        threadSleep(200L);

        while(running) {
            processIncomingEvents();
            writeOutgoingEvents();
            threadSleep(50);
        }
    }
    public abstract String getGameName();
    public abstract GameEvent createGameEvent();
    public abstract GameEvent createLoginEvent();
    public abstract GameEvent createDisconnectEvent(String reason);
    protected abstract void processIncomingEvents();

    private void writeOutgoingEvents() {
        GameEvent outEvent;
        while (outQueue.size() > 0) {
            try {
                outEvent = outQueue.deQueue();
                writeEvent(outEvent);
            }
            catch (InterruptedException ie) {}
        }
    }

    protected boolean connect() {
        log.info(" try to connect()");
        try {
            System.out.println("try to open channel");
            System.out.println("server adres : "+serverAddress+"\n port: "+Globals.PORT+"\n");

            channel = SocketChannel.open(new InetSocketAddress(Globals.PORT));
            System.out.println("opened channel");
            channel.configureBlocking(false);
            channel.socket().setTcpNoDelay(true);
            return true;
        }
        catch (ConnectException ce) {
            log.info("Connect Exception: " + ce.getMessage());
            return false;
        }
        catch (Exception e) {
            log.info("Exception while connecting");
            return false;
        }
    }

    protected void login() {
        GameEvent e = createLoginEvent();
        e.setGameName(getGameName());
        e.setPlayerId(playerId);
        writeEvent(e);
    }

    protected void shutdown() {
        running = false;
        netReader.shutdown();
        //	consoleReader.shutdown();
        try {
            channel.close();
        }
        catch (IOException ioe) {
            log.info("exception while closing channel");
        }
    }

    protected void writeEvent(GameEvent ge) {
        // set the gamename and player id
        ge.setGameName(getGameName());
        ge.setPlayerId(playerId);

        NIOUtils.prepBuffer(ge, writeBuffer);
        NIOUtils.channelWrite(channel, writeBuffer);
    }



    private void threadSleep(long time) {
        try {
            Thread.sleep(time);
        }
        catch(InterruptedException e) {}
    }

    public void stdOut(String str) {
        if ((str != null) && !str.equals(""))
            System.out.println("\n" + str);
        if (inGame)
            System.out.print( playerId + " vs. " + opponentId + " > " );
        else
            System.out.print( playerId + " > " );

    }

    public void stdErr(String str) {
        System.err.println("\n" + str);
    }

    public void setOpponent(String opp) {
        opponentId = opp;
    }
}

