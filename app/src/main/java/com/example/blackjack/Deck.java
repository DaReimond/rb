package com.example.blackjack;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<Card>();

    public Deck() {
        this.deck = Deck.createNewDeck();
    }

    public void shuffle(){
        Random random = new Random();

        for(int i = 0; i < deck.size(); i++){
            int randomIndexToSwap = random.nextInt(deck.size());
            Card temporaryCard = deck.get(randomIndexToSwap);
            deck.set(randomIndexToSwap, deck.get(i));
            deck.set(i, temporaryCard);
        }
    }

    public Card takeCard(){
        return deck.remove(0);
    }

    public void returnCard(Card card){

            deck.add(card);

    }

    public int getSize(){
        return deck.size();
    }

    public ArrayList<Card> getAllCards(){
        return new ArrayList<Card>(deck);
    }
    private static ArrayList<Card> createNewDeck(){
        ArrayList<Card> deck = new ArrayList<Card>();
        for(int i = 1; i <= 4; i++){
            for(int j = 1; j<= 13; j++){
                deck.add(new Card(i,j));
            }
        }
        return deck;
    }
}
