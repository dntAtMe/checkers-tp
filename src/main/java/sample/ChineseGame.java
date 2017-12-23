package main.java.sample;

public class ChineseGame {

    private Player[] players = new Player[Globals.numberOfPlayers];


    public ChineseGame(Player player1,Player player2,Player player3){

        player1.setInGame(true);
        player2.setInGame(true);
        player3.setInGame(true);
    }

    public Player getOpponent(String pid) {
        return players[1];
    }
}
