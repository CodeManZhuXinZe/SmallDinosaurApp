package com.huaxiaobin.smalldinosaurapp.scene;

import android.graphics.Bitmap;

/**
 * 运动对象类(树、云、鸟、土地的父类)
 *
 * @author CodeMan工作室
 */

public class MovingObject {

    public Bitmap image;                 //图片
    public int x;                       //图片的x坐标
    public int y;                       //图片的y坐标
    public int width;                   //图片的宽度
    public int height;                  //图片的高度
    public int speed;                   //物体运动的速度，这里的值为物体运动一步的距离
    public boolean gameOver = false;    //对象是否死亡，这里只针对鸟

    /**
     * 走步方法
     */
    public void step() {
        this.x -= speed;                    //物体下一步的x坐标为当前x坐标减去一步的距离
    }

    /**
     * 判断是否出界的方法，当物体完全消失在游戏窗口中，即为出界
     */
    public boolean outOfBounds() {
        return this.x + this.width <= 0;    //物体只会往左移动，物体的x坐标加上物体的宽度即为物体的右边界坐标，右边界坐标小于等于0，就为出界
    }
}
