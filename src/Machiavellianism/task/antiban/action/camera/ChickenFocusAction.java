package Machiavellianism.task.antiban.action.camera;

import Machiavellianism.task.antiban.action.AntiBanAction;
import Machiavellianism.util.CommonUtil;

import org.powerbot.script.Area;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Npc;

/**
 * Focuses the camera on a chicken.
 */
public class ChickenFocusAction extends AntiBanAction {

    public ChickenFocusAction(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void Move(Area area) {
        for (Npc c : ctx.npcs.within(area).select().id(CommonUtil.CHICKEN_IDS).shuffle()) {
            if (c.valid()) {
                System.out.println("Turning towards a random chicken.");

                ctx.camera.turnTo(c);
                break;
            }
        }
    }
}
