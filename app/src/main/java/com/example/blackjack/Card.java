package com.example.blackjack;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;

public class Card {
    private int suit;
    private int rank;

    public Card(int suit, int rank) {
        this.suit = suit;
        this.rank = rank;

    }

    public int getSuit() {
        return this.suit;
    }

    public int getRank() {
        return this.rank;
    }

    public int getValue(){

        if (this.rank >= 10) return 10;
        if(this.rank == 1)return 11;
        return this.rank;
    }

    public void displayCard(ImageView imageView, Context context) {

        Resources resources = context.getResources();
        String resourceName = this.toString();
        int id = resources.getIdentifier(resourceName, "drawable", context.getPackageName());

        imageView.setImageResource(id);
        imageView.setVisibility(View.VISIBLE);
    }

    public String toString(){
        String stringSuit = "";
        String stringRank = ""+this.rank;

        if(this.suit == 1){
            stringSuit = "diamonds";
        }
        if(this.suit == 2){
            stringSuit = "hearts";
        }
        if(this.suit == 3){
            stringSuit = "spades";
        }
        if(this.suit == 4){
            stringSuit = "clovers";
        }

        return (stringSuit+"_"+stringRank);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card that = (Card) o;
        return this.toString().equals(that.toString());
    }
}
