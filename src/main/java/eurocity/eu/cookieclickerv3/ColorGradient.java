package eurocity.eu.cookieclickerv3;

import net.md_5.bungee.api.ChatColor;

public class ColorGradient {

        public String applyCookieGradient(String text) {
            StringBuilder gradientText = new StringBuilder();

            int redStart = 255;
            int greenStart = 215;
            int blueStart = 0;

            int redEnd = 139;
            int greenEnd = 69;
            int blueEnd = 19;

            for (int i = 0; i < text.length(); i++) {
                float ratio = (float) i / (text.length() - 1);

                int red = (int) (redStart + ratio * (redEnd - redStart));
                int green = (int) (greenStart + ratio * (greenEnd - greenStart));
                int blue = (int) (blueStart + ratio * (blueEnd - blueStart));

                ChatColor color = ChatColor.of(String.format("#%02X%02X%02X", red, green, blue));
                gradientText.append(color).append(text.charAt(i));
            }

            return gradientText.toString();
        }
    }