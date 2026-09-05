import java.awt.Color;

public final class Theme {
    private Theme() {}
    public static boolean dark = true;

    public static Color bg() { return dark ? new Color(12, 18, 30) : new Color(244, 247, 251); }
    public static Color surface() { return dark ? new Color(21, 30, 46) : Color.WHITE; }
    public static Color surface2() { return dark ? new Color(27, 39, 59) : new Color(235, 240, 247); }
    public static Color text() { return dark ? new Color(244, 247, 255) : new Color(30, 38, 50); }
    public static Color muted() { return dark ? new Color(163, 173, 193) : new Color(100, 110, 125); }
    public static Color border() { return dark ? new Color(45, 60, 84) : new Color(218, 224, 233); }
    public static Color primary() { return new Color(92, 82, 255); }
    public static Color secondary() { return new Color(0, 177, 255); }
    public static Color success() { return new Color(38, 190, 120); }
    public static Color danger() { return new Color(230, 70, 90); }
    public static Color warning() { return new Color(245, 158, 50); }
}
