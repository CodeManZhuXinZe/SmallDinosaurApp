package com.huaxiaobin.smalldinosaurapp.scene;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 得分类
 *
 * @author CodeMan工作室
 */

public class Score {

    private int score;                          //得分，实际得分为当前值除10
    private int hScore;                         //最高得分，实际得分为当前值除10
    public boolean scoreFlashing = false;       //得分是否在闪烁状态
    private int scoreFlashCount;                //得分闪烁次数
    public String scoreFlag;                    //得分闪烁时，闪烁的数值（空或者闪烁开始时的得分）
    private int temp;
    private Timer timer;

    /**
     * 得到历史最高分的方法
     *
     * @return 历史最高分
     */
    public int gethScore() {
        return hScore;
    }

    /**
     * 写入历史最高分的方法
     */
    public void sethScore(int hScore) {
        this.hScore = hScore;
    }

    /**
     * 得到得分的方法
     *
     * @return 当前等分
     */
    public int getScore() {
        return score;
    }

    /**
     * 写入当前分的方法
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * 等分闪烁的方法，当当前得分为100的倍数时，等分会闪烁6次
     */
    public void scoreFlash() {
        scoreFlashing = true;                               //把闪烁状态置为true
        scoreFlashCount = 0;                                //设置默认闪烁次数为0
        temp = score / 10;                              //定义一个临时变量存储当前等分
        timer = new Timer();                          //定义并实例化一个计时器
        /*
            闪烁的具体方法，每500毫秒闪烁一次
         */
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                scoreFlashCount++;                          //闪烁次数加1
                /*
                    当闪烁次数达到6次时，结束闪烁
                 */
                if (scoreFlashCount == 6) {
                    scoreFlashing = false;                  //闪烁状态置为false
                    timer.cancel();                         //取消定时器
                }
                /*
                    闪烁次数为2的倍数时，把得分置为空，反之置为闪烁开始时的等分
                 */
                if (scoreFlashCount % 2 == 0) {
                    scoreFlag = "";                         //把得分闪烁时的数值置为空，实现一种闪烁的效果
                } else {
                    scoreFlag = String.valueOf(temp);       //把等分闪烁时的数值置为闪烁开始时的等分
                }
            }
        }, 0, 500);
    }
}

