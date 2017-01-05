package embedded_programing_final_term_project.dx_ball;

import android.graphics.RectF;

/**
 * Created by Sunny on 12/29/2016.
 */

public class Bricks {

    private RectF rect;

    private boolean isVisible;

    public Bricks(int row, int column, int width, int height){

        isVisible = true;

        float padding = 0.88f;

        rect = new RectF(column * width + padding,
                row * height + padding,
                column * width + width - padding,
                row * height + height - padding);
    }

    public RectF getRect(){
        return this.rect;
    }

    public void setInvisible(){
        isVisible = false;
    }

    public boolean getVisibility(){
        return isVisible;
    }
}
