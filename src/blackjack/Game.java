package blackjack;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

	// create an array of Player object  
	Player[] arrOFplayers = new Player[4];    // the dealer is the last player object in the array at index [3])
	// create an array of Card object  private Card[] DeckArray = new Card[52];
	Card[] DeckArray = new Card[52];
	private int index = 0;
	// keeps track of the existing VALID high score of all players (<= 21)
	private int highScore = 0;
	private Random rand = new Random();
	
	public void generateCardDeck(Card[] cardDeck) {
		for(int i = 0 ; i < cardDeck.length ; i++) 
			this.DeckArray[i] = new Card(cardDeck[i]); 	
	}
	
	
	
	public Card drawCardRand(Card[] arrOFcardDeck) {
		while(true) {
			int x = rand.nextInt(52);
			if(check(x) == 1) {
				Card card = DeckArray[x];          // 2) Saves that card object in a new object to be returned
				rmObjectFromDeckArray(card ,x);              // 3) Make that object equals null in the card deck array
				return card;
				//break;
			}
			else
				continue;
		} // 4) Return the new card object created that holds the drawn card from the deck		
		//return card;                                 // Make sure that the drawn card from the deck is not equal to null before saving and returning it.
	}
	
	public int check(int x) {
		// TODO Auto-generated method stub
		if(DeckArray[x] != null)
			return 1;
		return 0;
	}



	public void setPlayersInfo() {
		Scanner input = new Scanner(System.in);
		
		for(int i = 0 ; i < 4 ; i++) {
			System.out.print("Enter the name of player : ");
			String PlayerName = input.next();

			// draw (create) 2 random cards for each player
			int x = rand.nextInt(52);
			int y = rand.nextInt(52);
			Card card1 = new Card(DeckArray[x]);
			Card card2 = new Card(DeckArray[y]);
			
			rmObjectFromDeckArray(card1 ,x);    // remove the card from DeckArray
			rmObjectFromDeckArray(card2 ,x);
			
			arrOFplayers[i] = new Player(PlayerName ,card1.getValue()+card2.getValue() ,false ,true);
			arrOFplayers[i].addCard(card1);
			arrOFplayers[i].addCard(card2);
		}
	}
	
	// max score should be less then or equal 21
	public int updateMAXscore(int index1 ,int index2 ,int index3) {
		int i = index1;
		int maxScore1_2 = Math.max(arrOFplayers[index1].getScore() ,arrOFplayers[index2].getScore());
		if(maxScore1_2 > 21)
			maxScore1_2 = Math.min(arrOFplayers[index1].getScore() ,arrOFplayers[index2].getScore());
		
		if(arrOFplayers[index2].getScore() == maxScore1_2)
			i = index2;
			
		highScore = Math.max(arrOFplayers[index3].getScore() ,arrOFplayers[i].getScore());
		if(highScore > 21)
			highScore = Math.min(arrOFplayers[index3].getScore() ,maxScore1_2);
			
		if(arrOFplayers[index3].getScore() == highScore)
			i = index3;
		
		if(arrOFplayers[i].getScore() > 21)
			i = 111;
		
		return i;
	}
	
	// setter
	public void setHighScore(int score) {
		this.highScore = score;
	}
	// getter
	public int getHighScore() {
		return highScore;
	}

	
	// 3) Make that object equals null in the card deck array
	public int rmObjectFromDeckArray(Card card ,int index) {
		if(index < 52) {
			DeckArray[index] = null;
			return 1;
		}
		
		
		return 0;
	}
	
	public Card[] remove(int index){
		Card[] ret = new Card[DeckArray.length-1];
		for(int i = 0; i<index; i++){
			ret[i]=DeckArray[i];
		}
		for(int i = index+1; i<DeckArray.length; i++){
			ret[i-1]=DeckArray[i];
		}
		return ret;
	}
	
	// check if card is picked before of not
	public int checkIsPick(Card card) {
		for (int i = 0 ; i < DeckArray.length ; i++) {
			if(card.getSuit() == DeckArray[i].getSuit() && card.getRank() == DeckArray[i].getRank()) {
				return 1;
			}
			
		}
		return 0;
		
		
	}
	
}
