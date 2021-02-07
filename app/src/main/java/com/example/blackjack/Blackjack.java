package com.example.blackjack;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Blackjack extends AppCompatActivity {


    //SharedPreference


    // BetPopup Box Objects
    private AlertDialog.Builder betDialogBuilder;
    private AlertDialog betDialog;
    private TextView popup_betView, popup_moneyTextView;
    private Button popup_bet, popup_undo, bet0, bet2, bet3, bet4, bet5, bet1, returnButton;
    private int betNum = 0;

    //RoundResult Popup Objects

    private AlertDialog.Builder resultDialogBuilder;
    private AlertDialog resultDialog;
    private Button betAgainButton, titleScreenButton;
    private TextView resultTextView;


    // Main Objects
    private TextView moneyTextView, playerBetTextView, dealerCardSum, playerCardSum;
    private Button hitButton, standButton;

    ArrayList<ImageView> playerCards = new ArrayList<ImageView>();
    ArrayList<ImageView> dealerCards = new ArrayList<ImageView>();

    public static int score;

    //Card Places

    public ArrayList<Card> playerTableCards = new ArrayList<Card>();
    public ArrayList<Card> dealerTableCards = new ArrayList<Card>();
    Deck deck = new Deck();

    private int playerSum = 0;
    private int dealerSum = 0;

    private int money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackjack);

        @SuppressLint("WrongConstant") SharedPreferences sp = getSharedPreferences("MySharedPref", MODE_APPEND);


        money = sp.getInt("money", money);
        score = sp.getInt("score", score);

        if(money == 0){
            money = 100;
            score = 100;
        }

        dealerCardSum = (TextView) findViewById(R.id.dealerCardSum);
        playerCardSum = (TextView) findViewById(R.id.playerCardSum);

        playerCards.add(findViewById(R.id.hitCard0));
        playerCards.add(findViewById(R.id.hitCard1));
        playerCards.add(findViewById(R.id.hitCard2));
        playerCards.add(findViewById(R.id.hitCard3));
        playerCards.add(findViewById(R.id.hitCard4));
        playerCards.add(findViewById(R.id.hitCard5));

        dealerCards.add(findViewById(R.id.dealerCard0));
        dealerCards.add(findViewById(R.id.dealerCard1));
        dealerCards.add(findViewById(R.id.dealerCard2));
        dealerCards.add(findViewById(R.id.dealerCard3));
        dealerCards.add(findViewById(R.id.dealerCard4));
        dealerCards.add(findViewById(R.id.dealerCard5));

        hitButton = (Button) findViewById(R.id.hitButton);
        standButton = (Button) findViewById(R.id.standButton);

        betPopupDialog();
        deck.shuffle();

    }
    //START ROUND!
    public void startRound(int money, int bet){
        resetTable();

        moneyTextView = (TextView) findViewById(R.id.moneyTextView);
        moneyTextView.setText(money+"$");

        playerBetTextView = (TextView) findViewById(R.id.playerBetTextView);
        playerBetTextView.setText(bet+"$");

        playerTableCards.add(takeAndDisplayCard(playerCards.get(0)));

        dealerCards.get(0).setVisibility(View.VISIBLE);
        dealerTableCards.add(takeAndDisplayCard(dealerCards.get(1)));

        playerTableCards.add(takeAndDisplayCard(playerCards.get(1)));

        playerSum = getCardsSum(playerTableCards);

        dealerCardSum.setText("Count: "+getCardsSum(dealerTableCards));
        playerCardSum.setText("Count: "+getCardsSum(playerTableCards));

        if(playerSum == 21) standButtonAction(21);
    }

    public void returnCardsToDeck(ArrayList<Card> arrayList) {

    int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            deck.returnCard(arrayList.remove(0));
        }
    }

    public int getCardsSum(ArrayList<Card> arrayList){
        int countAce = 0;
        int sum = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            sum += arrayList.get(i).getValue();
        }
        if(sum > 21)
            for (int i = 0; i < arrayList.size(); i++){
                if(arrayList.get(i).getRank() == 1) countAce++;
        }
        return sum - countAce*10;
    }

    public Card takeAndDisplayCard(ImageView imageView) {
        Card temporaryCard;
        temporaryCard = deck.takeCard();
        temporaryCard.displayCard(imageView, getApplicationContext());

        return temporaryCard;
    }

    public ImageView getNextCardPosition(ArrayList<ImageView> arrayList){
        int index = 0;
        while(arrayList.get(index).getVisibility() != View.INVISIBLE){
            index++;
            if(index == 6) return null;
        }
        return arrayList.get(index);
    }

    public void resultPopupDialog(String resultString) {

        resultDialogBuilder = new AlertDialog.Builder(this);
        final View resultPopupView = getLayoutInflater().inflate(R.layout.result_popup, null);

        betAgainButton = (Button) resultPopupView.findViewById(R.id.betAgainButton);
        titleScreenButton = (Button) resultPopupView.findViewById(R.id.titleScreenButton);

        resultTextView = (TextView) resultPopupView.findViewById(R.id.resultTextView);
        resultTextView.setText(resultString);

        resultDialogBuilder.setView(resultPopupView);
        resultDialog = resultDialogBuilder.create();
        resultDialog.show();
        resultDialog.setCancelable(false);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putInt("money", money);
        myEdit.putInt("score", score);

        myEdit.apply();

        betAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultDialog.dismiss();
                betPopupDialog();
            }
        });

        titleScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultDialog.dismiss();
                openActivityTitleScreen();

            }
        });

    }

    private void openActivityTitleScreen() {
        Intent openTitleScreen = new Intent(this, MainActivity.class);
        startActivity(openTitleScreen);
    }

    private void openActivityGameOver() {
        Intent openGameOver = new Intent(this, activity_game_over.class);
        startActivity(openGameOver);
    }

    public void betPopupDialog(){
        betDialogBuilder = new AlertDialog.Builder(this);
        final View betPopupView = getLayoutInflater().inflate(R.layout.bet_popup, null);

        popup_betView = (TextView) betPopupView.findViewById(R.id.betTextView);
        popup_moneyTextView = (TextView) betPopupView.findViewById(R.id.betMoneyTextView);
        popup_moneyTextView.setText(money+"$");

        bet0 = (Button) betPopupView.findViewById(R.id.bet_button0);
        bet1 = (Button) betPopupView.findViewById(R.id.bet_button1);
        bet2 = (Button) betPopupView.findViewById(R.id.bet_button2);
        bet3 = (Button) betPopupView.findViewById(R.id.bet_button3);
        bet4 = (Button) betPopupView.findViewById(R.id.bet_button4);
        bet5 = (Button) betPopupView.findViewById(R.id.bet_button5);
        popup_undo = (Button) betPopupView.findViewById(R.id.undoButton);
        popup_bet = (Button) betPopupView.findViewById(R.id.betButton);
        returnButton = (Button) betPopupView.findViewById(R.id.returnButton);

        betDialogBuilder.setView(betPopupView);
        betDialog = betDialogBuilder.create();
        betDialog.show();
        betDialog.setCancelable(false);

        betNum = 0;

        ArrayList<Integer> betHistory = new ArrayList<Integer>();
        betHistory.add(0);

        popup_bet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(betNum > 0){
                betDialog.dismiss();
                startRound(money, betNum); }
            }
        });
        popup_undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(betHistory.get(betHistory.size()-1) != 0){
                    money += betHistory.get(betHistory.size()-1);
                    betNum -= betHistory.get(betHistory.size()-1);
                    betHistory.remove(betHistory.size()-1);

                    popup_betView.setText(betNum+"$");
                }
            }
        });
        bet0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(money - 5 >= 0){
                    betNum += 5;
                    money -= 5;
                    popup_betView.setText(betNum+"$");
                    betHistory.add(5);
                }
            }
        });
        bet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(money - 10 >= 0){
                    betNum += 10;
                    money -= 10;
                    popup_betView.setText(betNum+"$");
                    betHistory.add(10);
                }
            }
        });
        bet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(money - 50 >= 0){
                    betNum += 50;
                    money -= 50;
                    popup_betView.setText(betNum+"$");
                    betHistory.add(50);
                }
            }
        });
        bet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(money - 100 >= 0){
                    betNum += 100;
                    money -= 100;
                    popup_betView.setText(betNum+"$");
                    betHistory.add(100);
                }
            }
        });
        bet4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(money - 1000 >= 0){
                    betNum += 1000;
                    money -= 1000;
                    popup_betView.setText(betNum+"$");
                    betHistory.add(1000);
                }

            }
        });
        bet5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(money - 2500 >= 0){
                    betNum += 2500;
                    money -= 2500;
                    popup_betView.setText(betNum+"$");
                    betHistory.add(2500);
                }
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityTitleScreen();
                finish();
                return;
            }
        });

        }
    public void hitMeAction(View view) {
        if(getNextCardPosition(playerCards) != null){
        playerTableCards.add(takeAndDisplayCard(getNextCardPosition(playerCards)));
        playerSum = getCardsSum(playerTableCards);
        playerCardSum.setText("Count: "+getCardsSum(playerTableCards));

        if(playerSum >= 21) standButtonAction(playerSum);

        }
    }

    private void standButtonAction(int playerSum) {
        hitButton.setVisibility(View.INVISIBLE);
        standButton.setVisibility(View.INVISIBLE);

        dealerTableCards.add(takeAndDisplayCard(dealerCards.get(0)));
        dealerCards.get(0).setVisibility(View.VISIBLE);
        dealerSum = getCardsSum(dealerTableCards);
        dealerCardSum.setText("Count: "+getCardsSum(dealerTableCards));

        Thread dealerThread = new Thread() {
            public void run() {

                while(dealerSum < 16){
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Card card = deck.takeCard();
                    dealerTableCards.add(card);
                    dealerSum = getCardsSum(dealerTableCards);

                    standButton.getHandler().post(new Runnable() {
                        public void run() {
                            card.displayCard(getNextCardPosition(dealerCards), getApplicationContext());
                            dealerCardSum.setText("Count: "+getCardsSum(dealerTableCards));
                        }
                    });
                }

                String result = "-"+ betNum + "$";

                if(playerSum == dealerSum){
                    money += betNum; result = "DRAW!";}
                if(playerSum > 21 && dealerSum > 21){ money += betNum; result = "DRAW!";}

                if(dealerSum > 21 && playerSum <= 21) {money += 2*betNum; result = "+" + betNum + "$";}

                if(playerSum > dealerSum && playerSum <= 21){
                    money += 2*betNum; result = "+"+ betNum + "$";}

                if(money <= 0) {
                    openActivityGameOver();
                    finish();
                    return;
                }

                if(score < money) score = money;

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String finalResult = result;
                standButton.getHandler().post(new Runnable() {
                    public void run() {
                        resultPopupDialog(finalResult);
                        returnCardsToDeck(dealerTableCards);
                        returnCardsToDeck(playerTableCards);
                        deck.shuffle();
                    }
                });
            }
        };

        dealerThread.start();
    }

    private void resetTable() {


        for (ImageView n : playerCards) {
            n.setVisibility(View.INVISIBLE);
        }
        for (ImageView n : dealerCards) {
            n.setVisibility(View.INVISIBLE);
        }
        dealerSum = 0;
        playerSum = 0;

        hitButton.setVisibility(View.VISIBLE);
        standButton.setVisibility(View.VISIBLE);

        dealerCards.get(0).setImageResource(R.drawable.card_back);
    }

    public void standButton(View view) {
        standButtonAction(playerSum);
    }
}
