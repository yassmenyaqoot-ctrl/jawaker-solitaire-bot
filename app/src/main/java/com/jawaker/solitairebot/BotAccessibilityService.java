
package com.jawaker.solitairebot;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.os.SystemClock;
import android.util.Log;
import android.graphics.Rect;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public class BotAccessibilityService extends AccessibilityService {

    private static final String TAG = "JawakerBot";
    private long lastScanTime = 0;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event == null) return;

        long now = SystemClock.uptimeMillis();
        if (now - lastScanTime < 1000) return;
        lastScanTime = now;

        AccessibilityNodeInfo root = getRootInActiveWindow();
        if (root == null) return;

        StringBuilder report = new StringBuilder();

        try {
            report.append("SCREEN SCAN\n");
            report.append("Package: ")
                    .append(event.getPackageName())
                    .append("\n\n");

            scanAllNodes(root, 0, report);

            try (FileOutputStream output = openFileOutput(
                    "screen_scan.txt", MODE_PRIVATE)) {
                output.write(report.toString().getBytes(
                        StandardCharsets.UTF_8));
            }

            Log.d(TAG, "Scan saved to screen_scan.txt");
        } catch (Exception e) {
            Log.e(TAG, "Could not save scan", e);
        } finally {
            root.recycle();
        }
    }

    private void scanAllNodes(
            AccessibilityNodeInfo node,
            int depth,
            StringBuilder report) {

        if (node == null) return;

        Rect bounds = new Rect();
        node.getBoundsInScreen(bounds);

        report.append("Depth: ").append(depth)
                .append(" | Class: ").append(node.getClassName())
                .append(" | Text: ").append(node.getText())
                .append(" | Description: ")
                .append(node.getContentDescription())
                .append(" | Clickable: ").append(node.isClickable())
                .append(" | Bounds: ")
                .append(bounds.flattenToString())
                .append("\n");

        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo child = node.getChild(i);

            if (child != null) {
                try {
                    scanAllNodes(child, depth + 1, report);
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
