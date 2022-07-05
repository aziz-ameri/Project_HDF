package org.csc133.a3.gameobject;

import com.codename1.ui.geom.Rectangle;

abstract public class Movable extends GameObject {

    public abstract int getSpeed();
    public abstract int getHeading();
    public abstract void move();

    public Rectangle getBoundingRectangle(int x,int y, int w, int h){
        return new Rectangle();
    }

    public boolean intersects(GameObject first, GameObject second){
        return false;
    }



}
