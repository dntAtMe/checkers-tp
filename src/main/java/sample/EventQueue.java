package main.java.sample;

import java.util.LinkedList;
import java.util.logging.Logger;


/**
 * Wykorzystuje wzorzec Wrap
 * polega to na opakowniu watkow w kolejke
 * kazdy watek wykonuje okreslone zadanie przez kilka etapow
 * wzorzec powoduje ze jest nadzorca nad rozdcielaniem zadan watka
 * przetwazanie zadan przez zarzadce jest bardziej odporne na przeciazenie
 * bo on odpowiada za to ile watkow pracuje
 */
public class EventQueue {
    private static final Logger log = Logger.getLogger( EventQueue.class.getName() );
    private LinkedList events;
    private int count = 0;

    /**
     * Tworzy liste watkow
     */

    public EventQueue (String name){
        log.info("EventQueue: " + name);
        events = new LinkedList();
    }

    /**
      * dodajemy do kolejki zdarzenie
     */

    public synchronized void enQueue(GameEvent event) {
        //	log.debug("enQueue " + event.hashCode());
        events.addLast(event);
        notifyAll();
    }

    /**
     * blokuje watek do momentu gdy w kolejce nie pojawi sie element
     */
    public synchronized GameEvent deQueue() throws InterruptedException {
        while (events.size() == 0) {
            count++;
            wait();
            count --;
        }
        GameEvent e = (GameEvent) events.removeFirst();

        return e;
    }

    public synchronized int size() {
        return events.size();
    }

}
