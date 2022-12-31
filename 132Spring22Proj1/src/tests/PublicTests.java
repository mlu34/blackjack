package tests;

import deckOfCards.*;
import blackjack.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;
import org.junit.Test;

public class PublicTests {

	@Test
	public void testDeckConstructorAndDealOneCard() {
		Deck deck = new Deck();
		for (int suitCounter = 0; suitCounter < 4; suitCounter++) {
			for (int valueCounter = 0; valueCounter < 13; valueCounter++) {
				Card card = deck.dealOneCard();
				assertEquals(card.getSuit().ordinal(), suitCounter);
				assertEquals(card.getRank().ordinal(), valueCounter);
			}
		}
	}

	/* This test will pass only if an IndexOutOfBoundsException is thrown */
	@Test (expected = IndexOutOfBoundsException.class)
	public void testDeckSize() {
		Deck deck = new Deck();
		for (int i = 0; i < 53; i++) {  // one too many -- should throw exception
			deck.dealOneCard();
		}
	}

	@Test
	public void testDeckShuffle() {
		Deck deck = new Deck();
		Random random = new Random(1234);
		deck.shuffle(random);
		assertEquals(new Card(Rank.KING, Suit.CLUBS), deck.dealOneCard());
		assertEquals(new Card(Rank.TEN, Suit.CLUBS), deck.dealOneCard());
		assertEquals(new Card(Rank.JACK, Suit.SPADES), deck.dealOneCard());
		for (int i = 0; i < 20; i++) {
			deck.dealOneCard();
		}
		assertEquals(new Card(Rank.SIX, Suit.CLUBS), deck.dealOneCard());
		assertEquals(new Card(Rank.FIVE, Suit.CLUBS), deck.dealOneCard());
		for (int i = 0; i < 24; i++) {
			deck.dealOneCard();
		}
		assertEquals(new Card(Rank.EIGHT, Suit.CLUBS), deck.dealOneCard());
		assertEquals(new Card(Rank.JACK, Suit.HEARTS), deck.dealOneCard());
		assertEquals(new Card(Rank.JACK, Suit.CLUBS), deck.dealOneCard());
	}

	@Test
	public void testGameBasics() {
		Random random = new Random(3723);
		BlackjackModel game = new BlackjackModel();
		game.createAndShuffleDeck(random);
		game.initialPlayerCards();
		game.initialDealerCards();
		game.playerTakeCard();
		game.dealerTakeCard();
		ArrayList<Card> playerCards = game.getPlayerCards();
		ArrayList<Card> dealerCards = game.getDealerCards();
		assertTrue(playerCards.get(0).equals(new Card(Rank.QUEEN, Suit.HEARTS)));
		assertTrue(playerCards.get(1).equals(new Card(Rank.SIX, Suit.DIAMONDS)));
		assertTrue(playerCards.get(2).equals(new Card(Rank.EIGHT, Suit.HEARTS)));
		assertTrue(dealerCards.get(0).equals(new Card(Rank.THREE, Suit.CLUBS)));
		assertTrue(dealerCards.get(1).equals(new Card(Rank.NINE, Suit.SPADES)));
		assertTrue(dealerCards.get(2).equals(new Card(Rank.FIVE, Suit.CLUBS)));		
	}

