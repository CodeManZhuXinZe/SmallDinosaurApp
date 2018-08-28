package com.huaxiaobin.smalldinosaurapp.scene;

import com.huaxiaobin.smalldinosaurapp.main.GameView;

import java.util.Random;

/**
 * 土地类，继承MovingObject类
 *
 * @author CodeMan工作室
 */

public class Land extends MovingObject {

    /**
     * 构造方法，初始化一块土地
     */
    public Land() {
        image = GameView.landPic[getType()];         //设置土地的图片
        width = image.getWidth();       //设置土地的宽度
        height = image.getHeight();     //设置土地的高度
        this.x = GameView.WIDTH-10;                     //设置土地出现时的初始x坐标，x坐标为游戏窗口的右边界
        this.y = 780;                                //设置土地的初始y坐标
        speed = 10;                                   //设置土地移动一步的距离
    }

    /**
     * 土地的类型的方法，一共有3种类型
     *
     * @return 返回土地的类型
     */
    private int getType() {
        Random ra = new Random();           //定义并实例化一个随机类
        return ra.nextInt(3);        //返回一个0～2的随机数
    }
}
