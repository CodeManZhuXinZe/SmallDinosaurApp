package com.huaxiaobin.smalldinosaurapp.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.huaxiaobin.smalldinosaurapp.R;
import com.huaxiaobin.smalldinosaurapp.scene.Bird;
import com.huaxiaobin.smalldinosaurapp.scene.Cloud;
import com.huaxiaobin.smalldinosaurapp.scene.Coin;
import com.huaxiaobin.smalldinosaurapp.scene.Dinosaur;
import com.huaxiaobin.smalldinosaurapp.scene.Ending;
import com.huaxiaobin.smalldinosaurapp.scene.Land;
import com.huaxiaobin.smalldinosaurapp.scene.MovingObject;
import com.huaxiaobin.smalldinosaurapp.scene.Score;
import com.huaxiaobin.smalldinosaurapp.scene.Tree;

import java.util.Arrays;
import java.util.Random;

public class GameView extends View {

    private Paint paint;
    public static int WIDTH;                                   //游戏窗口宽度
    public static int HEIGHT;                                   //游戏窗口高度
    private Score score = new Score();                                      //定义并实例化一个得分类
    private Ending ending;                                                  //定义一个结局类
    private int[] sleepTimeArray = {10, 9, 8, 7};                        //睡眠时间数组
    private int sleepTime;                                                  //睡眠时间，控制游戏总线程的睡眠时间，即控制游戏难度，时间越短，游戏速度越快
    private static int coinNum = 0;
    private static boolean AI = false;

    public static final int START = 0;                                      //开始状态
    public static final int RUNNING = 1;                                    //运行状态
    public static final int PAUSE = 2;                                      //暂停状态
    public static final int GAME_OVER = 3;                                  //死亡状态
    public static final int ENDING = 4;                                     //结局状态
    private int state = START;                                              //游戏运行的状态
    private int gameRunningTime = 0;                                        //游戏运行的时间
    private int tempTime = 0;                                               //一个存储时间的变量，用于判断时间的间隔

    public static Bitmap start_codeman;
    public static Bitmap start_title;
    public static Bitmap start_info;
    public static Bitmap dinosaurRunPic1;                                   //恐龙跑动图片1
    public static Bitmap dinosaurRunPic2;                                   //恐龙跑动图片2
    public static Bitmap dinosaurSquatsPic1;                          //恐龙下蹲图片1
    public static Bitmap dinosaurSquatsPic2;                          //恐龙下蹲图片2
    public static Bitmap dinosaurJumpPic;                            //恐龙跳跃图片
    public static Bitmap dinosaurDiePic;                             //恐龙死亡图片
    public static Bitmap[] landPic = new Bitmap[3];           //土地图片数组
    public static Bitmap[] treePic = new Bitmap[4];           //树图片数组
    public static Bitmap cloudPic;                                   //云朵图片
    public static Bitmap birdPic1;                                   //鸟图片1
    public static Bitmap birdPic2;                                   //鸟图片2
    public static Bitmap coinPic;                                    //金币图片
    public static Bitmap femaleDinosaurRunPic1;                      //结局女恐龙跑动图片1
    public static Bitmap femaleDinosaurRunPic2;                      //结局女恐龙跑动图片2
    public static Bitmap femaleDinosaurJumpPic;                      //结局女恐龙跳跃图片
    public static Bitmap gameOver;                      //游戏结束图片
    public static Bitmap pause;                                      //游戏暂停图片
    public static Bitmap start_button;
    public static Bitmap pause_button;
    public static Bitmap ai_button1;
    public static Bitmap ai_button2;

    private Dinosaur dinosaur;                             //定义并创建一只恐龙
    private Land[] land = {};                                               //土地数组
    private Cloud[] cloud = {};                                             //云朵数组
    private MovingObject[] obstacles = {};                                  //障碍物数组（树、鸟）
    private Coin[] coin = {};

