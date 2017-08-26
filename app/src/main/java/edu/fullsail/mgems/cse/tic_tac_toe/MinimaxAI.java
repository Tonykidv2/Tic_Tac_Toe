package edu.fullsail.mgems.cse.tic_tac_toe;

import java.util.ArrayList;

/**
 * Created by TheNinjaFS1 on 8/25/17.
 */

public class MinimaxAI {

    public class ComputerMove
    {
        int mRow;
        int mCollumn;
        int mRank;

        ComputerMove(int _row, int _col)
        {
            mRow = _row;
            mCollumn = _col;
            mRank = 0;
        }
    }

    public class GameBoard
    {
        GameActivity.DrawWhat[][] _board = new GameActivity.DrawWhat[3][3];


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
                        return true;
                }
            return false;
        }

        public boolean IsWinner(GameActivity.DrawWhat _who)
        {
            if(_board[0][0].equals(_board[0][1]) && _board[0][2].equals(_who))
                return true;
            if(_board[1][0].equals(_board[1][1]) && _board[1][2].equals(_who))
                return true;
            if(_board[2][0].equals(_board[2][1]) && _board[2][2].equals(_who))
                return true;
            if(_board[0][0].equals(_board[1][0]) && _board[2][0].equals(_who))
                return true;
            if(_board[0][1].equals(_board[1][1]) && _board[2][1].equals(_who))
                return true;
            if(_board[0][2].equals(_board[1][2]) && _board[2][2].equals(_who))
                return true;
            if(_board[0][0].equals(_board[1][1]) && _board[2][2].equals(_who))
                return true;
            if(_board[0][2].equals(_board[1][1]) && _board[2][0].equals(_who))
                return true;
            return false;
        }
    }

    public ComputerMove Run(GameActivity.DrawWhat _whosTurn, GameBoard _gameBoard, int _depth)
    {
        ComputerMove nextMove = GetBestMove(_whosTurn, _gameBoard, _depth);

        return nextMove;
    }

    private ComputerMove GetBestMove(GameActivity.DrawWhat _whosTurn, GameBoard _gameBoard, int _depth)
    {
        ComputerMove BestMove = null;
        ArrayList<ComputerMove> Moves = new ArrayList<ComputerMove>();
        GameBoard newState = new GameBoard();


        for (int r = 0; r < 3; r++)
            for(int c = 0; c < 3; c++)
            {
                if(_gameBoard.isValidMove(_whosTurn, r,c))
                    Moves.add(new ComputerMove(r,c));
            }

        for (int i =0; i < Moves.size(); i++)
        {
            newState.Copy(_gameBoard);
            newState.MakeMove(_whosTurn, Moves.get(i).mRow, Moves.get(i).mCollumn);

            if(_gameBoard.isTerminal() || _depth == 0)
                Moves.get(i).mRank = Evaluate(newState);

            else
                Moves.get(i).mRank = GetBestMove(GetNextPlayer(_whosTurn), newState, _depth - 1).mRank;

            //Player 1 Value 1
            if(_whosTurn == GameActivity.DrawWhat.X)
                if(BestMove == null || Moves.get(i).mRank > BestMove.mRank)
                    BestMove = Moves.get(i);

            //Player 2 Value -1
            else
                if (BestMove == null || Moves.get(i).mRank < BestMove.mRank)
                    BestMove = Moves.get(i);

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
            for (int c = 0; c < 3; c++)
            {
                if(_board._board[r][c] == GameActivity.DrawWhat.X)
                    NumX++;
                else if(_board._board[r][c] == GameActivity.DrawWhat.O)
                    NumO++;
                else if(_board._board[r][c] == GameActivity.DrawWhat.NONE)
                    NumNone++;
            }

        Score = NumX + (NumO*-1);

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
