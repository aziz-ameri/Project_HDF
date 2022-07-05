package org.csc133.a3.gameobject;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import com.codename1.util.MathUtil;
import org.csc133.a3.GameWorld;

import java.util.ArrayList;

public class FlightPath extends GameObject{

    private BezierCurve helipadToRiverCurve;
    private BezierCurve riverToFireCurve;
    private BezierCurve fireBackToRiverCurve;
    private ArrayList<Point2D> helipadToRiverControlPoints;
    private ArrayList<Point2D> riverToFireControlPoints;
    private ArrayList<Point2D> fireBackToRiveControlPoints;
    private boolean usedOnce;
    private boolean active;
    private GameWorld gw;



    public FlightPath(Helipad helipad,River river) {

        //t=0;
        usedOnce = true;
        active = true;

        helipadToRiverControlPoints = new ArrayList<>();
        riverToFireControlPoints = new ArrayList<>();
        fireBackToRiveControlPoints = new ArrayList<>();

        helipadToRiverControlPoints.add(helipad.getHelipadLocation());
        helipadToRiverControlPoints.add(new Point2D(300,300));
        helipadToRiverControlPoints.add(new Point2D(700 , 1200));
        helipadToRiverControlPoints.add(river.goToRIver());

        riverToFireControlPoints.add(river.goToRIver());
        riverToFireControlPoints.add(new Point2D(800,1400));
        riverToFireControlPoints.add(new Point2D(1450,1350));
        riverToFireControlPoints.add(new Point2D(1500,800 ));

        fireBackToRiveControlPoints.add(riverToFireControlPoints.get(
                riverToFireControlPoints.size()-1));
        fireBackToRiveControlPoints.add(new Point2D(2800,-100));
        fireBackToRiveControlPoints.add(new Point2D(900,50));
        fireBackToRiveControlPoints.add(river.goToRIver());


        helipadToRiverCurve = new BezierCurve(helipadToRiverControlPoints);
        riverToFireCurve = new BezierCurve(riverToFireControlPoints);
        fireBackToRiverCurve = new BezierCurve(fireBackToRiveControlPoints);

    }

    public void getFireLocation(ArrayList<GameObject> fires) {

    }

    public void moveAlongAPath(Point2D c, NonePlayerHelicopter helicopter) {
        if (usedOnce && active) {
            helipadToRiverCurve.moveAlongAPath(c,helicopter);
            if (helipadToRiverCurve.getT() >= 1) {
                riverToFireCurve.setT(0);
                usedOnce = false;
                helicopter.setWater(1000);
            }
        }

        if (!usedOnce && active) {
            riverToFireCurve.moveAlongAPath(c,helicopter);
            if (riverToFireCurve.getT() >= 1){
                fireBackToRiverCurve.setT(0);
                active = false;
                helicopter.fight();
            }
        }

        if (!active) {
            fireBackToRiverCurve.moveAlongAPath(c,helicopter);
            if (fireBackToRiverCurve.getT() >= 1) {
                riverToFireCurve.setT(0);
                active = true;

            }
        }

    }


//    private Point2D getMove(double t) {
//        if (active && t <= 1) {
//            return riverEvaluateCurve(t);
//
//        }
//        else if (active && t >= 1){
//            active = false;
//            t =0;
//            return fireEvaluateCurve(t);
//        }
//
//        if (!active && t <= 1)
//            return fireEvaluateCurve(t);
//
//        else {
//            active = true;
//            t=0;
//            return riverEvaluateCurve(t);
//        }
//
//        //else active = true;
//
//    }



    public void setTail(Point2D p) {
        riverToFireControlPoints.set(riverToFireControlPoints.size()-1,p );
        //riverToFireCurve.setControlPoints(riverToFireControlPoints);
    }

    public void setFireTailBackToRIver() {
        fireBackToRiveControlPoints.set( 0,riverToFireControlPoints.get(
                riverToFireControlPoints.size()-1));
        //fireBackToRiverCurve.setControlPoints(fireBackToRiveControlPoints);
    }

