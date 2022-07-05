package org.csc133.a3.gameobject;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Rectangle;
import org.csc133.a3.GameWorld;

import java.util.ArrayList;

public class PlayerHelicopter extends Helicopter {

    private int rotationalSpeed;
    private int heading;
    private int speed;
    private int water;
    private int fuelConsummationRate = GameWorld.FUEL;

    private Helipad helipad;

    private static PlayerHelicopter getInstance;



    public static synchronized PlayerHelicopter getInstance(Helipad helipad) {
        if (getInstance == null)
            getInstance = new PlayerHelicopter(helipad);


        return getInstance;
    }

    private PlayerHelicopter(Helipad helipad) {
        this.helipad = helipad;
        heloState = new Off();
//        setColor(ColorUtil.CYAN);

        translate(helipad.theTranslation.getTranslateX() + 130,
                helipad.theTranslation.getTranslateY()  );

    }



    /////////////
    private HeloState heloState;

    public  void restart() {

        theTranslation.setTranslation(helipad.theTranslation.getTranslateX()
                        + 130, helipad.theTranslation.getTranslateY() );

        heloState = new Off();
        fuelConsummationRate = GameWorld.FUEL;
        speed =0;
        //heading=0;
    }


    private void changeState(HeloState heloState) {
        this.heloState = heloState;
    }



    //```````````````````````````````````````````````````````````````````````````````

    private abstract class HeloState {

        protected PlayerHelicopter getHelo() {
            return PlayerHelicopter.this;
        }

        public void move( ) {}
        public void slowDown(){}
        public void accelerate(){}
        public void steerLeft() { }
        public void steerRight() { }

        public abstract void startOrStopEngine();

        public abstract boolean hasLanded();

        public void checkBladeSpeed(){};

        public void updateLocalTransforms(){}
        public void canDrinkFrom(River r){};
        public void fight(ArrayList<GameObject> gameObjectCollection){}

        public Rectangle getBoundingRectangle(int x, int y, int w, int h) {
            return new Rectangle();
        }


        public boolean intersects(Ready heli, GameObject go) {
            return false;
        }

    }

    private class Off extends HeloState {

        @Override
        public void startOrStopEngine() {

            getHelo().changeState(new Starting());
        }

        @Override
        public boolean hasLanded() {
           return true;
        }

        @Override
        public void checkBladeSpeed() {
            rotationalSpeed = 0;

        }
    }

    private class Starting extends HeloState {


        @Override
        public void accelerate() {
            int MAX_SPEED =1;
            if (speed < MAX_SPEED)
                speed++;

        }


        @Override
        public void startOrStopEngine() {
            if (hasLanded() && speed < 1 )
                getHelo().changeState(new Stopping());

            else
                getHelo().changeState(new Ready());
//            if (speed < 3)
//                getHelo().changeState(new Stopping());
//            else
//                getHelo().changeState(new Ready());
        }


        @Override
        public void checkBladeSpeed() {
            rotationalSpeed = 8;
            fuelConsummationRate = fuelConsummationRate -5;
        }

        @Override
        public boolean hasLanded() {
            return  theTranslation.getTranslateX() > helipad.theTranslation.getTranslateX() &&
                    theTranslation.getTranslateX() < helipad.theTranslation.getTranslateX() +
                            helipad.getSize() &&
                    theTranslation.getTranslateY() > helipad.theTranslation.getTranslateY() &&
                    theTranslation.getTranslateY() < helipad.theTranslation.getTranslateY() +
                            helipad.getSize() /2 ;

        }

    }

    private class Stopping extends HeloState {

        @Override
        public void startOrStopEngine() {
            if (hasLanded() && speed < 1  )
                getHelo().changeState(new Off());

            else
                getHelo().changeState(new Starting());

            //getHelo().changeState(new Starting());
        }



        @Override
        public boolean hasLanded() {

            return  theTranslation.getTranslateX() > helipad.theTranslation.getTranslateX() &&
                    theTranslation.getTranslateX() < helipad.theTranslation.getTranslateX() +
                            helipad.getSize() &&
                    theTranslation.getTranslateY() > helipad.theTranslation.getTranslateY() &&
                    theTranslation.getTranslateY() < helipad.theTranslation.getTranslateY() +
                            helipad.getSize() ;
        }

        @Override
        public void checkBladeSpeed() {
                rotationalSpeed = 1;
        }
    }

    public class Ready extends HeloState {

        @Override
        public void startOrStopEngine() {
            if (hasLanded() )
                getHelo().changeState(new Stopping());
            // conditions go here to test for whether
            // we can stop the engine or not
            //
        }

        @Override
        public void checkBladeSpeed() {
            rotationalSpeed = 19;

        }

        @Override
        public boolean hasLanded() {

            return  theTranslation.getTranslateX() >
                    helipad.theTranslation.getTranslateX() &&
                    theTranslation.getTranslateX() <
                            helipad.theTranslation.getTranslateX() +
                            helipad.getSize() &&
                    theTranslation.getTranslateY() >
                            helipad.theTranslation.getTranslateY() &&
                    theTranslation.getTranslateY() <
                            helipad.theTranslation.getTranslateY() +
                            helipad.getSize() /2 ;

        }

        @Override
        public void updateLocalTransforms() {
            translate(deltaX,deltaY);

        }

        double deltaX;
        double deltaY;

