package Lars.Util.FloodData;

import Lars.Util.ColorPoint3D;
import Lars.Util.Vector2D;
import Lars.Util.Vector3D;
import Lars.Util.color;
import processing.core.PApplet;
import processing.core.PGraphics;

import java.util.ArrayList;

/**
 * Created by Lars on 6/17/2015.
 */
public class TopDownChart {
    public static  enum LAYOUT{
        XY,
        YX,
        YZ,
        ZY,
        ZX,
        XZ
    }
    public final int boundOffset = 5;

    public Vector2D Size;
    public ArrayList<ColorPoint3D> Coordinates;
    public Vector2D minCoords;
    public Vector2D maxCoords;
    public LAYOUT CoordsToUse;
    public float PointSize;
    public float PointMargin;

    float drawCorrection;




    public TopDownChart(Vector2D size, ArrayList<ColorPoint3D> coordinates, Vector3D minCoords, Vector3D maxCoords, LAYOUT coordsToUse,float drawCorrection) {
        Size = size;
        Coordinates = coordinates;
        CoordsToUse = coordsToUse;
        PointSize = 1;
        PointMargin = 0.5f;
        this.minCoords = getCoordinates(minCoords);
        this.maxCoords = getCoordinates(maxCoords);
        this.drawCorrection=drawCorrection;
    }

    public TopDownChart(Vector2D size, ArrayList<ColorPoint3D> coordinates, Vector3D minCoords, Vector3D maxCoords, LAYOUT coordsToUse, float pointSize, float pointMargin,float drawCorrection) {
        Size = size;
        Coordinates = coordinates;
        CoordsToUse = coordsToUse;
        PointSize = pointSize;
        PointMargin = pointMargin;


        this.minCoords = getCoordinates(minCoords);
        this.maxCoords = getCoordinates(maxCoords);

        this.drawCorrection=drawCorrection;
    }


    public void Draw(PGraphics applet){
        DrawBox(applet);
        DrawPoints(applet);

    }

    /**
     * Draw box around this component
     * @param applet the component on which to draw
     */
    protected void DrawBox(PGraphics applet){
        applet.pushMatrix();
        applet.translate(50,50);
        applet.strokeWeight(5);
        applet.rect(-boundOffset/2,-boundOffset/2,Size.X+boundOffset,Size.Y+boundOffset);
        applet.popMatrix();
    }

    /**
     * Draw points on this component
     * @param applet the component on which to draw
     */
    protected void DrawPoints(PGraphics applet){

        applet.pushMatrix();
        applet.translate(50,50);
        for (ColorPoint3D point : Coordinates){
            Vector2D coords = getCoordinates(point);
            applet.strokeWeight(2.5f/drawCorrection);
            color Color = point.getColor();
            applet.stroke(Color.R,Color.G,Color.B);
            applet.point(
                    PApplet.map(coords.X,minCoords.X,maxCoords.X,0,Size.X),
                    PApplet.map(coords.Y,minCoords.Y,maxCoords.Y,Size.Y,0));

        }
        applet.popMatrix();

    }

    /**
     * Gets the points which to use from the data set
     * This allows the program to use, for example, a X-Z based system without any change in the code
     * @param point
     * @return
     */
    private Vector2D getCoordinates(Vector3D point){
        switch (CoordsToUse){
            case XY:
                return new Vector2D(point.getX(),point.getY());

            case YX:
                return new Vector2D(point.getY(),point.getX());

            case YZ:
                return new Vector2D(point.getY(),point.getZ());

            case ZY:
                return new Vector2D(point.getZ(),point.getY());

            case ZX:
                return new Vector2D(point.getZ(),point.getX());

            case XZ:
                return new Vector2D(point.getX(),point.getZ());

            default: return new Vector2D(0,0);
        }
    }
}
