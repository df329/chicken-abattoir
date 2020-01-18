package Machiavellianism.util;

import java.awt.*;

/**
 * Class for common utilities.
 */
public class CommonUtil {
    public static final int PLAYER_IDLE = -1;
    public static final int[] CHICKEN_IDS = {
            1173,
            1174
    };

    /**
     * OSRS client's corner points, this will be inaccurate with client resizing.
     */
    public static class ClientCornerPoint {
        public static final Point NE = new Point(770, -1);
        public static final Point SE = new Point(770, 510);
        public static final Point SW = new Point(-1, 510);
        public static final Point NW = new Point(-1, -1);
    }
}
