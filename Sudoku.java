import java.util.ArrayList;
import java.util.Random;

import java.lang.Math;
class Sudoku {

    //--------------------------------------------------
    //board/grid things
    private final int N = 9;

    private int[][] board; //<--used for constructor 
    private boolean[][] boolBoard;
    private ArrayList< ArrayList<Integer> > gridS;
    private ArrayList<ArrayList<ArrayList<Integer>>> gridSPre;
    private boolean verbose = false;
    
    public enum GameMode { normalGame, fourGame, hyperGame };
    private GameMode mode;
    
    
    private int boardLength = 9;
    //--------------------------------------------------


    //--------------------------------------------------
    Sudoku( int[][] b, int width, int height) {
        gridS = new ArrayList< ArrayList<Integer> >(N);//initialize array list
        //gridSPre = new ArrayList< ArrayList< ArrayList<Integer> > >();
        for (int i = 0; i < N; i++){
            gridS.add( new ArrayList<Integer>() ); //initialize inside lists
            for (int j = 0; j < N; j++){
                Integer n = b[j][i];
                gridS.get(i).add(n);
            }
        }
    }
    //--------------------------------------------------
    Sudoku(){
        gridS = new ArrayList< ArrayList<Integer> >(boardLength);//initialize array list
        for (int i = 0; i < boardLength; i++){
            gridS.add( new ArrayList<Integer>() ); //initialize inside lists
            for (int j = 0; j < boardLength; j++){
                Integer n = 0;
                gridS.get(i).add(n);
            }
        }
        generate(81, boardLength);
    }
    //--------------------------------------------------
    Sudoku( int len, int amt, GameMode mode ){
        boolBoard = new boolean[len][len];
        this.mode = mode;
        gridS = new ArrayList< ArrayList<Integer> >(len);//initialize array list
        for (int i = 0; i < len; i++){
            gridS.add( new ArrayList<Integer>() ); //initialize inside lists
            for (int j = 0; j < len; j++){
                Integer n = 0;
                gridS.get(i).add(n);
            }
        }
        generate(amt, len);
        for (int i = 0; i < len; i++){
            for (int j = 0; j < len; j++){
                if ((int)gridS.get(i).get(j) == 0){
                    boolBoard[i][j] = false;
                } else {
                    boolBoard[i][j] = true;
                }
            }
        }
    }   

    //--------------------------------------------------
    boolean boxSolveOK ( int r, int c, ArrayList< ArrayList< Integer >> s, int len ) { 
        int L = (int)Math.sqrt(len); 
        int[][] n = new int[L][L]; 
        boolean[][] b = new boolean[L][L];
        for (int i = r; i < r + L; i++){
            for (int j = c; j < c + L; j++){
                //------
                for (int ii = 0; ii < L; ii++){
                    for (int jj = 0; jj < L; jj++){
                        n[ii][jj] = s.get(r + jj).get(c + ii);
                    }
                }
                //------
                if (verbose)
                    System.out.println("s(" + i + "" + j + ") " + s.get(i).get(j) );
                for (int ii = 0; ii < L; ii++){
                    for (int jj = 0; jj < L; jj++){
                        if (s.get(i).get(j) == n[ii][jj] && b[ii][jj] && (s.get(i).get(j) > 0 && s.get(i).get(j) < 10) ){
                            return false;
                        } else if (s.get(i).get(j) == n[ii][jj]){
                            b[ii][jj] = true;
                        }
                        if (verbose)
                            System.out.print(" n(" + ii + "" + jj + ") " + n[ii][jj] + " b: " + b[ii][jj]);
                    }
                    if (verbose)
                        System.out.println();
                }

            }
        }
        return true; 
    }
    //--------------------------------------------------
    
    
    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
    //                  IS CORRECT?
    //------------------------------------------------------------------------
    //------------------------------------------------------------------------

    //--------------------------------------------------
    boolean hasEmptySpot ( ArrayList< ArrayList< Integer >> s, int len){
        for (int i = 0; i < len; i++){
            for (int ii = 0; ii < len; ii++){
                if (s.get(i).get(ii) < 1 || s.get(i).get(ii) > 9){ //value out of bounds denoting empty 
                    return true;
                }
            }
        }
        return false;
    }
    //--------------------------------------------------

    //complete; no changes necessary
    boolean isCorrect ( ArrayList< ArrayList< Integer >> s, int len ) {
        return !hasEmptySpot( s, len )
            && allRowsOK( s, len )
            && allColsOK( s, len )
            && boxOK( 0, 0, s, len ) && boxOK( 0, 3, s, len ) && boxOK( 0, 6, s, len )
            && boxOK( 3, 0, s, len ) && boxOK( 3, 3, s, len ) && boxOK( 3, 6, s, len )
            && boxOK( 6, 0, s, len ) && boxOK( 6, 3, s, len ) && boxOK( 6, 6, s, len );
    }
    //--------------------------------------------------
    
