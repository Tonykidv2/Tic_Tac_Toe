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
    DrawWhat whosTurn = DrawWhat.X;
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
        if(mMode == GameMode.CVC)
        {
            CVCLogic();
            return false;
        }

        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN &&  mMode == GameMode.PVC)
        {
            PVCLogic(view);
        }

        if(mBoard.IsWinner(DrawWhat.O))
        {
            Intent i = new Intent(this, WinnerScreen.class);
            Winner win = Winner.O;
            i.putExtra("Win", win.ordinal());
            startActivity(i);
            finish();
        }
        if(mBoard.isDraw())
        {
            Intent i = new Intent(this, WinnerScreen.class);
            Winner win = Winner.DRAW;
            i.putExtra("Win", win.ordinal());
            startActivity(i);
            finish();
        }
        return false;
    }

    private void PVCLogic(View view)
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
        if(!mBoard.isDraw())
        {
            MinimaxAI.ComputerMove move = mComputer1.Run(DrawWhat.O, mBoard, mDiff);
            PlaceMove(move);
        }
    }

    private void CVCLogic()
    {
        if(!mBoard.isDraw() && !mBoard.IsWinner(DrawWhat.X) && !mBoard.IsWinner(DrawWhat.O))
        {
            MinimaxAI.ComputerMove move = mComputer1.Run(whosTurn, mBoard, mDiff);
            PlaceMove(move, whosTurn);

            if(whosTurn == DrawWhat.X)
                whosTurn = DrawWhat.O;
            else
                whosTurn = DrawWhat.X;

        }

        if(mBoard.IsWinner(DrawWhat.O))
        {
            Intent i = new Intent(this, WinnerScreen.class);
            Winner win = Winner.O;
            i.putExtra("Win", win.ordinal());
            startActivity(i);
            finish();
        }
        else if(mBoard.IsWinner(DrawWhat.X))
        {
            Intent i = new Intent(this, WinnerScreen.class);
            Winner win = Winner.X;
            i.putExtra("Win", win.ordinal());
            startActivity(i);
            finish();
        }
        else if(mBoard.isDraw())
        {
            Intent i = new Intent(this, WinnerScreen.class);
            Winner win = Winner.DRAW;
            i.putExtra("Win", win.ordinal());
            startActivity(i);
            finish();
        }
    }

    void PlaceMove(MinimaxAI.ComputerMove move)
    {
        if(move != null) {
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

    void PlaceMove(MinimaxAI.ComputerMove move, DrawWhat _who)
    {
        if(move != null) {
            if (move.mRow == 0 && move.mCollumn == 0) {
                mSurfaces.get(0).DrawingWhat(_who);
                mSurfaces.get(0).invalidate();
                mBoard._board[0][0] = _who;
            }
            if (move.mRow == 0 && move.mCollumn == 1) {
                mSurfaces.get(1).DrawingWhat(_who);
                mSurfaces.get(1).invalidate();
                mBoard._board[0][1] = _who;
            }
            if (move.mRow == 0 && move.mCollumn == 2) {
                mSurfaces.get(2).DrawingWhat(_who);
                mSurfaces.get(2).invalidate();
                mBoard._board[0][2] = _who;
            }
            if (move.mRow == 1 && move.mCollumn == 0) {
                mSurfaces.get(3).DrawingWhat(_who);
                mSurfaces.get(3).invalidate();
                mBoard._board[1][0] = _who;
            }
            if (move.mRow == 1 && move.mCollumn == 1) {
                mSurfaces.get(4).DrawingWhat(_who);
                mSurfaces.get(4).invalidate();
                mBoard._board[1][1] = _who;
            }
            if (move.mRow == 1 && move.mCollumn == 2) {
                mSurfaces.get(5).DrawingWhat(_who);
                mSurfaces.get(5).invalidate();
                mBoard._board[1][2] = _who;
            }
            if (move.mRow == 2 && move.mCollumn == 0) {
                mSurfaces.get(6).DrawingWhat(_who);
                mSurfaces.get(6).invalidate();
                mBoard._board[2][0] = _who;
            }
            if (move.mRow == 2 && move.mCollumn == 1) {
                mSurfaces.get(7).DrawingWhat(_who);
                mSurfaces.get(7).invalidate();
                mBoard._board[2][1] = _who;
            }
            if (move.mRow == 2 && move.mCollumn == 2) {
                mSurfaces.get(8).DrawingWhat(_who);
                mSurfaces.get(8).invalidate();
                mBoard._board[2][2] = _who;
            }
        }
    }
}
