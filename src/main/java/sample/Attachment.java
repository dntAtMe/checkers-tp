package main.java.sample;
import java.nio.ByteBuffer;
import java.nio.BufferUnderflowException;
import java.util.logging.Logger;

/**
 * Klasa ta przsechowuje bufor odczytu oraz
 * obsluguje kontrole kompletniosci komunikatu
 */

public class Attachment {
    private static final Logger log = Logger.getLogger( Attachment.class.getName() );
    public static final int HEADER_SIZE = 12;
    int clientId;
    int payloadSize;
    public int gameNameHash;
    private boolean gotHeader;
    public ByteBuffer readBuff;
    public byte payload[];

    public Attachment (){
        payload = new byte[Globals.MAX_EVENT_SIZE];
        readBuff = ByteBuffer.allocateDirect(Globals.NET_BUFFER_SIZE);
    }

    public boolean eventReady() throws IllegalArgumentException {
        if (checkHeader() && checkPayload())
            return true;
        else
            return false;
    }


    public void reset() {
        gotHeader = false;
    }

    private boolean checkHeader() throws IllegalArgumentException {
        if (gotHeader)
            return true;
        if (readBuff.remaining() >= HEADER_SIZE) {
            clientId = readBuff.getInt();
            gameNameHash = readBuff.getInt();
            payloadSize = readBuff.getInt();

            if (payloadSize > Globals.MAX_EVENT_SIZE)
                throw new IllegalArgumentException("Header specifies payload size (" +
                        payloadSize + ") greater than MAX_EVENT_SIZE(" +
                        Globals.MAX_EVENT_SIZE + ")");
            gotHeader = true;
            return true;
        }
        else {
            return false;
        }
    }


    private boolean checkPayload() {
        if (readBuff.remaining() >= payloadSize) {
            try {
                readBuff.get(payload, 0, payloadSize);
            }
            catch (BufferUnderflowException bue) {
                log.info("buffer underflow");
            }
            return true;
        }
        else {
            return false;
        }
    }
}

