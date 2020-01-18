package Machiavellianism.task.grounditem;

import Machiavellianism.task.*;
import Machiavellianism.ui.ISettingsUi;
import Machiavellianism.util.*;

import org.powerbot.script.Area;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GroundItem;

import java.util.logging.Logger;

/**
 * Take ground item (chicken feather) task.
 */
public class TakeGroundItemTask extends Task<ClientContext> {
    private static final int CHICKEN_FEATHER_ID = 314;
    private final ISettingsUi settings;

    public TakeGroundItemTask(ClientContext ctx, Logger logger, ISettingsUi settingsUi) {
        super(ctx, logger);

        settings = settingsUi;
    }

    /**
     * Condition to pick up a chicken feather.
     * @return true to pick up the chicken feather
     */
    @Override
    public boolean activate() {
        if (!settings.pickupChickenFeathers()) {
            return false;
        }

        // Full inventory with feather is okay
        if (ctx.inventory.isFull()) {
            this.logger.info("Inventory full: dropping unwanted chicken loot.");
            DropItemsUtil.DropAllUnwantedChickenLoot(ctx);
        }

        // Inventory is not full, chicken feather exists and player is idle
        boolean chickenFeathersExistInInventory = ctx.inventory.select().id(CHICKEN_FEATHER_ID).count(true) > 0;
        return (!ctx.inventory.isFull() || chickenFeathersExistInInventory)
                && !ctx.groundItems.select().id(CHICKEN_FEATHER_ID).isEmpty()
                && ctx.players.local().animation() == CommonUtil.PLAYER_IDLE;
    }

    /**
     * Attempts to pick up a chicken feather in the area and returns the number picked up.
     * @param area the area to execute the action
     * @return the number of chicken feathers picked up from the action
     */
    @Override
    public int execute(Area area) {
        GroundItem chickenFeather = ctx.groundItems.within(area).nearest().poll();
        if (!chickenFeather.valid()) {
            return 0;
        }

        int oldChickenFeatherCount = ctx.inventory.select().id(CHICKEN_FEATHER_ID).count(true);

        // Move to the chicken feather if necessary
        if (!chickenFeather.inViewport()) {
            this.logger.info("Moving to chicken feather.");
            ctx.camera.turnTo(chickenFeather);
            ctx.movement.step(chickenFeather);
        }

        WaitBeforeAndAfterTakeChickenFeather(chickenFeather);
        int chickenFeathersPickedUpCount = ChickenFeathersPickedUpCount(oldChickenFeatherCount);

        // Workaround to wait until taking chicken feather has completed
        if (chickenFeathersPickedUpCount > 0) {
            this.logger.info("Successful.");
        } else {
            this.logger.warning("Picking up chicken feather was unsuccessful.");
        }

        this.logger.info("...");
        return chickenFeathersPickedUpCount;
    }

    /**
     * Wait until player is idle then take the chicken feather on the ground.
     * @param chickenFeather chicken feather to pick up
     */
    private void WaitBeforeAndAfterTakeChickenFeather(GroundItem chickenFeather) {
        this.logger.info("Found a feather, picking it up...");

        WaitUntilPlayerIdleUtil.Wait(ctx);
        chickenFeather.interact("Take", "Feather");
        WaitUntilPlayerIdleUtil.Wait(ctx, 1000, 2);
    }

    /**
     * Number of chicken feathers picked up.
     * @param oldChickenFeatherCount previous chicken feathers in inventory
     * @return number of chicken feathers picked up
     */
    private int ChickenFeathersPickedUpCount(int oldChickenFeatherCount) {
        int newChickenFeatherCount = ctx.inventory.select().id(CHICKEN_FEATHER_ID).count(true);
        return newChickenFeatherCount > oldChickenFeatherCount ? newChickenFeatherCount - oldChickenFeatherCount: 0;
    }
}
