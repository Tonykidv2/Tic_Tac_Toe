package edu.fullsail.mgems.cse.tic_tac_toe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.pvptext).setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            if(view.getId() == R.id.pvptext)
            {
                Intent i = new Intent(this, GameActivity.class);
                startActivity(i);
                finish();
            }
        }
        return false;
    }

}