	@Test
	public void testPossibleHandValues() {	
		//passed
		ArrayList<Card> hand1 = new ArrayList<>();
		hand1.add(new Card(Rank.TWO, Suit.SPADES));
		hand1.add(new Card(Rank.THREE, Suit.SPADES));
		hand1.add(new Card(Rank.FOUR, Suit.SPADES));
		ArrayList<Integer> hand1Results = new ArrayList<>();
		hand1Results.add(9);
		assertEquals(BlackjackModel.possibleHandValues(hand1), hand1Results);

		//passed
		ArrayList<Card> hand2 = new ArrayList<>();
		hand2.add(new Card(Rank.ACE, Suit.SPADES));
		hand2.add(new Card(Rank.TWO, Suit.SPADES));
		hand2.add(new Card(Rank.THREE, Suit.SPADES));
		ArrayList<Integer> hand2Results = new ArrayList<>();
		hand2Results.add(6);
		hand2Results.add(16);
		assertEquals(BlackjackModel.possibleHandValues(hand2), hand2Results);

		//passed
		ArrayList<Card> hand3 = new ArrayList<>();
		hand3.add(new Card(Rank.ACE, Suit.SPADES));
		hand3.add(new Card(Rank.SIX, Suit.SPADES));
		hand3.add(new Card(Rank.FIVE, Suit.SPADES));
		ArrayList<Integer> hand3Results = new ArrayList<>();
		hand3Results.add(12);
		assertEquals(BlackjackModel.possibleHandValues(hand3), hand3Results);

		//passed
		ArrayList<Card> hand4 = new ArrayList<>();
		hand4.add(new Card(Rank.ACE, Suit.SPADES));
		hand4.add(new Card(Rank.EIGHT, Suit.SPADES));
		hand4.add(new Card(Rank.FOUR, Suit.SPADES));
		hand4.add(new Card(Rank.QUEEN, Suit.SPADES));
		ArrayList<Integer> hand4Results = new ArrayList<>();
		hand4Results.add(23);
		assertEquals(BlackjackModel.possibleHandValues(hand4), hand4Results);
		
		//passed
		ArrayList<Card> hand5 = new ArrayList<>();
		hand5.add(new Card(Rank.FOUR, Suit.SPADES));
		hand5.add(new Card(Rank.NINE, Suit.SPADES));
		hand5.add(new Card(Rank.SEVEN, Suit.SPADES));
		ArrayList<Integer> hand5Results = new ArrayList<>();
		hand5Results.add(20);
		assertEquals(BlackjackModel.possibleHandValues(hand5), hand5Results);
		
		//passed
		ArrayList<Card> hand6 = new ArrayList<>();
		hand6.add(new Card(Rank.KING, Suit.SPADES));
		hand6.add(new Card(Rank.TWO, Suit.SPADES));
		hand6.add(new Card(Rank.FIVE, Suit.SPADES));
		hand6.add(new Card(Rank.TWO, Suit.DIAMONDS));
		ArrayList<Integer> hand6Results = new ArrayList<>();
		hand6Results.add(19);
		assertEquals(BlackjackModel.possibleHandValues(hand6), hand6Results);
		
		//passed
		ArrayList<Card> hand7 = new ArrayList<>();
		hand7.add(new Card(Rank.TEN, Suit.SPADES));
		hand7.add(new Card(Rank.EIGHT, Suit.SPADES));
		hand7.add(new Card(Rank.SEVEN, Suit.SPADES));
		ArrayList<Integer> hand7Results = new ArrayList<>();
		hand7Results.add(25);
		assertEquals(BlackjackModel.possibleHandValues(hand7), hand7Results);
		
		//passed
		ArrayList<Card> hand8 = new ArrayList<>();
		hand8.add(new Card(Rank.ACE, Suit.SPADES));
		hand8.add(new Card(Rank.SIX, Suit.SPADES));
		ArrayList<Integer> hand8Results = new ArrayList<>();
		hand8Results.add(7);
		hand8Results.add(17);
		assertEquals(BlackjackModel.possibleHandValues(hand8), hand8Results);
		
		//passed
		ArrayList<Card> hand9 = new ArrayList<>();
		hand9.add(new Card(Rank.ACE, Suit.SPADES));
		hand9.add(new Card(Rank.KING, Suit.SPADES));
		ArrayList<Integer> hand9Results = new ArrayList<>();
		hand9Results.add(11);
		hand9Results.add(21);
		assertEquals(BlackjackModel.possibleHandValues(hand9), hand9Results);
		
		//passed
		ArrayList<Card> hand10 = new ArrayList<>();
		hand10.add(new Card(Rank.ACE, Suit.SPADES));
		hand10.add(new Card(Rank.SEVEN, Suit.SPADES));
		hand10.add(new Card(Rank.FOUR, Suit.SPADES));
		ArrayList<Integer> hand10Results = new ArrayList<>();
		hand10Results.add(12);
		assertEquals(BlackjackModel.possibleHandValues(hand10), hand10Results);
		
		//passed
		ArrayList<Card> hand11 = new ArrayList<>();
		hand11.add(new Card(Rank.ACE, Suit.SPADES));
		hand11.add(new Card(Rank.ACE, Suit.DIAMONDS));
		hand11.add(new Card(Rank.EIGHT, Suit.SPADES));
		ArrayList<Integer> hand11Results = new ArrayList<>();
		hand11Results.add(10);
		hand11Results.add(20);
		assertEquals(BlackjackModel.possibleHandValues(hand11), hand11Results);
		
		//passed
		ArrayList<Card> hand12 = new ArrayList<>();
		hand12.add(new Card(Rank.ACE, Suit.SPADES));
		hand12.add(new Card(Rank.SIX, Suit.DIAMONDS));
		hand12.add(new Card(Rank.SIX, Suit.SPADES));
		hand12.add(new Card(Rank.TEN, Suit.SPADES));
		ArrayList<Integer> hand12Results = new ArrayList<>();
		hand12Results.add(23);
		assertEquals(BlackjackModel.possibleHandValues(hand12), hand12Results);
		
		//passed
		ArrayList<Card> hand13 = new ArrayList<>();
		hand13.add(new Card(Rank.ACE, Suit.SPADES));
		hand13.add(new Card(Rank.ACE, Suit.DIAMONDS));
		hand13.add(new Card(Rank.ACE, Suit.CLUBS));
		hand13.add(new Card(Rank.ACE, Suit.HEARTS));
		ArrayList<Integer> hand13Results = new ArrayList<>();
		hand13Results.add(4);
		hand13Results.add(14);
		assertEquals(BlackjackModel.possibleHandValues(hand13), hand13Results);
		
		//passed
		ArrayList<Card> hand14 = new ArrayList<>();
		hand14.add(new Card(Rank.ACE, Suit.SPADES));
		hand14.add(new Card(Rank.ACE, Suit.DIAMONDS));
		hand14.add(new Card(Rank.ACE, Suit.CLUBS));
		ArrayList<Integer> hand14Results = new ArrayList<>();
		hand14Results.add(3);
		hand14Results.add(13);
		assertEquals(BlackjackModel.possibleHandValues(hand14), hand14Results);
		 
	}

