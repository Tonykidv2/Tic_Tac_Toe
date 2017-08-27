package edu.fullsail.mgems.cse.tic_tac_toe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class WinnerScreen extends AppCompatActivity implements View.OnTouchListener{

    TextView mText;
    GameActivity.Winner mWhoWon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_screen);

        mText = (TextView)findViewById(R.id.WhoWon);

        Bundle b = getIntent().getExtras();
        mWhoWon = GameActivity.Winner.values()[b.getInt("Win")];

        if(mWhoWon == GameActivity.Winner.X)
            mText.setText(R.string.X);
        else if(mWhoWon == GameActivity.Winner.O)
            mText.setText(R.string.O);
        else
            mText.setText(R.string.Draw);

        mText.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if(view.getId() == mText.getId())
        {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
        return false;
    }
}
