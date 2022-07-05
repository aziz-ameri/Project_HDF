package org.csc133.a3;

/*
/* Assignment A3b :: Hornets HFD, Abdulaziz Ameri 5/16/2020

Expected Grade Comment: Exceeds Expectations
I finished all requirements, all the fields are privets and
those that should not be changed are final, all the lines
are less than 80 columns, there is no run time error,
and I normally added and commit to git with tiles of
what been done for each commit.
 * */


/* assignment comment

Luckily, I finished the last two projects on time and that
 helped me to move faster for this project since I had all
  the files needed for this project this made things easier
   and gave me time to focus on new requirements for this
   project instead of working on what’s not working from last
    projects and this was a big help.
The River and Helipad classes were easiest to implement since there
 were not many changes from the previous project, and for the building
  class, there was not much challenge just refactoring some parts of the
   last project. The most challenging part of this project is the
    Helicopter and Fire class and maybe the Flightpath I haven’t
     worked on it so I don’t know yet.
I have also finished the Helicopter class there are a few minor
adjustments that need to be taken care of, the player Helicopter
 class is almost done also, for the Helicopter the only class that
 is not implemented yet is the None Player Helicopter.  The Fire and
 its state are almost finished and need some adjustments to complete,
  I still have to implement the new futures that are added to this project.
   All views and interfaces are done.

My plan is to finish the fire and then move to the flight
 patch and none player helicopter, then refactor the codes.



*/

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.UITimer;
import org.csc133.a3.views.ControlCluster;
import org.csc133.a3.views.GlassCockpit;
import org.csc133.a3.views.MapView;

public class Game extends Form implements Runnable {
    private final GameWorld gw;
    private final MapView mapView;
    private final GlassCockpit glassCockpit;
    private final ControlCluster controlCluster;





    public Game() {
        setLayout(new BorderLayout());

        gw = GameWorld.getGameWorld();
        mapView = new MapView(gw);
        glassCockpit = new GlassCockpit(gw);
        controlCluster = new ControlCluster(gw);



        addKeyListener('w',(evt) -> gw.quit());
        addKeyListener(-91,(evt) -> gw.speedUp());
        addKeyListener(-92,(evt) -> gw.speedDown());
        addKeyListener(-93,(evt) -> gw.steerLeft());
        addKeyListener(-94,(evt) -> gw.steerRight());
        addKeyListener('s',(evt) -> gw.startOrStopEngine());
        addKeyListener(102,(evt) -> gw.dumpingWater());
        addKeyListener(100,(evt) -> gw.drinkWater());
        addKeyListener('z',(evt) -> upDateZoom());

        this.getAllStyles().setBgColor(ColorUtil.BLACK);



        this.add(BorderLayout.CENTER, mapView);
        this.add(BorderLayout.NORTH,glassCockpit);
        this.add(BorderLayout.SOUTH, controlCluster);

        UITimer timer = new UITimer(this);
        timer.schedule(10,true,this);

        this.show();

    }

    private void upDateZoom() {
        mapView.upDateZoom();
    }

    public void paint(Graphics g) {
        super.paint(g);

    }



    @Override
    public void run() {
        gw.tick();
        glassCockpit.update();
        repaint();
    }

}
