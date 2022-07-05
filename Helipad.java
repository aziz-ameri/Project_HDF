package org.csc133.a3.gameobject;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;

public class Helipad extends Fixed{


    private final int recSize;
    private final int arcSize;

    public Helipad(Dimension worldSize) {
        setWorldSize(worldSize);
        setColor(ColorUtil.GRAY);
        recSize = 260;
        arcSize = 220;
        translate(getWorldSize().getWidth()/2 - recSize/2,recSize);

    }


    @Override
    public void localDraw(Graphics g, Point originParent,
                          Point originScreen) {

        g.setColor(getColor());
        g.drawRect(0,0,recSize,recSize,5);

        g.drawArc((int) 20,20,arcSize,arcSize,0,360);

    }

    public Point2D getHelipadLocation() {
        return new Point2D(theTranslation.getTranslateX(),
                theTranslation.getTranslateY());
    }

    //This returns the ARC size since the helicopter
    // should be inside the ARC to land.
    ///
    public int getSize() {
        return  arcSize;
    }
}
