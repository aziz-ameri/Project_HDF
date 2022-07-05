package org.csc133.a3.gameobject;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;

public class River extends Fixed{

    private int getLargeDim() {
        return Math.max(getWorldSize().getWidth(),getWorldSize().getHeight());
    }
    private int getSmallDim() {
        return Math.min(getWorldSize().getWidth(),getWorldSize().getHeight());
    }

    public River(Dimension worldSize) {
        setWorldSize(worldSize);
        setColor(ColorUtil.BLUE);
        setDimension(new Dimension(getLargeDim(),getSmallDim()/5));
        translate(-5,getWorldSize().getHeight() / 1.9);

    }


    @Override
    public void localDraw(Graphics g, Point originParent,
                          Point originScreen) {
        g.setColor(getColor());
        containerTranslate(g,originParent);
        g.drawRect(0,0,
                getDimension().getWidth(),getDimension().getHeight());

    }

    public Point2D goToRIver() {
        return new Point2D(theTranslation.getTranslateX() +
                getDimension().getWidth()/2 ,
                            theTranslation.getTranslateY() +
                                    getDimension().getHeight() /2 );
    }
}
