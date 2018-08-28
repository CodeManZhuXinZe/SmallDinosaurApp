package com.huaxiaobin.smalldinosaurapp.scene;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.huaxiaobin.smalldinosaurapp.R;
import com.huaxiaobin.smalldinosaurapp.main.GameView;

//import tools.Sound;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 恐龙类
 *
 * @author CodeMan工作室
 */

public class Dinosaur {

    public Bitmap image;                                     //定义恐龙图片
    public int width;                                       //定义图片宽度
    public int height;                                      //定义图片高度
    public int x;                                           //定义x坐标
    public int y;                                           //定义y坐标
    public Timer runningTimer;                              //定义跑动的定时器
    private Timer jumpTimer;                                //定义跳跃的定时器
    public Timer quatsTimer;                               //定义下蹲的定时器
    public boolean quatsState = false;                      //定义下蹲的状态
    private boolean isJumpTop;                              //是否跳跃至最高点
    private int jumpHeight;                                 //定义跳跃高度
    private int[] jumpSleepTimeArray = {9, 9, 8, 8};     //定义跳跃时定时器的间隔时间数组
    private int jumpSleepTime;                              //定义跳跃时的间隔时间
    public boolean pause = false;                           //定义是否暂停，默认为false
    private SoundPool soundPool;

    /**
     * 构造方法，初始化小恐龙
     */
    public Dinosaur(Context context) {
        image = GameView.dinosaurRunPic1;           //默认图片设置为跑动的图片
        width = image.getWidth();      //得到图片的宽度
        height = image.getHeight();    //得到图片的高度
        this.x = 200;                                //设置恐龙的默认X坐标
        this.y = 640;                               //设置恐龙的默认Y坐标
        setJumpSleepTime(1);                        //设置跳跃时的定时器时间间隔
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(context, R.raw.jump_sound, 1);
    }

    /**
     * get方法，得到下蹲的状态
     *
     * @return true为下蹲中，false为未下蹲
     */
    public boolean getQuatsState() {
        return quatsState;
    }

    /**
     * 设置跳跃时的定时器时间间隔方法
     *
     * @param grade 当前的游戏难度等级
     */
    public void setJumpSleepTime(int grade) {
        switch (grade) {
            case 1:
                jumpSleepTime = jumpSleepTimeArray[0];
                break;
            case 2:
                jumpSleepTime = jumpSleepTimeArray[1];
                break;
            case 3:
                jumpSleepTime = jumpSleepTimeArray[2];
                break;
            case 4:
                jumpSleepTime = jumpSleepTimeArray[3];
                break;
            case 5:
                jumpSleepTime = jumpSleepTimeArray[4];
                break;
        }
    }