	@Test
	public void testAssessHand() {
		//passed
		ArrayList<Card> hand1 = new ArrayList<>();
		assertEquals(BlackjackModel.assessHand(hand1), HandAssessment.INSUFFICIENT_CARDS);
		
		//passed
		ArrayList<Card> hand2 = new ArrayList<>();
		hand2.add(new Card(Rank.ACE, Suit.SPADES));
		assertEquals(BlackjackModel.assessHand(hand2), HandAssessment.INSUFFICIENT_CARDS);
		
		//passed
		ArrayList<Card> hand3 = new ArrayList<>();
		hand3.add(new Card(Rank.ACE, Suit.SPADES));
		hand3.add(new Card(Rank.KING, Suit.SPADES));
		assertEquals(BlackjackModel.assessHand(hand3), HandAssessment.NATURAL_BLACKJACK);
	
		//passed
		ArrayList<Card> hand4 = new ArrayList<>();
		hand4.add(new Card(Rank.ACE, Suit.SPADES));
		hand4.add(new Card(Rank.QUEEN, Suit.SPADES));
		assertEquals(BlackjackModel.assessHand(hand4), HandAssessment.NATURAL_BLACKJACK);
	
		//passed
		ArrayList<Card> hand5 = new ArrayList<>();
		hand5.add(new Card(Rank.ACE, Suit.SPADES));
		hand5.add(new Card(Rank.JACK, Suit.SPADES));
		assertEquals(BlackjackModel.assessHand(hand5), HandAssessment.NATURAL_BLACKJACK);
	
		//passed
		ArrayList<Card> hand6 = new ArrayList<>();
		hand6.add(new Card(Rank.KING, Suit.SPADES));
		hand6.add(new Card(Rank.JACK, Suit.SPADES));
		hand6.add(new Card(Rank.QUEEN, Suit.SPADES));
		assertEquals(BlackjackModel.assessHand(hand6), HandAssessment.BUST);
		
		//passed
		ArrayList<Card> hand7 = new ArrayList<>();
		hand7.add(new Card(Rank.KING, Suit.SPADES));
		hand7.add(new Card(Rank.JACK, Suit.SPADES));
		assertEquals(BlackjackModel.assessHand(hand7), HandAssessment.NORMAL);
	}
	
	@Test
	public void testDealerShouldTakeCard() {
		BlackjackModel game = new BlackjackModel();
		
		//passed
		ArrayList<Card> hand1 = new ArrayList<>();
		hand1.add(new Card(Rank.KING, Suit.SPADES));
		game.setDealerCards(hand1);
		assertTrue(game.dealerShouldTakeCard());
		
		//passed
		ArrayList<Card> hand2 = new ArrayList<>();
		hand2.add(new Card(Rank.THREE, Suit.SPADES));
		hand2.add(new Card(Rank.FOUR, Suit.SPADES));
		hand2.add(new Card(Rank.ACE, Suit.SPADES));
		game.setDealerCards(hand2);
		assertFalse(game.dealerShouldTakeCard());
		
		//passed
		ArrayList<Card> hand3 = new ArrayList<>();
		hand3.add(new Card(Rank.TWO, Suit.SPADES));
		hand3.add(new Card(Rank.FOUR, Suit.SPADES));
		hand3.add(new Card(Rank.ACE, Suit.SPADES));
		game.setDealerCards(hand3);
		assertTrue(game.dealerShouldTakeCard());
		
		//passed
		ArrayList<Card> hand4 = new ArrayList<>();
		hand4.add(new Card(Rank.EIGHT, Suit.SPADES));
		hand4.add(new Card(Rank.EIGHT, Suit.HEARTS));
		hand4.add(new Card(Rank.ACE, Suit.SPADES));
		game.setDealerCards(hand4);
		assertFalse(game.dealerShouldTakeCard());
		
		//passed
		ArrayList<Card> hand5 = new ArrayList<>();
		hand5.add(new Card(Rank.FIVE, Suit.SPADES));
		hand5.add(new Card(Rank.KING, Suit.SPADES));
		hand5.add(new Card(Rank.TWO, Suit.SPADES));
		game.setDealerCards(hand5);
		assertFalse(game.dealerShouldTakeCard());
		
		
	}


}