    private SoundPool soundPool;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadImage();
        loadSound();
        dinosaur = new Dinosaur(getContext());
        paint = new Paint();
        dinosaur.running();
    }

    private void loadImage() {
        start_codeman = BitmapFactory.decodeResource(getResources(), R.mipmap.codeman_text);
        start_title = BitmapFactory.decodeResource(getResources(), R.mipmap.title);
        start_info = BitmapFactory.decodeResource(getResources(), R.mipmap.start_game_text);
        dinosaurRunPic1 = BitmapFactory.decodeResource(getResources(), R.mipmap.dinosaur_run_pic1);
        dinosaurRunPic2 = BitmapFactory.decodeResource(getResources(), R.mipmap.dinosaur_run_pic2);
        dinosaurSquatsPic1 = BitmapFactory.decodeResource(getResources(), R.mipmap.dinosaur_squats_pic1);
        dinosaurSquatsPic2 = BitmapFactory.decodeResource(getResources(), R.mipmap.dinosaur_squats_pic2);
        dinosaurJumpPic = BitmapFactory.decodeResource(getResources(), R.mipmap.dinosaur_jump_pic);
        dinosaurDiePic = BitmapFactory.decodeResource(getResources(), R.mipmap.dinosaur_die_pic);
        landPic[0] = BitmapFactory.decodeResource(getResources(), R.mipmap.land_pic1);
        landPic[1] = BitmapFactory.decodeResource(getResources(), R.mipmap.land_pic2);
        landPic[2] = BitmapFactory.decodeResource(getResources(), R.mipmap.land_pic3);
        cloudPic = BitmapFactory.decodeResource(getResources(), R.mipmap.cloud_pic);
        treePic[0] = BitmapFactory.decodeResource(getResources(), R.mipmap.tree_pic1);
        treePic[1] = BitmapFactory.decodeResource(getResources(), R.mipmap.tree_pic2);
        treePic[2] = BitmapFactory.decodeResource(getResources(), R.mipmap.tree_pic3);
        treePic[3] = BitmapFactory.decodeResource(getResources(), R.mipmap.tree_pic4);
        birdPic1 = BitmapFactory.decodeResource(getResources(), R.mipmap.bird_pic1);
        birdPic2 = BitmapFactory.decodeResource(getResources(), R.mipmap.bird_pic2);
        coinPic = BitmapFactory.decodeResource(getResources(), R.mipmap.coin_pic);
        femaleDinosaurRunPic1 = BitmapFactory.decodeResource(getResources(), R.mipmap.female_dinosaur_run_pic1);
        femaleDinosaurRunPic2 = BitmapFactory.decodeResource(getResources(), R.mipmap.female_dinosaur_run_pic2);
        femaleDinosaurJumpPic = BitmapFactory.decodeResource(getResources(), R.mipmap.female_dinosaur_jump_pic);
        gameOver = BitmapFactory.decodeResource(getResources(), R.mipmap.game_over_pic);
        pause = BitmapFactory.decodeResource(getResources(), R.mipmap.pause_pic);
        start_button = BitmapFactory.decodeResource(getResources(), R.mipmap.start_button_pic);
        pause_button = BitmapFactory.decodeResource(getResources(), R.mipmap.pause_button_pic);
        ai_button1 = BitmapFactory.decodeResource(getResources(), R.mipmap.ai_switch1);
        ai_button2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ai_switch2);
    }

    private void loadSound() {
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(getContext(), R.raw.background_sound, 1);
        soundPool.load(getContext(), R.raw.jump_sound, 1);
        soundPool.load(getContext(), R.raw.eat_coin_sound, 1);
        soundPool.load(getContext(), R.raw.die_sound, 1);
        soundPool.load(getContext(), R.raw.ending_sound, 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        gameRunningTime += sleepTime;               //设置当前的游戏运行时间
        try {
            Thread.sleep(sleepTime);                //线程睡眠
        } catch (Exception e) {
            e.printStackTrace();
        }
        WIDTH = canvas.getWidth();
        HEIGHT = canvas.getHeight();
        switch (state) {
            case START:
                canvas.drawBitmap(dinosaur.image, dinosaur.x, dinosaur.y, paint);
                canvas.drawBitmap(start_codeman, 10, 10, paint);
                canvas.drawBitmap(start_title, canvas.getWidth() / 2 - start_title.getWidth() / 2, canvas.getHeight() / 2 - start_title.getHeight(), paint);
                canvas.drawBitmap(start_info, canvas.getWidth() / 2 - start_info.getWidth() / 2, canvas.getHeight() - start_info.getHeight() - 50, paint);
                break;
            case RUNNING:
                enterLandAction();                      //进入土地的行为
                enterCloudAction();                     //进入云朵的行为
                enterObstaclesAction();                 //进入障碍物的行为
                stepAction();                           //走步的方法
                superAiAction();
                outOfBoundsAction();                    //越界的方法
                gradeAction();                          //当前游戏难度等级的行为
                endingAction();                         //结局模式的行为
                checkGameOverAction();
                eatCoinAction();
                paint(canvas);
                paint_btn(canvas);
                score.setScore(score.getScore() + 1);   //设置当前得分
                break;
            case ENDING:
                paint(canvas);
                paintEnding(canvas);
                endingStepAction();                     //结束状态下走步的行为
                endingSubtitleAction();                 //结束状态下字幕的行为
                break;
            case PAUSE:
                paint(canvas);
                paint_btn(canvas);
                canvas.drawBitmap(pause, canvas.getWidth() / 2 - pause.getWidth() / 2, canvas.getHeight() / 2 - pause.getHeight() / 2, paint);
                break;
            case GAME_OVER:
                paint(canvas);
                canvas.drawBitmap(gameOver, canvas.getWidth() / 2 - gameOver.getWidth() / 2, canvas.getHeight() / 2 - gameOver.getHeight() / 2, paint);
                break;
        }
        postInvalidate();
    }

    private void paint(Canvas canvas) {
        for (int i = 0; i < land.length; i++) {
            canvas.drawBitmap(land[i].image, land[i].x, land[i].y, paint);
        }
        for (int i = 0; i < cloud.length; i++) {
            canvas.drawBitmap(cloud[i].image, cloud[i].x, cloud[i].y, paint);
        }
        for (int i = 0; i < coin.length; i++) {
            canvas.drawBitmap(coin[i].image, coin[i].x, coin[i].y, paint);
        }
        for (int i = 0; i < obstacles.length; i++) {
            canvas.drawBitmap(obstacles[i].image, obstacles[i].x, obstacles[i].y, paint);
        }
        canvas.drawBitmap(dinosaur.image, dinosaur.x, dinosaur.y, paint);
        paintScore(canvas, score);
        paintCoinNum(canvas, coinNum);
    }

    private void paint_btn(Canvas canvas) {
        if (state == PAUSE) {
            canvas.drawBitmap(start_button, 20, 20, paint);
        } else {
            canvas.drawBitmap(pause_button, 20, 20, paint);
        }
        if (AI) {
            canvas.drawBitmap(ai_button1, 180, 20, paint);
        } else {
            canvas.drawBitmap(ai_button2, 180, 20, paint);
        }
    }

    private void paintEnding(Canvas canvas) {
        canvas.drawBitmap(ending.femaleDinosaur.image, ending.femaleDinosaur.x, ending.femaleDinosaur.y, paint);
        /*
            对字幕数组进行循环
         */
        for (int i = 0; i < ending.subtitle.length; i++) {
            paint.setTextSize(ending.subtitleSize[i]);
            canvas.drawText(ending.subtitle[i], ending.subtitleX[i], ending.subtitleY[i].intValue(), paint);      //绘画当前行的字幕
        }
    }

    /**
     * 绘画得分
     */
    public void paintScore(Canvas canvas, Score score) {
        paint.setTextSize(40);
        /*
            每当等分为100的倍数时，等分会闪烁，即开启一个闪烁的动画
         */
        if ((score.getScore() / 10) % 100 == 0 && (score.getScore() / 10) != 0 && !score.scoreFlashing) {
            score.scoreFlash();                                                         //调用得分闪烁的方法
        }
        /*
            显示历史最高分，没有记录时，最高等分不显示
         */
        if (score.gethScore() != 0) {
            canvas.drawText("最高分：" + score.gethScore() / 10, WIDTH - 290, 125, paint);   //绘画历史最高分
        }
        /*
            如果等分为闪烁状态时，显示的数值为闪烁进行时的数值，反之显示当前得分
         */
        if (score.scoreFlashing) {
            canvas.drawText("当前得分：" + score.scoreFlag, WIDTH - 330, 60, paint);               //绘画得分闪烁时的等分
        } else {
            if (score.getScore() == 0) {
                canvas.drawText("", WIDTH - 330, 30, paint);
            } else {
                canvas.drawText("当前得分：" + score.getScore() / 10, WIDTH - 330, 60, paint);     //绘画当前等分
            }
        }
    }

    /**
     * 绘画金币数目
     */
    public void paintCoinNum(Canvas canvas, int num) {
        paint.setTextSize(40);
        canvas.drawBitmap(GameView.coinPic, WIDTH / 2 - 60, 20, paint);                    //绘画金币图片
        canvas.drawText("x", WIDTH / 2 - 5, 60, paint);
        canvas.drawText(String.valueOf(num), WIDTH / 2 + 25, 63, paint);                               //绘画吃到的金币数目
    }

    public void pause_game() {
        if (state == RUNNING) {
            soundPool.pause(1);
            state = PAUSE;                                  //游戏状态置为暂停
            dinosaur.pause = true;                          //恐龙状态置为暂停
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (state) {
            case START:
                state = RUNNING;
                soundPool.play(1, 1, 1, 0, -1, 1);
                break;
            case RUNNING:
                if ((event.getX() >= 20 && event.getX() <= 20 + start_button.getWidth()) && (event.getY() >= 20 && event.getY() <= 20 + start_button.getHeight())) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        soundPool.pause(1);
                        state = PAUSE;                                  //游戏状态置为暂停
                        dinosaur.pause = true;                          //恐龙状态置为暂停
                    }
                    break;
                } else if ((event.getX() >= 180 && event.getX() <= 180 + ai_button1.getWidth()) && (event.getY() >= 20 && event.getY() <= 20 + ai_button1.getHeight())) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (AI) {
                            AI = false;
                        } else {
                            AI = true;
                        }
                    }
                    break;
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!dinosaur.getQuatsState()) {
                            dinosaur.quats();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (dinosaur.getQuatsState()) {
                            dinosaur.finishSquats();
                            break;
                        }
                        dinosaur.jump();
                        break;
                }
                break;
            case PAUSE:
                if ((event.getX() >= 20 && event.getX() <= 20 + start_button.getWidth()) && (event.getY() >= 20 && event.getY() <= 20 + start_button.getHeight())) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        soundPool.resume(1);
                        state = RUNNING;                                     //游戏状态置为运行状态
                        dinosaur.pause = false;                             //恐龙的暂停状态置为false
                    }
                } else if ((event.getX() >= 180 && event.getX() <= 180 + ai_button1.getWidth()) && (event.getY() >= 20 && event.getY() <= 20 + ai_button1.getHeight())) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (AI) {
                            AI = false;
                        } else {
                            AI = true;
                        }
                    }
                }
                break;
            case GAME_OVER:
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (score.getScore() > score.gethScore()) {
                        score.sethScore(score.getScore());              //设置历史最高分
                    }
                    if (gameRunningTime >= tempTime + 500) {
                        soundPool.resume(1);
                        tempTime = 0;                                           //设置临时时间变量为0
                        obstacles = new MovingObject[0];                    //重新创建一个空的障碍物数组
                        coin = new Coin[0];                                 //清空金币数组
                        state = RUNNING;                                    //游戏状态置为运行
                        dinosaur = new Dinosaur(getContext());                          //重新创建一只新的恐龙
                        dinosaur.jump();                                    //调用恐龙跳跃方法
                            /*
                                历史最高分的判断，如果当前分大于历史最高分，则重新设置历史最高分
                             */
                        if (score.getScore() > score.gethScore()) {
                            score.sethScore(score.getScore());              //设置历史最高分
                        }
                        score.setScore(0);                                  //设置当前分为0
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 游戏难度等级的控制
     */
    private void gradeAction() {
        int temp = score.getScore() / 10;
        if (temp < 200) {
            setGrade(1);                            //如果得分小于200分，设置游戏难度等级为1
        } else if (temp < 400) {
            setGrade(2);                            //如果得分为200～400分，设置游戏难度等级为2
        } else if (temp < 600) {
            setGrade(3);                            //如果得分为400～600分，设置游戏难度等级为3
        } else {
            setGrade(4);                            //如果得分为600分以上，设置游戏难度等级为4
        }
    }

    /**
     * 设置游戏难度
     *
     * @param grade 游戏难度等级
     */
    private void setGrade(int grade) {
        switch (grade) {
            case 1:
                sleepTime = sleepTimeArray[0];      //设置游戏总线程的循环速度，即改变线程睡眠时间
                dinosaur.setJumpSleepTime(1);       //设置恐龙的跳跃速度，即改变跳跃定时器的间隔时间
                break;
            case 2:
                sleepTime = sleepTimeArray[1];      //设置游戏总线程的循环速度，即改变线程睡眠时间
                dinosaur.setJumpSleepTime(2);       //设置恐龙的跳跃速度，即改变跳跃定时器的间隔时间
                break;
            case 3:
                sleepTime = sleepTimeArray[2];      //设置游戏总线程的循环速度，即改变线程睡眠时间
                dinosaur.setJumpSleepTime(3);       //设置恐龙的跳跃速度，即改变跳跃定时器的间隔时间
                break;
            case 4:
                sleepTime = sleepTimeArray[3];      //设置游戏总线程的循环速度，即改变线程睡眠时间
                dinosaur.setJumpSleepTime(4);       //设置恐龙的跳跃速度，即改变跳跃定时器的间隔时间
                break;
        }
    }

    /**
     * 游戏中所有的物体的走步方法（鸟、树、云、土地）
     */
    private void stepAction() {
        /*
            对障碍物数组进行循环，使每一个障碍物走一步
         */
        if (obstacles.length > 0) {
            for (int i = 0; i < obstacles.length; i++) {
                obstacles[i].step();
            }
        }
        /*
            对云朵数组进行循环，使每一朵云走一步
         */
        if (cloud.length > 0) {
            for (int i = 0; i < cloud.length; i++) {
                cloud[i].step();
            }
        }
        /*
            对土地数组进行循环，使每一块土地走一步
         */
        for (int i = 0; i < land.length; i++) {
            land[i].step();
        }
        /*
            对金币数组进行循环，使每一枚金币走一步
         */
        if (coin.length > 0) {
            for (int i = 0; i < coin.length; i++) {
                coin[i].step();
            }
        }
    }

    /**
     * 判断是否进入土地的方法
     */
    private void enterLandAction() {
        /*
            当土地数量为0时或者最后一块土地的右边界小于等于游戏窗口的右边界时，生成一块新的土地
         */
        if (land.length == 0 || (land[land.length - 1].x + land[land.length - 1].width <= WIDTH)) {
            nextLand();
        }
    }

    /**
     * 创建土地的方法
     */
    private void nextLand() {
        Land landObject = new Land();                                               //定义并实例化一块土地
        land = Arrays.copyOf(land, land.length + 1);                      //利用数组的复制方法把土地数组的长度加1
        land[land.length - 1] = landObject;                                         //把新生成的土地加到土地数组的最后一位上
    }

    /**
     * 判断是否进入云朵的方法
     */
    private void enterCloudAction() {
        /*
            等分每隔40分，新生成一朵云
         */
        if (score.getScore() % 400 == 0) {
            nextCloud();
        }
    }

    /**
     * 创建云朵的方法
     */
    private void nextCloud() {
        Cloud cloudObject = new Cloud();                                            //定义并实例化一朵云
        cloud = Arrays.copyOf(cloud, cloud.length + 1);                   //利用数组的复制方法把云朵数组的长度加1
        cloud[cloud.length - 1] = cloudObject;                                      //把新生成的云加到云朵数组的最后一位上
    }

    /**
     * 判断是否进入障碍物的方法（鸟、树）
     */
    private void enterObstaclesAction() {
        /*
            得分大于15分后开始生成障碍物，每隔150分新生成一种障碍物
         */
        if (score.getScore() % 100 == 0 && score.getScore() / 10 > 10) {
            Random ra = new Random();                               //定义并实例化一个随机类
            int temp = ra.nextInt(4);                        //定义一个临时变量，存储一个0～3的随机数
            /*
                根据得到的随机数，判断是生成树还是鸟，其中75%可能生成树、25%可能生成鸟，并且鸟只会在游戏等分200分以后才会开始出现
             */
            if (temp >= 3 && score.getScore() / 10 >= 0) {
                nextBird();
                enterCoinAction(1);
            } else {
                int p = ra.nextInt(10) + 1;                 //定义一个临时变量，存储一个1～10的随机数
                int treeCount;                                     //生成树的数量
                /*
                    根据得到的随机数，判断生成树的数量，其中40%可能生成一棵树，30%可能生成两棵树，20%可能生成三棵树，10%可能生成四棵树
                 */
                if (p > 6) {
                    treeCount = 1;
                } else if (p > 3) {
                    treeCount = 2;
                } else if (p > 1) {
                    treeCount = 3;
                } else {
                    treeCount = 4;
                }
                /*
                    利用得到的树的数量，进行循环生成树
                 */
                for (int i = 0; i < treeCount; i++) {
                    nextTree();     //创建一棵树
                    /*
                        每次出现树障碍物，会随机出现1～4棵树，后面的三棵树的x坐标都在各自前一棵树的右边界后面
                     */
                    if (i != 0) {
                        obstacles[obstacles.length - 1].x = obstacles[obstacles.length - 2].x + obstacles[obstacles.length - 2].width + 2;
                    }
                }
                enterCoinAction(treeCount);
            }
        }
    }

    /**
     * 创建一棵树的方法
     */
    private void nextTree() {
        Tree treeObject = new Tree();                                               //定义并实例化一棵树
        obstacles = Arrays.copyOf(obstacles, obstacles.length + 1);      //利用数组的复制方法把障碍物数组的长度加1
        obstacles[obstacles.length - 1] = treeObject;                               //把新生成的树加到障碍物数组的最后一位上
    }

    /**
     * 创建鸟的方法
     */
    private void nextBird() {
        Bird birdObject = new Bird();                                               //定义并实例化一只鸟
        obstacles = Arrays.copyOf(obstacles, obstacles.length + 1);       //利用数组的复制方法把障碍物数组的长度加1
        obstacles[obstacles.length - 1] = birdObject;                               //把新生成的鸟加到障碍物数组的最后一位上
    }

    /**
     * 判断是否进入金币的方法
     */
    private void enterCoinAction(int pares) {
        Random ra = new Random();           //定义并实例化一个随机类
        int temp = ra.nextInt(5);   //定义一个临时变量存储一个0～4随机数
        /*
            20%几率，新生成一枚金币
         */
        if (temp >= 4) {
            nextCoin(pares);
        }
    }

    /**
     * 创建金币的方法
     */
    private void nextCoin(int pares) {
        Coin coinObject = new Coin();                                                           //定义并实例化一枚金币
        int right = obstacles[obstacles.length - 1].x + obstacles[obstacles.length - 1].width;  //障碍物的左边界
        int left = obstacles[obstacles.length - pares].x;                                       //障碍物的右边界
        coinObject.x = (left + right) / 2 - coinObject.width / 2;                               //设置生成的金币的初始x坐标
        coin = Arrays.copyOf(coin, coin.length + 1);                                //利用数组的复制方法把金币数组的长度加1
        coin[coin.length - 1] = coinObject;                                                     //把新生成的金币加到金币数组的最后一位上
    }

    /**
     * 越界后删除物体的行为，每当物体移动到游戏窗口外面后，删除该物体
     */
    private void outOfBoundsAction() {
        /*
            如果土地数组中的第一块土地越界了，则删除该土地，云朵、障碍物也同理
         */
        if (land[0].outOfBounds()) {
            land = Arrays.copyOfRange(land, 1, land.length);                    //利用数组的截取方法，截取第一块土地后面的所有土地
        }
        if (cloud[0].outOfBounds()) {
            cloud = Arrays.copyOfRange(cloud, 1, cloud.length);                 //利用数组的截取方法，截取第一朵云后面的所有云
        }
        if (obstacles.length != 0) {
            if (obstacles[0].outOfBounds()) {
                obstacles = Arrays.copyOfRange(obstacles, 1, obstacles.length); //利用数组的截取方法，截取第一个障碍物后面的所有的障碍物
            }
        }
        if (coin.length != 0) {
            if (coin[0].outOfBounds()) {
                coin = Arrays.copyOfRange(coin, 1, coin.length);                //利用数组的截取方法，截取第一个金币后面的所有的金币
            }
        }
    }

    /**
     * 吃金币的判定
     */
    private void eatCoinAction() {
        if (coin.length != 0) {
            boolean a = dinosaur.x >= coin[0].x - 20 && dinosaur.x <= (coin[0].x + coin[0].width + 20);
            boolean b = (dinosaur.x + dinosaur.width) >= coin[0].x - 20 && (dinosaur.x + dinosaur.width) <= (coin[0].x + coin[0].width + 20);
            boolean c = dinosaur.y >= coin[0].y - 20 && dinosaur.y <= (coin[0].y + coin[0].height + 20);
            boolean d = (dinosaur.y + dinosaur.height) >= coin[0].y - 20 && (dinosaur.y + dinosaur.height) <= (coin[0].y + coin[0].height + 20);
            if (((a || b) && (c || d))) {
                soundPool.play(3, 1, 1, 0, 0, 1);
                coinNum++;                                                            //吃到的金币数目加1
                coin = Arrays.copyOfRange(coin, 1, coin.length);                //利用数组的截取方法，截取第一个金币后面的所有的金币
            }
        }
    }

    /**
     * AI模式恐龙的行为控制
     */
    private void superAiAction() {
        if (AI) {
            if (obstacles.length != 0) {
                /*
                    树和鸟有两种不同的判定，当恐龙快碰到树或鸟时，执行恐龙的跳跃方法
                 */
                if (obstacles[0] instanceof Bird) {
                    if (dinosaur.x + dinosaur.width + 120 >= obstacles[0].x && dinosaur.x <= obstacles[0].x) {
                        dinosaur.jump();
                    }
                } else {
                    if (dinosaur.x + dinosaur.width + 20 >= obstacles[0].x && dinosaur.x < obstacles[0].x + obstacles[0].width && dinosaur.x <= obstacles[0].x) {
                        dinosaur.jump();
                    }
                }
            }
        }
    }

    /**
     * 检查游戏是否结束的行为
     */
    private void checkGameOverAction() {
        if (isGameOver()) {
            soundPool.pause(1);
            soundPool.play(4, 1, 1, 0, 0, 1);
            state = GAME_OVER;                              //游戏状态置为GameOver状态
            tempTime = gameRunningTime;                     //临时时间变量设为当前游戏运行时间
            dinosaur.die();                                 //恐龙死亡
                /*
                    对障碍物数组进行循环，如果是鸟，就停止鸟的飞行状态
                 */
            for (int i = 0; i < obstacles.length; i++) {
                if (obstacles[i] instanceof Bird) {
                    obstacles[i].gameOver = true;
                }
            }
        }
    }

    /**
     * 判断恐龙是否死亡的行为，利用恐龙与障碍物的边界碰撞判定
     *
     * @return true：恐龙死亡  false：恐龙未死亡
     */
    private boolean isGameOver() {
        /*
            对障碍物数组进行循环
         */
        for (int i = 0; i < obstacles.length; i++) {
            /*
                由于恐龙的外形不规则，有两种判定，一种为跑动状态和跳跃状态的判定，一种为下蹲状态下的判定
             */
            if (dinosaur.image.equals(GameView.dinosaurRunPic1) || dinosaur.image.equals(GameView.dinosaurRunPic2) || dinosaur.image.equals(GameView.dinosaurJumpPic)) {
                boolean a = (dinosaur.x + 90) >= obstacles[i].x && (dinosaur.x + 90) <= (obstacles[i].x + obstacles[i].width);
                boolean b = dinosaur.x + dinosaur.width - 10 >= obstacles[i].x && (dinosaur.x + dinosaur.width - 10) <= (obstacles[i].x + obstacles[i].width);
                boolean c = dinosaur.y >= obstacles[i].y && dinosaur.y <= (obstacles[i].y + obstacles[i].height);
                boolean d = (dinosaur.y + 60) >= obstacles[i].y && (dinosaur.y + 60) <= (obstacles[i].y + obstacles[i].height);
                boolean e = dinosaur.x + 50 >= obstacles[i].x && dinosaur.x + 50 <= (obstacles[i].x + obstacles[i].width);
                boolean f = (dinosaur.x + 120) >= obstacles[i].x && (dinosaur.x + 120) <= (obstacles[i].x + obstacles[i].width);
                boolean g = (dinosaur.y + 60) >= obstacles[i].y && (dinosaur.y + 60) <= (obstacles[i].y + obstacles[i].height);
                boolean h = (dinosaur.y + dinosaur.height) >= obstacles[i].y && (dinosaur.y + dinosaur.height) <= (obstacles[i].y + obstacles[i].height);
                if (((a || b) && (c || d)) || ((e || f) && (g || h))) {
                    return true;
                }
            } else if (dinosaur.image.equals(GameView.dinosaurSquatsPic1) || dinosaur.image.equals(GameView.dinosaurSquatsPic2)) {
                boolean a = dinosaur.x >= obstacles[i].x && dinosaur.x <= (obstacles[i].x + obstacles[i].width);
                boolean b = (dinosaur.x + dinosaur.width) >= obstacles[i].x && (dinosaur.x + dinosaur.width) <= (obstacles[i].x + obstacles[i].width);
                boolean c = dinosaur.y >= obstacles[i].y && dinosaur.y <= (obstacles[i].y + obstacles[i].height);
                boolean d = (dinosaur.y + dinosaur.height) >= obstacles[i].y && (dinosaur.y + dinosaur.height) <= (obstacles[i].y + obstacles[i].height);
                if (((a || b) && (c || d))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否进入结局状态的行为
     */
    private void endingAction() {
        /*
            如果得分到达188分，则进入结局状态
         */
        if (score.getScore() / 10 == 188) {
            /*
                如果进入结局状态时，恐龙为下蹲状态，则取消下蹲状态，使之跑动
             */
            if (dinosaur.quatsState) {
                dinosaur.finishSquats();
            }
            state = ENDING;                                 //状态置为结局状态
            ending = new Ending(getContext());                          //实例化结局类
            soundPool.pause(1);
            soundPool.play(5, 1, 1, 0, 0, 1);
            soundPool.stop(1);
            /*
                对障碍物数组进行循环，如果是鸟，就停止鸟的飞行状态
            */
            for (int i = 0; i < obstacles.length; i++) {
                if (obstacles[i] instanceof Bird) {
                    obstacles[i].gameOver = true;
                }
            }
        }
    }

    /**
     * 结局状态下的走步行为（两只恐龙）
     */
    private void endingStepAction() {
        //如果两只恐龙未接触，则相互靠近，接触后，则恐龙停止跑动
        if (dinosaur.x + dinosaur.width < ending.femaleDinosaur.x + 10) {
            dinosaur.step(3);                                   //主角恐龙的走步
            ending.femaleDinosaur.step(-6);                     //女恐龙的走步
        } else {
            dinosaur.runningTimer.cancel();                            //停止主角恐龙的跑动定时器
            ending.runningTimer.cancel();                              //停止女恐龙的跑动定时器
            dinosaur.image = dinosaurJumpPic;                          //主角恐龙图片切换为站定状态
            ending.femaleDinosaur.image = femaleDinosaurJumpPic;       //女恐龙图片切换为站定状态
        }
    }

    /**
     * 结局状态下进入字幕的行为
     */
    private void endingSubtitleAction() {
        /*
            记录进入结局状态的当前游戏运行时间
         */
        if (tempTime == 0) {
            tempTime = gameRunningTime;
        }
        /*
            如果进入游戏结局状态达到2000毫秒，则开始进入字幕
         */
        if (gameRunningTime >= tempTime + 2000) {
            /*
                如果第一行字幕到达游戏窗口的指定位置时，字幕停止上升
             */
            if (ending.subtitleY[0] > 200) {
                for (int i = 0; i < ending.subtitle.length; i++) {
                    ending.subtitleY[i] -= 2f;        //设置每一行字幕的y坐标
                }
            }
        }
        /*
            如果进入游戏结局状态达到13000毫秒，则重新开始游戏
         */
        if (gameRunningTime >= tempTime + 13000) {
            tempTime = 0;                               //临时的时间变量置为0
            state = START;                              //游戏状态置为开始状态
            obstacles = new MovingObject[0];            //清空障碍物数组
            cloud = new Cloud[0];                       //清空云数组
            land = new Land[0];                         //清空土地数组
            coin = new Coin[0];                        //清空金币数组
            dinosaur = new Dinosaur(getContext());                  //实例化一只新的恐龙
            dinosaur.running();                         //恐龙跑动
            score.sethScore(score.getScore());              //设置历史最高分
            score.setScore(0);                          //游戏等分置为0
        }
    }

    public void destroy_game() {
        tempTime = 0;                               //临时的时间变量置为0
        state = START;                              //游戏状态置为开始状态
        obstacles = new MovingObject[0];            //清空障碍物数组
        cloud = new Cloud[0];                       //清空云数组
        land = new Land[0];                         //清空土地数组
        coin = new Coin[0];                        //清空金币数组
        score.setScore(0);                          //游戏等分置为0
    }
}
