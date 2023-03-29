package smeen.global;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public final class Fonts {

    public static final String THAI_FONT_FAMILY;

    public static final Font BASIC_REGULAR_FONT;

    public static final Font BASIC_BOLD_FONT;

    public static final Font SMALL_REGULAR_FONT;

    public static final Font EXTRA_SMALL_REGULAR_FONT;

    static{
        BASIC_BOLD_FONT = Font.loadFont(Fonts.class.getResourceAsStream("/fonts/IBMPlexSansThai-Bold.ttf"), 24);
        BASIC_REGULAR_FONT = Font.loadFont(Fonts.class.getResourceAsStream("/fonts/IBMPlexSansThai-Regular.ttf"), 24);
        SMALL_REGULAR_FONT  = Font.loadFont(Fonts.class.getResourceAsStream("/fonts/IBMPlexSansThai-Bold.ttf"), 14);
        EXTRA_SMALL_REGULAR_FONT = Font.loadFont(Fonts.class.getResourceAsStream("/fonts/IBMPlexSansThai-Bold.ttf"), 10);
        THAI_FONT_FAMILY = BASIC_BOLD_FONT.getFamily();
    }

}
