package blackjack;

import java.security.DrbgParameters.NextBytes;
import java.util.Scanner;

public class BlackJack {

	static Game game = new Game();
	static GUI gui = new GUI();
	static Scanner input = new Scanner(System.in);
	static int indexMaxScore;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Card[] cardDeck = new Card[52];
		// initialize Card objects using constructor 
		int index = 0;
		for(int i = 0 ; i < 4 ; i++) {
			for(int j = 0 ; j < 13 ; j++) {
				cardDeck[index] = new Card(i, j);
				index++;
			}
		}
		
		
		try {
			game.generateCardDeck(cardDeck);
			game.setPlayersInfo();
			gui.runGUI( cardDeck,
					game.arrOFplayers[0].card,
					game.arrOFplayers[1].card,
					game.arrOFplayers[2].card,
					game.arrOFplayers[3].card);
			
			// for player turn
			for(int i = 0 ; i < 3 ; i++) {
				System.out.printf("\n\n*Player %d (%s) round*\n" ,i+1 ,game.arrOFplayers[i].getName());
				while(true) {
					int x = playerTurn(game.arrOFplayers[i] ,i);
					if(x == 1)
						break;
				}	
			}
			
			
			// update high score
			indexMaxScore = game.updateMAXscore(0, 1, 2);
			if(indexMaxScore != 111)   // 111 number refer to no player has valid high score
				System.out.printf("high score = %d ,player : %d\n\n" ,game.getHighScore() ,indexMaxScore+1);
			else
				System.out.printf("No high score!!!\n\n");
			
			// for dealer turn
			while(true) {  
				int i = 3;
				System.out.printf("\n\n*Players %d (%s)(dealer) round*\n" ,i+1 ,game.arrOFplayers[i].getName());
//				if(indexMaxScore == 111) { // no high score
//					System.out.printf("Dealer's Hand is %d \n" ,game.arrOFplayers[i].getScore());
//					game.arrOFplayers[3].setIsLost(false);
//					Card c = new Card();
//					gui.updateDealerHand(c ,game.DeckArray);             // gui
//					break;
//				}
//				else if(game.arrOFplayers[i].getScore() > game.getHighScore()) {
//					System.out.printf("Dealer's Hand is %d \n" ,game.arrOFplayers[i].getScore());
//					game.arrOFplayers[3].setIsLost(false);
//					Card c = new Card();
//					gui.updateDealerHand(c ,game.DeckArray);                // gui
//					break;
//				}
				
				while(true) {
					int x = dealerTurn(game.arrOFplayers[i] ,i);
					if(x == 1)
						break;
				}
				
				if(game.arrOFplayers[3].getScore() >= 21)
					break;
				
				
			}
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		playerWin(game.arrOFplayers[3] ,indexMaxScore);
		System.out.printf("\n\n\t\tEND THE GAME.......");
		
		
	}
	
	
	
	



	
	
	// player
	public static int playerTurn(Player player,int index) {
		if(player.getScore() < 21){   // less then 21 and not lose
			System.out.printf("Player's Hand is %d \n" ,player.getScore());
			System.out.printf("1- hit\n");
			System.out.printf("2- stand\n");
			System.out.printf("answer : ");
			int ans = input.nextInt();
			if(ans == 1) {
				pickCardToPlayer(index);
				int check = checkPlayer(player ,index);
				if(check == 1)
					return 1;    //  the score is greater then (or equal) 21
				else
					return 0;
			}
			else if(ans == 2) {
				System.out.printf("STAND\n");
				System.out.printf("------------------------------------------\n\n");
				return 1;
			}
		 }
		
		 
		return 1;	 
	}
	
	public static void pickCardToPlayer(int index) {
		Card card = new Card(game.drawCardRand(game.DeckArray));            // pick card from the deck
		game.arrOFplayers[index].addCard(card);                 // add card to this player
		game.arrOFplayers[index].updateScore(card.getValue());  // update the score
		gui.updatePlayerHand(card ,index);                      // gui
	}
	