    //--------------------------------------------------
    boolean isCorrectX( ArrayList< ArrayList< Integer >> s, int len ){
        return !hasEmptySpot( s, len )
            && allRowsOK( s, len )
            && allColsOK( s, len )
            && boxOK( 0, 0, s, len ) && boxOK( 0, 3, s, len ) && boxOK( 0, 6, s, len )
            && boxOK( 3, 0, s, len ) && boxOK( 3, 3, s, len ) && boxOK( 3, 6, s, len )
            && boxOK( 6, 0, s, len ) && boxOK( 6, 3, s, len ) && boxOK( 6, 6, s, len ) && xOK(s, len);
    }
    //--------------------------------------------------
    
    //--------------------------------------------------
    boolean isCorrectHyper( ArrayList< ArrayList< Integer >> s, int len ){
        return !hasEmptySpot( s, len )
            && allRowsOK( s, len )
            && allColsOK( s, len )
            && boxOK( 0, 0, s, len ) && boxOK( 0, 3, s, len ) && boxOK( 0, 6, s, len )
            && boxOK( 3, 0, s, len ) && boxOK( 3, 3, s, len ) && boxOK( 3, 6, s, len )
            && boxOK( 6, 0, s, len ) && boxOK( 6, 3, s, len ) && boxOK( 6, 6, s, len ) 
            && boxOK( 1, 1, s, len ) && boxOK( 5, 1, s, len ) && boxOK( 1, 5, s, len ) && boxOK( 5, 5, s, len );
    }
    //--------------------------------------------------

    //--------------------------------------------------
    boolean isCorrectFour( ArrayList< ArrayList< Integer >> s, int len ){
        return !hasEmptySpot( s, len )
            && allRowsOK( s, len )
            && allColsOK( s, len )
            && boxOK( 0, 0, s, len ) && boxOK( 0, 2, s, len ) && boxOK( 2, 0, s, len )
            && boxOK( 2, 2, s, len );
    }
    //--------------------------------------------------

    //--------------------------------------------------
    boolean isCorrectSixteen( ArrayList< ArrayList< Integer >> s, int len ){
        return !hasEmptySpot( s, len )
            && allRowsOK( s, len )
            && allColsOK( s, len )
            && boxOK( 0, 0, s, len ) && boxOK( 0, 4, s, len ) && boxOK( 0, 8, s, len ) && boxOK( 0, 12, s, len)
            && boxOK( 4, 0, s, len ) && boxOK( 4, 4, s, len ) && boxOK( 4, 8, s, len ) && boxOK( 4, 12, s, len)
            && boxOK( 8, 0, s, len ) && boxOK( 8, 4, s, len ) && boxOK( 8, 8, s, len ) && boxOK( 8, 12, s, len)
            && boxOK( 12, 0, s, len ) && boxOK( 12, 4, s, len ) && boxOK( 12, 8, s, len ) && boxOK( 12, 12, s, len);
    }
    //--------------------------------------------------

    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
    //                  IS CORRECT?
    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
    

    //----------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------
    //                  All ROWS, COL, X, BOX, OK?
    //----------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------

    //--------------------------------------------------
    boolean allRowsOK ( ArrayList< ArrayList< Integer >> s, int len ) {
        int[] n = new int[len]; 
        boolean[] f = new boolean[len];
        //go through each row
        for (int i = 0; i < len; i++){
            //go through each element of n and f and 
            //copy the row into n and set f to false
            for (int z = 0; z < len; z++){
                n[z] = s.get(i).get(z);
                f[z] = false;
                if (verbose)
                    System.out.print(n[z]);
            }
            if (verbose)
                System.out.println( " all rows");
            //go theough each elemnt of this row
            for (int j = 0; j < len; j++){
                if (verbose)
                    System.out.println("s: " + s.get(i).get(j) );
                for (int ii = 0; ii < len; ii++){
                    if (s.get(i).get(j) == n[ii] && f[ii] ){ 
                        if (verbose)
                            System.out.println(false);
                        return false;
                    } else if ( s.get(i).get(j) == n[ii] ){
                        f[ii] = true;
                    }
                }
            }
        }
        if (verbose)
            System.out.println(true);
        return true;
    }
    //--------------------------------------------------

    //--------------------------------------------------
    //goes through each column of s too see if there are no matching values
    boolean allColsOK ( ArrayList< ArrayList< Integer >> s, int len ) { 
        //new array for the values of each column
        int[] n = new int[len];
        //new array for the bools of each column
        boolean[] f = new boolean[len];

        for (int i = 0; i < len; i++){ //i is the index of the current column
            
            for (int z = 0; z < len; z++){//z is the element of each column 
                n[z] = s.get(z).get(i);//s[z][i]; //copy the current column into the n array
                f[z] = false;   //set all the f array to false
                //print n[z] if verbose is true
                if (verbose)
                    System.out.print(n[z]);
            }

            if (verbose)
                System.out.println( " all cols");
            //go through each 
            for (int j = 0; j < len; j++){ 
                if (verbose)
                    System.out.println("s: " + s.get(j).get(i) );
                for (int ii = 0; ii < len; ii++){
                    if ( s.get(j).get(i) == n[ii] && f[ii] ){
                            if (verbose)
                                System.out.println(false);
                            return false;
                    } else if ( s.get(j).get(i) == n[ii] ){
                        //if the element matches one in the array
                        f[ii] = true;
                    }
                }
            }
        }
        if (verbose)
           System.out.println(true);
        return true;
    }
    //--------------------------------------------------

