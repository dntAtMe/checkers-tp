package main.java.sample;

import java.nio.*;
import java.nio.channels.*;
public class NIOUtils {
    /**
     * zawiera metody do obslugi kanalow i buforow
     */
    public static void prepBuffer(GameEvent event, ByteBuffer writeBuffer) {
        // write header
        writeBuffer.clear();
        writeBuffer.putInt(0);

        if (event.getGameName() != null)
            writeBuffer.putInt(event.getGameName().hashCode());

        else
            writeBuffer.putInt(0);

        int sizePos = writeBuffer.position();
        writeBuffer.putInt(0);
        int payloadSize = event.write(writeBuffer);
        writeBuffer.putInt(sizePos, payloadSize);
        writeBuffer.flip();
    }

    public static void channelWrite(SocketChannel channel, ByteBuffer writeBuffer) {
        long nbytes = 0;
        long toWrite = writeBuffer.remaining();

        try {
            while (nbytes != toWrite) {
                nbytes += channel.write(writeBuffer);

                try {
                    Thread.sleep(Globals.CHANNEL_WRITE_SLEEP);
                }
                catch (InterruptedException e) {}
            }
        }
        catch (ClosedChannelException cce) {
        }
        catch (Exception e) {
        }

        writeBuffer.rewind();
    }

    public static void putStr(ByteBuffer buff, String str) {
        if (str == null) {
            buff.putShort((short)0);
        }
        else {
            buff.putShort((short)str.length());
            buff.put(str.getBytes());
        }
    }
    public static String getStr(ByteBuffer buff) {
        short len = buff.getShort();
        if (len == 0) {
            return null;
        }
        else {
            byte[] b = new byte[len];
            buff.get(b);
            return new String(b);
        }
    }
}
