package Machiavellianism.task.antiban.action;

import org.powerbot.script.rt4.ClientContext;

/**
 * Abstract class for anti-ban actions.
 */
public abstract class AntiBanAction implements IAntiBanAction {
    public final ClientContext ctx;

    /**
     * Anti-ban action.
     * @param ctx client context
     */
    public AntiBanAction(ClientContext ctx) {
        this.ctx = ctx;
    }
}
