package org.csc133.a3.gameobject;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.CN;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import org.csc133.a3.GameWorld;
import org.csc133.a3.interfaces.Steerable;

import java.util.ArrayList;


public abstract class Helicopter extends Movable implements Steerable {

    private static final int BUBBLE_RADIUS = 125;
    private static final int ENGINE_BLOCK_WIDTH = (int) (BUBBLE_RADIUS * 1.8);
    private static final int ENGINE_BLOCK_HEIGHT = ENGINE_BLOCK_WIDTH /3;
    private static final int BLADE_WIDTH = 25;
    private static final int BLADE_LENGTH = BUBBLE_RADIUS * 5;
    private static final int SKIDS_WIDTH = 25;
    private static final int SKIDS_LENGTH = (int) (BUBBLE_RADIUS * 2.7);
    private static final int TAIL_BOX_WIDTH = (int) (BUBBLE_RADIUS / 2.2);
    private static final int TAIL_BOX_HEIGHT = (int) (BUBBLE_RADIUS /  4.5);

    private GameWorld gw;




    private ArrayList<GameObject> heloParts;
    protected HeloBlade heloBlade;

    private int color;

    //Helicopter Constructor get helipad as a parameter !Not anymore
    //
    public Helicopter() {



        heloParts = new ArrayList<>();

        heloBlade = new HeloBlade();

        heloParts.add(new HeloBubBle());
        heloParts.add(new HeloEngineBlock());
        heloParts.add(heloBlade);
        heloParts.add(new HeloBladeShaft());
        heloParts.add(new Skids());
        heloParts.add(new HiloTail());
        heloParts.add(new Info());

        scale(0.2,0.2);
        color = changeColor();

    }

    public abstract int changeColor();

    //```````````````````````````````````````````````````````````````````````````````
    public class HeloBubBle extends GameObject {



        public HeloBubBle() {

            setColor(changeColor());
            setDimension(new Dimension(2* BUBBLE_RADIUS,
                    2* BUBBLE_RADIUS));

            translate(0,(float) (BUBBLE_RADIUS * 0.80));
            scale(1,1);
            rotate(0);
        }

        @Override
        public void localDraw(Graphics g,Point originParent,
                              Point originScreen) {
            g.setColor(changeColor());
            containerTranslate(g,originParent);
            cn1ForwardPrimitiveTranslate(g,getDimension());

            g.drawArc(0,0,
                    getDimension().getWidth(),
                    getDimension().getHeight(),
                    135,270);
        }

        @Override
        public void setSize(int size) {

        }


    }


    //``````````````````````````````````````````````````````````````````````````````````````````

    public class HeloEngineBlock extends GameObject {

        public HeloEngineBlock() {

            setColor(changeColor());
            setDimension(new Dimension(ENGINE_BLOCK_WIDTH,
                    ENGINE_BLOCK_HEIGHT));

            translate(0,(float) -ENGINE_BLOCK_HEIGHT/2);
            scale(1,1);
            rotate(0);


        }

        public void localDraw(Graphics g, Point originParent,
                              Point originScreen) {

            g.setColor(changeColor());
            containerTranslate(g,originParent);
            cn1ForwardPrimitiveTranslate(g,getDimension());
            g.drawRect(0,0,
                    getDimension().getWidth(),
                    getDimension().getHeight());

        }

        @Override
        public void setSize(int size) {

        }

    }

    //``````````````````````````````````````````````````````````````````````````````````````````

    public class HeloBlade extends GameObject {

        public HeloBlade() {

            setColor(ColorUtil.GRAY);
            setDimension(new Dimension(BLADE_LENGTH,BLADE_WIDTH));
            translate(0,- ENGINE_BLOCK_HEIGHT/2);
            scale(1,1);
            rotate(42);

        }

        @Override
        public void localDraw(Graphics g, Point originParent,
                              Point originScreen) {

            g.setColor(getColor());
            containerTranslate(g,originParent);
            cn1ForwardPrimitiveTranslate(g,getDimension());
            g.drawRect(0,0,
                    getDimension().getWidth(),
                    getDimension().getHeight());

        }

