
class ConwayGameOfLife{
    private int rows = 10;
    private int cols = 50;
    boolean[][] board;
    String[][] displayBoard;
    boolean playing = true;
    ConwayGameOfLife(){
        board = new boolean[rows][cols];
        displayBoard = new String[rows][cols];
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                if (r == 0 || c == 0 || r == rows -1 || c == cols -1){
                    board[r][c] = false;
                    displayBoard[r][c] = "#";
                } else {
                    board[r][c] = true;
                    displayBoard[r][c] = ".";
                }
            }
        }
        gameLoop();
    }
    
    private void gameLoop(){
        //while (playing == true){
            try{Thread.sleep(500);}catch(Exception e){System.out.println("n");}
            //update board
            // reset terminal
            //print board
        //}
    }

    private void updateBoard(){
        for (int r = 1; r < rows - 1; r++){
            for (int c = 0; c < cols - 1; c++){
                if ( board[r][c] == )
            }
        }
    }


    public String toString(){
        String s = "";
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                s += displayBoard[r][c];
            }
            s += "\n";
        }
        return s;
    }

    public static void main (String[] args){
        ConwayGameOfLife c = new ConwayGameOfLife();
        System.out.println(c);
    }
}