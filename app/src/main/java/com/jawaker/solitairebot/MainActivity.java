package com.jawaker.solitairebot;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(32, 32, 32, 32);

        TextView title = new TextView(this);
        title.setText("Jawaker Solitaire Bot");
        title.setTextSize(24);
        title.setGravity(Gravity.CENTER);
        layout.addView(title);

        Button openButton = new Button(this);
        openButton.setText("Open Jawaker");
        layout.addView(openButton);

        openButton.setOnClickListener(v -> {
            Intent intent = getPackageManager()
                    .getLaunchIntentForPackage("com.jawaker");

            if (intent != null) {
                startActivity(intent);
            } else {
                try {
                    Intent browserIntent = new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://www.jawaker.com/")
                    );
                    startActivity(browserIntent);
                } catch (Exception e) {
                    Toast.makeText(
                            this,
                            "Could not open Jawaker",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

        setContentView(layout);
    }
}