    boolean xOK( ArrayList< ArrayList< Integer >> s, int len ){
        int[] n1     = new int[len];
        boolean[] b1 = new boolean[len];
        int[] n2     = new int[len];
        boolean[] b2 = new boolean[len];
        for (int i = 0; i < len; i++){
            n1[i] = s.get(i).get(i);
            b1[i] = false;
            n2[i] = s.get( i ).get( len - 1 - i );
            b2[i] = false;
            if (verbose){
                System.out.print( "1:" + s.get(i).get(i) );
                System.out.println( " 2:" + s.get(i).get( len - 1 - i) );
            }
        }

        for (int i = 0; i < len; i++){
            for (int j = 0; j < len; j++){
                if ( !(s.get(i).get(i) == 0) ){ //if it isnt an empty space
                    if ( s.get(i).get(i) == n1[j] ){ //if it is equal to a number in the row
                        if (b1[j] == true) { //if weve already seen it
                            return false;
                        } else {
                            b1[j] = true;
                        }
                    }
                }
                if ( !(s.get(i).get( len - 1 - i) == 0) ){
                    if ( s.get(i).get( len - 1 - i) == n2[j] ){
                        if (b2[j] == true){
                            return false;
                        } else {
                            b2[j] = true;
                        }
                    }
                }
            }
        }
        return true;
    }

    //--------------------------------------------------
    boolean boxOK ( int r, int c, ArrayList< ArrayList< Integer >> s, int len ) {  
        int L = (int)Math.sqrt(len);
        int[][] n = new int[L][L]; 
        boolean[][] b = new boolean[L][L];
        for (int i = r; i < r + L; i++){
            for (int j = c; j < c + L; j++){
                //------
                for (int ii = 0; ii < L; ii++){
                    for (int jj = 0; jj < L; jj++){
                        n[ii][jj] = s.get(r + jj).get(c + ii);

                        if (verbose){
                            System.out.print( " n(" + ii + "0) " + n[ii][jj] + " b: " + b[ii][0]);
                        }
                    }
                }
                //------
                if (verbose){
                    System.out.println("s(" + i + "" + j + ") " + s.get(i).get(j));
                }
                for (int ii = 0; ii < L; ii++){
                    for (int jj = 0; jj < L; jj++){
                        if (s.get(i).get(j) == n[ii][jj] && b[ii][jj]){
                            return false;
                        } else if (s.get(i).get(j) == n[ii][jj]){
                            b[ii][jj] = true;
                        }
                        if (verbose){
                            System.out.print(" n(" + ii + "" + jj + ") " + n[ii][jj] + " b: " + b[ii][jj]);
                        }
                    }
                    if (verbose){
                        System.out.println();
                    }
                }

            }
        }
        return true; 
    }
    //--------------------------------------------------

    //----------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------
    //                                   All ROWS, COL, X, BOX, OK?
    //----------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------
    
    //----------------------------------------------------------------------------
    //----------------------------------------------------------------------------
    //             SOLVE FUNCTIONS NORMAL (9X9, 4X4), XGame, HYPER
    //----------------------------------------------------------------------------
    //----------------------------------------------------------------------------

    //-------------------NORMAL--------------------------
    boolean solve( ArrayList< ArrayList<Integer> > g, int row, int col, int len ){
        if (row == len - 1 && col == len){
            return true;
        }
        
        if (col == len){
            row++;
            col = 0;
        }
        if ( g.get(row).get(col) != 0 ){
            return solve(g, row, col + 1, len);
        }
        if (verbose)
            System.out.print( "row, col: " + g.get(row).get(col) );
        for (int i = 0; i < gridSPre.get(row).get(col).size(); i++){
            if (verbose)
                System.out.println( " 3d: " + gridSPre.get(row).get(col).get(i) );
            if (isLegal(g, row, col, gridSPre.get(row).get(col).get(i), len )){
                g.get(row).set( col, gridSPre.get(row).get(col).get(i) );
                if ( solve(g, row, col + 1, len) ){
                    return true;
                }
            } 
            g.get(row).set( col, 0 ); 
              
        } 
        
        return false;
    }
    //-------------------------------------------------------

    //----------------------X-GAME---------------------------
    boolean solveX( ArrayList< ArrayList<Integer> > g, int row, int col, int len ){
        if (row == len - 1 && col == len){
            return true;
        }
        
        if (col == N){
            row++;
            col = 0;
        }
        if ( g.get(row).get(col) != 0 ){
            return solveX(g, row, col + 1, len);
        }
        if (verbose)
            System.out.print( "row, col: " + g.get(row).get(col) );
        for (int i = 0; i < gridSPre.get(row).get(col).size(); i++){
            if (verbose)
                System.out.println( " 3d: " + gridSPre.get(row).get(col).get(i) );
            if (isLegalX(g, row, col, gridSPre.get(row).get(col).get(i), len )){
                g.get(row).set( col, gridSPre.get(row).get(col).get(i) );
                if ( solveX(g, row, col + 1, len) ){
                    return true;
                }
            } 
            g.get(row).set( col, 0 ); 
              
        } 
        
        return false;
    }
    //-------------------------------------------------------

