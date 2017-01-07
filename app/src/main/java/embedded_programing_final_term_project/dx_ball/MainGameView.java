package embedded_programing_final_term_project.dx_ball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.Random;


public class MainGameView extends SurfaceView implements Runnable {
    public static int canvasHeight, canvasWidth;
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
    Paint paint, paint1;
    //Screen Size
    int screenX, screenY;
    //Players Paddle Bar


    Ball ball;
    float left,
            right,
            top,
            bottom;

    float downX,
            downY,
            upX,
            upY;

    boolean
            leftPos,
            rightPos,
            first = true;

    int min_distance = 50;

    int ballSpeed;



    Bar bar;
    //bricks
    // Up to 200 bricks
    Bricks[] bricks = new Bricks[50];
    int numBricks = 0;

    Random rand = new Random();
    int r = rand.nextInt();
    int g = rand.nextInt();
    int b = rand.nextInt();
    // Track the fps rate
    private long timeThisFrame;


    //Constructor
    public MainGameView(Context context, Canvas canvas) {
        super(context);
        this.context = context;
        this.canvas = canvas;
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

        bar = new Bar(this, R.drawable.bar, context);

        ball = new Ball(screenX/2,screenY/2,Color.RED,30);




        //Bricks
        createBricksAndRestart();
    }

    public void createBricksAndRestart() {



        // Put the ball back to the start
        //  ball.reset(screenX, screenY);

        int brickWidth = screenX / 3;
        int brickHeight = screenY / 9;

        // Build a wall of bricks
        numBricks = 0;
        //paint.setARGB(255,r,g,b);

        for (int column = 0; column < 3; column++) {
            for (int row = 0; row < 3; row++) {
                bricks[numBricks] = new Bricks(row, column, brickWidth, brickHeight);
                numBricks++;
            }
        }

    }


    //Runnable Object
    @Override
    public void run() {
        while (playing) {
            //Capture the current time
            long startFrameTime = System.currentTimeMillis();
            //Update the frame
            if (!paused) {
                update();
            }
            //Draw the frame
            draw();
            //Calculate the fps
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }
    }

    private void update() {
        ball.move();
    }



    public void draw() {
        //this.canvas = canvas;

        if (ourHolder.getSurface().isValid()) {
            //Lock the canvas
            canvas = ourHolder.lockCanvas();
            //Draw Background Color
            canvas.drawColor(Color.WHITE);


            canvas.drawBitmap(bar.barBitmap, bar.leftmostPoint, bar.topPoint, null);

            //Ball

            canvas.drawCircle(ball.getX(), ball.getY(), ball.getRadius(), ball.getPaint());
            ball.bounce(canvas);
            ball.setDx(2);
            ball.setDy(-2);
            ball.ballBoundaryChech(canvas);
            ball.move();





            // Draw the bricks if visible
            for (int i = 0; i < numBricks; i++) {

                if (bricks[i].getVisibility()) {
                    if (i % 4 == 0) {
                        paint.setColor(Color.BLUE);
                        canvas.drawRect(bricks[i].getRect(), paint);
                    } else {
                        paint.setColor(Color.BLACK);
                        canvas.drawRect(bricks[i].getRect(), paint);
                    }

                }

            }


            //Now Unlock
            ourHolder.unlockCanvasAndPost(canvas);

        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Error", "Joining thread");
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                bar.latestBarPosition(event.getX(), context);
                break;
            case MotionEvent.ACTION_DOWN:
                bar.latestBarPosition(event.getX(), context);
                break;
            case MotionEvent.ACTION_UP:
                bar.latestBarPosition(event.getX(), context);
                break;
        }
        return true;
    }
}
