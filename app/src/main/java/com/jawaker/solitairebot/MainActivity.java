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
        status.setText("Bot ready\n\nPress the button to test.");
        status.setTextSize(17);
        status.setTextColor(Color.WHITE);
        status.setGravity(Gravity.CENTER);
        status.setPadding(0, 30, 0, 30);

        Button start = new Button(this);
        start.setText("Test Bot");

        start.setOnClickListener(v -> {
            status.setText(
                "Test successful!\n\n" +
                "The app responds to button presses.\n\n" +
                "Card detection is not added yet."
            );
        });

        layout.addView(title);
        layout.addView(status);
        layout.addView(start);

        setContentView(layout);
    }
}
