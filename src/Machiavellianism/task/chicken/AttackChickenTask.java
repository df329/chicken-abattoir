package Machiavellianism.task.chicken;

import Machiavellianism.task.Task;
import Machiavellianism.util.CommonUtil;

import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Game;
import org.powerbot.script.rt4.Npc;

/**
 * Attack a chicken task.
 */
public class AttackChickenTask extends Task<ClientContext> {

    public AttackChickenTask(ClientContext ctx) {
        super(ctx);
    }

    /**
     * Attacks a chicken if not in combat and idle.
     * @return true to attack a chicken
     */
    @Override
    public boolean activate() {
        return !ctx.players.local().inMotion()
                && ctx.players.local().animation() == CommonUtil.PLAYER_IDLE
                && ctx.combat.healthPercent() > 50;
    }

    @Override
    public int execute(Area area) {
        boolean chickenKilled = false;

        // Get the closest chicken that is not in combat
        ctx.npcs.select().within(area).select(npc -> !npc.healthBarVisible() && !npc.interacting().valid()).id(CommonUtil.CHICKEN_IDS).nearest();
        if (ctx.npcs.isEmpty()) {
            System.out.println("No chickens found.");
            System.out.println("...");
            return 0;
        }

        Npc chicken = ctx.npcs.poll();

        // Periodically attack the chicken if it is still not dead (limit 5 times = 30s)
        // The chicken isn't dead due the possible scenarios:
        // - player has intervened input and moved away
        // - player is behind a gate/wall/object
        // - mis-click due to chicken moving
        // Player might die if auto-retaliate is off
        int i = 0;
        for (; i < 15 && !chickenKilled; i++) {
            if (i == 0 || !ctx.players.local().healthBarVisible()) {
                System.out.println("Attacking chicken.");
                chicken.interact("Attack", "Chicken");
            }

            // Check if the chicken is dying else enable auto-retaliate if disabled (2s)
            if (Condition.wait(() -> chicken.animation() == 5389 || !chicken.valid(), 50, 40)) {
                chickenKilled = true;
            } else if (!ctx.combat.autoRetaliate() && ctx.game.tab(Game.Tab.ATTACK)) {
                enableAutoRetaliateWithDelays();
            }
        }

        if (!chickenKilled) {
            System.out.println("Unable to kill chicken.");
            System.out.println("...");
            return 0;
        }

        System.out.println("Chicken slaughtered.");
        System.out.println("...");
        return 1;
    }

    /**
     * Enable auto-retaliate and go back to inventory.
     */
    private void enableAutoRetaliateWithDelays() {
        System.out.printf("Enable auto-retaliate.");
        Condition.sleep(Random.nextInt(250, 400));

        ctx.combat.autoRetaliate(true);

        Condition.sleep(Random.nextInt(50, 100));

        ctx.input.send("{VK_ESCAPE}");
    }
}
