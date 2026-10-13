package com.jawaker.solitairebot;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    static class Card {
        String rank;
        String suit;

        Card(String rank, String suit) {
            this.rank = rank;
            this.suit = suit;
        }

        boolean isRed() {
            return suit.equals("♥") || suit.equals("♦");
        }

        boolean canMoveOnto(Card other) {
            if (other == null) {
                return rank.equals("K");
            }

            return isRed() != other.isRed()
                    && value(rank) == value(other.rank) + 1;
        }

        static int value(String rank) {
            switch (rank) {
                case "A": return 1;
                case "J": return 11;
                case "Q": return 12;
                case "K": return 13;
                default: return Integer.parseInt(rank);
            }
        }

        @Override
        public String toString() {
            return rank + suit;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(32, 32, 32, 32);

        TextView title = new TextView(this);
        title.setText("Jawaker Solitaire Bot");
        title.setTextSize(23);
        title.setGravity(Gravity.CENTER);
        layout.addView(title);

        Button openButton = new Button(this);
        openButton.setText("Open Jawaker");
        layout.addView(openButton);

        openButton.setOnClickListener(v -> {
            android.content.Intent intent =
                    getPackageManager().getLaunchIntentForPackage("com.jawaker");

            if (intent != null) {
                startActivity(intent);
            } else {
                startActivity(new android.content.Intent(
                        android.content.Intent.ACTION_VIEW,
                        android.net.Uri.parse("https://www.jawaker.com/")
                ));
            }
        });

        Button testButton = new Button(this);
        testButton.setText("Test Solitaire Rules");
        layout.addView(testButton);

        TextView result = new TextView(this);
        result.setTextSize(18);
        result.setGravity(Gravity.CENTER);
        layout.addView(result);

        testButton.setOnClickListener(v -> {
            Card queen = new Card("Q", "♠");
            Card king = new Card("K", "♥");

            if (queen.canMoveOnto(king)) {
                result.setText("Rules test passed: Q♠ can move onto K♥");
            } else {
                result.setText("Rules test failed");
            }
        });

        setContentView(layout);
    }
}
