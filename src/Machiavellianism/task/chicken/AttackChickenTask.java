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
     * Attacks a chick if not in combat and idle.
     * @return true to attack a chicken
     */
    @Override
    public boolean activate() {
        return !ctx.players.local().inMotion()
                && !ctx.players.local().healthBarVisible()
                && ctx.players.local().animation() == CommonUtil.PLAYER_IDLE
                && ctx.combat.healthPercent() > 50;
    }

    @Override
    public int execute(Area area) {
        // Get the closest chicken that is not in combat
        ctx.npcs.within(area).select().select(npc -> !npc.healthBarVisible() && !npc.interacting().valid()).id(CommonUtil.CHICKEN_IDS).nearest();
        if (ctx.npcs.isEmpty()) {
            System.out.println("No chickens found.");
            return 0;
        }

        Npc chicken = ctx.npcs.poll();

        // Attack the chicken and wait until it is killed
        // Chicken can be killed on the first hit thus no player health bar
        chicken.interact("Attack");
        System.out.println("Attacking chicken.");
        if (!Condition.wait(() -> ctx.players.local().healthBarVisible(), 200, 20)) {
            System.out.println("Not in combat still, moving on...");
            System.out.println("...");
            return chicken.valid() && chicken.healthBarVisible() ? 0 : 1;
        }

        // Periodically attack the chicken in case
        // Or if the chicken hasn't been killed in 30s, attack again
        for (int i = 0, retries = 0; i < 60 && retries < 3 && (chicken.valid() && chicken.healthBarVisible()); i++) {
            Condition.sleep(500);
            if (i == 59 && chicken.valid()) {
                chicken.interact("Attack");
                i = 0;
                retries++;
                System.out.println("Attempting to attack chicken again.");
            }
        }

        // The chicken isn't dead due the possible scenarios:
        // - player has intervened input and moved away
        // - player is behind a gate/wall/object
        // Player might die if auto-retaliate is off
        if (chicken.valid()) {
            if (ctx.game.tab(Game.Tab.ATTACK)) {
                ctx.combat.autoRetaliate(true);
            }
            return 0;
        }

        System.out.println("Chicken slaughtered.");
        System.out.println("...");
        Condition.sleep(Random.nextInt(50, 250));
        return 1;
    }
}