    /**
     * 跑步方法
     */
    public void running() {
        runningTimer = new Timer();     //创建定时器
        if (jumpTimer != null) {
            jumpTimer.cancel();          //关闭跳跃定时器
        }
        runningTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                /*
                    两张跑动的图片不断切换，相隔100毫秒
                 */
                if (image.equals(GameView.dinosaurRunPic1)) {
                    image = GameView.dinosaurRunPic2;
                } else {
                    image = GameView.dinosaurRunPic1;
                }
                width = image.getWidth();       //重新获取图片的宽度
                height = image.getHeight();     //重新获取图片的高度
            }
        }, 0, 100);
    }

    /**
     * 跳跃方法
     */
    public void jump() {
        //在跳跃高度为0时才可执行跳跃
        if (jumpHeight == 0) {
            /*
                如果跳跃之前在跑动时，停止跑动
             */
            if (runningTimer != null) {
                runningTimer.cancel();                    //停止跑动的定时器
                runningTimer = null;
            }
            /*
                按住下蹲的同时跳跃的逻辑处理
             */
            if (quatsTimer != null) {
                quatsState = false;                       //设置下蹲状态为false
                quatsTimer.cancel();                      //停止下蹲定时器
                quatsTimer = null;                        //把下蹲定时器置为空
                this.y -= 35;
            }
            soundPool.play(1, 1, 1, 0, 0, 1);
            image = GameView.dinosaurJumpPic;             //图片变为跳跃的图片
            width = image.getWidth();        //重新获取图片的宽度
            height = image.getHeight();      //重新获取图片的高度
            isJumpTop = false;                            //设置是否跳跃到顶部为false
            jumpTimer = new Timer();                      //创建一个定时器
            jumpTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    /*
                        如果未跳跃到顶部，就继续向上，反之下降
                     */
                    if (!isJumpTop) {
                        int temp = (int) (Math.ceil(((330 - (double) jumpHeight) / 12)));          //定义一个临时变量并赋值，存放接下来上升的距离
                        /*
                            如果游戏为暂停状态，则停止上升
                         */
                        if (pause) {
                            temp = 0;                    //把上升的距离置为0
                        }
                        y -= temp;                       //重新设置恐龙的y坐标
                        jumpHeight += temp;              //设置恐龙的当前高度
                        /*
                            如果跳跃到顶部了，就把是否到顶部至为true
                         */
                        if (jumpHeight >= 330) {
                            isJumpTop = true;            //把是否跳跃到顶部置为true
                        }
                    } else {
                        int temp = (int) (Math.ceil(((330 - (double) jumpHeight) / 12))) + 1;     //定义一个临时变量并赋值，存放接下来下降的距离
                        /*
                            如果游戏为暂停状态，则停止下降
                         */
                        if (pause) {
                            temp = 0;                    //把下降的距离置为0
                        }
                        y += temp;                       //重新设置恐龙的y坐标
                        jumpHeight -= temp;              //设置恐龙的当前高度
                        /*
                            如果下降过程中跳跃的高度变为0了，就意味着跳跃结束
                         */
                        if (jumpHeight <= 0) {
                            jumpHeight = 0;              //把恐龙高度置为0
                            y = 640;                     //重新设置y坐标
                            running();                   //跳用跑动的方法
                        }
                    }
                }
            }, 0, jumpSleepTime);
        }
    }

    /**
     * 下蹲方法
     */
    public void quats() {
        /*
            跳跃高度为0时才可执行下蹲（即恐龙在地面上时才可下蹲）
         */
        if (jumpHeight == 0) {
            /*
                如果下蹲状态为false时，则执行下蹲，反之，执行下蹲到运动这一过程
             */
            if (!quatsState) {
                quatsTimer = new Timer();                               //创建定时器
                quatsTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runningTimer.cancel();                                  //停止跑动方法
                        quatsState = true;                                      //下蹲状态制为true
                        y = 700;                                           //图片位置放到地面上
                        //两张下蹲时的跑动图片不断切换，100毫秒切换一次
                        if (!image.equals(GameView.dinosaurSquatsPic1)) {
                            image = GameView.dinosaurSquatsPic1;
                        } else {
                            image = GameView.dinosaurSquatsPic2;
                        }
                        width = image.getWidth();          //重新获取图片的宽度
                        height = image.getHeight();        //重新获取图片的高度
                    }
                }, 200, 100);
            }
        }
    }

    public void finishSquats() {
        /*
                    下蹲到运动的过程
                 */
        if (image.equals(GameView.dinosaurSquatsPic1) || image.equals(GameView.dinosaurSquatsPic2)) {
            this.y -= 60;
            quatsTimer.cancel();                                //停止下蹲定时器
            quatsState = false;                                 //下蹲状态制为false
            quatsTimer = null;                                  //下蹲定时器制为空
            running();                                          //执行跑动方法
        }
    }

    /**
     * 恐龙死亡时的各种设置
     */
    public void die() {
        if (jumpTimer != null) {
            jumpTimer.cancel();                 //停止跳跃定时器
        }
        if (runningTimer != null) {
            runningTimer.cancel();              //停止跑动定时器
        }
        if (quatsTimer != null) {
            quatsTimer.cancel();                //停止下蹲定时器
            this.y -= 60;                       //重新设置恐龙的y坐标（下蹲时的图片和死亡时的图片大小不同）
            this.x += 30;                       //重新设置恐龙的x坐标（下蹲时的图片和死亡时的图片大小不同）
        }
        image = GameView.dinosaurDiePic;        //把恐龙的图片置为死亡时的图片
    }

    /**
     * 恐龙的走步方法
     *
     * @param temp 恐龙每一步移动的距离
     */
    public void step(int temp) {
        this.x += temp;
    }
}