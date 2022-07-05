package org.csc133.a3.gameobject;

import com.codename1.ui.geom.Point2D;
import com.codename1.ui.geom.Rectangle;

import java.util.Random;

public class FireStates extends Fire {

    private int size;
    //private boolean active;
    //private int xDifferentiation;
    //private int yDifferentiation;

    public FireStates() {
        fireState = new UnStared();
        //active = false;
    }


    @Override
    public int getSize() {
        return fireState.getSize();
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }


    /// State Pattern
    //
    private State fireState;

    protected void changeState(State fireStates) {
        this.fireState = fireStates;
    }

    public void assignLocation(float translateX, float translateY) {
        fireState.assignLocation( translateX, translateY);
    }

    //Selection methods

    public boolean contains(Point2D p) {return fireState.contains(p);}

    private double distanceBetween(Point2D a, Point2D b) {
        return fireState.distanceBetween(a,b);
    }

    public void select(boolean select) {
        fireState.select(select);
    }

    public boolean isSelect() {return fireState.isSelect();}

    @Override
    public void move() {
        fireState.move();
    }

    public int getTotalSize() {
        return fireState.getSize();
    }

    @Override
    public void activate() {
        fireState.activate();
    }

    //Base class for All States
    //
    private abstract class State {



        public FireStates getFire() {
            return FireStates.this;
        }

        public void updateLocalTransforms(){}
        public void start(){};

        //public abstract void checkStates(boolean changeState);
        public abstract void switchState(boolean changeState);
        public void assignLocation(float x ,float y){};


        //Methods for selecting fire
        //
        public boolean contains(Point2D p) {return false;}

        private double distanceBetween(Point2D a, Point2D b) {
            return 0;
        }

        public void select(boolean select) {
        }

        public boolean isSelect() {return false;}

        public void move() {}
        public int getSize() {return size;}


        public void activate() {}
    }

    // UnStarted class
    //

    private class UnStared extends State {

        public UnStared() {
        }

        @Override
        public void switchState(boolean changeState) {
            if (changeState)
                getFire().changeState(new Burning() );
        }


    }


    // Burning class
    //

    private class Burning extends State {
        private boolean select;
       // int size;
        Random r;
        public Burning() {
            select = false;
            r = new Random();
        }


        @Override
        public void switchState(boolean changeState) {
            if (changeState)
                getFire().changeState(new Extinguished());
        }

        @Override
        public void assignLocation(float x,float y) {
            Random r = new Random();
            translate(x  + r.nextInt(260) ,
                    y + r.nextInt(160));

        }

        @Override
        public void activate() {
            active = true;
        }

        //Methods for selecting fire
        //
        public boolean contains(Point2D p) {
            return distanceBetween(getLocation(),p) ;
        }

        private boolean distanceBetween(Point2D a, Point2D b) {
            return intersects(a,b);
        }

        public boolean intersects(Point2D a, Point2D b) {
            return getBoundingRectangle((int) b.getX(),
                    (int) b.getY(),1,1).intersects(
                    getBoundingRectangle((int)a.getX(),
                            (int)a.getY() - 100,size,size));
        }



        public Rectangle getBoundingRectangle(int x,int y,int w, int h) {
            return new Rectangle(x,y,w,h);
        }

        public void select(boolean select) {
            this.select = select;
        }

        public boolean isSelect() {
            return select;
        }

        @Override
        public void move() {
            fireGrowthSize();
        }


        public void fireGrowthSize() {
            setSize(getSize()  + r.nextInt(2) );
        }

        @Override
        public int getSize() {
            return size;
        }

    }



    private class Extinguished extends State {

        public Extinguished() {
        }

        @Override
        public void switchState(boolean changeState) {

        }


    }




    public void switchState(boolean sw) {
        fireState.switchState(sw);
    }



}