	public static int checkPlayer(Player player ,int i) {
		if(player.getScore() == 21) {
			System.out.printf("Player's %d  is BlackJack\n" ,i+1);
			System.out.printf("------------------------------------------\n\n");
			player.setIsBlackJack(true);
			player.setIsLost(false);
			return 1;     //  the score is greater then (or equal) 21
		}
		else if(player.getScore() > 21) {
			System.out.printf("Player %d is Busted ,Player's hand = %d\n" ,i+1 ,player.getScore());
			System.out.printf("------------------------------------------\n\n");
			player.setIsBlackJack(false);
			player.setIsLost(true);
			return 1;     //  the score is greater then (or equal) 21
		}
		return 0;  //  the score is less then 21 ( 19 or 15)
		
	}
	
	
	// dealer
	public static int dealerTurn(Player dealer,int index) {
		if(dealer.getScore() < 21){   // less then 21 and not lose
			System.out.printf("Dealer's Hand is %d \n" ,dealer.getScore());
			System.out.printf("1- hit\n");
			System.out.printf("answer : ");
			int ans = input.nextInt();
			if(ans == 1) {
				pickCardToDealer(index);
				int check = checkDealer(dealer ,index);
				if(check == 1)
					return 1;  //  the score is greater then (or equal) 21
				else
					return 0;
			}
			else {
				System.out.printf("Please Enter (1)\n");
				return 0;
			}

			
		 }
		
		 
		return 1;	 
	}
	
	public static void pickCardToDealer(int index) {
		Card card = new Card(game.drawCardRand(game.DeckArray));            // pick card from the deck
		game.arrOFplayers[index].addCard(card);                 // add card to this player
		game.arrOFplayers[index].updateScore(card.getValue());  // update the score
		gui.updateDealerHand(card ,game.DeckArray);             // gui
	}
	
	public static int checkDealer(Player dealer ,int i) {
		if(dealer.getScore() == 21) {
			System.out.printf("Dealer's %d  is BlackJack\n" ,i+1);
			dealer.setIsBlackJack(true);
			dealer.setIsLost(false);
			return 1;   //  the score is greater then (or equal) 21
		}
		else if(dealer.getScore() > game.getHighScore() && dealer.getScore() < 21) {
			System.out.printf("Player's hand = %d\n" ,dealer.getScore());
			dealer.setIsBlackJack(false);
			dealer.setIsLost(false);
			return 1;   //  the score is greater then (or equal) 21
		}
		else if(game.arrOFplayers[i].getScore() > 21) {
			System.out.printf("Dealer %d is Busted ,Player's hand = %d\n" ,i+1 ,game.arrOFplayers[i].getScore());
			dealer.setIsBlackJack(false);
			dealer.setIsLost(true);
			return 1;    //  the score is greater then (or equal) 21
		}
		return 0;  //  the score is less then 21 ( 19 or 15)
		
	}

	
	public static void playerWin(Player dealer ,int indexMaxScore) {
		if(indexMaxScore != 111) {
			if(game.arrOFplayers[indexMaxScore].getIsBlackJack() && dealer.getIsBlackJack())
				System.out.printf("\n\n Tie \n" ,indexMaxScore+1);
			else if(game.arrOFplayers[indexMaxScore].getIsBlackJack())
				System.out.printf("\n\nPlayer's %d is WIN\n" ,indexMaxScore+1);
			else if(dealer.isBlackJack)
				System.out.printf("\n\nPlayer's 4 is WIN\n");
		
			if(!game.arrOFplayers[indexMaxScore].getIsLost() && !dealer.getIsLost())  // 2 player has the same score
				System.out.printf("\n\n Tie \n" ,indexMaxScore+1);
			else if(!game.arrOFplayers[indexMaxScore].getIsLost())
				System.out.printf("\n\nPlayer's %d is WIN\n" ,indexMaxScore+1);
			else if(!dealer.getIsLost())
				System.out.printf("\n\nPlayer's 4 is WIN\n");
			else if(game.arrOFplayers[indexMaxScore].getScore() > dealer.getScore())
				System.out.printf("\n\nPlayer's %d is WIN\n" ,indexMaxScore+1);
			
		}
		else {
			if(!dealer.getIsLost())
				System.out.printf("\n\nPlayer's 4 is WIN\n");
			else
				System.out.printf("\n\n---( No Player is WIN )---\n");
			
		}

		
	}
	
	
	
	
	
	
}
