package com.huaxiaobin.smalldinosaurapp.scene;

import com.huaxiaobin.smalldinosaurapp.main.GameView;

/**
 * 金币类，继承MovingObject类
 *
 * @author CodeMan工作室
 */

public class Coin extends MovingObject {

    /**
     * 构造方法，初始化金币
     */
    public Coin() {
        image = GameView.coinPic;                       //设置金币的图片
        width = image.getWidth();           //金币的宽度为金币图片的宽度
        height = image.getHeight();         //金币的高度为金币图片的高度
        y = 400;                                        //设置金币的初始y坐标
        speed = 10;                                      //设置金币移动一步的距离
    }
}
