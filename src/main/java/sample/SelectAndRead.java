package main.java.sample;

import main.java.sample.GameServer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Logger;

/**
 * obsluguje seektor obslugujacy wiecej kanalow i odczytuje ich zdarzenia
 */
public class SelectAndRead extends Thread {
    private static Logger log = Logger.getLogger("SelectAndRead");
    private LinkedList newClients;
    private Selector selector;
    private GameServer gameServer;

    public SelectAndRead (GameServer gameServer){
        this.gameServer = gameServer;
        newClients = new LinkedList();
    }

    public void addNewClient(SocketChannel clientChannel) {
        synchronized (newClients) {
            newClients.addLast(clientChannel);
        }
        selector.wakeup();
    }

    public void run () {
        try {
            selector = Selector.open();

            while (true) {
                select();
                checkNewConnections();

                try { Thread.sleep(30); } catch (InterruptedException e) {}
            }
        }
        catch (IOException e) {
            log.info("exception while opening Selector");
        }
    }

    private void checkNewConnections() {
        synchronized(newClients) {
            while (newClients.size() > 0) {
                try {
                    SocketChannel clientChannel = (SocketChannel)newClients.removeFirst();
                    clientChannel.configureBlocking( false);
                    clientChannel.register( selector, SelectionKey.OP_READ, new Attachment());
                }
                catch (ClosedChannelException cce) {
                    log.info("channel closed");
                }
                catch (IOException ioe) {
                    log.info("ioexception on clientChannel");
                }
            }
        }
    }

    private void select() {
        try {
            selector.select();
            Set readyKeys = selector.selectedKeys();

            Iterator i = readyKeys.iterator();
            while (i.hasNext()) {
                SelectionKey key = (SelectionKey) i.next();
                i.remove();
                SocketChannel channel = (SocketChannel) key.channel();
                Attachment attachment = (Attachment) key.attachment();

                try {
                    // read from the channel
                    long nbytes = channel.read(attachment.readBuff);
                    // check for end-of-stream condition
                    if (nbytes == -1) {
                        log.info("disconnect: " + channel.socket().getInetAddress() +
                                ", end-of-stream");
                        channel.close();
                    }
                    try {
                        if (attachment.readBuff.position() >= attachment.HEADER_SIZE) {
                            attachment.readBuff.flip();

                            // read as many events as are available in the buffer
                            while(attachment.eventReady()) {
                                GameEvent event = getEvent(attachment);
                                delegateEvent(event, channel);
                                attachment.reset();
                            }
                            // prepare for more channel reading
                            attachment.readBuff.compact();
                        }
                    }
                    catch (IllegalArgumentException e) {
                        log.info("illegal argument exception");
                    }
                }
                catch (IOException ioe) {
                    log.warning("IOException during read(), closing channel:" + channel.socket().getInetAddress());
                    channel.close();
                }
            }
        }
        catch (IOException ioe2) {
            log.warning("IOException during select(): " + ioe2.getMessage());
        }
        catch (Exception e) {
            log.info("exception during select()");
        }
    }

    private GameEvent getEvent(Attachment attachment) {
        GameEvent event = null;
        ByteBuffer bb = ByteBuffer.wrap(attachment.payload);
        GameController gc = gameServer.getGameControllerByHash(attachment.gameNameHash);
        if (gc == null) {
            return null;
        }
        event = gc.createGameEvent();
        event.read(bb);
        return event;
    }

    private void delegateEvent(GameEvent event, SocketChannel channel) {
        if (event != null && event.getGameName() == null) {
            log.info("GameServer.handleEvent() : gameName is null");
            return;
        }

        GameController gc = gameServer.getGameController(event.getGameName());
        if (gc == null) {
            log.info("No GameController for gameName: " + event.getGameName());
            return;
        }

        Player p = gameServer.getPlayerById(event.getPlayerId());
        if (p != null) {
            if (p.getChannel() != channel) {
                log.warning("player is on a new channel, must be reconnect.");
                p.setChannel(channel);
            }
        }
        else {
            p = gc.createPlayer();
            p.setPlayerId(event.getPlayerId());
            p.setChannel(channel);
            gameServer.addPlayer(p);
            log.info("delegate event, new player created and channel set, player:" +
                    p.getPlayerId() + ", channel: " + channel);
        }

        gc.handleEvent(event);
    }

}