    @Override
    public void localDraw(Graphics g, Point originParent, Point originScreen) {

        containerTranslate(g,originParent);

        helipadToRiverCurve.drawBezierCurer(g,helipadToRiverControlPoints);
        riverToFireCurve.drawBezierCurer(g,riverToFireControlPoints);
        fireBackToRiverCurve.drawBezierCurer(g,fireBackToRiveControlPoints);
    }

    @Override
    public void setSize(int size) {

    }

    //AI Helicopter methods
    //

    public Point2D getHelipadStartingPoint() {
           return helipadToRiverControlPoints.get(0);
    }

    public Point2D helipadEvaluateCurve(double t) {
        return helipadToRiverCurve.evaluateCurve(t);
    }
    public  Point2D riverEvaluateCurve(double t) {
        return riverToFireCurve.evaluateCurve(t);
    }

    public Point2D fireEvaluateCurve(double t) {
        return fireBackToRiverCurve.evaluateCurve(t);
    }

    public class BezierCurve {

        private ArrayList<Point2D> controlPoints;
        public BezierCurve(ArrayList<Point2D> controlPoints) {
            this.controlPoints = controlPoints;
        }
        public void setControlPoints(ArrayList<Point2D> controlPoints) {
            this.controlPoints = controlPoints;
        }

        public Point2D getStartingPoint () {
            return controlPoints.get(0);
        }

        ////////
        private double t;
        public void setT(double t) {this.t = t;}
        public double getT(){return t;}

        public void moveAlongAPath(Point2D c,
                                   NonePlayerHelicopter helicopter) {
            Point2D p = evaluateCurve(t);

            double tx = p.getX() - c.getX();
            double ty = p.getY() - c.getY();

            double theta = Math.toDegrees(MathUtil.atan2(ty,tx));

            helicopter.translate(tx,ty);

            if (t <= 1) {
                t = t + 0.001;
                helicopter.rotate(helicopter.getHeading() - theta);
                helicopter.setHeading(theta);
            }

        }



        //////////

        public Point2D evaluateCurve(double t) {
            Point2D p = new Point2D(0,0);
            int d = controlPoints.size() -1;

            for (int i=0; i < controlPoints.size(); i++) {
                double b = bernsteinD(d,i,t);

                double mx = b * controlPoints.get(i).getX();
                double my = b* controlPoints.get(i).getY();

                p.setX(p.getX() + mx);
                p.setY(p.getY() + my);
            }
            return p;
        }

        private double bernsteinD(int d, int i, double t) {
            return choose(d,i) * MathUtil.pow(t,i) * MathUtil.pow(1-t,d-i);
        }

        private double choose(int n, int k) {
            // Base case
            if (k <= 0 || k >= n)
                return 1;

            // Recursive using pascal's triangle
            //
            return choose(n-1,k-1) + choose(n-1,k);
        }

        private void drawBezierCurer(Graphics g,
                                     ArrayList<Point2D> controlPoints) {

            final double smallFloatInterment =0.0001;


            g.setColor(ColorUtil.YELLOW);

            Point2D currentPoint = controlPoints.get(0);
            Point2D nextPoint;

            double t=0;

            while (t<1) {
                nextPoint = evaluateCurve(t);


                g.drawLine((int) currentPoint.getX(),
                        (int) currentPoint.getY(),
                        (int) nextPoint.getX(), (int) nextPoint.getY() );

                currentPoint = nextPoint;
                t = t + smallFloatInterment;
            }

            nextPoint = controlPoints.get(controlPoints.size()-1);

            g.drawLine((int) currentPoint.getX(), (int) currentPoint.getY(),
                    (int) nextPoint.getX(), (int) nextPoint.getY() );


        }

        public void setTail(Point2D lastControlPoint) {
            controlPoints.set(controlPoints.size()-1,lastControlPoint);
        }


        //THis is the END of BezierCurve Class Bracket
    }


}
