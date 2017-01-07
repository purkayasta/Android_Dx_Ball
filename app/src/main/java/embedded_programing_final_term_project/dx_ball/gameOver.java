package embedded_programing_final_term_project.dx_ball;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Sunny on 1/8/2017.
 */

public class gameOver extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        Log.d("GameOver Class", "Again Start: ");
        Intent i = new Intent(this,StartingGameView.class);
        startActivity(i);
    }
}
