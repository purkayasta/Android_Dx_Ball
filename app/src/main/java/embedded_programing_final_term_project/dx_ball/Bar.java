package embedded_programing_final_term_project.dx_ball;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class Bar{
    MainGameView mainGameView;
    Bitmap barBitmap;
    int barWidth,barHeight;
    Point bottomcenterPoint,topleftPoint= new Point(0,0);
    int leftmostPoint,rightmostPoint;

    public Bar(MainGameView mainGameView, int bitmapId){
        this.mainGameView = mainGameView;
        Bitmap tempBitmap = BitmapFactory.decodeResource(mainGameView.context.getResources(),bitmapId);
        tempBitmap = Bitmap.createScaledBitmap(tempBitmap,mainGameView.screenX/2,mainGameView.screenY/8,true);
        barBitmap = tempBitmap;
        barHeight = barBitmap.getHeight();
        barWidth = barBitmap.getWidth();
        bottomcenterPoint = new Point(mainGameView.screenX/4,mainGameView.screenY);
        topleftPoint.y = bottomcenterPoint.y-barHeight;
        updateInfo();
    }

    private void updateInfo() {
        leftmostPoint = bottomcenterPoint.x-barHeight/2;
        rightmostPoint = bottomcenterPoint.x-barWidth/2;
        topleftPoint.x =leftmostPoint;
    }

    public void moveDocktoLeft(){
        bottomcenterPoint.x-=14;
        updateInfo();
    }
    public void moveDocktoright(){
        bottomcenterPoint.x+=14;
        updateInfo();
    }

    public void startMovingLeft(){
        moveDocktoLeft();
    }
    public void startMovingRight(){
        moveDocktoright();
    }

}