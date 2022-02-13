package blackjack;

import java.util.ArrayList;
import java.util.Map;

public class Player {

	private String name;
	private int score = 0;  
	 Card[] card = new Card[11];
	 private int index = 0;
	//optional if you want
	boolean isBlackJack = false;
	boolean isLost = true;
	
	
	public Player(String name,int score,boolean isBlackJack,boolean isLost) {
		this.name =name;
		this.score = score;
		this.isBlackJack = isBlackJack;
		this.isLost = isLost;
	}
	
	public Player() {
		// TODO Auto-generated constructor stub
	}

	public void addCard(Card c) {
		card[index] = new Card(c.getSuit(), c.getRank());
		index++;
		//System.out.printf("the card is added\n");
	}
	
	
	
	public void updateScore(int score) {
		this.score += score;
	}
	
	// setter
	public void setName(String name) {
		this.name = name;
	}
	public void setIsBlackJack(boolean blackJack) {
		this.isBlackJack = blackJack;
	}
	public void setIsLost(boolean lost) {
		this.isLost = lost;
	}
	
	// getter
	public String getName() {
		return name;
	}
	public int getScore() {
		return score;
	}
	public boolean getIsBlackJack() {
		return isBlackJack;
	}
	public boolean getIsLost() {
		return isLost;
	}
	

	
	
	
	
	
	
	
}
