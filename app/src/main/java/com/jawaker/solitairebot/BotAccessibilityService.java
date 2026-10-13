package com.jawaker.solitairebot;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.os.SystemClock;
import android.util.Log;
import android.graphics.Rect;

public class BotAccessibilityService extends AccessibilityService {

    private static final String TAG = "JawakerBot";
    private long lastScanTime = 0;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event == null) return;

        long now = SystemClock.uptimeMillis();

        // فحص الشاشة عند تغيّر محتواها، مع منع التكرار السريع.
        if (now - lastScanTime < 700) return;
        lastScanTime = now;

        AccessibilityNodeInfo root = getRootInActiveWindow();
        if (root == null) {
            Log.d(TAG, "No active window available");
            return;
        }

        try {
            Log.d(TAG, "========== SCREEN SCAN START ==========");
            Log.d(TAG, "Package: " + event.getPackageName());

            scanAllNodes(root, 0);

            Log.d(TAG, "========== SCREEN SCAN END ==========");
        } finally {
            root.recycle();
        }
    }

    private void scanAllNodes(AccessibilityNodeInfo node, int depth) {
        if (node == null) return;

        Rect bounds = new Rect();
        node.getBoundsInScreen(bounds);

        CharSequence text = node.getText();
        CharSequence description = node.getContentDescription();
        CharSequence className = node.getClassName();

        Log.d(TAG,
                "depth=" + depth
                + " class=" + className
                + " text=" + text
                + " description=" + description
                + " clickable=" + node.isClickable()
                + " enabled=" + node.isEnabled()
                + " bounds=" + bounds.flattenToString());

        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo child = node.getChild(i);

            if (child != null) {
                try {
                    scanAllNodes(child, depth + 1);
                } finally {
                    child.recycle();
                }
            }
        }
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "Accessibility service interrupted");
    }
}