    //--------------------------HYPER------------------------
    boolean solveHyper( ArrayList< ArrayList<Integer> > g, int row, int col, int len ){
        if (row == len - 1 && col == len){
            return true;
        }
        
        if (col == len){
            row++;
            col = 0;
        }
        if ( g.get(row).get(col) != 0 ){
            return solveHyper(g, row, col + 1, len);
        }
        if (verbose)
            System.out.print( "row, col: " + g.get(row).get(col) );
        for (int i = 0; i < gridSPre.get(row).get(col).size(); i++){
            if (verbose)
                System.out.println( " 3d: " + gridSPre.get(row).get(col).get(i) );
            if (isLegalHyper(g, row, col, gridSPre.get(row).get(col).get(i), len )){
                g.get(row).set( col, gridSPre.get(row).get(col).get(i) );
                if ( solveHyper(g, row, col + 1, len) ){
                    return true;
                }
            } 
            g.get(row).set( col, 0 ); 
              
        } 
        
        return false;
    }
    //-------------------------------------------------------

    //----------------------------------------------------------------------------
    //----------------------------------------------------------------------------
    //             SOLVE FUNCTIONS NORMAL (9X9, 4X4), XGame, HYPER
    //----------------------------------------------------------------------------
    //----------------------------------------------------------------------------

    //-------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------
    //             ISLEGAL FUNCTIONS NORMAL (9X9, 4X4), XGame, HYPER
    //-------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------

    //------------------NORMAL-------------------------
    boolean isLegal( ArrayList< ArrayList<Integer>> g, int r, int c, int n, int len){
        for (int i = 0; i < len; i++){
            //System.out.println(board[i][c] + " " + board[r][i]);
            if (g.get(i).get(c) == n || g.get(r).get(i) == n){
                return false;
            }
        }
        int i = 0; //i for the index in which box we need to check
        int j = 0; //j for the index in which box we need to check

        //figure out what box it is in
        if (GameMode.normalGame == mode){
            if (r < 3 && c < 3){
                i = 0;
                j = 0;
            } else if ( (r < 3) && (c > 2 && c < 6)) {
                i = 0;
                j = 3;
            } else if ( (r < 3) && (c > 5 && c < 10)) {
                i = 0;
                j = 6;
            } else if ( (r > 2 && r < 6) && (c < 3) ){
                i = 3;
                j = 0;
            } else if ( (r > 2 && r < 6) && (c > 2 && c < 6) ){
                i = 3;
                j = 3;
            } else if ( (r > 2 && r < 6) && (c > 5 && c < 10) ){
                i = 3;
                j = 6;
            } else if ( (r > 5 && r < 10) && (c < 3) ){
                i = 6;
                j = 0;
            } else if ( (r > 5 && r < 10) && (c > 2 && c < 6) ){
                i = 6;
                j = 3;
            } else if ( (r > 5 && r < 10) && (c > 5 && c < 10) ){
                i = 6; 
                j = 6;
            }
        } else if (GameMode.fourGame == mode){
            if ( r < 2 && c < 2){
                i = 0;
                j = 0;
            } else if ( r < 2 && (c > 1)){
                i = 0;
                j = 2;
            } else if ( (r > 2 && r < 4) && c < 2){
                i = 2;
                j = 0;
            } else if ( (r > 2 && r < 4) && (c > 1) ){
                i = 2;
                j = 2;
            }
        } /*else if (sixteenGame){
            if (r < 4 && c < 4){ //1
                i = 0;
                j = 0;
            } else if (r < 4 && (c > 3 && c < 8)){
                i = 0;
                j = 4;
            } else if ( r < 4 && (c > 7 && c < 12)){
                i = 0;
                j = 8;
            } else if ( r < 4 && (c > 11 && c < 16)){
                i = 0;
                j = 12;
            } else if ( (r > 3 && r < 8) && c < 4){ //2
                i = 4; 
                j = 0;
            } else if ( (r > 3 && r < 8) && (c > 3 && c < 8) ){
                i = 4;
                j = 4;
            } else if ( (r > 3 && r < 8) && (c > 7 && c < 12) ){
                i = 4;
                j = 8;
            } else if ( (r > 3 && r < 8) && (c > 11 && c < 16) ){
                i = 4;
                j = 12;
            } else if ( (r > 7 && r < 12) && c < 4){ //3
                i = 8; 
                j = 0;
            } else if ( (r > 7 && r < 12) && (c > 3 && c < 8) ){
                i = 8;
                j = 4;
            } else if ( (r > 7 && r < 12) && (c > 7 && c < 12) ){
                i = 8;
                j = 8;
            } else if ( (r > 7 && r < 12) && (c > 11 && c < 16) ){
                i = 8;
                j = 12;
            } else if ( (r > 11 && r < 16) && c < 4){ //3
                i = 12; 
                j = 0;
            } else if ( (r > 11 && r < 16) && (c > 3 && c < 8) ){
                i = 12;
                j = 4;
            } else if ( (r > 11 && r < 16) && (c > 7 && c < 12) ){
                i = 12;
                j = 8;
            } else if ( (r > 11 && r < 16) && (c > 11 && c < 16) ){
                i = 12;
                j = 12;
            } 
        }*/
        //System.out.println(i + " " + j);
        int p = g.get(r).get(c); //board[r][c]; //place holder for the current number of the board
        g.get(r).set(c, n); //board[r][c] = n; //set the board r,c to n
        if ( boxSolveOK(i, j, g, len) ){ //check if adding n to the box it is in is legal
            g.get(r).set(c, p); //set board r,c back to the place holder p
            return true; //it is a legal n
        } else {
            g.get(r).set(c, p); //set board r,c back to the place holder p
            return false; //not a legal n
        }
        
    }
    //-----------------------------------------------
    
