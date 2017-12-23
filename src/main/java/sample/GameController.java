package main.java.sample;



import java.util.*;

/**
 * tu cala gra sie odbywa
 */
public abstract class GameController extends Wrap {

    protected
    GameServer gameServer;

    public final void init(GameServer s, GameConfig gc) {
        this.gameServer = s;
        int nw = gc.getInt("NUM_WORKERS", 5);
        initWrap(Globals.DEFAULT_CONTROLLER_WORKERS);
        initController(gc);
    }

    protected void sendEvent(GameEvent e, Player p) {
       // e.setPlayerId(p.getPlayerId());
        gameServer.writeEvent(e);
    }
    protected synchronized void sendBroadcastEvent(GameEvent e, Collection players) {
        Iterator i = players.iterator();
        String[] recipients = new String[players.size()];
        int j=0;
        while(i.hasNext()) {
            Player p = (Player) i.next();
            if (!(p.getPlayerId().equals(e.getPlayerId())))
                recipients[j++] = p.getPlayerId();
        }
        e.setRecipients(recipients);
        gameServer.writeEvent(e);
    }

    protected abstract void initController(GameConfig gc);
    public abstract String getGameName();
    public abstract Player createPlayer();
    public abstract GameEvent createGameEvent();

}

