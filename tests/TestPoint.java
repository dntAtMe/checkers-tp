package common;

import org.junit.Assert;
import org.junit.Test;

public class TestPoint {

    @Test
    public void testPoint(){
        Assert.assertNotNull(new Point(2,3));
    }

    @Test
    public void testGetQ(){
        Assert.assertEquals(2,new Point(2,3).getQ());
    }

    @Test
    public void testGetR(){
        Assert.assertEquals(3,new Point(2,3).getR());
    }

    @Test
    public void testGetS(){
        Assert.assertEquals(-5,new Point(2,3).getS());
    }

    @Test
    public void testSubstract(){
        Point point = Point.substract(new Point(2,3), new Point(2,3));
        Assert.assertEquals(0, point.getQ() );
    }

    @Test
    public void testMultiply(){
        Point point = Point.multiply(new Point(2,3) ,5);
        Assert.assertEquals(10, point.getQ() );
    }

    @Test
    public void testAdd(){
        Point point = Point.add(new Point(2,3), new Point(5,9));
        Assert.assertEquals(7, point.getQ() );
    }

    @Test
    public void testDistaneceBetween(){
        Point point = Point.multiply(new Point(2,3) ,5);
        //distance = (|2-5| + |3-7| + |-2-3-(-5-7)|) / 2 = (3+4+7)/2 = 14/2 = 7
        Assert.assertEquals(7, (int)Point.distanceBetween(new Point(2,3),new Point(5,7) ));
    }

    @Test
    public void testEquals(){
        Assert.assertTrue(new Point(2,3).equals(new Point(2,3)));
    }
}
