package edu.fullsail.mgems.cse.tic_tac_toe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;

/**
 * Created by TheNinjaFS1 on 8/24/17.
 */

public class DrawSurface extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    public Bitmap mCross;
    public Bitmap mCircle;
    private Paint painter;
    private boolean mFirstTime = true;
    private boolean IBeenTouched = false;
    private GameActivity.DrawWhat Drawthis = GameActivity.DrawWhat.NONE;

    public DrawSurface(Context context) {
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        init();
    }

    public DrawSurface(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        init();
    }

    public DrawSurface(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        init();
    }

    void init()
    {
        mCross = BitmapFactory.decodeResource(getResources(), R.drawable.cross);
        mCircle = BitmapFactory.decodeResource(getResources(), R.drawable.circle);
        painter = new Paint();

        //Make sure onDraw is called when I touch screen
        setWillNotDraw(false);

    }
    @Override
    public void onDraw(Canvas canvas)
    {
        //Draw the field Here
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        if(Drawthis == GameActivity.DrawWhat.X)
        {
            canvas.drawBitmap(mCross,0,0,painter);
        }
        else if(Drawthis == GameActivity.DrawWhat.O)
        {
            canvas.drawBitmap(mCircle,0,0,painter);
        }
    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        System.out.println("Creating" + getId());

        Canvas c = null;

        try
        {
            c = surfaceHolder.lockCanvas(null);
            mCross = BitmapFactory.decodeResource(getResources(), R.drawable.cross);
            mCross = Bitmap.createScaledBitmap(mCross, c.getWidth(),c.getHeight(), false);
            mCircle = BitmapFactory.decodeResource(getResources(), R.drawable.circle);
            mCircle = Bitmap.createScaledBitmap(mCircle, c.getWidth(),c.getHeight(), false);
            synchronized (surfaceHolder)
            {
                System.out.println("Resizing Images");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Error Scaling");
        }
        finally
        {
            if(c != null)
                surfaceHolder.unlockCanvasAndPost(c);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
            IBeenTouched = true;

        return false;
    }

    public boolean GetisTouched()
    {
        return IBeenTouched;
    }
    public void DrawingWhat(GameActivity.DrawWhat _this)
    {
        Drawthis = _this;
    }
}
