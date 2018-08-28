package com.huaxiaobin.smalldinosaurapp.scene;

import android.content.Context;

import com.huaxiaobin.smalldinosaurapp.main.GameView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 结局类，用于控制结局的动画
 *
 * @author CodeMan工作室
 */

public class Ending {

    public Dinosaur femaleDinosaur;                                                      //定义结局时出现的女恐龙
    public Timer runningTimer;                                                           //定义女恐龙跑动效果的定时器
    public String[] subtitle = {"迅龙酷跑", "Running Dinosaur", "Technical Director ： 华小彬", "Project Manager ： 蔡小翔", "Design Director ： 朱小泽", "CodeMan 工作室"};   //定义并创建字幕数组
    public int[] subtitleX = {GameView.WIDTH / 2 - 100, GameView.WIDTH / 2 - 140, GameView.WIDTH / 2 - 240, GameView.WIDTH / 2 - 230, GameView.WIDTH / 2 - 220, GameView.WIDTH / 2 - 215};                //定义并创建字幕的x坐标数组
    public Float[] subtitleY = {GameView.HEIGHT + 50f, GameView.HEIGHT + 120f, GameView.HEIGHT + 190f, GameView.HEIGHT + 260f, GameView.HEIGHT + 330f, GameView.HEIGHT + 420f};        //定义并创建字幕的y坐标数组
    public int[] subtitleSize = {50, 36, 38, 38, 38, 56};                   //定义并创建字幕的字体大小数组

    /**
     * 构造方法，初始化恐龙
     */
    public Ending(Context context) {
        femaleDinosaur = new Dinosaur(context);    //实例化一只女恐龙
        settingDinosaur();                  //调用恐龙的设置方法
        running();                          //调用恐龙的跑动方法
    }

    /**
     * 初始化时设置恐龙的方法
     */
    private void settingDinosaur() {
        femaleDinosaur.image = GameView.femaleDinosaurRunPic1;                       //设置恐龙的图片
        femaleDinosaur.width = femaleDinosaur.image.getWidth();         //设置恐龙的宽度
        femaleDinosaur.height = femaleDinosaur.image.getHeight();       //设置恐龙的高度
        femaleDinosaur.x = GameView.WIDTH + 100;                                     //设置恐龙的x坐标
        femaleDinosaur.y = 620;                                                      //设置恐龙的y坐标
    }

    /**
     * 跑步方法
     */
    private void running() {
        runningTimer = new Timer();     //创建定时器
        runningTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                /*
                    两张跑动的图片不断切换，相隔100毫秒
                 */
                if (femaleDinosaur.image.equals(GameView.femaleDinosaurRunPic1)) {
                    femaleDinosaur.image = GameView.femaleDinosaurRunPic2;
                } else {
                    femaleDinosaur.image = GameView.femaleDinosaurRunPic1;
                }
            }
        }, 0, 100);
    }
}
