package Gather;

import java.util.Random;

public class GameMap {
    private  int Rows = 60;      // 二维游戏世界的大长
    private  int Cols = 60;       // 二维游戏世界的宽
    public   boolean[][]Map= new  boolean[Rows][Cols];   // 游戏地图

    // 构造方法
    public GameMap(){
        initMap();
    }
    /*
    随机生成游戏地图
    */
    public void initMap(){
        int random;     // 随机数
        for(int i =0;i<Rows;++i) {
            for (int j = 0; j < Cols; ++j) {
               Map[i][j]=false;
            }
        }
    }

    /*
    重置地图并根据比例生成填充活细胞
     */
    public void reset(double lifeRatio){
        // 如果是全细胞状态，直接赋值并返回
        if(lifeRatio==1){
            for(int i =0;i<Rows;++i) {
                for (int j = 0; j < Cols; ++j) {
                    Map[i][j]=true;
                    return;
                }
            }
        }
        int lifeNumber=(int)(Rows*Cols*lifeRatio);    // 计算活细胞个数
        Random rand=new Random();
        int randomRow;     // 随机数
        int randomCol;

        // 先将活细胞全部清除
        for(int i =0;i<Rows;++i) {
            for (int j = 0; j < Cols; ++j) {
                Map[i][j]=false;
            }
        }

        // 随机填充活细胞
        while(lifeNumber!=0){
            while(true){
                randomRow=(int)rand.nextInt(59);
                randomCol=(int)rand.nextInt(59);
                if(!Map[randomRow][randomCol]){
                    Map[randomRow][randomCol]=true;
                    break;
                }
            }
            lifeNumber--;
        }
    }

    // 获得某个细胞相邻的活细胞数
    public int  getNeighborCount(int row,int col ){
        int count = 0;
        int[][] move={{-1,-1},{0,-1},{1,-1},{-1,0},{1,0},{-1,1},{0,1},{1,1}};   // 位移数组
        for(int i=0;i<8;i++) {
            if((row+move[i][0]<0)||(row+move[i][0]>=Rows)||(col+move[i][1]<0)||(col+move[i][1]>=Cols)){
                continue;
            }
            if(Map[row+move[i][0]][col+move[i][1]]) {
                count ++;
            }
        }
        return count;
    }


    /*
    设置Map中row行col列和Val的值
     */
    public void set(int row,int col,boolean val){
        if (row>=0&&row<Rows&&col>=0&&col<Cols){
            Map[row][col]=val;
        }else {
            return;
        }
    }

    /*
    获取Map中row行col列和Val的值
     */
    public boolean get(int row,int col){
        return Map[row][col];
    }

    public int getRows(){
        return Rows;
    }

    public int getCols() {
        return Cols;
    }

}
