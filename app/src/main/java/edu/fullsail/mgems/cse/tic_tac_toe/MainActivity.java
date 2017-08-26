package edu.fullsail.mgems.cse.tic_tac_toe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    GameActivity.GameMode currMode = GameActivity.GameMode.PVP;
    GameActivity.Difficulty currDiff = GameActivity.Difficulty.EASY;

    TextView pvp;
    TextView pvc;
    TextView cvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pvp = (TextView)(findViewById(R.id.pvptext));
        pvc = (TextView)(findViewById(R.id.cvptext));
        cvc = (TextView)(findViewById(R.id.cvctext));
        pvp.setOnTouchListener(this);
        pvc.setOnTouchListener(this);
        cvc.setOnTouchListener(this);
        pvp.setTextSize(30);
        findViewById(R.id.difficulty).setOnTouchListener(this);
        findViewById(R.id.Start).setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (view.getId() == R.id.Start && motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            Intent i = new Intent(this, GameActivity.class);
            GameActivity.GameMode mode = currMode;
            GameActivity.Difficulty diff = GameActivity.Difficulty.EASY;
            TextView t1 = ((TextView) (findViewById(R.id.difficulty)));

            if (t1.getText().equals("Difficulty: Medium"))
                diff = GameActivity.Difficulty.MEDIUM;
            else if (t1.getText().equals("Difficulty: Hard"))
                diff = GameActivity.Difficulty.HARD;

            i.putExtra("mode", mode.ordinal());
            i.putExtra("diff", diff.ordinal());
            startActivity(i);
            finish();
        }
        else if (view.getId() == R.id.difficulty && motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            TextView t1 = ((TextView) (findViewById(R.id.difficulty)));

            if (t1.getText().equals("Difficulty: Medium"))
            {
                t1.setText(R.string.DHard);
                currDiff = GameActivity.Difficulty.HARD;
            }
            else if (t1.getText().equals("Difficulty: Hard"))
            {
                t1.setText(R.string.DEasy);
                currDiff = GameActivity.Difficulty.EASY;
            }
            else
            {
                t1.setText(R.string.DMedium);
                currDiff = GameActivity.Difficulty.MEDIUM;
            }
        }
        else if (view.getId() == R.id.pvptext && motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            cvc.setTextSize(20);
            pvc.setTextSize(20);
            pvp.setTextSize(30);
            currMode = GameActivity.GameMode.PVP;
        }
        else if (view.getId() == R.id.cvptext && motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            cvc.setTextSize(20);
            pvc.setTextSize(30);
            pvp.setTextSize(20);
            currMode = GameActivity.GameMode.PVC;
        }
        else if (view.getId() == R.id.cvctext && motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            cvc.setTextSize(30);
            pvc.setTextSize(20);
            pvp.setTextSize(20);
            currMode = GameActivity.GameMode.CVC;
        }
        return false;
    }

}