        @Override
        public void setSize(int size) {

        }

        public void updateLocalTransforms(double rotationSpeed) {
            this.rotate(rotationSpeed);
        }
    }


    //``````````````````````````````````````````````````````````````````````````````````````````

    public class HeloBladeShaft extends GameObject {

        public HeloBladeShaft() {

            setColor(ColorUtil.GRAY);
            setDimension(new Dimension(2 * BLADE_WIDTH /3,
                    2 * BLADE_WIDTH /3));

            translate(0,- ENGINE_BLOCK_HEIGHT/2);
            scale(1,1);
            rotate(0);
        }
        @Override
        public void localDraw(Graphics g, Point originParent,
                              Point originScreen) {
            g.setColor(getColor());
            containerTranslate(g,originParent);
            cn1ForwardPrimitiveTranslate(g,getDimension());

            g.drawArc(0,0,
                    getDimension().getWidth(),
                    getDimension().getHeight(),
                    0,360);
        }

        @Override
        public void setSize(int size) {

        }

    }

    //``````````````````````````````````````````````````````````````````````````````````````````

    public class HiloTail extends GameObject   {

        public HiloTail() {
            setColor(changeColor());
        }
        @Override
        public void localDraw(Graphics g, Point originParent,
                              Point originScreen) {
            g.setColor(changeColor());

            containerTranslate(g,originParent);


            //left baseline for tail
            //
            g.drawLine(-ENGINE_BLOCK_HEIGHT /2 - 15 ,-ENGINE_BLOCK_HEIGHT,
                    -ENGINE_BLOCK_HEIGHT/2 + 10 , -ENGINE_BLOCK_WIDTH * 2);


            //Right baseline for tail
            //
            g.drawLine(ENGINE_BLOCK_HEIGHT /2 + 15,-ENGINE_BLOCK_HEIGHT,
                    ENGINE_BLOCK_HEIGHT/2 - 10,-ENGINE_BLOCK_WIDTH * 2);

            //lines between the tails
            //
            g.drawLine(-ENGINE_BLOCK_HEIGHT /2 - 15 ,-ENGINE_BLOCK_HEIGHT,
                    ENGINE_BLOCK_HEIGHT/2 , -ENGINE_BLOCK_WIDTH );

            g.drawLine(ENGINE_BLOCK_HEIGHT/2 , -ENGINE_BLOCK_WIDTH ,
                    -ENGINE_BLOCK_HEIGHT/2 + 10 , -ENGINE_BLOCK_WIDTH * 2);

            g.drawLine(ENGINE_BLOCK_HEIGHT /2 + 15,-ENGINE_BLOCK_HEIGHT,
                    -ENGINE_BLOCK_HEIGHT/2 , -ENGINE_BLOCK_WIDTH);

            g.drawLine(-ENGINE_BLOCK_HEIGHT/2 , -ENGINE_BLOCK_WIDTH,
                    ENGINE_BLOCK_HEIGHT/2-10 , -ENGINE_BLOCK_WIDTH * 2);


            //left Rectangle to tail box
            //
            //g.setColor(ColorUtil.GREEN);
            g.drawRect(-ENGINE_BLOCK_HEIGHT , (int) (-ENGINE_BLOCK_WIDTH * 2.2),
                    TAIL_BOX_WIDTH,TAIL_BOX_HEIGHT);

            //Rectangle after the attached box tp tail
            //
            g.setColor(ColorUtil.WHITE);
            g.fillRect(-ENGINE_BLOCK_HEIGHT/2 + 6  ,
                    -ENGINE_BLOCK_WIDTH * 2 - TAIL_BOX_WIDTH,
                    TAIL_BOX_WIDTH ,TAIL_BOX_WIDTH );



            //little rectangle attached to tail blade
            //
            g.setColor(ColorUtil.GRAY);
            g.fillRect(-ENGINE_BLOCK_HEIGHT/2 + TAIL_BOX_WIDTH + 6,
                    (int) (-ENGINE_BLOCK_WIDTH * 2 - TAIL_BOX_WIDTH / 1.5),
                    TAIL_BOX_WIDTH/3,TAIL_BOX_HEIGHT/2);

            //tail blade
            g.setColor(ColorUtil.LTGRAY);
            g.fillRect((int) (ENGINE_BLOCK_HEIGHT / 1.7),
                    (int) (-ENGINE_BLOCK_WIDTH * 2.38),
                    TAIL_BOX_HEIGHT/2,TAIL_BOX_WIDTH*2);

            //tail shaft
            g.setColor(ColorUtil.LTGRAY);
            g.fillRect(-ENGINE_BLOCK_HEIGHT / 8  ,
                    -ENGINE_BLOCK_HEIGHT * 6 ,
                    TAIL_BOX_HEIGHT/2, (int) (TAIL_BOX_WIDTH  * 6.7));

        }

