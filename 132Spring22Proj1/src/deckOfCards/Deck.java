package deckOfCards;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/* 
 * A Deck represents a standard deck of 52 cards
 * A Deck can be shuffled and dealt  
 */
public class Deck {
	
	// a Card ArrayList of cards
	private ArrayList<Card> cards;
	
	/* This method instantiates the cards with 52 cards.
	 * The cards are separated by suit (spades, hearts, clubs, diamond) and
	 * ordered in increasing value (ace to king) */
	public Deck() {
		cards = new ArrayList<>(51);
		for(int suit = 0; suit < 4; suit++) {
			for(int rank = 0; rank < 13; rank++) {
				cards.add(new Card(Rank.values()[rank], Suit.values()[suit]));
			}
		}
	}
	
	/* This method shuffles the deck randomly by calling Collections.shuffle,
	 * passing in cards and randomNumberGenerator as its parameter */
	public void shuffle(Random randomNumberGenerator) {
		Collections.shuffle(cards, randomNumberGenerator);
	}
	
	/* This method removes one card from the front of the list and returns it */
	public Card dealOneCard() {
		Card frontCard = cards.get(0);
		cards.remove(0);
		return frontCard;
	}
	
}
