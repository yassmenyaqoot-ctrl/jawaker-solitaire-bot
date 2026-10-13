
package com.jawaker.solitairebot;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;

public class MainActivity extends Activity {

    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(24, 24, 24, 24);

        TextView title = new TextView(this);
        title.setText("Jawaker Solitaire Bot");
        title.setTextSize(22);
        title.setGravity(Gravity.CENTER);
        layout.addView(title);

        Button openButton = new Button(this);
        openButton.setText("Open Jawaker");
        layout.addView(openButton);

        openButton.setOnClickListener(v -> {
            Intent intent =
                getPackageManager().getLaunchIntentForPackage("com.jawaker");

            if (intent != null) {
                startActivity(intent);
            } else {
                startActivity(new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.jawaker.com/")
                ));
            }
        });

        Button readButton = new Button(this);
        readButton.setText("Read Last Screen Scan");
        layout.addView(readButton);

        ScrollView scroll = new ScrollView(this);
        result = new TextView(this);
        result.setTextSize(14);
        result.setText("افتح جواكر أولاً، ثم ارجع واضغط قراءة آخر فحص.");
        scroll.addView(result);
        layout.addView(scroll);

        readButton.setOnClickListener(v -> readScan());

        setContentView(layout);
    }

    private void readScan() {
        try (FileInputStream input =
                 openFileInput("screen_scan.txt");
             ByteArrayOutputStream output =
                 new ByteArrayOutputStream()) {

            byte[] buffer = new byte[4096];
            int count;

            while ((count = input.read(buffer)) != -1) {
                output.write(buffer, 0, count);
            }

            result.setText(output.toString("UTF-8"));

        } catch (Exception e) {
            result.setText(
                "ما في فحص محفوظ لسا. فعّل خدمة إمكانية الوصول " +
                "وافتح جواكر ثم ارجع جرّب."
            );
        }
    }
}
