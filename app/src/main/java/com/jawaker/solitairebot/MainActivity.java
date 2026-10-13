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
            if (other == null) return value == 13;

            boolean thisRed =
                suit.equals("H") || suit.equals("D");
            boolean otherRed =
                other.suit.equals("H") || other.suit.equals("D");

            return faceUp
                && other.faceUp
                && thisRed != otherRed
                && value == other.value - 1;
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
        status.setText("Solitaire move engine ready.");
        status.setTextSize(17);
        status.setTextColor(Color.WHITE);
        status.setGravity(Gravity.CENTER);
        status.setPadding(0, 30, 0, 30);

        Button start = new Button(this);
        start.setText("Test Card Rules");

        start.setOnClickListener(v -> {
            Card king = new Card("H", 13, true);
            Card queen = new Card("S", 12, true);

            boolean valid = queen.canMoveOn(king);

            status.setText(valid
                ? "Rules test passed! Queen can move on King."
                : "Rules test failed.");
        });

        layout.addView(title);
        layout.addView(status);
        layout.addView(start);

        setContentView(layout);
    }
}
