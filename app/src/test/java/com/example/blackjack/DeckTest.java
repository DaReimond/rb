package com.example.blackjack;

import junit.framework.TestCase;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class DeckTest extends TestCase {


    public void testShuffle() {
        Deck unshuffledDeck = new Deck();
        Deck deck = new Deck();
        assertEquals(52, deck.getSize());
        deck.shuffle();
        assertEquals(52, deck.getSize());
        ArrayList<Card> shuffledCards = deck.getAllCards();
        ArrayList<Card> unshuffledCards = unshuffledDeck.getAllCards();
        unshuffledCards.forEach(card -> {
            assertThat(shuffledCards.contains(card), is(true));
        });


    }

    public void testTakeCard() {
        Deck deck = new Deck();
        assertEquals(52, deck.getSize());
        Card card = deck.takeCard();
        assertEquals(51, deck.getSize());
        assertEquals(new Card(1, 1), card);
        deck.takeCard().toString;

    }
}