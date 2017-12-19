package main.java.sample;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.logging.Logger;

public class EventWriter extends Wrap {
    private static GameServer gameServer;
    private static Logger log = Logger.getLogger("EventWriter");

    public EventWriter(GameServer gameServer, int numWorkers) {
        this.gameServer = gameServer;
        initWrap(numWorkers);
    }

    public void run() {
        ByteBuffer writeBuffer = ByteBuffer.allocateDirect(Globals.MAX_EVENT_SIZE);

        GameEvent event;
        running = true;
        while (running) {
            try {
                if ((event = eventQueue.deQueue()) != null) {
                    processEvent(event, writeBuffer);
                }
            }
            catch(InterruptedException e) {
            }
        }
    }

    protected void processEvent(GameEvent event) {
    }

    protected void processEvent(GameEvent event, ByteBuffer writeBuffer) {
        NIOUtils.prepBuffer(event, writeBuffer);

        String[] recipients = event.getRecipients();
        if (recipients == null) {
            log.info("writeEvent: type=" + event.getType() + ", id=" +
                    event.getPlayerId() + ", msg=" + event.getMessage());
            String playerId = event.getPlayerId();
            write(playerId, writeBuffer);
        }
        else {
            for (int i = 0; i < recipients.length; i++) {
                if (recipients[i] != null) {
                    log.info("writeEvent(B): type=" + event.getType() + ", id=" +
                            recipients[i] + ", msg=" + event.getMessage());
                    write(recipients[i], writeBuffer);
                }
            }
        }

    }

    private void write( String playerId, ByteBuffer writeBuffer) {
        Player player = gameServer.getPlayerById(playerId);
        SocketChannel channel = player.getChannel();

        if (channel == null || !channel.isConnected()) {
            log.info("writeEvent: client channel null or not connected");
            return;
        }

        NIOUtils.channelWrite(channel, writeBuffer);
    }

}