    //------------------XGAME-------------------------
    boolean isLegalX( ArrayList< ArrayList<Integer>> g, int r, int c, int n, int len){
        for (int i = 0; i < len; i++){
            //System.out.println(board[i][c] + " " + board[r][i]);
            if (g.get(i).get(c) == n || g.get(r).get(i) == n){
                return false;
            }
        }
        int i = 0; //i for the index in which box we need to check
        int j = 0; //j for the index in which box we need to check

        //figure out what box it is in
        if (r < 3 && c < 3){
            i = 0;
            j = 0;
        } else if ( (r < 3) && (c > 2 && c < 6)) {
            i = 0;
            j = 3;
        } else if ( (r < 3) && (c > 5 && c < 10)) {
            i = 0;
            j = 6;
        } else if ( (r > 2 && r < 6) && (c < 3) ){
            i = 3;
            j = 0;
        } else if ( (r > 2 && r < 6) && (c > 2 && c < 6) ){
            i = 3;
            j = 3;
        } else if ( (r > 2 && r < 6) && (c > 5 && c < 10) ){
           i = 3;
           j = 6;
        } else if ( (r > 5 && r < 10) && (c < 3) ){
           i = 6;
           j = 0;
        } else if ( (r > 5 && r < 10) && (c > 2 && c < 6) ){
           i = 6;
           j = 3;
        } else if ( (r > 5 && r < 10) && (c > 5 && c < 10) ){
           i = 6; 
           j = 6;
        }
        //System.out.println(i + " " + j);
        int p = g.get(r).get(c); //board[r][c]; //place holder for the current number of the board
        g.get(r).set(c, n); //board[r][c] = n; //set the board r,c to n
        if ( boxSolveOK(i, j, g, len) && xOK(g, len)){ //check if adding n to the box it is in is legal
            g.get(r).set(c, p); //set board r,c back to the place holder p
            return true; //it is a legal n
        } else {
            g.get(r).set(c, p); //set board r,c back to the place holder p
            return false; //not a legal n
        }
    }
    //-----------------------------------------------
    
    //------------------HYPERGAME-------------------------
    boolean isLegalHyper( ArrayList< ArrayList<Integer>> g, int r, int c, int n, int len){
        for (int i = 0; i < len; i++){
            //System.out.println(board[i][c] + " " + board[r][i]);
            if (g.get(i).get(c) == n || g.get(r).get(i) == n){
                return false;
            }
        }
        int i = 0; //i for the index in which box we need to check
        int j = 0; //j for the index in which box we need to check

        //figure out what box it is in
        if (r < 3 && c < 3){
            i = 0;
            j = 0;
        } else if ( (r < 3) && (c > 2 && c < 6)) {
            i = 0;
            j = 3;
        } else if ( (r < 3) && (c > 5 && c < 10)) {
            i = 0;
            j = 6;
        } else if ( (r > 2 && r < 6) && (c < 3) ){
            i = 3;
            j = 0;
        } else if ( (r > 2 && r < 6) && (c > 2 && c < 6) ){
            i = 3;
            j = 3;
        } else if ( (r > 2 && r < 6) && (c > 5 && c < 10) ){
           i = 3;
           j = 6;
        } else if ( (r > 5 && r < 10) && (c < 3) ){
           i = 6;
           j = 0;
        } else if ( (r > 5 && r < 10) && (c > 2 && c < 6) ){
           i = 6;
           j = 3;
        } else if ( (r > 5 && r < 10) && (c > 5 && c < 10) ){
           i = 6; 
           j = 6;
        }

        //System.out.println(i + " " + j);
        int p = g.get(r).get(c); //board[r][c]; //place holder for the current number of the board
        g.get(r).set(c, n); //board[r][c] = n; //set the board r,c to n

        //check if it is in a hyper box
        if ( (r > 0 && r < 4) && (c > 0 && c < 4)/*leftTop*/){ 
            //System.out.println( "(r,c): (" + r + "," + c + ") (1, 1)" );
            if ( !boxSolveOK( 1, 1, g, len) ){
                g.get(r).set(c, p); //set board r,c back to the place holder p
                return false;
            }
        } else if ( (r > 4 && r < 8) && (c > 0 && c < 4) )/*rightTop*/ {
            //System.out.println( "(r,c): (" + r + "," + c + ") (1, 5)" );
            if ( !boxSolveOK( 1, 5, g, len) ){
                g.get(r).set(c, p); //set board r,c back to the place holder p
                return false;
            }
        } else if ( (r > 0 && r < 4) && (c > 4 && c < 8)/*leftBot*/) {
            //System.out.println( "(r,c): (" + r + "," + c + ") (5, 1)" );
            if ( !boxSolveOK( 5, 1, g, len) ){
                g.get(r).set(c, p); //set board r,c back to the place holder p
                return false;
            }
        } else if ( (r > 4 && r < 8) && (c > 4 && c < 8)/*rightBot*/){
            //System.out.println( "(r,c): (" + r + "," + c + ") (5, 5)" );
            if ( !boxSolveOK( 5, 5, g, len) ){
                g.get(r).set(c, p); //set board r,c back to the place holder p
                return false;
            }
        }

        if ( boxSolveOK(i, j, g, len)){ //check if adding n to the box it is in is legal
            g.get(r).set(c, p); //set board r,c back to the place holder p
            return true; //it is a legal n
        } else {
            g.get(r).set(c, p); //set board r,c back to the place holder p
            return false; //not a legal n
        }
        
    }
    //-------------------------------------------------------



