package blackjack;

import java.util.ArrayList;
import java.util.Random;

import deckOfCards.*;

/*
 * A BlackjackModel simulates a blackjack game.
 * It can be used to play a blackjack game
 */
public class BlackjackModel {

	// an ArrayList of Cards representing the dealer's cards
	private ArrayList<Card> dealerCards;

	// an ArrayList of Cards representing the player's cards
	private ArrayList<Card> playerCards;

	// the deck of cards that will be used to deal cards to dealer and player
	private Deck deck;

	/* a getter for dealerCards. Creates and returns a deep copy of 
	 * dealerCards (prevents privacy leak) */
	public ArrayList<Card> getDealerCards(){
		ArrayList<Card> copyDealerCards = new ArrayList<>();
		for(Card card: dealerCards) {
			copyDealerCards.add(card);
		}
		return copyDealerCards;
	}

	/* a getter for playerCards. Creates and returns a deep copy of 
	 * playerCards (prevents privacy leak) */
	public ArrayList<Card> getPlayerCards(){
		ArrayList<Card> copyPlayerCards = new ArrayList<>();
		for(Card card: playerCards) {
			copyPlayerCards.add(card);
		}
		return copyPlayerCards;
	}

	/* a setter for dealerCards. assigns dealerCards to the parameter */
	public void setDealerCards(ArrayList<Card> cards) {
		dealerCards = cards;
	}

	/* a setter for playerCards. assigns playerCards to the parameter */
	public void setPlayerCards(ArrayList<Card> cards) {
		playerCards = cards;
	}

	/* This method instantiates the deck variable and shuffles the deck */
	public void createAndShuffleDeck(Random random) {
		deck = new Deck();
		deck.shuffle(random);
	}

	/* this method instantiates dealerCards and deals the initial 2 cards 
	 * to the dealer (adds 2 cards to dealerCards) */
	public void initialDealerCards() {
		dealerCards = new ArrayList<>();
		for(int i = 0; i < 2; i++) {
			dealerCards.add(deck.dealOneCard());
		}
	}

	/* this method instantiates playerCards and deals the initial 2 cards 
	 * to the player (adds 2 cards to playerCards) */
	public void initialPlayerCards() {
		playerCards = new ArrayList<>();
		for(int i = 0; i < 2; i++) {
			playerCards.add(deck.dealOneCard());
		}
	}

	/* this method deals one card to the player (adds 1 card to playerCards) */
	public void playerTakeCard() {
		playerCards.add(deck.dealOneCard());
	}

	/* this method deals one card to the dealer (adds 1 card to dealerCards) */
	public void dealerTakeCard() {
		dealerCards.add(deck.dealOneCard());
	}

	/* this method evaluates the hand (parameter) and returns a short ArrayList
	 * of integers that has the values that the hand can represent.
	 * the size of the return value is always one or two */
	public static ArrayList<Integer> possibleHandValues(ArrayList<Card> hand) {
		/* an ArrayList of integers that has values the hand can represent.
		 * the return value for this method */
		ArrayList<Integer> handValues = new ArrayList<>();

		// a boolean that is true if the hand has an ace, false otherwise
		boolean hasAce = false;

		/* this loops adds up all values in the hand, and assigns hasAce as 
		 * true if there is an ace in the hand.
		 * if there is an ace present, the sum will add the ace as the value 1 
		 * instead of 11 first */
		int sum = 0;
		for(Card card: hand) {
			sum += card.getRank().getValue();

			if(card.getRank().equals(Rank.ACE)) {
				hasAce = true;
			}
		}
		handValues.add(sum);

		/* if there is an ace in the hand, this will add the ace as the value 
		 * of 11, and only add it to the list if it is less than, or equals 
		 * to 21 */
		if(hasAce) {
			sum += 10;

			if(sum <= 21) {
				handValues.add(sum);
			}
		}
		return handValues;
	}

	/* this method assesses the hand (parameter) and returns one of the four
	 * HandAssessment constants:
	 * 1) INSUFFICIENT_CARDS (hand is null or contains fewer than 2 cards)
	 * 2) NATURAL_BLACKJACK (hand represents natural blackjack - 
	 *    hand has 2 cards and has value equal to 21)
	 * 3) BUST (hand's value is over 21)
	 * 4) NORMAL (if none of the other categories apply) */
	public static HandAssessment assessHand(ArrayList<Card> hand) {
		
		// 4)
		HandAssessment assessment = HandAssessment.NORMAL;
		
		// 1)
		if(hand.equals(null) || hand.size() < 2) {
			return HandAssessment.INSUFFICIENT_CARDS;

			// 2)
		} else if(hand.size() == 2 && possibleHandValues(hand).get(0) == 21
				|| hand.size() == 2 && possibleHandValues(hand).size() == 2 
				&& possibleHandValues(hand).get(1) == 21) {
			return HandAssessment.NATURAL_BLACKJACK;

			// 3)
		} else if(possibleHandValues(hand).get(0) > 21 || 
				possibleHandValues(hand).size() == 2 && 
				possibleHandValues(hand).get(1) > 21) {
			return HandAssessment.BUST;
		}
		return assessment;
	}

