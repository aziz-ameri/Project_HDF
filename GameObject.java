package org.csc133.a3.gameobject;

import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import com.codename1.ui.geom.Rectangle;

abstract public class GameObject {

    private int color;
    private int size;
    private Dimension dimension;
    private Dimension worldSize;

    protected Transform theTranslation, theRotation, theScale;

    public Point2D getLocation() {
        return new Point2D(theTranslation.getTranslateX(),
                theTranslation.getTranslateY());
    }

    public GameObject() {

        theTranslation = Transform.makeIdentity();
        theRotation = Transform.makeIdentity();
        theScale = Transform.makeIdentity();
    }
    // I added this methods
    //
    public void setDimension(Dimension dim) {
        dimension = new Dimension(dim);
    }

    public Dimension getDimension() {
        return dimension;
    }


    public void setWorldSize(Dimension worldSize) {
        this.worldSize = new Dimension(worldSize);
    }

    public Dimension getWorldSize() {
        return worldSize;
    }

    public Transform getTheTranslation() {
        return theTranslation;
    }


    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
    //To here
    //

    public void rotate(double degrees) {
        theRotation.rotate((float) Math.toRadians(degrees),0,0);
    }

    public void scale(double sx,double sy) {
        theScale.scale((float) sx, (float) sy);
    }

    public void translate(double tx,double ty) {
        theTranslation.translate((float) tx, (float) ty);
    }

    //Methods to perform transformations
    //
    private Transform gOrigXform;

    protected Transform preLTTransform(Graphics g, Point originScreen) {

        Transform gXform = Transform.makeIdentity();

        //get the current coordinates back
        // pre LTTransforms
        g.getTransform(gXform);
        gOrigXform = gXform.copy();

        //move the drawing coordinates back
        //
        gXform.translate(originScreen.getX(), originScreen.getY());
        return gXform;
    }

    protected void localTransforms(Transform gXform) {

        gXform.translate(theTranslation.getTranslateX(),
                theTranslation.getTranslateY());
        gXform.concatenate(theRotation);
        gXform.scale(theScale.getScaleX(),theScale.getScaleY());
    }

    protected void postLTTransform(Graphics g, Point originScreen,
                                   Transform gXform) {
        //move the drawing coordinates sp that the local origin coincides with the screen origin
        //post local transforms
        gXform.translate(-originScreen.getX(),-originScreen.getY());
        g.setTransform(gXform);
    }

    protected void restoreOriginalTransforms(Graphics g ) {
        // restore the original gXform
        // restore original transforms
        g.setTransform(gOrigXform);
    }

    //Template Method Pattern
    //
    abstract public void localDraw(Graphics g,Point originParent,
                                   Point originScreen);

    public void draw(Graphics g, Point originParent, Point originScreen) {
        Transform gXform = preLTTransform(g,originScreen);
        localTransforms(gXform);
        postLTTransform(g,originScreen,gXform);
        localDraw(g,originParent,originScreen);
        restoreOriginalTransforms(g);
    }


    protected void containerTranslate(Graphics g,Point parentOrigin) {
        Transform gxForm = Transform.makeIdentity();
        g.getTransform(gxForm);
        gxForm.translate(parentOrigin.getX(),parentOrigin.getY());
        g.setTransform(gxForm);
    }

    protected void cn1ForwardPrimitiveTranslate(Graphics g,
                                                Dimension pDimension) {
        Transform gxForm = Transform.makeIdentity();
        g.getTransform(gxForm);
        gxForm.translate(-pDimension.getWidth()/2,-pDimension.getHeight()/2);
        g.setTransform(gxForm);
    }

    protected void cn1ReversePrimitiveTranslate(Graphics g,
                                                Dimension pDimension) {
        Transform gxForm = Transform.makeIdentity();
        g.getTransform(gxForm);
        gxForm.translate(pDimension.getWidth()/2,pDimension.hashCode()/2);
        g.setTransform(gxForm);
    }

    public abstract void setSize(int size);
    public int getSize(){return  size;};

    //Collision detection methods
    //


}
