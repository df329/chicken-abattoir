package Machiavellianism.ui;

public interface ISettingsUi {
    /**
     * Starts the script else it will nop.
     * @return true to start the script
     */
    boolean startScript();

    /**
     * Whether to pick up chicken feathers.
     * @return true to pick up chicken feathers
     */
    boolean pickupChickenFeathers();
}
