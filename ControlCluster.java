package org.csc133.a3.views;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import org.csc133.a3.GameWorld;
import org.csc133.a3.commands.*;

public class ControlCluster extends Container {

    private GameWorld gw;

    private final Button left = new Button("Left");
    private final Button right = new Button("Right");
    private final Button fight = new Button("Fight");
    private final Button exit = new Button("Exit");
    private final Button drink = new Button("Drink");
    private final Button bBreak = new Button("Break");
    private final Button accel= new Button("Accel");

    private final Button stopEngine = new Button("stopEngine");

    public ControlCluster(GameWorld gw) {
        this.gw = gw;

        setLayout(new GridLayout(1,8));


        add(left); add(right); add(fight);
        add(stopEngine);
        add(exit); add(drink); add(bBreak);
        add(accel);

        setStyle(left); setStyle(right);
        setStyle(fight); setStyle(exit);
        setStyle(drink); setStyle(bBreak);
        setStyle(accel);
        setStyle(stopEngine);
        //setEngineStyle(stopEngine);

        left.setCommand(new TurnLeft(gw));
        right.setCommand(new TurnRight(gw));
        fight.setCommand(new Fight(gw));
        exit.setCommand(new Exit(gw));
        drink.setCommand(new Drink(gw));
        bBreak.setCommand(new Brake(gw));
        accel.setCommand(new Accel(gw));
        stopEngine.setCommand(new StopEngine(gw));
    }

    private void setEngineStyle(Button b) {
        b.getAllStyles().setMargin(2,2,100,2);
        //b.getAllStyles().setFgColor(ColorUtil.GREEN);
        //b.getAllStyles().setBgColor(ColorUtil.WHITE);
    }


    private void setStyle(Button b) {
        Style setting = b.getStyle();
        setting.setFgColor(ColorUtil.GREEN);
        setting.setBgColor(ColorUtil.WHITE);
        setting.setBorder(Border.createOutsetBorder(5,ColorUtil.WHITE),false);

    }

}


