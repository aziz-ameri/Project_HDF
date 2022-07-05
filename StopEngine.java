package org.csc133.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.GameWorld;

public class StopEngine extends Command {
    GameWorld gw;
    public StopEngine(GameWorld gw) {
        super("StopEngine");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        gw.stopEngine();
    }
}
