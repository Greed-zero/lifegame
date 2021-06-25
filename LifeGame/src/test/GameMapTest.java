package test;

import Gather.GameMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameMapTest {
    private static GameMap gameMap=new GameMap();
    @Before
    public void setUp() throws Exception {
        for(int i =0;i<60;++i) {
            for (int j = 0; j < 60; ++j) {
                gameMap.Map[i][j] = false;
            }
        }
        System.out.println("Set up");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Tear down");
    }

    @Test
    public void initMap() {
        for(int i =0;i<60;++i) {
            for (int j = 0; j < 60; ++j) {
                gameMap.Map[i][j] = true;
            }
        }
        gameMap.initMap();
        for(int i =0;i<60;++i) {
            for (int j = 0; j < 60; ++j) {
                assertEquals(false,gameMap.Map[i][j]);
            }
        }
    }

    @Test
    public void reset() {
        double lifeRatio=0.5;
        int lifeCount=0;
        int expectedLife=1800;
        gameMap.reset(lifeRatio);
        for(int i =0;i<60;++i) {
            for (int j = 0; j < 60; ++j) {
                if( gameMap.Map[i][j]==true)
                    lifeCount++;
            }
        }
        assertEquals(expectedLife,lifeCount);
    }

    @Test
    public void getNeighborCount() {
        for (int i=0;i<3;++i){
            for(int j=0;j<3;++j){
                gameMap.Map[i][j]=true;
            }
        }
        assertEquals(8,gameMap.getNeighborCount(1,1));
        gameMap.Map[0][0]=false;
        assertEquals(7,gameMap.getNeighborCount(1,1));
        gameMap.Map[0][1]=false;
        assertEquals(6,gameMap.getNeighborCount(1,1));
        gameMap.Map[0][2]=false;
        assertEquals(5,gameMap.getNeighborCount(1,1));
        gameMap.Map[1][0]=false;
        assertEquals(4,gameMap.getNeighborCount(1,1));
        gameMap.Map[1][1]=false;
        assertEquals(4,gameMap.getNeighborCount(1,1));
        gameMap.Map[1][2]=false;
        assertEquals(3,gameMap.getNeighborCount(1,1));
        gameMap.Map[2][0]=false;
        assertEquals(2,gameMap.getNeighborCount(1,1));
        gameMap.Map[2][1]=false;
        assertEquals(1,gameMap.getNeighborCount(1,1));
        gameMap.Map[2][2]=false;
        assertEquals(0,gameMap.getNeighborCount(1,1));
    }

    @Test
    public void set_get() {
        assertEquals(false,gameMap.get(0,0));
        gameMap.set(0,0,true);
        assertEquals(true,gameMap.get(0,0));
    }


    @Test
    public void getRows() {
        assertEquals(60,gameMap.getRows());
    }

    @Test
    public void getCols() {
        assertEquals(60,gameMap.getCols());
    }
}
