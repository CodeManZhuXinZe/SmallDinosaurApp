package com.huaxiaobin.smalldinosaurapp.scene;

import com.huaxiaobin.smalldinosaurapp.main.GameView;

import java.util.Random;

/**
 * 鸟类，继承MovingObject类，实现Runnable接口
 *
 * @author CodeMan工作室
 */

public class Bird extends MovingObject implements Runnable {

    private int birdHeight[] = {520, 580, 660};          //鸟的高度数组，鸟只会出现的三种不同的高度上
    private Thread thread;                               //鸟运动的线程

    /**
     * 构造方法，初始化鸟
     */
    public Bird() {
        image = GameView.birdPic1;                       //设置鸟的图片
        width = image.getWidth();           //鸟的宽度为鸟图片的宽度
        height = image.getHeight();         //鸟的高度为鸟图片的高度
        this.x = GameView.WIDTH + getOffset();           //设置鸟的初始x坐标，x坐标为游戏窗口的宽度加上一个随机的偏移量
        this.y = birdHeight[getHeightType()];            //设置鸟的初始y坐标
        speed = 10;                                       //设置鸟移动一步的距离
        thread = new Thread(this);                //创建一个线程
        thread.start();                                  //开始线程
    }

    /**
     * 鸟的高度的方法
     *
     * @return 返回鸟的高度类型
     */
    private int getHeightType() {
        Random ra = new Random();           //定义并实例化一个随机类
        return ra.nextInt(3);        //返回一个0～2的随机数
    }

    /**
     * 鸟的偏移量的方法
     *
     * @return 返回鸟的偏移量
     */
    private int getOffset() {
        Random ra = new Random();           //定义并实例化一个随机类
        return ra.nextInt(200);      //返回一个0～199的随机数
    }

    /**
     * 重写线程的run方法，每200毫秒执行一次，不断切换鸟的图片，实现鸟飞行的效果
     */
    @Override
    public void run() {
        int temp = this.y;                                  //定义一个临时变量，存储鸟的y坐标
        while (true) {
            try {
                Thread.sleep(200);                   //线程睡眠
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /*
                如果游戏结束，则使鸟停止飞行
             */
            if (this.gameOver) {
                break;
            }
            /*
                切换鸟的图片，因为鸟的图片大小不同，所以需不断改变y坐标
             */
            if (this.image.equals(GameView.birdPic1)) {
                image = GameView.birdPic2;               //设置鸟的图片
                this.y = temp + 10;                      //设置y坐标
            } else {
                image = GameView.birdPic1;               //设置鸟的图片
                this.y = temp;                           //设置y坐标
            }
            width = image.getWidth();       //重新获取鸟的宽度
            height = image.getHeight();     //重新获取鸟的高度
        }
    }
}
