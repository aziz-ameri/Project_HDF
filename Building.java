package org.csc133.a3.gameobject;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;

import java.util.ArrayList;
import java.util.Random;

public class Building extends Fixed {

    private double checkDamage;
    private double area;
    private double percentage;
    private double tempValForPer;
    private double buildingDamage;
    private int buildingValue;
    private Random r;

    public Building( int width,int height,double tx,double ty,int value) {


        checkDamage =0;
        area =0;
        percentage =0;
        tempValForPer=0;
        buildingDamage =0;
        r= new Random();
        setColor(ColorUtil.rgb(255,0,0));

        setDimension(new Dimension(width,height));
        translate(tx + r.nextInt(20),ty + r.nextInt(5));
        buildingValue = value;


    }


    @Override
    public void localDraw(Graphics g, Point originParent,
                          Point originScreen) {

        g.setColor(getColor());

        g.drawRect(0 ,0,getDimension().getWidth(),
                getDimension().getHeight());


        //g.drawString("V: " + buildingValue,-130,0);
    }


    // set the fires in the buildings
    public void setFireInBuilding(FireStates fire) {
        fire.switchState(true);
        fire.assignLocation(getTheTranslation().getTranslateX(),
                getTheTranslation().getTranslateY() );

        startFiresInBuilding(fire);
    }

    //start fires in the Building
    //
    public void startFiresInBuilding(FireStates fire) {
        fire.activate();
    }

    public double getTheDamage() {
        return percentage;
    }

    public double checkPercentage() {
        if (tempValForPer < percentage) {
            tempValForPer = percentage;
        }
        return tempValForPer;
    }

    public double getBuildingDamage() {
        return buildingDamage;
    }

    public void checkTheDamage(ArrayList<GameObject> obj) {
        checkDamage =0;
        area =0;
        percentage =0;
        for (GameObject go: obj) {
            if (go instanceof FireStates) {
                if (go.getLocation().getX() >
                        theTranslation.getTranslateX() &&
                        go.getLocation().getX()  <
                                theTranslation.getTranslateX()
                                + getDimension().getWidth() &&
                        go.getLocation().getY() >
                                theTranslation.getTranslateY()
                ) {
                    checkDamage += go.getSize();
                }
            }
            area = getDimension().getWidth() * getDimension().getHeight();

        }
        checkDamage = (checkDamage * 3.14);
        area = area/30;
        //Next line will calculate the percentage for Damage
        //
        percentage = (checkDamage/area) * 100;
        //Next line will calculate the financial loss of buildings
        //
        buildingDamage = percentage * buildingValue / 100;
    }

}
