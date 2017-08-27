package edu.fullsail.mgems.cse.tic_tac_toe;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.DimenRes;
import android.support.v7.app.AlertDialog;
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
    AlertDialog.Builder _dialog;
    int SelectDp;
    int TitleDp;
    int SubDp;
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
        SelectDp = (int)(getResources().getDimension((R.dimen.menu_selected))/getResources().getDisplayMetrics().density);
        pvp.setTextSize(SelectDp);
        TitleDp = (int)(getResources().getDimension((R.dimen.menu_title))/getResources().getDisplayMetrics().density);
        SubDp = (int)(getResources().getDimension((R.dimen.menu_subtitle))/getResources().getDisplayMetrics().density);
        findViewById(R.id.difficulty).setOnTouchListener(this);
        findViewById(R.id.Start).setOnTouchListener(this);
        findViewById(R.id.credit).setOnTouchListener(this);
        _dialog = new AlertDialog.Builder(this);
        _dialog.setTitle("Made by");
        _dialog.setMessage("Anthony Ramoslebron\nMGMS | APM\n8/26/2017");
        _dialog.setPositiveButton(" OK ", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface _dialog, int id)
            {
                _dialog.dismiss();
            }
        });
        _dialog.setNegativeButton(" Find Source Code ", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface _dialog, int id)
            {
                Intent _intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Tonykidv2/Tic_Tac_Toe"));
                startActivity(_intent);
                _dialog.dismiss();
            }
        });
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
            cvc.setTextSize(SubDp);
            pvc.setTextSize(SubDp);
            pvp.setTextSize(SelectDp);
            currMode = GameActivity.GameMode.PVP;
        }
        else if (view.getId() == R.id.cvptext && motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            cvc.setTextSize(SubDp);
            pvc.setTextSize(SelectDp);
            pvp.setTextSize(SubDp);
            currMode = GameActivity.GameMode.PVC;
        }
        else if (view.getId() == R.id.cvctext && motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            cvc.setTextSize(SelectDp);
            pvc.setTextSize(SubDp);
            pvp.setTextSize(SubDp);
            currMode = GameActivity.GameMode.CVC;
        }
        else if (view.getId() == R.id.credit && motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            _dialog.show();
        }
        return false;
    }

}
