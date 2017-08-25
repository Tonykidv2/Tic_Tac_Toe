package edu.fullsail.mgems.cse.tic_tac_toe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener{

    ArrayList<DrawSurface> mSurfaces;

    public enum DrawWhat
    {
        X,
        O,
        NONE
    };

    public enum GameMode
    {
        PVP,
        PVC,
        CVC
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initGame();
    }
    void initGame()
    {
        mSurfaces = new ArrayList<DrawSurface>();

        mSurfaces.add((DrawSurface)findViewById(R.id.drawSurface1));
        mSurfaces.add((DrawSurface)findViewById(R.id.drawSurface2));
        mSurfaces.add((DrawSurface)findViewById(R.id.drawSurface3));
        mSurfaces.add((DrawSurface)findViewById(R.id.drawSurface4));
        mSurfaces.add((DrawSurface)findViewById(R.id.drawSurface5));
        mSurfaces.add((DrawSurface)findViewById(R.id.drawSurface6));
        mSurfaces.add((DrawSurface)findViewById(R.id.drawSurface7));
        mSurfaces.add((DrawSurface)findViewById(R.id.drawSurface8));
        mSurfaces.add((DrawSurface)findViewById(R.id.drawSurface9));
        //Bitmap mCross = BitmapFactory.decodeResource(getResources(), R.drawable.cross);
        //mCross = Bitmap.createScaledBitmap(mCross, mSurfaces.get(0).getWidth(),mSurfaces.get(0).getHeight(), false);
        //Bitmap mCircle = BitmapFactory.decodeResource(getResources(), R.drawable.circle);
        //mCircle = Bitmap.createScaledBitmap(mCircle, mSurfaces.get(0).getWidth(),mSurfaces.get(0).getHeight(), false);
        for (int i = 0; i < mSurfaces.size(); i++)
        {
            //mSurfaces.get(i).mCross = mCross;
            //mSurfaces.get(i).mCircle = mCircle;
            mSurfaces.get(i).setOnTouchListener(this);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            for (int i = 0; i < mSurfaces.size(); i++)
            {
                if(view.getId() == mSurfaces.get(i).getId())
                {
                    mSurfaces.get(i).DrawingWhat(DrawWhat.O);
                    mSurfaces.get(i).invalidate();
                }
            }
        }
        return false;
    }

}