        @Override
        public void setSize(int size) {

        }


    }

    //``````````````````````````````````````````````````````````````````````````````````````````

    public class Skids extends GameObject {

        public Skids() {
            setColor(changeColor());
            setDimension(new Dimension(SKIDS_WIDTH,SKIDS_LENGTH));
        }


        @Override
        public void localDraw(Graphics g, Point originParent,
                              Point originScreen) {

            g.setColor(changeColor());
            containerTranslate(g,originParent);
            cn1ForwardPrimitiveTranslate(g,getDimension());

            g.drawRect((int)(-ENGINE_BLOCK_WIDTH /1.35),ENGINE_BLOCK_WIDTH /5,
                    getDimension().getWidth(), getDimension().getHeight() );


            g.setColor(ColorUtil.GRAY);
            g.fillRect((int) (-ENGINE_BLOCK_WIDTH / 1.6),
                    ENGINE_BLOCK_WIDTH/2 , 40, 15);

            g.fillRect((int) (-ENGINE_BLOCK_WIDTH / 1.6),
                    (int) (ENGINE_BLOCK_WIDTH *1.3), 35, 15);

            //THe Second skid
            //
            g.setColor(changeColor());
            g.drawRect((int) (ENGINE_BLOCK_WIDTH /1.35),ENGINE_BLOCK_WIDTH /5,
                    getDimension().getWidth(), getDimension().getHeight() );


            g.setColor(ColorUtil.GRAY);
            g.fillRect((int) (ENGINE_BLOCK_WIDTH / 1.8),
                    ENGINE_BLOCK_WIDTH/2 , 40, 15);

            g.fillRect((int) (ENGINE_BLOCK_WIDTH / 1.7),
                    (int) (ENGINE_BLOCK_WIDTH * 1.3), 35, 15);


        }

        @Override
        public void setSize(int size) {

        }

    }
    //``````````````````````````````````````````````````````````````````````````````````````````

    public class Info extends GameObject{

        public Info() {
            rotate(180);
            scale(-1,1);
            translate(-ENGINE_BLOCK_WIDTH * 3,-BUBBLE_RADIUS * 6 );
            scale(3,3);
        }
        @Override
        public void localDraw(Graphics g, Point originParent,
                              Point originScreen) {

            g.setColor(ColorUtil.GREEN);
            g.setFont(Font.createSystemFont(CN.FACE_SYSTEM,
                    CN.STYLE_BOLD,CN.SIZE_LARGE ));

            g.drawString("F: " + getFull(),0,-20);
            g.drawString("w: " + getWater(),0,20);

        }

        @Override
        public void setSize(int size) {

        }
    }

    //``````````````````````````````````````````````````````````````````````````````````````````
    @Override
    public void localDraw(Graphics g, Point parentOrigin,
                          Point screenOrigin) {


        for (GameObject go: heloParts) {
            go.draw(g,parentOrigin ,screenOrigin);
        }
    }

    @Override
    public void setSize(int size) {

    }
    abstract public int getWater();
    abstract public int getFull();

    public abstract void updateLocalTransforms();

}
