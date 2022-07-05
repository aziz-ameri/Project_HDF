package org.csc133.a3;

import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.geom.Dimension;
import org.csc133.a3.gameobject.*;

import java.util.ArrayList;
import java.util.Random;

public class GameWorld {


    private Dimension worldSize;
    private static GameWorld theGameWorld;
    private ArrayList<GameObject> gameObjects;
    private PlayerHelicopter playerHelicopter;
    private NonePlayerHelicopter nonPlayerHelicopter;
    private River river;
    private Helipad helipad;
    private FlightPath flightPath;
    //FUEL consummation rate is height so I changed the Fuel
    //
    public static final int FUEL = 35000;
    private int ticks;

    private int DamageToAllBuildings;
    private int lossOFAllBuildings;


    //this method will check if there is an instance of
    // gameWorld object has been initialized or not, Singleton pattern
    //
    public static synchronized GameWorld getGameWorld() {
        if (theGameWorld == null)
            theGameWorld = new GameWorld();

        return theGameWorld;
    }

    private GameWorld() {
        worldSize = new Dimension();

    }

    public void init() {


        ticks =0;
        DamageToAllBuildings =0;
        lossOFAllBuildings =0;
        int initialFire = 1000;
        //int fireBuket=0;
        gameObjects = new ArrayList<>();
        ArrayList<GameObject> fires = new ArrayList<>();

        river = new River(worldSize);
        helipad = new Helipad(worldSize);



        gameObjects.add(helipad);
        gameObjects.add(river);

        gameObjects.add(new Building(300,450,250,250,200));
        gameObjects.add(new Building(300,450,1500,250,500));
        gameObjects.add(new Building(850,200,500,1150,900));









        while (initialFire > 100) {
            Random r = new Random();
            int temp;
            int size = 11 + r.nextInt(5);
            FireStates fire = new FireStates();
            fire.setSize(size);
            fires.add(fire);
            size = size/2;
            temp = (int) ((size * size) * 3.12);
            initialFire -= temp;
        }



        int FireBuket= fires.size();
        int firesInLoop=0;
        int keepTrack=2;

        for (GameObject go: gameObjects) {

            if (go instanceof Building) {
                while (firesInLoop < keepTrack ) {
                    //System.out.println(buildingLooping);
                    ((Building) go).setFireInBuilding( (FireStates )
                            fires.get(firesInLoop));
                    firesInLoop++;

                }
                //The Firs Time while Loop adds Two fires in first building
                // Next loop will add three fires in the second building
                //
                if (keepTrack == 2)
                    keepTrack =5;
                else keepTrack = FireBuket;
            }

        }


        playerHelicopter = PlayerHelicopter.getInstance(helipad);
        flightPath = new FlightPath(helipad,river);
        //nonPlayerHelicopter = NonePlayerHelicopter.getInstance(flightPath);

        nonPlayerHelicopter = new NonePlayerHelicopter(flightPath,this);
        gameObjects.addAll(fires);
        gameObjects.add(flightPath);
        gameObjects.add(playerHelicopter);
        gameObjects.add(nonPlayerHelicopter);


        for (GameObject go: gameObjects) {
            if (go instanceof Building) {
                ((Building) go).checkTheDamage(gameObjects);
            }
        }


    }

    public ArrayList<GameObject> getGameObjectCollection() {
        return gameObjects;
    }

    public void setWorldSize(Dimension worldSize) {
        this.worldSize = new Dimension(worldSize);
    }

