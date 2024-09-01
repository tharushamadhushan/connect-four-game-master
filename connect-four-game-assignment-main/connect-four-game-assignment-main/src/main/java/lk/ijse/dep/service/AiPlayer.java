
package lk.ijse.dep.service;

import java.util.Random;

public class AiPlayer extends Player {

    public AiPlayer(BoardImpl newBoard) {
        super(newBoard);
    }

    @Override
    public void movePiece(int col) {
//        int a = setPiece();
//        col = a;
//
//        if (this.board.isLegalMove(col)) {
//            this.board.updateMove(col,Piece.GREEN);
        int maxEval=(int)Double.NEGATIVE_INFINITY;
        int row=0;
        for (int tempCol=0;tempCol<board.NUM_OF_COLS;tempCol++){
            if(board.isLegalMove(tempCol)){
                int tempRow=board.findNextAvailableSpot(tempCol);
                board.updateMove(tempCol,tempRow,Piece.GREEN);
                int heuristicVal=minimax(0,false);
                board.updateMove(tempCol,tempRow,Piece.EMPTY);
                if(maxEval<heuristicVal){
                    maxEval=heuristicVal;
                    row=tempRow;
                    col=tempCol;
                }
            }
        }
        this.board.updateMove(col,row,Piece.GREEN);
            this.board.getBoardUI().update(col, false);
            Winner winner = this.board.findWinner();

            if (winner == null) {
                boolean b = this.board.exitLegalMoves();
                if (!b) {
                    this.board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
                }
            } else {
                this.board.getBoardUI().notifyWinner(winner);
            }
        }
    public int setPiece(){
        Piece[][] pieces = board.getPieces();
        int num=0;
        for (int c = 0; c < 6; c++) {
            for(int r=0;r<5;r++){
                if (pieces[c][0]==Piece.BLUE && pieces[c][1]==Piece.BLUE && pieces[c][2]==Piece.BLUE && pieces[c][3] == Piece.EMPTY ) {
                    return c;
                }if (pieces[c][0]==Piece.GREEN && pieces[c][1]==Piece.GREEN  && pieces[c][2]==Piece.GREEN && pieces[c][3] == Piece.EMPTY ) {
                    return c;
                }else if (pieces[0][r]==Piece.BLUE && pieces[1][r]==Piece.BLUE  &&  pieces[2][r] == Piece.EMPTY ) {
                    return 2;
                } else if (pieces[3][r]==Piece.BLUE && pieces[4][r]==Piece.BLUE  &&  pieces[5][r] == Piece.EMPTY ) {
                    return 5;
                }else if (pieces[0][r]==Piece.GREEN && pieces[1][r]==Piece.GREEN  &&  pieces[2][r] == Piece.EMPTY ) {
                    return 2;
                } else if (pieces[3][r]==Piece.GREEN && pieces[4][r]==Piece.GREEN  &&  pieces[5][r] == Piece.EMPTY ) {
                    return 5;
                } else{
                    do {
                        Random random = new Random();
                        num = random.nextInt(6);
                    } while (!this.board.isLegalMove(num));
                }
            }
        }
        return num;
    }
    private int minimax(int depth,boolean maximizingPlayer){
        Winner winner=board.findWinner();
        if(depth==4||winner!=null) {
            if (winner == null) {
                return 0;
            }
            if (winner.getWinningPiece() == Piece.GREEN) {
                return 1;
            }
            if (winner.getWinningPiece() == Piece.BLUE) {
                return -1;
            }
        }
        if(maximizingPlayer){
            int maxEval= (int) Double.NEGATIVE_INFINITY;
            for (int i=0;i<board.NUM_OF_COLS;i++) {
                if (board.isLegalMove(i)) {
                    int row = board.findNextAvailableSpot(i);
                    board.updateMove(i,row,Piece.GREEN);
                    int heuristicVal = minimax(depth + 1, false);
                    board.updateMove(i,row,Piece.EMPTY);
                    maxEval = Math.max(heuristicVal, maxEval);
                }
            }
        return maxEval;
        }else{
            int minEval=(int)Double.POSITIVE_INFINITY;
            for (int i=0;i<board.NUM_OF_COLS;i++) {
                if (board.isLegalMove(i)) {
                    int row=board.findNextAvailableSpot(i);
                    board.updateMove(i,row,Piece.BLUE);
                    int heuristicVal = minimax(depth + 1, true);
                    board.updateMove(i,row,Piece.EMPTY);
                    minEval = Math.min(heuristicVal, minEval);
                }
            }
            return minEval;
        }
    }
}