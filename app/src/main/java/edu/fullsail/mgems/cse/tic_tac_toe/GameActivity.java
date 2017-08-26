package edu.fullsail.mgems.cse.tic_tac_toe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener{

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
    public enum Difficulty
    {
        EASY,
        MEDIUM,
        HARD,
    };
    public enum Winner
    {
        TBD,
        X,
        O,
        DRAW
    };
    private ArrayList<DrawSurface> mSurfaces;
    private MinimaxAI mComputer1;
    private MinimaxAI mComputer2;
    private MinimaxAI.GameBoard mBoard;
    private GameMode mMode;
    private Difficulty mDiff;
    private boolean PlayerTurn;

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

        for (int i = 0; i < mSurfaces.size(); i++)
        {
            mSurfaces.get(i).setOnTouchListener(this);
        }

        Bundle b = getIntent().getExtras();
        mMode = GameMode.values()[b.getInt("mode")];
        mDiff = Difficulty.values()[b.getInt("diff")];
        if(mMode == GameMode.PVC)
        {
            mComputer1 = new MinimaxAI();
        }
        if(mMode == GameMode.CVC)
        {
            mComputer1 = new MinimaxAI();
            mComputer2 = new MinimaxAI();
        }

        mBoard = new MinimaxAI.GameBoard();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN && (mMode == GameMode.PVP || mMode == GameMode.PVC))
        {
            for (int i = 0; i < mSurfaces.size(); i++)
            {
                if(view.getId() == mSurfaces.get(i).getId())
                {
                    mSurfaces.get(i).DrawingWhat(DrawWhat.X);
                    mSurfaces.get(i).invalidate();
                    if(i == 0)
                        mBoard._board[0][0] = DrawWhat.X;
                    else if(i == 1)
                        mBoard._board[0][1] = DrawWhat.X;
                    else if(i == 2)
                        mBoard._board[0][2] = DrawWhat.X;
                    else if(i == 3)
                        mBoard._board[1][0] = DrawWhat.X;
                    else if(i == 4)
                        mBoard._board[1][1] = DrawWhat.X;
                    else if(i == 5)
                        mBoard._board[1][2] = DrawWhat.X;
                    else if(i == 6)
                        mBoard._board[2][0] = DrawWhat.X;
                    else if(i == 7)
                        mBoard._board[2][1] = DrawWhat.X;
                    else if(i == 8)
                        mBoard._board[2][2] = DrawWhat.X;


                }
            }
            if(mBoard.IsWinner(DrawWhat.X))
            {
                Intent i = new Intent(this, WinnerScreen.class);
                Winner win = Winner.X;
                i.putExtra("Win", win.ordinal());
                startActivity(i);
                finish();
            }
            if(!mBoard.isTerminal())
            {
                MinimaxAI.ComputerMove move = mComputer1.Run(DrawWhat.O, mBoard, mDiff);
                if (move.mRow == 0 && move.mCollumn == 0) {
                    mSurfaces.get(0).DrawingWhat(DrawWhat.O);
                    mSurfaces.get(0).invalidate();
                    mBoard._board[0][0] = DrawWhat.O;
                }
                if (move.mRow == 0 && move.mCollumn == 1) {
                    mSurfaces.get(1).DrawingWhat(DrawWhat.O);
                    mSurfaces.get(1).invalidate();
                    mBoard._board[0][1] = DrawWhat.O;
                }
                if (move.mRow == 0 && move.mCollumn == 2) {
                    mSurfaces.get(2).DrawingWhat(DrawWhat.O);
                    mSurfaces.get(2).invalidate();
                    mBoard._board[0][2] = DrawWhat.O;
                }
                if (move.mRow == 1 && move.mCollumn == 0) {
                    mSurfaces.get(3).DrawingWhat(DrawWhat.O);
                    mSurfaces.get(3).invalidate();
                    mBoard._board[1][0] = DrawWhat.O;
                }
                if (move.mRow == 1 && move.mCollumn == 1) {
                    mSurfaces.get(4).DrawingWhat(DrawWhat.O);
                    mSurfaces.get(4).invalidate();
                    mBoard._board[1][1] = DrawWhat.O;
                }
                if (move.mRow == 1 && move.mCollumn == 2) {
                    mSurfaces.get(5).DrawingWhat(DrawWhat.O);
                    mSurfaces.get(5).invalidate();
                    mBoard._board[1][2] = DrawWhat.O;
                }
                if (move.mRow == 2 && move.mCollumn == 0) {
                    mSurfaces.get(6).DrawingWhat(DrawWhat.O);
                    mSurfaces.get(6).invalidate();
                    mBoard._board[2][0] = DrawWhat.O;
                }
                if (move.mRow == 2 && move.mCollumn == 1) {
                    mSurfaces.get(7).DrawingWhat(DrawWhat.O);
                    mSurfaces.get(7).invalidate();
                    mBoard._board[2][1] = DrawWhat.O;
                }
                if (move.mRow == 2 && move.mCollumn == 2) {
                    mSurfaces.get(8).DrawingWhat(DrawWhat.O);
                    mSurfaces.get(8).invalidate();
                    mBoard._board[2][2] = DrawWhat.O;
                }
            }
        }
        else if(mBoard.IsWinner(DrawWhat.O))
        {
            Intent i = new Intent(this, WinnerScreen.class);
            Winner win = Winner.O;
            i.putExtra("Win", win.ordinal());
            startActivity(i);
            finish();
        }
        else if(mBoard.isTerminal())
        {
            Intent i = new Intent(this, WinnerScreen.class);
            Winner win = Winner.DRAW;
            i.putExtra("Win", win.ordinal());
            startActivity(i);
            finish();
        }
        return false;
    }

}
