package dev.alexengrig.intellij.resttime.ui;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RestTimeDialog extends DialogWrapper {
    public static final String TITLE = "Rest Time";

    private final JComponent myCenterPanel;

    public RestTimeDialog() {
        super(false);
        init();
        setTitle(TITLE);
        myCenterPanel = initCentralPanel();
    }

    private JComponent initCentralPanel() {
        final JPanel centerPanel = new JPanel();
        centerPanel.add(new JLabel("Time to rest now!"));
        return centerPanel;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return myCenterPanel;
    }
}
