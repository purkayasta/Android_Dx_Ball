package embedded_programing_final_term_project.dx_ball;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Ball {
    private int gameOver=0;
    private Point p;
    private int x;
    private int y;
    private int c;
    private int r;
    private  int dx;
    private int dy;
    private Paint paint;
    Canvas canvas;

    float dW,dH;

    public Ball(Canvas canvas){
        this.canvas = canvas;
    }

    public  Ball(int x,int y,int col,int radius){
        //xBall Position
        this.x=x;
        //YBall Position
        this.y=y;
        c = col;
        r=radius;
        paint=new Paint();
        paint.setColor(c);
        dx=0;
        dy=0;


    }
    public int getX(){
        return x;
    }

    public int getGameOver(){
        return gameOver;
    }
    public int getY() {
        return y;
    }

    public int getC() {
        return c;
    }

    public int getRadius() {
        return r;
    }

    public Paint getPaint() {
        return paint;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public void setColor(int col){
        c=col;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void move(){
        _checkBarBallCollusion();
    }

    public void ballBoundaryCheck(Canvas canvas) {
        this.canvas = canvas;
        dW = canvas.getWidth();
        dH = canvas.getHeight();

        if((this.x+this.r)>=canvas.getWidth()
                || (this.x-this.r)<=0){
            this.dx = -this.dx;
        }
        if( (this.y-this.r)<=0){
            this.dy = -this.dy;
        }
    }

    private void _checkBarBallCollusion() {
        if (this.x >= (dW - this.r)){
            this.dx = -this.dx;
        }
        if (this.x <= this.r){
            this.dx = -this.dx;
        }
        if (this.y <= this.r){
            this.dy = -this.dy;
        }

        x += dx;
        y += dy;
    }


    public void bounce(Canvas canvas){

        ballBoundaryCheck(canvas);

        move();

        if(x == canvas.getWidth()|| x < 0){
            x=0;
            y=0;
        }
        if(y == canvas.getWidth() || y < 0){

            x=0;
            x=0;
        }
    }
}
