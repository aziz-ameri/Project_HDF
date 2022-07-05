package org.csc133.a3.gameobject;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Point2D;
import com.codename1.ui.geom.Rectangle;
import com.codename1.util.MathUtil;
import org.csc133.a3.GameWorld;

import java.util.ArrayList;

public class NonePlayerHelicopter extends Helicopter{

    private int water;

    private double heading;

    private FlightPath fc;

    private double t;

    boolean active;
    boolean usedOnce;

    private GameWorld gw;

    private boolean changeToAvoidColor;

//    private static NonePlayerHelicopter getInstance;
//
//    public static synchronized NonePlayerHelicopter getInstance(FlightPath fc ) {
//        if (getInstance == null)
//            getInstance = new NonePlayerHelicopter(fc);
//
//        return getInstance;
//    }



    public NonePlayerHelicopter(FlightPath fc, GameWorld gameWorld) {
        gw = gameWorld;
        this.fc = fc;
        water =0;
        heading = -90;
        t =0;
        active = true;
        usedOnce = true;
        changeToAvoidColor = false;

        translate(fc.getHelipadStartingPoint().getX(),
                fc.getHelipadStartingPoint().getY());

        //setFlightControl(fc);
    }



    public void moveAlongPath(Point2D c) {
        Point2D p;
        if (usedOnce)
            p = fc.helipadEvaluateCurve(t);
        else
            p = getMove(t);

        double tx = p.getX() - c.getX();
        double ty = p.getY() - c.getY();

        double theta =  Math.toDegrees(MathUtil.atan2(tx,ty));
        translate(tx,ty);

        if (t <= 1) {
            t = t + 0.001;
            rotate(heading - theta);
            heading = theta;
        }
        else {
            t=0;
            usedOnce= false;
            water = 1000;
        }


    }

    private Point2D getMove(double t) {
        if (active && t <= 1) {
            return fc.riverEvaluateCurve(t);

        }
        else if (active && t >= 1){
            active = false;
            t =0;
            return fc.fireEvaluateCurve(t);
        }

        if (!active && t <= 1)
            return fc.fireEvaluateCurve(t);

        else {
            active = true;
            t=0;
            return fc.riverEvaluateCurve(t);
        }

        //else active = true;

    }


    public void setHeading(double theta) {
        heading = theta;
    }


    @Override
    public int getColor() {
        return ColorUtil.YELLOW;
    }

    @Override
    public int changeColor() {
        if (!changeToAvoidColor)
            return ColorUtil.CYAN;
        else return ColorUtil.MAGENTA;
    }

    //@Override methods From Helicopter class
    //
    @Override
    public int getWater() {
        return water;
    }

    @Override
    public int getFull() {
        return 0;
    }

    @Override
    public void updateLocalTransforms() {

        heloBlade.updateLocalTransforms(-15);

        //Point2D c = new Point2D(theTranslation.getTranslateX(),theTranslation.getTranslateY());

        fc.moveAlongAPath(new Point2D(theTranslation.getTranslateX(),
                        theTranslation.getTranslateY())
                ,this);

        aVoid();

        //moveAlongPath(c);

    }


    @Override
    public int getSpeed() {
        return 0;
    }


    public int getHeading() {
        return (int) heading;
    }

    @Override
    public void move() {

    }

    @Override
    public void steerLeft() {

    }

    @Override
    public void steerRight() {

    }

    public void setWater(int i) {
        water = i;
    }

    public void aVoid() {
        for (GameObject go: gw.getGameObjectCollection()){
            if (go instanceof PlayerHelicopter){
                if (intersects(this,go)) {
                     changeToAvoidColor = true;
                }
                else changeToAvoidColor = false;
            }
        }
    }

    public boolean intersects(NonePlayerHelicopter heli, GameObject go) {
        return heli.getBoundingRectangle( (int) getLocation().getX(),
                (int)getLocation().getY(),80,150).intersects((int)
                        go.getLocation().getX(),
                (int) go.getLocation().getY(),80, 150);
    }

    @Override
    public Rectangle getBoundingRectangle(int x, int y, int w, int h) {
        return new Rectangle(x,y,w,h);
    }

    public void fight() {

        ArrayList<GameObject> firesDown = new ArrayList<>();

        for (GameObject go: gw.getGameObjectCollection() ) {
            if (go instanceof FireStates) {
                if (((FireStates) go).isSelect()) {
                    ((FireStates) go).switchState(true);
                    firesDown.add(go);
                    water =0;
                }
            }
        }
        if (firesDown != null)
            gw.getGameObjectCollection().removeAll(firesDown);
    }


}
