package edu.fullsail.mgems.cse.tic_tac_toe;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by TheNinjaFS1 on 8/25/17.
 */

public class MinimaxAI {


    public class ComputerMove
    {
        int mRow;
        int mCollumn;
        int mRank;
        boolean JustCreated;

        ComputerMove(int _row, int _col)
        {
            mRow = _row;
            mCollumn = _col;
            mRank = 0;
            JustCreated = true;
        }
    }

    public static class GameBoard
    {

        GameActivity.DrawWhat[][] _board = new GameActivity.DrawWhat[3][3];

        GameBoard()
        {
            for (int r = 0; r < 3; r++)
                for(int c = 0; c < 3; c++)
                {
                    _board[r][c] = GameActivity.DrawWhat.NONE;
                }
        }

        boolean isValidMove(GameActivity.DrawWhat _whosTurn, int _row, int _collumn)
        {
            if(_board[_row][_collumn] == GameActivity.DrawWhat.NONE)
                return true;

            return false;
        }

        public void Copy(GameBoard _copythis)
        {
            for (int r = 0; r < 3; r++)
                for(int c = 0; c < 3; c++)
                {
                    if(_copythis._board[r][c] == GameActivity.DrawWhat.X)
                        _board[r][c] = GameActivity.DrawWhat.X;
                    else if(_copythis._board[r][c] == GameActivity.DrawWhat.O)
                        _board[r][c] = GameActivity.DrawWhat.O;
                    else
                        _board[r][c] = GameActivity.DrawWhat.NONE;
                }
        }

        public void MakeMove(GameActivity.DrawWhat _whosTurn, int _row, int _collumn)
        {
            _board[_row][_collumn] = _whosTurn;
        }

        public boolean isTerminal()
        {

            for (int r = 0; r < 3; r++)
                for(int c = 0; c < 3; c++)
                {
                    if(_board[r][c] == GameActivity.DrawWhat.NONE)
                        return false;
                }
            return true;
        }

        public boolean IsWinner(GameActivity.DrawWhat _who)
        {
            if(_board[0][0] == _who && _board[0][1] == _who && _board[0][2] == _who)
                return true;
            if(_board[1][0] == _who && _board[1][1] == _who && _board[1][2] == _who)
                return true;
            if(_board[2][0] == _who && _board[2][1] == _who && _board[2][2] == _who)
                return true;
            if(_board[0][0] == _who && _board[1][0] == _who && _board[2][0] == _who)
                return true;
            if(_board[0][1] == _who && _board[1][1] == _who && _board[2][1] == _who)
                return true;
            if(_board[0][2] == _who && _board[1][2] == _who && _board[2][2] == _who)
                return true;
            if(_board[0][0] == _who && _board[1][1] == _who && _board[2][2] == _who)
                return true;
            if(_board[0][2] == _who && _board[1][1] == _who && _board[2][0] == _who)
                return true;
            return false;
        }

        public boolean isDraw()
        {
            if(isTerminal())
                if(!IsWinner(GameActivity.DrawWhat.X))
                    if(!IsWinner(GameActivity.DrawWhat.O))
                        return true;

            return false;
        }

        public boolean isEmpty()
        {
            for (int r = 0; r < 3; r++)
                for (int c = 0; c < 3; c++)
                    if(_board[r][c] != GameActivity.DrawWhat.NONE)
                        return false;

            return true;
        }
    }

    public ComputerMove Run(GameActivity.DrawWhat _whosTurn, GameBoard _gameBoard, GameActivity.Difficulty _diff)
    {

        ComputerMove nextMove;
        if(_gameBoard.isEmpty())
        {
            Random ran = new Random();
            while (true)
            {
                int r = ran.nextInt(3);
                int c = ran.nextInt(3);
                if(_gameBoard.isValidMove(_whosTurn,r,c))
                {
                    nextMove = new ComputerMove(r, c);
                    return nextMove;
                }
            }
        }
        if(_diff == GameActivity.Difficulty.MEDIUM)
            nextMove = GetBestMove(_whosTurn, _gameBoard, 1);
        else if(_diff == GameActivity.Difficulty.HARD)
            nextMove = GetBestMove(_whosTurn, _gameBoard, 2);
        else
        {
           Random ran = new Random();
           while (true)
           {
               int r = ran.nextInt(3);
               int c = ran.nextInt(3);
               if(_gameBoard.isValidMove(_whosTurn,r,c))
               {
                   nextMove = new ComputerMove(r, c);
                   break;
               }
           }
        }
        return nextMove;
    }

    private ComputerMove GetBestMove(GameActivity.DrawWhat whosTurn, GameBoard _gameBoard, int _depth)
    {
        ComputerMove BestMove = null;
        ArrayList<ComputerMove> Moves = new ArrayList<ComputerMove>();
        GameBoard newState = new GameBoard();


        for (int r = 0; r < 3; r++)
        {
            for (int c = 0; c < 3; c++)
            {
                if (_gameBoard.isValidMove(whosTurn, r, c))
                    Moves.add(new ComputerMove(r, c));
            }
        }
        int i = 0;
        try
        {
            for ( ;i < Moves.size(); i++)
            {
                newState.Copy(_gameBoard);
                newState.MakeMove(whosTurn, Moves.get(i).mRow, Moves.get(i).mCollumn);

                if (/*_gameBoard.isTerminal() ||*/ newState.IsWinner(whosTurn) || newState.isDraw() || _depth == 0)
                {
                    Moves.get(i).mRank = Evaluate(newState);
                }
                else
                {
                    ComputerMove move =  GetBestMove(GetNextPlayer(whosTurn), newState, _depth - 1);
                    if(move == null)
                        return null;
                    Moves.get(i).mRank = move.mRank;
                }

                //Player 1 Value 1
                if (whosTurn == GameActivity.DrawWhat.X)
                {
                    if (BestMove == null || Moves.get(i).mRank > BestMove.mRank)
                    {
                        BestMove = Moves.get(i);
                    }
                }
                //Player 2 Value -1
                else if (BestMove == null || Moves.get(i).mRank < BestMove.mRank)
                {
                    BestMove = Moves.get(i);
                }

            }
        }
        catch (Exception e)
        {
          System.out.println("Error looping at depth" + " " + _depth + " " + " At Loop " + i);
        }
        return BestMove;
    }

    private int Evaluate(GameBoard _board)
    {
        int Score = 0;
        int NumX = 0;
        int NumO = 0;
        int NumNone = 0;

        for (int r = 0; r < 3; r++)
        {
            for (int c = 0; c < 3; c++)
            {
                if (_board._board[r][c] == GameActivity.DrawWhat.X)
                    NumX++;
                else if (_board._board[r][c] == GameActivity.DrawWhat.O)
                    NumO++;
                else if (_board._board[r][c] == GameActivity.DrawWhat.NONE)
                    NumNone++;
            }
        }
        Score += NumX + (-NumO);

        //Checking to see if there is a winner at this moment
        if(_board.IsWinner(GameActivity.DrawWhat.X))
            Score += NumNone * 100000;
        else if(_board.IsWinner(GameActivity.DrawWhat.O))
            Score += NumNone * -100000;

        return Score;
    }

    private GameActivity.DrawWhat GetNextPlayer(GameActivity.DrawWhat _player)
    {
        if(_player == GameActivity.DrawWhat.X)
            return GameActivity.DrawWhat.O;
        else
            return GameActivity.DrawWhat.X;
    }
}
