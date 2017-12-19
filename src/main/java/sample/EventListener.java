package main.java.sample;

import main.java.sample.GameEvent;

//wszystkie klasy klienta i servera ktore maja przyjmowac zdarzenia musza implementowac ten interfejs
public interface EventListener {
    public void handleEvent(GameEvent event);
}
