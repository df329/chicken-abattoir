package Machiavellianism.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChickenAbattoirSettingsUi implements ISettingsUi {
    private static final String SETTINGS_TITLE = "Chicken Abattoir";
    private static final String CHECKBOX_PICKUP_FEATHERS = "Pickup chicken feathers";
    private boolean startScript;
    private boolean pickupChickenFeathers;
    private final JFrame frame;

    public ChickenAbattoirSettingsUi() {
        startScript = false;
        pickupChickenFeathers = false;

        // JFrame with grid layout
        frame = new JFrame(SETTINGS_TITLE);
        frame.setLayout(new GridLayout(3, 1));
        frame.setSize(400, 200);

        JCheckBox checkBoxPickupFeathers = new JCheckBox(CHECKBOX_PICKUP_FEATHERS);
        checkBoxPickupFeathers.addItemListener(pickupFeathersCheckboxAction());

        JSeparator hSeparator = new JSeparator(SwingConstants.HORIZONTAL);

        // This will signal the script to read the settings and "start"
        JButton startButton = new JButton("Start");
        startButton.addActionListener(startButtonAction());

        frame.add(checkBoxPickupFeathers);
        frame.add(hSeparator);
        frame.add(startButton);

        frame.setVisible(true);
    }

    /**
     * Hides the UI.
     */
    public void hide() {
        frame.setVisible(false);
    }

    /**
     * @InheritDoc
     */
    public boolean startScript() {
        return startScript;
    }

    /**
     * @InheritDoc
     */
    public boolean pickupChickenFeathers() {
        return pickupChickenFeathers;
    }

    /**
     * Pick up chicken feathers setting.
     * @return action listener
     */
    private ItemListener pickupFeathersCheckboxAction() {
        return e -> pickupChickenFeathers = e.getStateChange() == ItemEvent.SELECTED;
    }

    /**
     * Hides the settings window and "starts" the script.
     * @return action listener
     */
    private ActionListener startButtonAction() {
        return e -> {
            frame.setVisible(false);
            startScript = true;
        };
    }
}
