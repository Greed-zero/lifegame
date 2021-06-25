package Serve;

import Gather.GameMap;

public class LifeControl {

    // 根据比例创建游戏地图
    public void init(GameMap Map,double lifeInitRatio){
        Map.reset(lifeInitRatio);   // 按照比例生成活细胞
    }

    // 进行游戏循环，更新地图
    public  void gameCycle(GameMap Map){
        int MapRows=  Map.getRows();
        int MapCols=  Map.getCols();
        boolean[][] tempMap=new boolean[MapRows][MapCols];   // 临时数组
        int NeighborCount=0;
        // 更新每个格子
        for(int i = 0;i<MapRows;++i){
            for(int j = 0;j<MapCols;++j){
                NeighborCount=Map.getNeighborCount(i,j);  // 获取邻居数
                // 根据相邻活细胞数更新该细胞状态
                if(NeighborCount>=4||NeighborCount<=1){
                    tempMap[i][j]=false;
                }else if(NeighborCount==3){
                    tempMap[i][j]=true;
                }else{
                    tempMap[i][j]=Map.get(i,j);
                }
            }
        }

        //将地图更新
        for(int i = 0;i<MapRows;++i){
            for(int j = 0;j<MapCols;++j) {
                Map.set(i,j,tempMap[i][j]);
            }
        }

    }
}