        @Override
        public void move() {
            deltaX = Math.cos(
                    Math.toRadians(heading +90)) * speed ;

            deltaY = Math.sin(
                    Math.toRadians(heading +90)) * speed;

            fuelConsummationRate -= (speed * speed );


        }

        //Left Turn and right turn are fixed also Helicopter moves in right direction
        //

        @Override
        public void steerRight() {
            rotate(-15);

            if(heading >= 15)
                heading -=15;

            else {
                heading = heading - 15;
                heading = heading + 360;
            }
        }

        @Override
        public void steerLeft() {
            rotate(15);

            if(heading < 345)
                heading +=15;

            else if(heading == 345)
                heading = 0;

            else {

                heading = heading - 360;
                heading = heading + 15;
            }

        }

        @Override
        public void accelerate() {
            int MAX_SPEED =10;
            if (speed < MAX_SPEED)
                speed++;

        }

        @Override
        public void slowDown() {
            int MIN_SPEED = 0;
            if (speed > MIN_SPEED)
                speed--;
        }

        public void canDrinkFrom(River r){

            if (getLocation().getX() > r.getLocation().getX() &&
                    getLocation().getX() < r.getLocation().getX() +
                            r.getDimension().getWidth() &&
                    getLocation().getY() > r.getLocation().getY() &&
                    getLocation().getY() < r.getLocation().getY() +
                            r.getDimension().getHeight()
            )
                if (water < 1000 && speed < 3)
                    water += 100;
        }

        public void fight(ArrayList<GameObject> gameObjectCollection) {

            ArrayList<GameObject> firesDown = new ArrayList<>();


            boolean aboveFire = false;
            int indexOfFire = -1;

            for (GameObject go: gameObjectCollection) {
                if (go instanceof FireStates) {
                    if (  intersects(this,go)
                    )
                    {
                        aboveFire = true;
                        indexOfFire = gameObjectCollection.indexOf(go);
                        break;

                    }
                    else
                        aboveFire = false;
                }
            }


            if (aboveFire && indexOfFire != -1 && water > 99) {

                //this will change the fire width and height if the helicopter
                // is above the fire and water is more than 99
                // the water will be change to 0 after it's been dropped
                //
                gameObjectCollection.get(indexOfFire).setSize(
                        gameObjectCollection.get(indexOfFire).getSize()
                                - water /3);

                water =0;

                //This if statement will remove the fire if the fire width
                // or height is less than 1
                if (gameObjectCollection.get(indexOfFire).getSize() < 1)

                {
                    //Adding the extinguished fires in firesDown Array
                    // for deleting
                    firesDown.add(gameObjectCollection.get(indexOfFire));

                    //Adding the Extinguished fires into extinguish list.
                    //
                    //extinguishList.add(new Extinguished(
                      //      gameObjectCollection.get(indexOfFire) ));


                    gameObjectCollection.get(indexOfFire).setSize(0);
                    //gameObjectCollection.get(indexOfFire).dimension.setHeight(0);
                }

            }
            //this else statement will change the water to 0 if the helicopter
            // drop the water anywhere outside the fire area
            //
            else
                water=0;

            //remove the fire objects that been added to firesDown
            // list from fires list.
            //
            gameObjectCollection.removeAll(firesDown);

        }

        @Override
        public boolean intersects(Ready heli, GameObject go) {
            return heli.getBoundingRectangle( (int) getLocation().getX(),
                    (int)getLocation().getY(),80,150).intersects((int)
                            go.getLocation().getX(),
                    (int) go.getLocation().getY(),go.getSize(), go.getSize());
        }

        @Override
        public Rectangle getBoundingRectangle(int x, int y, int w, int h) {
            return new Rectangle(x,y,w,h);
        }
    }


    public void startOrStopEngine() {heloState.startOrStopEngine();}

    @Override
    public void updateLocalTransforms() {
        heloBlade.updateLocalTransforms(rotationalSpeed);
        heloState.updateLocalTransforms();
        heloState.move();
        heloState.checkBladeSpeed();
    }

    public int getHelicopterFull() {
        return fuelConsummationRate;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public int getHeading() {
        return heading;
    }


    @Override
    public int getFull() {
        return fuelConsummationRate;
    }

    @Override
    public int getColor() {
        return ColorUtil.CYAN;
    }

    @Override
    public int changeColor( ) {
        return ColorUtil.GREEN;
    }

    @Override
    public  int getWater() {
        return water;
    }


    @Override
    public void move( ) {
        heloState.move();
    }
    @Override
    public void steerLeft() {heloState.steerLeft(); }
    @Override
    public void steerRight()    {heloState.steerRight();}
    public void accelerate()   {heloState.accelerate();}
    public void slowDown() { heloState.slowDown(); }
    public void canDrinkFrom(River r){ heloState.canDrinkFrom(r);}
    public void fight(ArrayList<GameObject> gameObjectCollection) {
        heloState.fight(gameObjectCollection);
    }

    public void stopEngine() {
        heloState.startOrStopEngine();
    }

    public boolean hasLanded() {
        return heloState.hasLanded();
    }
    public boolean intersects(Ready heli, GameObject go) {
        return heloState.intersects(heli,go);
    }
    @Override
    public Rectangle getBoundingRectangle(int x, int y, int w, int h) {

        return heloState. getBoundingRectangle( x,  y,  w,  h);
    }



}
