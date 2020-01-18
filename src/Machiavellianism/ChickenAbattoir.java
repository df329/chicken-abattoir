package Machiavellianism;

import Machiavellianism.task.Task;
import Machiavellianism.task.antiban.AntiBanTask;
import Machiavellianism.task.chicken.AttackChickenTask;
import Machiavellianism.task.grounditem.TakeGroundItemTask;
import Machiavellianism.ui.ChickenAbattoirSettingsUi;
import Machiavellianism.ui.ChickenAbattoirSummaryUi;

import org.powerbot.script.*;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

@Script.Manifest(
    name="Chicken Abattoir",
    properties="author=Machiavellianism; topic=1353799; client=4;",
    description="Slaughters chickens at the Lumbridge farm"
)
public class ChickenAbattoir extends PollingScript<ClientContext> implements PaintListener {
    // Version
    private static final int MAJOR_VERSION = 1;
    private static final int MINOR_VERSION = 2;
    private static final int PATCH_VERSION = 1;

    private static final long START_TIME = System.currentTimeMillis();
    private int totalChickensSlain;
    private int totalChickenFeathersPickedUp;
    private ChickenAbattoirSettingsUi settingsUi;
    private List<Task> taskList;

    // Lumbridge chicken area, this does not encompass the gates or farm house
    private static final Area LUMBRIDGE_CHICKEN_AREA = new Area(
            new Tile(3235, 3301, 0), // NE
            new Tile(3235, 3295, 0), // SE
            new Tile(3225, 3295, 0), // SW
            new Tile(3225, 3301, 0)  // NW
    );

    @Override
    public void start() {
        log.info("Welcome to the chicken abattoir at Lumbridge!");

        this.settingsUi = new ChickenAbattoirSettingsUi();

        this.totalChickenFeathersPickedUp = 0;
        this.taskList = Arrays.asList(
                new AttackChickenTask(ctx),
                new TakeGroundItemTask(ctx, this.settingsUi),
                new AntiBanTask(ctx)
        );
    }

    @Override
    public void poll() {
        int ret;

        if (!this.settingsUi.startScript()) {
            return;
        }

        // Actions are only valid within Lumbridge for now
        for (Task task : this.taskList) {
            if (task.activate()) {

                // Execute the action
                ret = task.execute(LUMBRIDGE_CHICKEN_AREA);

                if (task.getClass().isAssignableFrom(TakeGroundItemTask.class)) {
                    this.totalChickenFeathersPickedUp += ret;
                } else if (task.getClass().isAssignableFrom(AttackChickenTask.class)) {
                    this.totalChickensSlain += ret;
                }
            }
        }
    }

    @Override
    public void stop() {
        if (this.settingsUi != null) {
            this.settingsUi.hide();
            this.settingsUi = null;
        }
    }

    @Override
    public void repaint(Graphics graphics) {
        ChickenAbattoirSummaryUi.ShowStatisticsSummary(
                graphics,
                MAJOR_VERSION + "." + MINOR_VERSION + "." + PATCH_VERSION,
                START_TIME,
                this.totalChickensSlain,
                this.totalChickenFeathersPickedUp
        );
    }
}
