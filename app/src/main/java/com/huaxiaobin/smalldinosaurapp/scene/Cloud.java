package com.huaxiaobin.smalldinosaurapp.scene;

import com.huaxiaobin.smalldinosaurapp.main.GameView;

import java.util.Random;

/**
 * 云朵类，继承MovingObject类
 *
 * @author CodeMan工作室
 */

public class Cloud extends MovingObject {

    private int[] cloudHeight = {120, 220, 320};           //云y坐标的数组，云只会出现在三种不同的高度上

    /**
     * 构造方法，初始化云朵
     */
    public Cloud() {
        image = GameView.cloudPic;                       //设置云朵的图片
        width = image.getWidth();           //云朵的宽度为云朵图片的宽度
        height = image.getHeight();         //云朵的高度为云朵图片的高度
        this.x = GameView.WIDTH + getOffset();           //设置云朵的初始x坐标，x坐标为游戏窗口的宽度加上一个随机的偏移量
        this.y = cloudHeight[getHeightType()];           //设置云朵的初始y坐标
        speed = 2;                                       //设置云朵移动一步的距离
    }

    /**
     * 云朵的偏移量的方法
     *
     * @return 返回云朵的偏移量
     */
    private int getOffset() {
        Random ra = new Random();           //定义并实例化一个随机类
        return ra.nextInt(400);      //返回一个0～399的随机数
    }

    /**
     * 云朵的初始高度方法
     *
     * @return 云朵的初始高度类型
     */
    private int getHeightType() {
        Random ra = new Random();           //定义并实例化一个随机类
        return ra.nextInt(3);        //返回一个0～2的随机数
    }
}
