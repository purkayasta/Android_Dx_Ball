package embedded_programing_final_term_project.dx_ball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Bar{
    MainGameView mainGameView;
    Bitmap barBitmap;
    float leftmostPoint,centerPoint,topPoint,barWidth,barHeight;

    public Bar(MainGameView mainGameView, int bitmapId, Context context){
        this.mainGameView = mainGameView;
        Bitmap tempBitmap = BitmapFactory.decodeResource(mainGameView.context.getResources(),bitmapId);
        tempBitmap = Bitmap.createScaledBitmap(tempBitmap,mainGameView.screenX/2,mainGameView.screenY/8,true);
        barBitmap = tempBitmap;
        barHeight = barBitmap.getHeight();
        barWidth = barBitmap.getWidth();

        centerPoint = context.getResources().getDisplayMetrics().widthPixels/2;
        leftmostPoint = centerPoint - barWidth/2;
        topPoint = (context.getResources().getDisplayMetrics().heightPixels -
                context.getResources().getDisplayMetrics().density*10-barHeight);
    }

    public void latestBarPosition(float x, Context context) {
        if((x-(barWidth/2))>0 && (x+(barWidth/2))<context.getResources().getDisplayMetrics().widthPixels){
            leftmostPoint = x-(barWidth/2);

            Log.d("Bar","leftmostPoint: "+leftmostPoint+"barWidth/2: "+barWidth/2);
        }
    }
}