    public void tick() {
        ticks++;

        playerHelicopter.updateLocalTransforms();
//        helicopter.move();
//        helicopter.checkBladeSpeed();
//        helicopter.startOrStopEngine();
        flightPath.getFireLocation(getGameObjectCollection());

        nonPlayerHelicopter.updateLocalTransforms();

//        boolean hasLanded = playerHelicopter.hasLanded();



        if (ticks %19 == 0) {
            for (GameObject go : gameObjects) {
                if (go instanceof Fire)
                    ((Fire) go).move();
            }
        }

        if( playerHelicopter.hasLanded() &&
                playerHelicopter.getFull() > 0 &&
                playerHelicopter.getSpeed() < 1 &&
                Integer.parseInt(getNumberOfFires()) < 1 ) {

            finish(1);
        }
        if ( playerHelicopter.getFull()  < 1)
            finish(2);


        if (DamageToAllBuildings > 99)
            finish(3);



        for (GameObject go: gameObjects) {
            if (go instanceof Building) {
                ((Building) go).checkTheDamage(gameObjects);
            }
        }
        //playerHelicopter;

    }

    public void steerLeft() {
        playerHelicopter.steerLeft();
    }
    public void steerRight() {
        playerHelicopter.steerRight();
    }
    public void speedUp() {
        playerHelicopter.accelerate();
    }
    public void speedDown() {
        playerHelicopter.slowDown();
    }
    public void drinkWater(){
        playerHelicopter.canDrinkFrom(river);
    }
    public void dumpingWater() {
        playerHelicopter.fight(getGameObjectCollection());
    }



    public void stopEngine() {
        playerHelicopter.stopEngine();
    }


    public void startOrStopEngine() {
        playerHelicopter.startOrStopEngine();
    }

    public void quit() {
        Display.getInstance().exitApplication();
    }

    public String getHelicopterHeading() {
        return String.valueOf(playerHelicopter.getHeading());
    }

    public String getHelicopterSpeed() {
        return String.valueOf(playerHelicopter.getSpeed());
    }

    public String getHelicopterFuel() {
        return String.valueOf(playerHelicopter.getFull());
    }

    public String getNumberOfFires() {
        int numberOfFires=0;
        for (GameObject go: gameObjects) {
            if (go instanceof FireStates){
                numberOfFires++;
            }
        }
        return String.valueOf(numberOfFires);
    }
    public String getTotalFiresSize() {
        int fireSize=0;
        for (GameObject go: gameObjects) {
            if (go instanceof FireStates){
                fireSize += ((FireStates) go).getTotalSize();
            }
        }
        return String.valueOf(fireSize);
    }

    public String getTotalDamage() {
        int totalDamage=0;
        for (GameObject go: gameObjects) {
            if (go instanceof Building)
                totalDamage += ((Building) go).getTheDamage();
        }
        totalDamage = totalDamage /3;
        if (DamageToAllBuildings < totalDamage)
            DamageToAllBuildings = totalDamage;

        return String.valueOf(DamageToAllBuildings);
    }

    public String getLossOFAllBuildings() {
        int totalLoss=0;
        int temp=0;
        for (GameObject go: gameObjects) {
            if (go instanceof Building)
                totalLoss += ((Building) go).getBuildingDamage();
        }

        if (lossOFAllBuildings < totalLoss)
            lossOFAllBuildings = totalLoss;

        return String.valueOf(lossOFAllBuildings);
    }


    private void finish(int result) {

        if(result == 1){

            if(Dialog.show("Do you want to restart","You won, your Score is :"
                    + (100 - DamageToAllBuildings) ,"Yes", "No") ) {
                // user clicked yes
                playerHelicopter.restart();
                new Game();
            } else {
                // user clicked no
                quit();
            }
        }

        if(result ==2) {

            if(Dialog.show("Do you want to restart",
                    "You lost,ran of fuel",
                    "Yes", "No")) {
                // user clicked yes
                for (GameObject go:gameObjects){
                    if (go instanceof PlayerHelicopter){
                        playerHelicopter.restart();
                    }
                }
                new Game();
            } else {
                // user clicked no
                quit();
            }
        }

        if (result == 3) {

            if(Dialog.show("Do you want to restart",
                    "You lost, Buildings are Burned",
                    "Yes", "No")) {
                // user clicked yes
                playerHelicopter.restart();
                new Game();
            } else {
                // user clicked no
                quit();
            }
        }
    }


}
