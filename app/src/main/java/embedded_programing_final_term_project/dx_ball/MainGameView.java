package embedded_programing_final_term_project.dx_ball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.Random;


/**
 * Created by Sunny on 12/28/2016.
 */

public class MainGameView extends SurfaceView implements Runnable {
    //Using Paint And Canvas in a thread need a surface holder object
    SurfaceHolder ourHolder;

    //Thread
    Thread gameThread = null;

    Context context;

    //Game is set or unset Need a boolean to decide
    volatile boolean playing;
    //Game Is Paused at the start
    boolean paused = true;
    //To Track the frame per second rate
    long fps;
    //Canvas and Paint objects
    Canvas canvas;
    Paint paint,paint1;
    // Track the fps rate
    private long timeThisFrame;

    //Screen Size
    int screenX,screenY;
    //Players Paddle Bar


    Ball myBall;

    Bar bar;
    //bricks
    // Up to 200 bricks
    Bricks[] bricks = new Bricks[50];
    int numBricks = 0;

    Random rand = new Random();
    int r = rand.nextInt();
    int g = rand.nextInt();
    int b = rand.nextInt();



    public  static int canvasHeight, canvasWidth;

    //Constructor
    public MainGameView(Context context,Canvas canvas) {
        super(context);
        this.context = context;
        ourHolder = getHolder();
        paint = new Paint();
        Log.d("Ok", "MainGameView: ");

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        this.screenX = size.x;
        this.screenY = size.y;

        Log.d(String.valueOf(screenX), "X");
        Log.d(String.valueOf(screenY), "Y");



        //PaddleBar

        bar = new Bar(this,R.drawable.bar);


        //Ball
        myBall = new Ball(screenX/2,screenY/2,Color.RED,30);
        myBall.bounce(size);
        myBall.setDx(5);
        myBall.setDy(5);
        myBall.move();





        //Bricks

        createBricksAndRestart();

    }
    public void createBricksAndRestart(){

        // Put the ball back to the start
      //  ball.reset(screenX, screenY);

        int brickWidth = screenX / 5;
        int brickHeight = screenY / 9;

        // Build a wall of bricks
        numBricks = 0;
        //paint.setARGB(255,r,g,b);

        for(int column = 0; column < 6; column ++ ){
            for(int row = 0; row < 4; row ++ ){
                bricks[numBricks] = new Bricks(row, column, brickWidth, brickHeight);
                numBricks ++;
            }
        }

    }


    //Runnable Object
    @Override
    public void run() {
        while (playing){
            //Capture the current time
            long startFrameTime = System.currentTimeMillis();
            //Update the frame
            if (!paused){
                update();
            }
            //Draw the frame
            draw();

            //Calculate the fps
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1){
                fps = 1000 / timeThisFrame;
            }
        }
    }

    public void update() {
        myBall.move();
        for (int i=0;i < numBricks; i++){
            if (bricks[i].getVisibility()){

            }
        }
    }


    public void draw() {
        if (ourHolder.getSurface().isValid()){
            //Lock the canvas
            canvas = ourHolder.lockCanvas();
            //Draw Background Color
            canvas.drawColor(Color.WHITE);


            canvas.drawBitmap(bar.barBitmap,bar.topleftPoint.x,bar.topleftPoint.y,null);

            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(myBall.getX(),myBall.getY(),myBall.getRadius(), myBall.getPaint());

            // Draw the bricks if visible
            for(int i = 0; i < numBricks; i++){
                if(bricks[i].getVisibility()) {
                    if (i%6==0){
                        paint.setColor(Color.BLUE);
                        canvas.drawRect(bricks[i].getRect(), paint);
                    }else{
                        paint.setColor(Color.BLACK);
                        canvas.drawRect(bricks[i].getRect(), paint);
                    }

                }
            }

            //Now Unlock
            ourHolder.unlockCanvasAndPost(canvas);

        }
    }

    public void pause(){
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("Error", "Joining thread");
        }
    }
    public void resume(){
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                paused = false;
                if (event.getX() > (screenX / 2)) {
                    bar.startMovingRight();
                } else {
                    bar.startMovingLeft();
                }
                break;

            case MotionEvent.ACTION_UP:
                if (event.getX() > (screenX / 2)) {
                    bar.startMovingRight();
                } else {
                    bar.startMovingLeft();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getX() > (screenX / 2)) {
                    bar.startMovingRight();
                } else {
                    bar.startMovingLeft();
                }
                break;
        }
        return true;
    }
}
