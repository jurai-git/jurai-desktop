package com.jurai.ui.util;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class ImageUtils {

    public static double computeCenterLuminance(Image image) {
        if (image == null || image.getPixelReader() == null)
            return 0; // will not throw IllegalArgumentException to simplify exception handling and prevent crashes

        PixelReader reader = image.getPixelReader();

        int regionSize = 32;
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        if (width < regionSize || height < regionSize)
            regionSize = Math.min(width, height);

        int startX = width / 2 - regionSize / 2;
        int startY = height / 2 - regionSize / 2;

        double totalLuminance = 0;
        int count = 0;

        for (int y = 0; y < regionSize; y++) {
            for (int x = 0; x < regionSize; x++) {
                Color color = reader.getColor(startX + x, startY + y);
                double luminance = 0.2126 * color.getRed()
                        + 0.7152 * color.getGreen()
                        + 0.0722 * color.getBlue();
                totalLuminance += luminance;
                count++;
            }
        }

        return totalLuminance / count;
    }
}
