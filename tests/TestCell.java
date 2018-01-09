package common;

import main.java.sample.Player;
import org.junit.Assert;
import org.junit.Test;

public class TestCell {

    @Test
    public void testCell(){
        Assert.assertNotNull(new Cell(2,3, PlayerTag.PLAYER_1));
    }

    @Test
    public void testGetPoint(){
        Assert.assertNotNull(new Cell(2,3,PlayerTag.PLAYER_1).getPoint());
    }

    @Test
    public void testGetOwner(){
        Assert.assertEquals(PlayerTag.PLAYER_4, new Cell(2,3,PlayerTag.PLAYER_4).getOwner());
    }

    @Test
    public void testSetOwner(){
        Cell cell = new Cell(2,3,PlayerTag.PLAYER_2);
        cell.setOwner(PlayerTag.PLAYER_3);
        Assert.assertEquals(PlayerTag.PLAYER_3, cell.getOwner());
    }
}
