package main.java.sample;

/**
 *  pozwala na przesylanie zdarzen miedzy etapami zdarzen serwera
 *  wsyztskie klasy ktore  maja przymowac przychodzace zdarzenia
 *  musza implemetowac ten interfejs
 */
public interface EventHandler {

    public void handleEvent(GameEvent event);
}