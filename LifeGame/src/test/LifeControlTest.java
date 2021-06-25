package test;

import Gather.GameMap;
import Serve.LifeControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class LifeControlTest {
    private static GameMap gameMap=new GameMap();    // 测试用到地图类的对象
    private static LifeControl lifeControl=new LifeControl();
    @Before
    public void setUp() throws Exception {
        // 测试之前将地图清空
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

    // 测试LifeControl中的gameCycle()方法
    @Test
    public  void gameCycle(){
        int lifeNum=0;    //保存更新后游戏地图的细胞数
        // 在地图中设置几个活细胞点
        gameMap.Map[1][1]=true;
        gameMap.Map[1][3]=true;
        gameMap.Map[2][1]=true;
        gameMap.Map[2][2]=true;
        gameMap.Map[2][3]=true;
        gameMap.Map[3][1]=true;
        gameMap.Map[3][3]=true;
        lifeControl.gameCycle(gameMap);   // 根据规则更新地图

        for(int i=0;i<60;++i){
            for(int j = 0;j<60;++j){
                if(gameMap.Map[i][j]){
                    lifeNum++;
                }
            }
        }

        assertEquals(8,lifeNum);   // 更新后因该仅有7个活点

        // 下面验证应该是活细胞的点
        assertEquals(true,gameMap.Map[1][1]);
        assertEquals(true,gameMap.Map[1][3]);
        assertEquals(true,gameMap.Map[2][0]);
        assertEquals(true,gameMap.Map[2][1]);
        assertEquals(true,gameMap.Map[2][3]);
        assertEquals(true,gameMap.Map[2][4]);
        assertEquals(true,gameMap.Map[3][1]);
        assertEquals(true,gameMap.Map[3][3]);

    }

}