package Machiavellianism.task.antiban.action;

import org.powerbot.script.Area;

/**
 * Interface for anti-ban actions.
 */
public interface IAntiBanAction {

    /**
     * Anti-ban move action.
     * @param area area boundary
     */
    public void Move(Area area);
}
