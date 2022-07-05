package org.csc133.a3.views;

import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.GridLayout;
import org.csc133.a3.GameWorld;

public class GlassCockpit extends Container {


    private GameWorld gw;

    private Label heading;
    private Label speed;
    private Label fuel;
    private Label fires;
    private Label fireSize;
    private Label damage;
    private Label loss;

    public GlassCockpit(GameWorld gw) {
        this.gw = gw;
        setLayout(new GridLayout(2,7));

        add("HEADING");
        add("SPEED");
        add("FUEL");
        add("FIRES");
        add("FIRE SIZE");
        add("DAMAGE");
        add("LOSS");

        heading = new Label("0");
        speed = new Label("0");
        fuel = new Label("0");
        fires = new Label("0");
        fireSize = new Label("0");
        damage = new Label("0");
        loss = new Label("0");

        add(heading);
        add(speed);
        add(fuel);
        add(fires);
        add(fireSize);
        add(damage);
        add(loss);

    }

    public void update() {
        heading.setText(gw.getHelicopterHeading() );
        speed.setText(gw.getHelicopterSpeed() );
        fuel.setText(gw.getHelicopterFuel() );
        fires.setText(gw.getNumberOfFires() );
       fireSize.setText(gw.getTotalFiresSize() );
        damage.setText(gw.getTotalDamage());
        loss.setText(gw.getLossOFAllBuildings());

    }



}
