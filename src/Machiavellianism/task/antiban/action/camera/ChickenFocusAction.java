package Machiavellianism.task.antiban.action.camera;

import Machiavellianism.task.antiban.action.AntiBanAction;
import Machiavellianism.util.CommonUtil;

import org.powerbot.script.Area;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Npc;

import java.util.logging.Logger;

/**
 * Focuses the camera on a chicken.
 */
public class ChickenFocusAction extends AntiBanAction {

    public ChickenFocusAction(ClientContext ctx, Logger logger) {
        super(ctx, logger);
    }

    @Override
    public void Move(Area area) {
        for (Npc c : ctx.npcs.within(area).select().id(CommonUtil.CHICKEN_IDS).shuffle()) {
            if (c.valid()) {
                this.logger.info("Turning towards a random chicken.");

                ctx.camera.turnTo(c);
                break;
            }
        }
    }
}