    //-------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------
    //             ISLEGAL FUNCTIONS NORMAL (9X9, 4X4), XGame, HYPER
    //-------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //             GENERATE FUNCTIONS NORMAL (9X9, 4X4), XGame, HYPER
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    
    //--------------------NORMAL----------------------
    void generate (int num, int len){//num is amount of the numbers on board
        Random rand = new Random();
        ArrayList< ArrayList<Integer> > newGrid = new ArrayList< ArrayList<Integer>>(len);
        for (int i = 0; i < len; i++){
            newGrid.add( new ArrayList<Integer>() );
            for (int j = 0; j < len; j++){
                newGrid.get(i).add(0);
            }
        }
        ArrayList<Integer> nums1 = new ArrayList(len);
        for (int i = 1; i <= len; i++){
            nums1.add(i);
        }
        ArrayList<Integer> nums2 = new ArrayList<Integer>(nums1);
        ArrayList<Integer> nums3 = new ArrayList<Integer>(nums1);
        ArrayList<Integer> nums4 = new ArrayList<Integer>(nums1);
        if (GameMode.normalGame == mode){
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++){
                    int ii = i + 3;
                    int iii = i + 6;
                    int jj = j + 3;
                    int jjj = j + 6;
                    int ri = rand.nextInt( nums1.size() );
                    newGrid.get(i).set( j, nums1.get(ri) );
                    nums1.remove(ri);

                    ri = rand.nextInt( nums2.size() );
                    newGrid.get(ii).set( jj, nums2.get(ri) );
                    nums2.remove(ri);

                    ri = rand.nextInt( nums3.size() );
                    newGrid.get(iii).set( jjj, nums3.get(ri) );
                    nums3.remove(ri);
                }
            }
        } 
        
        setPreemtiveSet(newGrid, len);
        while ( !solve(newGrid, 0, 0, len) ){
            int n = rand.nextInt(len) + 1;
            int r = rand.nextInt(len);
            int c = rand.nextInt(len);
            if ( isLegal(newGrid, r, c, n, len) ){
                newGrid.get(r).set(c, n);
            }
        }
        
        int count = (len * len) - num;
        while (count > 0){
            int r = rand.nextInt(len);
            int c = rand.nextInt(len);
            if (newGrid.get(r).get(c) != 0){
                //System.out.println("" + newGrid.get(r).get(c));
                newGrid.get(r).set(c, 0);
                //System.out.println("" + newGrid.get(r).get(c));
                count--;
            }
        }
        gridS = newGrid;
    }
    //-------------------------------------------------------
    
    //-------------------XGAME--------------------------
    void generateX (int num, int len){//num is amount of the numbers on board
        Random rand = new Random();
        ArrayList< ArrayList<Integer> > newGrid = new ArrayList< ArrayList<Integer>>(len);
        for (int i = 0; i < len; i++){
            newGrid.add( new ArrayList<Integer>() );
            for (int j = 0; j < len; j++){
                newGrid.get(i).add(0);
            }
        }
        ArrayList<Integer> nums1 = new ArrayList(len);
        for (int i = 1; i < len; i++){
            nums1.add(i);
        }
        ArrayList<Integer> nums2 = new ArrayList<Integer>(nums1);
        ArrayList<Integer> nums3 = new ArrayList<Integer>(nums1);
        
        setPreemtiveSetX(newGrid, len);

        while ( !solveX(newGrid, 0, 0, len) ){
            int n = rand.nextInt(len) + 1;
            int r = rand.nextInt(len);
            int c = rand.nextInt(len);
            if ( isLegalX(newGrid, r, c, n, len) ){
                newGrid.get(r).set(c, n);
            }
        }

        int count = (len * len) - num;
        while (count > 0){
            int r = rand.nextInt(len);
            int c = rand.nextInt(len);
            if (newGrid.get(r).get(c) != 0){
                //System.out.println("" + newGrid.get(r).get(c));
                newGrid.get(r).set(c, 0);
                //System.out.println("" + newGrid.get(r).get(c));
                count--;
            }
        }
        gridS = newGrid;
    }
    //-------------------------------------------------------

    //---------------------HYPER----------------------------
    void generateHyper (int num, int len){//num is amount of the numbers on board
        Random rand = new Random();
        ArrayList< ArrayList<Integer> > newGrid = new ArrayList< ArrayList<Integer>>(len);
        for (int i = 0; i < len; i++){
            newGrid.add( new ArrayList<Integer>() );
            for (int j = 0; j < len; j++){
                newGrid.get(i).add(0);
            }
        }
        ArrayList<Integer> nums1 = new ArrayList(len);
        for (int i = 1; i < len; i++){
            nums1.add(i);
        }
        ArrayList<Integer> nums2 = new ArrayList<Integer>(nums1);
        ArrayList<Integer> nums3 = new ArrayList<Integer>(nums1);
        
        setPreemtiveSetHyper(newGrid, len);

        while ( !solveHyper(newGrid, 0, 0, len) ){
            int n = rand.nextInt(len) + 1;
            int r = rand.nextInt(len);
            int c = rand.nextInt(len);
            if ( isLegalHyper(newGrid, r, c, n, len) ){
                newGrid.get(r).set(c, n);
            }
        }

        int count = (len * len) - num;
        while (count > 0){
            int r = rand.nextInt(len);
            int c = rand.nextInt(len);
            if (newGrid.get(r).get(c) != 0){
                //System.out.println("" + newGrid.get(r).get(c));
                newGrid.get(r).set(c, 0);
                //System.out.println("" + newGrid.get(r).get(c));
                count--;
            }
        }
        gridS = newGrid;
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //             GENERATE FUNCTIONS NORMAL (9X9, 4X4), XGame, HYPER
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------

    //-----------------------------------------------------------------------------
    //-----------------------------------------------------------------------------
    //              SET PREEMTIVE SET FUNCTIONS NORMAL (9X9, 4X4), XGame, HYPER
    //-----------------------------------------------------------------------------
    //-----------------------------------------------------------------------------
    
    //-------------------XGAME---------------------------
    void setPreemtiveSetX( ArrayList< ArrayList<Integer>> g, int len){
        gridSPre = new ArrayList< ArrayList< ArrayList<Integer> > >();

        for (int i = 0; i < len; i++){ //trace the grid
            gridSPre.add(new ArrayList< ArrayList<Integer> >() );
            for (int j = 0; j < len; j++){ //trace the grid
                gridSPre.get(i).add( new ArrayList<Integer>() );
                if ( g.get(i).get(j) == 0 ){ //if the space is empty
                    for (int ii = 1; ii < len + 1; ii++){ //go through the 3d part of the n arr
                        if ( isLegalX(g, i, j, ii, len)){
                            gridSPre.get(i).get(j).add(ii);
                        }
                    }
                }
            }
        }
    }
    //-------------------------------------------------------

    //-----------------NORMAL--------------------
    void setPreemtiveSet( ArrayList< ArrayList<Integer>> g, int len){
        gridSPre = new ArrayList< ArrayList< ArrayList<Integer> > >(len);

        for (int i = 0; i < len; i++){ //trace the grid
            gridSPre.add(new ArrayList< ArrayList<Integer> >(len) );
            for (int j = 0; j < len; j++){ //trace the grid
                gridSPre.get(i).add( new ArrayList<Integer>(len) );
                if ( g.get(i).get(j) == 0 ){ //if the space is empty
                    for (int ii = 1; ii < len + 1; ii++){ //go through the 3d part of the n arr
                        if ( isLegal(g, i, j, ii, len)){
                            gridSPre.get(i).get(j).add(ii);
                        }
                    }
                }
            }
        }
    }
    //--------------------------------------------------

    //-------------------HYPER---------------------------
    void setPreemtiveSetHyper( ArrayList< ArrayList<Integer>> g, int len){
        gridSPre = new ArrayList< ArrayList< ArrayList<Integer> > >();

        for (int i = 0; i < len; i++){ //trace the grid
            gridSPre.add(new ArrayList< ArrayList<Integer> >() );
            for (int j = 0; j < len; j++){ //trace the grid
                gridSPre.get(i).add( new ArrayList<Integer>() );
                if ( g.get(i).get(j) == 0 ){ //if the space is empty
                    for (int ii = 1; ii < len + 1; ii++){ //go through the 3d part of the n arr
                        if ( isLegalHyper(g, i, j, ii, len)){
                            gridSPre.get(i).get(j).add(ii);
                        }
                    }
                }
            }
        }
    }
    //-------------------------------------------------------

    //-----------------------------------------------------------------------------
    //-----------------------------------------------------------------------------
    //              SET PREEMTIVE SET FUNCTIONS NORMAL (9X9, 4X4), XGame, HYPER
    //-----------------------------------------------------------------------------
    //-----------------------------------------------------------------------------


    //--------------------------------------------------
    //          GET AND SET GRID FUNCTIONS
    //--------------------------------------------------
    ArrayList< ArrayList<Integer> > getGrid(){
        return gridS;
    }
    void setGrid( ArrayList< ArrayList<Integer> > s){
        gridS = s;
    }

    void setGrid( int r, int c, int n){
        gridS.get(r).set(c, n);
        System.out.println( gridS.get(r).get(c) );
    }

    int getGrid(int r, int c){
        return gridS.get(r).get(c);
    }
    //--------------------------------------------------
    //          GET AND SET GRID FUNCTIONS
    //--------------------------------------------------
    boolean getBool(int r, int c){
        return boolBoard[r][c];
    }
    public String toString(){
        String str = "     0  1  2     3  4  5     6  7  8   \n";
        for (int r = 0; r < 9; r++){
            if(r % 3 == 0){
                str += "  ";
                for (int i = 0; i < 19; i++){
                    str += "# ";
                }
                str += "\n  #           #           #           #\n";
            }
            str += r + " ";
            for (int c = 0; c < 9; c++){
                if ( c % 3 == 0){
                    str += "#  ";
                }

                if ( boolBoard[r][c] == false  && gridS.get(r).get(c) == 0){
                    str += ".  ";
                } else if (boolBoard[r][c] == true) {
                    str += Terminal.redText + gridS.get(r).get(c) + Terminal.resetText + "  ";
                } else {
                    str += Terminal.blueText + gridS.get(r).get(c) + Terminal.resetText + "  ";
                }
            }
            str += "#";
            if ( r == 2 || r == 5 || r == 8){
                str += "\n  #           #           #           #\n";
            } else {
                str += "\n";
            }
        }
        str += "  ";
        for (int i = 0; i < 19; i++){
            str += "# ";
        }
        return str;
    }

    public String toString( int rr, int cc ){
        String str = "     0  1  2     3  4  5     6  7  8\n";
        for (int r = 0; r < 9; r++){
            if(r % 3 == 0){
                str += "  ";
                for (int i = 0; i < 19; i++){
                    str += "# ";
                }
                str += "\n  #           #           #           #\n";
            }
            str += r + " ";
            for (int c = 0; c < 9; c++){
                if ( c % 3 == 0){
                    str += "#  ";
                }
                if (r == rr && c == cc){
                    str += "X  ";
                } else if ( boolBoard[r][c] == false && gridS.get(r).get(c) == 0 ){
                    str += ".  ";
                } else if (boolBoard[r][c] == true){
                    str += Terminal.redText + gridS.get(r).get(c) + Terminal.resetText + "  ";
                } else {
                    str += Terminal.blueText + gridS.get(r).get(c) + Terminal.resetText + "  ";
                }

            }
            str += "#";
            if ( r == 2 || r == 5 || r == 8){
                str += "\n  #           #           #           #\n";
            } else {
                str += "\n";
            }
        }
        str += "  ";
        for (int i = 0; i < 19; i++){
            str += "# ";
        }
        return str;
    }
    
    //--------------------------------------------------
    public static void main( String [] args ){
        /*int[][] sB = new int[][]{ {0, 0, 0,  0, 0, 0,  0, 0, 0},
                                  {0, 4, 0,  0, 0, 0,  0, 1, 0},
                                  {0, 0, 0,  0, 0, 0,  0, 0, 0},

                                  {0, 0, 0,  0, 0, 1,  0, 0, 0},
                                  {0, 0, 0,  0, 0, 0,  0, 0, 0},
                                  {0, 0, 0,  0, 0, 0,  0, 0, 0},

                                  {0, 0, 0,  0, 0, 0,  0, 0, 0},
                                  {0, 0, 0,  0, 0, 0,  0, 0, 0},
                                  {0, 0, 0,  0, 0, 0,  0, 0, 0},
                                };*/
        Sudoku ss = new Sudoku(); //sB, 1000, 650
        System.out.println( ss );
        //System.out.println( ss.allColsOK( ss.getGrid(), 9)  );
        //System.out.println( ss.checkBoard( ss.getGrid(), 9)  );

    }
    //--------------------------------------------------
    boolean checkBoard( ArrayList< ArrayList< Integer > > s, int len){
        //boolean[] b = new boolean[len];
        for(int r = 0; r < len; r++){
            boolean[] b1 = new boolean[len];
            boolean[] b2 = new boolean[len];
            for (int c = 0; c < len; c++){
                int n1 = s.get(r).get(c);
                if (b1[n1 - 1]){
                    return false;
                }
                if ( n1 >= 1 && n1 <= len ){
                    b1[n1 - 1] = true;
                } else {
                    return false;
                }
                
                int n2 = s.get(c).get(r);
                if (b2[n2 - 1]){
                    return false;
                }
                if (n2 >= 1 && n2 <= len){
                    b2[n2 - 1] = true;
                } else {
                    return false;
                }

                if ( (r % 3 == 0) && (c % 3 == 0) ){
                    if ( !boxOK(r, c, s, len) ){
                        return false;
                    }
                }
            }
        }
        return true;
    }

}