import java.util.Scanner;

class Pong{
    private static boolean playing = true;
    public static void main(String [] args){
        //while (playing){
            printBoard();
        //}
    }

    private static void printBoard(){
        for (int r = 0; r < 15; r++){
            for (int c = 0; c < 30; c++){
                if (r == 0|| r == 14 || c == 0 || c == 29){
                    if (r == 7 || r == 6 || r == 8){
                        System.out.print(" ");
                    } else {
                        System.out.print("#");
                    }
                } else{
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}