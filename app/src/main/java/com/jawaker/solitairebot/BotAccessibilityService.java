package com.jawaker.solitairebot;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.os.SystemClock;
import android.util.Log;

public class BotAccessibilityService extends AccessibilityService {

    private static final String TAG = "JawakerBot";
    private long lastEventTime = 0;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event == null) return;

        // منع معالجة الأحداث المتكررة بسرعة كبيرة
        long now = SystemClock.uptimeMillis();
        if (now - lastEventTime < 250) return;
        lastEventTime = now;

        CharSequence packageName = event.getPackageName();
        if (packageName == null) return;

        String currentPackage = packageName.toString();

        // نراقب جواكر فقط، ولا ننفّذ أي حركة تلقائية حالياً
        if (currentPackage.toLowerCase().contains("jawaker")) {
            Log.d(TAG, "Jawaker detected: " + currentPackage);

            AccessibilityNodeInfo root = getRootInActiveWindow();
            if (root != null) {
                inspectScreen(root);
                root.recycle();
            }
        }
    }

    private void inspectScreen(AccessibilityNodeInfo node) {
        if (node == null) return;

        CharSequence text = node.getText();
        CharSequence description = node.getContentDescription();

        if (text != null && text.length() > 0) {
            Log.d(TAG, "Screen text: " + text);
        }

        if (description != null && description.length() > 0) {
            Log.d(TAG, "Screen description: " + description);
        }

        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo child = node.getChild(i);
            if (child != null) {
                inspectScreen(child);
                child.recycle();
            }
        }
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "Accessibility service interrupted");
    }
}
