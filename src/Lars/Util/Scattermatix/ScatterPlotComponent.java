package Lars.Util.Scattermatix;

import Lars.Util.Vector2D;
import javafx.util.Pair;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Lars on 6/11/2015.
 */
public class ScatterPlotComponent extends AMatrixComponent {

    public Pair<String, ArrayList<Float>> pairX;
    public Pair<String, ArrayList<Float>> pairY;




    protected Vector2D xRange;
    protected Vector2D yRange;
    protected Integer maxCharacterX;
    protected Integer maxCharacterY;
    protected ArrayList<Vector2D> normalizedPoints = new ArrayList<Vector2D>();

    public ScatterPlotComponent(Pair<String, ArrayList<Float>> pairX, Pair<String, ArrayList<Float>> pairY) {
        this.pairX = pairX;
        this.pairY = pairY;

        for(Float i : pairX.getValue()){
            int charSize = i.toString().toCharArray().length;
            if(xRange==null){xRange = new Vector2D(i,i);}
            if(maxCharacterX == null){maxCharacterX = charSize;}

            if(i < xRange.X){xRange.X = i;}
            else if(i > xRange.Y){xRange.Y = i;}
            if(maxCharacterX < charSize){maxCharacterX=charSize;}
        }

        for(Float i : pairY.getValue()){
            int charSize = i.toString().toCharArray().length;
            if(yRange==null){ yRange = new Vector2D(i,i);}
            if(maxCharacterY == null){maxCharacterY = charSize;}

            if(i < yRange.X){ yRange.X = i;}
            else if(i > yRange.Y){yRange.Y = i;}
            if(maxCharacterY < charSize){maxCharacterY = charSize;}
        }
        if(pairX.getValue().size()==pairY.getValue().size()) {
            for (int i = 0; i < pairX.getValue().size(); i++) {
                normalizedPoints.add(
                        new Vector2D(
                                PApplet.map(pairX.getValue().get(i), xRange.X, xRange.Y, 0, this.size.X - 1),
                                PApplet.map(pairY.getValue().get(i), yRange.X, yRange.Y, this.size.Y - 1, 0)
                        )
                );
            }
            Collections.sort(normalizedPoints, new Comparator<Vector2D>() {
                @Override
                public int compare(Vector2D o1, Vector2D o2) {
                    return (int)(o1.X-o2.X);
                }
            });

        }else
        {
            System.out.println("data sets should be of equal size");
            System.exit(1);
        }

    }

    @Override
    public void drawPointsOnGraph(PApplet applet) {
        for(Vector2D point : normalizedPoints){
            applet.fill(0);
            applet.ellipse(point.X,point.Y,5,5);
        }

    }


    @Override
    public void DrawComponent(PApplet applet) {
        drawBoundingBox(applet);
    }

    @Override
    public void drawXAxisComponents(PApplet applet, XOrientation orientation) {
        int xPosition=0;
        switch (orientation){
            case LEFT:
                xPosition=-(10*maxCharacterX);
                break;
            case RIGHT:
                xPosition = (int)size.X+(10*maxCharacterX);
                break;
        }
        for (int i = 0; i<5;i++){
            float AxisValue = xRange.X + ((xRange.Y-xRange.X)/4)*i;
            applet.text(AxisValue,xPosition,(size.Y -((size.Y/4)*i))+2);
        }

    }

    @Override
    public void drawYAxisComponents(PApplet applet, YOrientation orientation) {
        int yPosition=0;
        switch (orientation){
            case UP:
                yPosition=-(5*maxCharacterY);
                break;
            case DOWN:
                yPosition = (int)size.Y+(5*maxCharacterY);
                break;
        }
        for (int i = 0; i<5;i++){
            float AxisValue = yRange.X + ((yRange.Y-yRange.X)/4)*i;
            applet.pushMatrix();

            applet.translate(((size.X/4)*i)-5*i,yPosition);
            applet.rotate(45);
            applet.textAlign(PConstants.CENTER);
            applet.text(AxisValue,0,0);
            applet.popMatrix();

        }
    }



}
