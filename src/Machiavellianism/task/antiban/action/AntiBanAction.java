package Machiavellianism.task.antiban.action;

import org.powerbot.script.rt4.ClientContext;

import java.util.logging.Logger;

/**
 * Abstract class for anti-ban actions.
 */
public abstract class AntiBanAction implements IAntiBanAction {
    public final ClientContext ctx;
    public final Logger logger;

    /**
     * Anti-ban action.
     * @param ctx client context
     */
    public AntiBanAction(ClientContext ctx, Logger logger) {
        this.ctx = ctx;
        this.logger = logger;
    }
}
