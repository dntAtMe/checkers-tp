package main.java.sample;

import main.java.sample.GameClient;

import java.nio.*;
import java.nio.channels.*;
import java.util.*;
import java.net.*;
import java.io.*;
import java.util.logging.Logger;

public class NIOEventReader extends Thread {
    private static final Logger log = Logger.getLogger( NIOEventReader.class.getName() );
    GameClient gameClient;
    private SocketChannel channel;
    private EventQueue queue;
    private Selector selector;
    private  boolean running;
    public NIOEventReader(GameClient gc, SocketChannel channel, EventQueue queue) {
        super("NIOEventReader");
        this.gameClient = gc;
        this.queue = queue;
        this.channel = channel;
    }

    public void run() {
        try {
            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ, new Attachment());
        }
        catch (ClosedChannelException cce) {
            log.info("closedchannelexception while registering channel with selector");
            return;
        }
        catch (IOException ioe) {
            log.info("ioexception while registering channel with selector");
            return;
        }

        running = true;
        while (running) {
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
                        long nbytes = channel.read(attachment.readBuff);
                        if (nbytes == -1) {
                            channel.close();

                            GameEvent event = gameClient.createDisconnectEvent("end-of-stream");
                            queue.enQueue(event);
                        }

                        try {
                            if (attachment.readBuff.position() >= attachment.HEADER_SIZE) {
                                attachment.readBuff.flip();

                                while(attachment.eventReady()) {
                                    GameEvent event = getEvent(attachment);
                                    queue.enQueue(event);
                                    attachment.reset();
                                }
                                attachment.readBuff.compact();
                            }
                        }
                        catch (IllegalArgumentException e) {
                            log.info("illegalargument while parsing incoming event");
                        }
                    }
                    catch (IOException ioe) {
                        log.warning("IOException during read(), closing channel:" + channel.socket().getInetAddress());
                        channel.close();
                    }
                }
            }
            catch (IOException ioe2) {
                log.warning("error during select(): " + ioe2.getMessage());
            }
            catch (Exception e) {
                log.info("exception during select()");
            }
        }
    }

    private GameEvent getEvent(Attachment attachment) {
        GameEvent event = null;
        ByteBuffer bb = ByteBuffer.wrap(attachment.payload);
        event = gameClient.createGameEvent();
        event.read(bb);

        return event;
    }

    public void shutdown() {
        running = false;
        selector.wakeup();
    }
}