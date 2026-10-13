package com.jawaker.solitairebot;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends Activity {

    TextView status;

    static class Card {
        String suit;
        int value;
        boolean faceUp;

        Card(String suit, int value, boolean faceUp) {
            this.suit = suit;
            this.value = value;
            this.faceUp = faceUp;
        }

        boolean canMoveOn(Card other) {
            if (!faceUp) return false;
            if (other == null) return value == 13;
            if (!other.faceUp) return false;

            boolean red = suit.equals("H") || suit.equals("D");
            boolean otherRed =
                other.suit.equals("H") || other.suit.equals("D");

            return red != otherRed && value == other.value - 1;
        }

        String name() {
            String[] ranks = {
                "", "A", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "J", "Q", "K"
            };
            return ranks[value] + suit;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(32, 32, 32, 32);
        layout.setBackgroundColor(Color.rgb(20, 70, 45));

        TextView title = new TextView(this);
        title.setText("Jawaker Solitaire Bot");
        title.setTextSize(25);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);

        status = new TextView(this);
        status.setText("Ready to test move selection.");
        status.setTextSize(17);
        status.setTextColor(Color.WHITE);
        status.setGravity(Gravity.CENTER);
        status.setPadding(0, 30, 0, 30);

        Button start = new Button(this);
        start.setText("Find Best Move");

        start.setOnClickListener(v -> {
            Card[] choices = {
                new Card("S", 12, true),
                new Card("H", 9, true),
                new Card("D", 7, true)
            };

            Card target = new Card("H", 13, true);
            Card best = null;

            for (Card card : choices) {
                if (card.canMoveOn(target)) {
                    best = card;
                    break;
                }
            }

            if (best != null) {
                status.setText(
                    "Move found!\n\n" +
                    best.name() + " can move onto " +
                    target.name() + ".\n\n" +
                    "This is a test with sample cards, " +
                    "not the live Jawaker board."
                );
            } else {
                status.setText("No legal move found.");
            }
        });

        layout.addView(title);
        layout.addView(status);
        layout.addView(start);

        setContentView(layout);
    }
}
