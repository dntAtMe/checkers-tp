package main.java.sample;


import java.util.logging.Logger;

/**
 * Wrap is a thread pool with an incoming BlockingQueue
 * of GameEvents
 *
 */
public abstract class Wrap implements Runnable, EventHandler {
    private static final Logger log = Logger.getLogger( Wrap.class.getName() );
    protected static final long WORKER_SLEEP_MILLIS = 10;
    protected EventQueue eventQueue;
    protected boolean running = false;
    private Thread workers[];
    private int spareCount;
    private Object countLock = new Object();
    private String shortname;

    public final void initWrap(int numWorkers) {
        // setup the log4j Logger
        shortname = this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".") + 1);
        log.info("initWrap - " + shortname);

        eventQueue = new EventQueue(shortname + "-in");
        workers = new Thread[numWorkers];
        for (int i=0; i<numWorkers; i++) {
            workers[i] = new Thread(this, shortname + "-" + (i+1));
            workers[i].setDaemon(true);
            workers[i].start();
        }
    }

    public void shutdown () {
        running = false;
        if (workers != null) {
            for (int i=0;i<workers.length;i++) {
                workers[i].interrupt();
            }
        }
    }

    public void handleEvent(GameEvent event) {
        eventQueue.enQueue(event);
    }

    public void run() {
        GameEvent event;
        running = true;
        while (running) {
            try {
                if ((event = eventQueue.deQueue()) != null) {
                    processEvent(event);
                }
            }
            catch (InterruptedException e) {
            }
        }
    }

    protected abstract void processEvent(GameEvent event);

}
