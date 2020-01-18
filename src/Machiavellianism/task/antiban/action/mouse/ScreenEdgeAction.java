package Machiavellianism.task.antiban.action.mouse;

import Machiavellianism.task.antiban.action.AntiBanAction;
import Machiavellianism.util.CommonUtil;

import org.powerbot.script.Area;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;

/**
 * Moves the mouse to the edge of the screen.
 */
public class ScreenEdgeAction extends AntiBanAction {

    public ScreenEdgeAction(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void Move(Area area) {
        int x;
        int y;

        // Randomly select a side of the screen to move to
        switch (Random.nextInt(0, 4)) {
            case 0:
                // North
                x = Random.nextInt(CommonUtil.ClientCornerPoint.NW.x, CommonUtil.ClientCornerPoint.NE.x + 1);
                y = CommonUtil.ClientCornerPoint.NE.y;
                break;
            case 1:
                // East
                x = CommonUtil.ClientCornerPoint.NE.x;
                y = Random.nextInt(CommonUtil.ClientCornerPoint.NE.y, CommonUtil.ClientCornerPoint.SE.y + 1);
                break;
            case 2:
                // SOUTH
                x = Random.nextInt(CommonUtil.ClientCornerPoint.SW.x, CommonUtil.ClientCornerPoint.SE.x + 1);
                y = CommonUtil.ClientCornerPoint.SE.y;
                break;
            default:
                // West
                x = CommonUtil.ClientCornerPoint.NW.x;
                y = Random.nextInt(CommonUtil.ClientCornerPoint.NW.y, CommonUtil.ClientCornerPoint.SW.y + 1);
                break;
        }

        System.out.println("Moving mouse to the edge of the screen.");

        this.ctx.input.move(new Point(x, y));
    }
}
