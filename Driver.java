import java.util.Scanner;
import java.util.Random;
class Driver{
    private static boolean playing = true;
    private static Sudoku s;
    private static String name = "";
    private static Scanner key = new Scanner(System.in);
    public static void main(String [] args){
        //Scanner key = new Scanner(System.in);
        String ans = "n";
        do {
            System.out.println("Pretty UI [y/n]: ");
            ans = key.next();
        } while ( !(ans.equals("y") || ans.equals("n") ) );
        System.out.println(Terminal.reset);
        if ( ans.equals("y") ){
            Board b = new Board();
        } else {
            System.out.println("  #####################################");
            System.out.println("  #                                   #");
            System.out.println("  #              Sudoku               #");
            System.out.println("  #                                   #");
            System.out.println("  #####################################");
            System.out.print("     Enter Name to begin: ");
            name = key.next();
            Random rand = new Random();
            int amt = rand.nextInt(15) + 20;
            s = new Sudoku(9, amt, Sudoku.GameMode.normalGame);
            do {
                gameLoop();
            } while (playing);
        }
    }

    private static void gameLoop(){
        //Scanner key = new Scanner(System.in);
        String r, c, n;
        int rr = -1, cc = -1, nn = -1;
        do{
            //System.out.println(Terminal.reset);
            System.out.println(s);
            printMenu();
            System.out.print("     Input (r, c) to modify: ");
            r = key.next();
            c = key.next();
            r = r.charAt(0) + "";
            c = c.charAt(0) + "";
            if ( legalString( r, Terminal.numberStrings) && legalString( c, Terminal.numberStrings) ){
                rr = Integer.parseInt(r);
                cc = Integer.parseInt(c);
            } else if ( legalString(r, Terminal.exitText) || legalString(r, Terminal.exitText) ){
                playing = false;
                break;
            }
        } while ( !isInBounds(rr, cc) );
        do{
            //System.out.println(Terminal.reset);
            System.out.println(s.toString( rr, cc ));
            printMenu();
            System.out.print("     Input number: ");
            n = key.next();
            nn = Integer.parseInt(n);
        } while ( !isInRange( nn ) );
        s.setGrid( rr, cc, nn );

        //System.out.println(Terminal.reset);
        System.out.println(s);
        printMenu();
        String input = key.next();
        if ( input.equals( name ) ){
            playing = false;
        }
        if ( legalString( input, Terminal.checkText) ){ //compare input to checkText[]{ "c", "check", "chk" }
            if ( s.isCorrect( s.getGrid(), 9) ){
                System.out.println("Winner!");
            } else {
                System.out.println("Nope!");
                try { Thread.sleep(500);} catch (Exception e){ System.out.println("error");}
            }
        }
        if ( input.equals( "h" ) ){
            help();
        }
    }

    static void checkInput( String in ){
        int g = 0;
    }

    static boolean legalString( String str, String[] com ){
        for (int i = 0; i < com.length; i++){
            if (str.equals(com[i])){
                return true;
            }
        }
        return false;
    }

    static boolean legalNumber( int n, int[] com){
        for (int i = 0; i < com.length; i++){
            if ( n == com[i] ){
                return true;
            }
        }
        return false;
    }

    static boolean isInBounds(int r, int c){
        if ( (r >= 0 && r < 9) && (c >= 0 && c < 9)){
            System.out.println( "s" );
            if ( !s.getBool(r, c) ){ //check if it is a given number
                System.out.println( "ss" );
                return true;
            }
        }
        return false;
    }

    static boolean isInRange( int n ){
        if (n >= 1 && n <= 9 ){
            return true;
        } else {
            return false;
        }
    }
    
    static void printMenu(){
        System.out.println("  #####################################");
        System.out.println("  #   <h-help>     <Input numbers>    #");
        System.out.println("  #  <c-check>      <to continue>     #");
        System.out.println("  #        <enter name to exit>       #");
        System.out.println("  #####################################");
    }

    static void help(){
        String response = "";
        do {
            System.out.println(Terminal.reset);
            System.out.println("  ###########################################");
            System.out.println("  #               HELP MENU                 #");
            System.out.println("  # # # # # # # # # # # # # # # # # # # # # #");
            System.out.print("  # Name: " + name + "");
            for (int i = 0; i < (33 - name.length()); i++ ){
                System.out.print(" ");
            }
            System.out.println(" #");
            System.out.println("  # Check Board: Input<c, ch, chk, check>   #");
            System.out.println("  # Clear Board: Input<cl, clr, clear>      #");
            System.out.println("  # Exit/Quit:   Input<e, ex, exit>         #");
            System.out.println("  # Back/Resume: Input<b, bk, back>         #");
            System.out.println("  # Example (r, c) modify input:            #");
            System.out.println("  #      < 1 5 >                            #"); 
            System.out.println("  #      < 1 [new line] 1 >                 #"); 
            System.out.println("  # Example number input: <1><2>...<9>      #");
            System.out.println("  ###########################################");
            System.out.print(  "     Input response: ");
            response = key.next();
            
        } while ( response.equals("") );
    }
}