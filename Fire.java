package org.csc133.a3.gameobject;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public abstract class Fire extends Movable {

   // int size;
    boolean active;

    public Fire() {
        active = false;
    }



    @Override
    public void localDraw(Graphics g, Point originParent,
                          Point originScreen) {

        g.setColor(ColorUtil.MAGENTA);
        if (active)
        g.fillArc(0,0,getSize(),getSize(),0,360);

//        g.drawString("" + getSize(),0,0);

    }

    public abstract void activate();

    @Override
    public int getSpeed() {
        return 0;
    }

    @Override
    public int getHeading() {
        return 0;
    }

}