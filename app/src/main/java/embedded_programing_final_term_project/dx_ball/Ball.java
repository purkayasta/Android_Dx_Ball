package embedded_programing_final_term_project.dx_ball;


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
    public  Ball(int x,int y,int col,int radius){
        this.x=x;
        this.y=y;
        col=c;
        r=radius;
        paint=new Paint();
        paint.setColor(Color.RED);
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
        x=x+dx;
        y=y+dy;
    }

    public void ballBoundaryChech(Canvas canvas) {

        if((this.y-this.r)>=canvas.getHeight()){

            MainGameView.life-=1;
            MainGameView.newLife=true;
            this.gameOver=1;
        }

        if(MainGameView.life==0)
            MainGameView.gameOver = true;

        if((this.x+this.r)>=canvas.getWidth()
                || (this.x-this.r)<=0){
            this.dx = -this.dx;
        }
        if( (this.y-this.r)<=0){
            this.dy = -this.dy;
        }
    }


    public void bounce(Canvas canvas){
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
