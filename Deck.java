import java.util.Scanner;

class Deck{
    public static Card[] cards = new Card[52];
    public int length = 52;
    private String[] suiteValues = new String[]{ "Hearts", "Clubs", "Diamonds", "Spades" };
    Deck() {
        int ii = 0;
        for (int sV = 0; sV < 4; sV++){
            for (int i = 0; i < 13; i++){
                if ( i == 0) {
                    cards[ii] = new Card( suiteValues[sV], "Ace" );
                } else if ( i < 10 ){
                    cards[ii] = new Card( suiteValues[sV], (i + 1) + "" );
                } else if ( i == 10){
                    cards[ii] = new Card( suiteValues[sV], "Jack" );
                } else if ( i == 11){
                    cards[ii] = new Card( suiteValues[sV], "Queen" );
                } else if ( i == 12) {
                    cards[ii] = new Card( suiteValues[sV], "King" );
                } else {
                    cards[ii] = new Card(  );
                }
                ii++;
            }
        }

        
    }

    public Card getCard( int index ){
        return cards[ index ];
    }

    public static void main (String[] args){
        //System.out.println("hw");
        Deck d = new Deck();
        for (int i = 0; i < 52; i++){
            System.out.println( d.getCard( i ).toString() );
        } 
    }
}