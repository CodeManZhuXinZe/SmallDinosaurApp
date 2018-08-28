package com.huaxiaobin.smalldinosaurapp.scene;

import com.huaxiaobin.smalldinosaurapp.main.GameView;

import java.util.Random;

/**
 * 树类，继承MovingObject类
 *
 * @author CodeMan工作室
 */

public class Tree extends MovingObject {
    /**
     * 构造方法，初始化树
     */
    public Tree() {
        image = GameView.treePic[getType()];                 //设置树的图片
        width = image.getWidth();               //树的宽度为树图片的宽度
        height = image.getHeight();             //树的高度为树图片的高度
        this.x = GameView.WIDTH + getOffset();               //设置树的初始x坐标，x坐标为游戏窗口的宽度加上一个随机的偏移量
        this.y = 800 - height;                               //设置树的初始y坐标，使不同类型的树的底边都在同一水平线上
        speed = 10;
    }

    /**
     * 树的偏移量的方法
     *
     * @return 返回树的偏移量
     */
    private int getOffset() {
        Random ra = new Random();           //定义并实例化一个随机类
        return ra.nextInt(200);      //返回一个0～199的随机数
    }

    /**
     * 树的类型的方法，共有4种树的类型
     *
     * @return 返回树的类型
     */
    private int getType() {
        Random ra = new Random();           //定义并实例化一个随机类
        return ra.nextInt(4);        //返回一个0～3的随机数
    }
}
