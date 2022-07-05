package org.csc133.a3.gameobject;

import org.csc133.a3.GameWorld;

import java.util.ArrayList;

public class GameObjectCollection {
    GameWorld gw;

    public GameObjectCollection (GameWorld gw) {
        this.gw = gw;
    }

    public ArrayList<Building> getBuildings() {
        ArrayList<Building> buildings = new ArrayList<>();

        for (GameObject go: gw.getGameObjectCollection() )
            if (go instanceof Building)
                buildings.add((Building) go);

        return buildings;
    }

    public River getRiver() {
        River rive = null;

        for (GameObject go: gw.getGameObjectCollection())
            if (go instanceof River)
                rive = (River) go;

        return rive;
    }

}
