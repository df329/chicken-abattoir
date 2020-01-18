package Machiavellianism.task.antiban;

import Machiavellianism.task.Task;
import Machiavellianism.task.antiban.action.IAntiBanAction;
import Machiavellianism.task.antiban.action.camera.ChickenFocusAction;
import Machiavellianism.task.antiban.action.mouse.ScreenEdgeAction;
import Machiavellianism.util.CommonUtil;

import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;

import java.util.Arrays;
import java.util.List;

/**
 * Class implementing anti-ban actions.
 */
public class AntiBanTask extends Task<ClientContext> {
    private final List<IAntiBanAction> antiBanActions;

    public AntiBanTask(ClientContext ctx) {
        super(ctx);

        this.antiBanActions = Arrays.asList(
                new ChickenFocusAction(ctx),
                new ScreenEdgeAction(ctx)
        );
    }

    /**
     * When player is idle, perform anti-ban actions.
     * @return true to perform anti-ban action
     */
    @Override
    public boolean activate() {
        return ctx.players.local().animation() == CommonUtil.PLAYER_IDLE;
    }

    /**
     * Execute the anti-ban actions in the area. Not all anti-ban actions will always be executed.
     * Poor man's attempt at randomizing.
     * @param area the area to execute the action
     * @return number of anti-ban actions executed
     */
    @Override
    public int execute(Area area) {
        int numberOfAntiBanActions = 0;

        for (IAntiBanAction action : this.antiBanActions) {
            if (Random.nextInt(0, 10) == 3) {
                action.Move(area);
                numberOfAntiBanActions++;
                Condition.sleep(Random.nextInt(50, 1500));
            }
        }

        return numberOfAntiBanActions;
    }
}
