package org.csc133.a3.views;

import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import org.csc133.a3.GameWorld;
import org.csc133.a3.gameobject.FireStates;
import org.csc133.a3.gameobject.FlightPath;
import org.csc133.a3.gameobject.GameObject;

import javax.swing.tree.TreeNode;

public class MapView extends Container {
    private GameWorld gw;
     private boolean sw;

    private Transform worldToND, ndToDisplay,theVTM;
    private float winLeft,winRight,winTop,winBottom;
    private int zoom;

    public MapView(GameWorld gw) {
        this.gw = gw;
        zoom =1;
        sw = false;
    }

    public void upDateZoom() {
        if (sw) {
            zoom  = 2;
            sw = false;
        }
        else {
            sw = true;
            zoom =1;
        }

    }

    //set up the world to DN transform
    //
    private Transform buildWorldToNDXform(float winWidth,float winHeight,
                                          float winLeft,float winBottom) {

        Transform tmpXform = Transform.makeIdentity();
        tmpXform.scale(1/winWidth,1/winHeight);
        tmpXform.translate(-winLeft,-winBottom);
        return tmpXform;
    }


    // set up ND to Screen transform
    //
    private Transform buildToDisplayXform(float displayWidth,
                                          float displayHeight) {

        Transform tpmXform = Transform.makeIdentity();
        tpmXform.translate(0,displayHeight);
        tpmXform.scale(displayWidth, - displayHeight);
        return tpmXform;
    }

    private void setupVTM(Graphics g) {

        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);
        gXform.translate(getAbsoluteX(),getAbsoluteY());
        gXform.concatenate(getVTM());
        gXform.translate(-getAbsoluteX(),-getAbsoluteY());
        g.setTransform(gXform);

    }

    private Transform getVTM() {

        winLeft = winBottom = 0;
        winRight = this.getWidth() * zoom;
        winTop = this.getHeight() *  zoom;

        float winHeight = winTop - winBottom;
        float winWidth = winRight - winLeft;

        worldToND = buildWorldToNDXform(winWidth,winHeight,winLeft,winBottom);
        ndToDisplay = buildToDisplayXform(this.getWidth(),this.getHeight());

        theVTM = ndToDisplay.copy();
        theVTM.concatenate(worldToND);

        return theVTM;

    }


    public void laidOut() {
        gw.setWorldSize(new Dimension(this.getWidth(),this.getHeight()));

        gw.init();
    }

    @Override
    public void pointerPressed(int x,int y){
        x = x - getAbsoluteX();
        y = y - getAbsoluteY();

        Point2D selectedFire = transformPoint2D(getInverseVTM(),new Point2D(x,y));

        Point2D fireLocation = new Point2D(1500,800);
        for (GameObject go: gw.getGameObjectCollection()) {
            if (go instanceof FireStates) {
                if (((FireStates) go).contains(selectedFire) &&
                        !((FireStates) go).isSelect()) {
                    ((FireStates) go).select(true);

                    fireLocation = transformPoint2D(getInverseVTM(),
                            new Point2D(x,y));
                }
                else ((FireStates) go).select(false);
            }

        }

        for (GameObject go : gw.getGameObjectCollection()) {
            if (go instanceof FlightPath) {
            ((FlightPath) go).setTail(fireLocation);
            ((FlightPath) go).setFireTailBackToRIver();

            }
        }


        repaint();

    }

    private Point2D transformPoint2D(Transform t, Point2D p) {
        float[] in = new float[2];
        float[] out = new float[2];

        in[0] = (float) p.getX();
        in[1] = (float) p.getY();

        t.transformPoint(in,out);

        return new Point2D(out[0],out[1]);
    }

    private Transform getInverseVTM() {
        Transform inverseVTM = Transform.makeIdentity();

        try {
            getVTM().getInverse(inverseVTM);
        } catch (Transform.NotInvertibleException e) {
            e.printStackTrace();
        }

        return inverseVTM;
    }
    public void paint(Graphics g) {
        super.paint(g);

        setupVTM(g);

        Point parentOrigin = new Point(this.getX(),this.getY());
        Point screenOrigin = new Point(getAbsoluteX(),getAbsoluteY());





        for (GameObject go : gw.getGameObjectCollection())
            go.draw(g,parentOrigin,screenOrigin);

        g.resetAffine();
    }

}