	/* This method assesses the game and returns a GameResult, where the game
	 * can result in a push, natural blackjack, player wins, or player loses.
	 * 
	 * The following are how the results are determined:
	 * 1) NATURAL_BLACKJACK - the player has a natural blackjack and the dealer 
	 * doesn't. The player wins 1.5 times their bet (NATURAL_BLACKJACK is used 
	 * to distinguish this from a regular win) 
	 * 2) PUSH - the player AND dealer both have a natural blackjack (player 
	 * neither wins or loses)
	 * 3) PLAYER_LOST - the player busts even if the dealer also busts
	 * 4) PLAYER_WON - the player doesn't get a bust, and the dealer does
	 * 5) if the player and dealer both don't get a bust, the result is 
	 * determined by comparing the values of the player and dealer's hand:
	 * 		5a) PLAYER_WON - player's hand value is greater than dealer's
	 * 		5b) PLAYER_LOST - player's hand value is less than dealer's
	 * 		5c) PUSH - player and dealer's hand are the same
	 */
	public GameResult gameAssessment() {
		
		// represents the HandAssessment of the player and dealer
		HandAssessment playerHand = assessHand(playerCards);
		HandAssessment dealerHand = assessHand(dealerCards);
		
		GameResult results = GameResult.PLAYER_LOST;

		// 1)
		if(playerHand.equals(HandAssessment.NATURAL_BLACKJACK) &&
				!dealerHand.equals(HandAssessment.NATURAL_BLACKJACK)) {
			results = GameResult.NATURAL_BLACKJACK;

			// 2)
		} else if(playerHand.equals(HandAssessment.NATURAL_BLACKJACK) &&
				dealerHand.equals(HandAssessment.NATURAL_BLACKJACK)) {
			results = GameResult.PUSH;

			// 3)
		} else if(playerHand.equals(HandAssessment.BUST)) {
			results = GameResult.PLAYER_LOST;

			// 4)
		} else if(!playerHand.equals(HandAssessment.BUST) && 
				dealerHand.equals(HandAssessment.BUST)) {
			results = GameResult.PLAYER_WON;

			// 5)
		} else if(!playerHand.equals(HandAssessment.BUST) &&
				!dealerHand.equals(HandAssessment.BUST)){

			//these variables represent player and dealer's possible hand values
			ArrayList<Integer> playerHandVal = possibleHandValues(playerCards);
			ArrayList<Integer> dealerHandVal = possibleHandValues(dealerCards);
			
			/* these variables will store the highest possible hand value for
			 * the player and dealer */
			int maxPlayerHandVal = 0;
			int maxDealerHandVal = 0;

			// assigns maxPlayerHandVal the player's highest possible hand value
			for(int index = 0; index < playerHandVal.size(); index++){
				maxPlayerHandVal = playerHandVal.get(index);
			}

			// assigns maxDealerHandVal the dealer's highest possible hand value
			for(int index = 0; index < dealerHandVal.size(); index++) {
				maxDealerHandVal = dealerHandVal.get(index);
			}

			// 5a)
			if(maxPlayerHandVal > maxDealerHandVal) {
				results = GameResult.PLAYER_WON;

				// 5b)
			} else if(maxPlayerHandVal < maxDealerHandVal) {
				results = GameResult.PLAYER_LOST;
				
				// 5c)
			} else {
				results = GameResult.PUSH;
			}
		}
		return results;
	}

	/* this method determines when the dealer will stop taking cards.
	 * if true, the dealer will keep taking cards, otherwise the dealer stops.
	 * 
	 * this method will return true (dealer keeps taking cards) if:
	 * 1a) dealer's hand is 16 or less
	 * 1b) dealer's hand includes an ace, and the hand's value could be either
	 * 	  7 or 17
	 * 
	 * this methods will return false (dealer stops taking cards) if:
	 * 2a) dealer's hand reaches 18 or more
	 * 2b) dealer's hand is 17 and not be valued as 7 */
	public boolean dealerShouldTakeCard() {
		
		boolean shouldTakeCard = false;
		
		// represents a list of the dealer's possible hand values
		ArrayList<Integer> possibleHandValues = possibleHandValues(dealerCards);
		
		// represents the dealer's hand's number of possible hand values 
		int size = possibleHandValues.size();
		
		boolean hasAce = false;
		for(Card card: dealerCards) {
			if(card.getRank().equals(Rank.ACE)) {
				hasAce = true;
			}
		}
		
		// this loop checks all the possible hand values of the dealer's hand
		for(int index = 0; index < size; index++) {
			int handValue = possibleHandValues.get(index);
			// 1a)
			if(handValue <= 16) {
				shouldTakeCard = true;
				
				// 2a)
			} else if(handValue >= 18) {
				shouldTakeCard = false;
				
				// 1b)
			} else if(hasAce && size == 2 && possibleHandValues.get(0) == 7 
					&& possibleHandValues.get(1) == 17) {
				shouldTakeCard = true;
				
				// 2b)
			} else if(size == 1 && possibleHandValues.get(0) == 17) {
				shouldTakeCard = false;
			}
		}
		return shouldTakeCard;
	}
}
