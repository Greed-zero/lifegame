package UI;

import Serve.LifeControl;
import Gather.GameMap;


import javax.annotation.processing.Messager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameFrame extends JFrame {
    private GameMap Map=new GameMap();         // 定义一个游戏地图
    private int interval =1600;                // 刷新的秒数
    private double lifeRatio=0.5;              // 活细胞比例，初始化为0.5
    private int lifeCells=0;                   // 细胞存活数量
    private GameThread gameThreadr= new GameThread(); // 线程类对象
    private int countTime = 0;                 // 线程执行次数
    private LifeControl lifeLogic = new LifeControl();   // 控制类
    private boolean   flag=true;               // 是否第一次执行开始游戏


    // GUI元素
    private JFrame gameFrame;    // 框体
    private JPanel gamePanl;     // 总面板
    private JPanel operatePanel; // 操作面板1
    private JPanel viewPanel;    // 显示面板
    private JButton startButton; // 开始游戏按钮
    private JButton pausetButton; // 重新开始游戏按钮
    private JButton changeButton;  // 修改活细胞比率按钮
    private JButton endButton;    // 结束游戏按钮

    //描述文本
    private JTextField JText1;
    private JTextField JText2;
    private JTextField JText3;
    private JTextField JText4;

    private JTextField lifeRatioText;  // 细胞比率文本 0-100
    private JTextField countText;    // 细胞繁殖次数文本
    private JTextField lifeCellsText; // 活细胞个数面板
    private JComboBox<String> speedComboBox;   // 速度下拉列表


//这是在week1中作出的改变
    // 构造方法
    public GameFrame(){
        gameFrame= new JFrame("生命游戏");
        gamePanl=new JPanel();
        gamePanl.setLayout(null);     // 设置为空布局

        // 设置操作面板1
        operatePanel =new JPanel();
        operatePanel.setBounds(0,0,130,600);
        operatePanel.setLayout(new FlowLayout(FlowLayout.LEFT,2,15));

        // 设置按钮
        startButton=new JButton();
        startButton.setText("开始游戏");
        startButton.addActionListener(new startGameListener());   // 注册开始游戏监听

        changeButton=new JButton();
        changeButton.setText("更换比率");
        changeButton.addActionListener(new changeLifeRatio());

        pausetButton=new JButton();
        pausetButton.setText("暂停游戏");
        pausetButton.setEnabled(false);
        pausetButton.addActionListener(new pauseGameListener());

        endButton=new JButton();
        endButton.setText("结束游戏");
        endButton.setEnabled(false);
        endButton.addActionListener(new endGameListener());



        // 设置生存比率文本
        JText1=new JTextField();
        JText1.setText("生存比率");
        JText1.setEditable(false);

        lifeRatioText =new JTextField(6);
        lifeRatioText.setText(lifeRatio+"");


        // 设置显示繁殖次数
        JText2=new JTextField();
        JText2.setText("繁殖次数");
        JText2.setEditable(false);

        countText=new JTextField(6);
        countText.setText("0");
        countText.setEditable(false);

        // 设置活细胞个数
        JText3=new JTextField();
        JText3.setText("活细胞数");
        JText3.setEditable(false);

        lifeCellsText=new JTextField(6);
        lifeCellsText.setText("0");
        lifeCellsText.setEditable(false);
        // 设置繁殖速度
        JText4=new JTextField();
        JText4.setText("繁殖速度");
        JText4.setEditable(false);

        speedComboBox=new JComboBox<String>();
        speedComboBox.setFont(new Font("宋体",Font.BOLD,9));
        speedComboBox.addItem("慢速");
        speedComboBox.addItem("中等");
        speedComboBox.addItem("快速");
        speedComboBox.addItem("飞快");
        speedComboBox.addActionListener(new  setSpeedLisener());


        // 将组件添加到操作面板
        operatePanel.add(startButton);
        operatePanel.add(pausetButton);
        operatePanel.add(changeButton);
        operatePanel.add(endButton);
        operatePanel.add(JText1);
        operatePanel.add(lifeRatioText);
        operatePanel.add(JText2);
        operatePanel.add(countText);
        operatePanel.add(JText3);
        operatePanel.add(lifeCellsText);
        operatePanel.add(JText4);
        operatePanel.add(speedComboBox);

        // 设置游戏面板
        viewPanel=new JPanel();
        viewPanel.setBounds(132,0,620,620);
        // 将面板添加到总面板
        gamePanl.add(operatePanel);
        gamePanl.add(viewPanel);

        // 将总面板加载到框图
        gameFrame.setBounds(300,80,747,635);
        gameFrame.add(gamePanl);
        gameFrame.setVisible(true);    // 设置窗口可见
        gameFrame.setResizable(false); // 不可改变窗口大小
        paintBlank();    // 绘制空白地图

    }

    // 清除地图
    public void deleteGrid(){
        Graphics g=viewPanel.getGraphics();
        super.paint(g);
        g.setColor(Color.gray);
        int Rows=Map.getRows();
        int Cols=Map.getRows();
        int side=600/Rows;    // 网格中格子的边长
        for(int row=0;row<Cols;++row){
            for(int col =0;col<Rows;++col){
                int x=row*side;
                int y=col*side;
                // 全画成白色
                g.setColor(Color.white);
                g.fillRect(x,y,side,side);
                // 绘制每格子边框
                g.setColor(Color.gray);
                g.drawRect(x,y,side,side);
            }
        }
    }

    // 绘制空白地图
    public void paintBlank(){
        lifeCells=0;
        Graphics blank=viewPanel.getGraphics();
        super.paint(blank);
        blank.setColor(Color.gray);
        int Rows=Map.getRows();
        int Cols=Map.getRows();
        int side=600/Rows;    // 网格中格子的边长
        for(int row=0;row<Rows;++row){
            for(int col =0;col<Cols;++col){
                int x=row*side;
                int y=col*side;
                blank.setColor(Color.white);
                blank.fillRect(x,y,side,side);
                // 绘制每格子边框
                blank.setColor(Color.gray);
                blank.drawRect(x,y,side,side);

            }
        }
        
    }
    // 画格子
    public void paintGrid(){
        lifeCells=0;
        Graphics g=viewPanel.getGraphics();
        super.paint(g);
        g.setColor(Color.gray);
        int Rows=Map.getRows();
        int Cols=Map.getRows();
        int side=600/Rows;    // 网格中格子的边长
        for(int row=0;row<Rows;++row){
            for(int col =0;col<Cols;++col){
                int x=row*side;
                int y=col*side;
                if(Map.get(row,col)){   // 如果该点为活细胞则涂成绿色
                    lifeCells++;
                    g.setColor(Color.green);
                    g.fillRect(x,y,side,side);
                }else{
                    g.setColor(Color.white);
                    g.fillRect(x,y,side,side);
                }
                // 绘制每格子边框
                g.setColor(Color.gray);
                g.drawRect(x,y,side,side);
            }
        }
    }


    // 开始游戏监听类
    private class startGameListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(flag){   // 第一次开始游戏时开启线程
                gameThreadr.start();
                flag=!flag;
            }
            gameThreadr.recoverThread();         // 开始或者恢复游戏线程
            lifeRatioText.setText(lifeRatio+"");
            lifeLogic.init(Map,lifeRatio);       // 初始化地图
            countTime=0;                         // 繁殖次数设置为0
            countText.setText("0");
            pausetButton.setEnabled(true);       // 设置暂按钮可以点击
            startButton.setEnabled(false);       // 开始游戏设置为不可点击
            endButton.setEnabled(true);          // 结束游戏设置为可点击
        }
    }

    // 暂停游戏监听类
    private class pauseGameListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            // 判断现在的游戏状态，从而改变游戏状态
            if(pausetButton.getText().equals("暂停游戏")){
                gameThreadr.pauseThread();
                pausetButton.setText("继续游戏");
            }
            else{
                gameThreadr.recoverThread();
                pausetButton.setText("暂停游戏");
            }
        }
    }

    // 更换生存比例监听
    private class changeLifeRatio implements ActionListener{
        public void actionPerformed(ActionEvent e){
            double tempLifeRatio;
            String str= lifeRatioText.getText();
            tempLifeRatio=Double.parseDouble(str);      // 将获取到的文本转换城双精度类型
            //判断比率是否符合，合理比率位0-0.9或者1
            if ((tempLifeRatio >= 0&&tempLifeRatio <= 0.9) || (tempLifeRatio == 1)) {
                lifeRatio = tempLifeRatio;
                JOptionPane.showMessageDialog(null, "比率修改成功，重新开始游戏时生效");
            }
            else {
                JOptionPane.showMessageDialog(null, "比率应该为0-0.9或者1");
            }
        }
    }

    // 结束游戏按钮监听
    private class endGameListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            gameThreadr.pauseThread();      // 暂停线程
            deleteGrid();                   // 清除边框
            startButton.setEnabled(true);   // 开始游戏可以点击
            endButton.setEnabled(false);    // 结束游戏不可点击
            pausetButton.setEnabled(false); // 暂停游戏不可点击
            pausetButton.setText("暂停游戏");
        }
    }

    // 根据下拉列表的内容设置繁殖速度
    public class setSpeedLisener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if( speedComboBox.getSelectedItem().equals("慢速")){
                interval=1600;
            }
            if( speedComboBox.getSelectedItem().equals("中等")){
                interval=800;
            }
            if( speedComboBox.getSelectedItem().equals("快速")){
                interval=250;
            }
            if( speedComboBox.getSelectedItem().equals("飞快")){
                interval=100;
            }
        }
    }

    // 线程类，执行线程
    public class GameThread extends Thread {
        private boolean pause=false;               // 控制线程的暂停或者开启
        private final Object lock = new Object();  //

        // 线程暂停方法
        public void pauseThread(){
            pause=true;
        }

        // 线程恢复方法
        public void recoverThread(){
            pause=false;
            synchronized (lock){
                lock.notify();
            }
        }
        void onPause() {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        // 重写run方法，进行地图的更新操作
        @Override
        public  void run(){
            super.run();
            while(true) {
                while (pause){   // 如果游戏不是开始状态，则等待
                    onPause();
                }
                // 睡眠interval秒
                // 执行操作
                paintGrid();
                if (lifeCells == 0) {   // 没有活细胞时生命结束
                    JOptionPane.showMessageDialog(null,
                            "生命结束！", "系统信息", JOptionPane.INFORMATION_MESSAGE);
                    endButton.doClick();
                    break;
                }
                lifeLogic.gameCycle(Map);
                countTime++;
                countText.setText(countTime + "");  // 刷新繁殖次数
                lifeCellsText.setText(lifeCells + ""); // 刷新或细胞数
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }
    // 主函数
    public static  void main(String[] args){
        new GameFrame();
    }

}
