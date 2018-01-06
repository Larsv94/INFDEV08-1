package Lars.Util;

import Lars.Util.Chart.IChartable;
import processing.core.*;
import java.util.Arrays;
/**
 * Created by Lars on 6/9/2015.
 * Extended version from the applet to allow all applets extending from this to use the utilitarian methods
 */
public class PAppletExtended extends PApplet {

    public static enum Axis{
        Y_AXIS,
        X_AXIS
    }

    public Vector2D SIZE;

    @Override
    public void setup() {
        super.setup();

        //if(SIZE!=null){
            size((int)SIZE.X,(int)SIZE.Y);
        //}
    }

    /**
     * Draws gradient
     * @param x start X position
     * @param y start Y position
     * @param w Width
     * @param h Height
     * @param c1 First color
     * @param c2 Second color
     * @param axis Axis in which the color lapses
     */
    public void gradient(int x, int y, float w, float h, color c1, color c2, Axis axis) {
        noFill();

        if (axis == Axis.Y_AXIS) {  // Top to bottom gradient
            for (int i = y; i <= y + h; i++) {
                float inter = map(i, y, y + h, 0, 1);
                int c = lerpColor(color(c1.R,c1.G,c1.B), color(c2.R,c2.G,c2.B), inter);
                stroke(c);
                line(x, i, x + w, i);
            }
        } else if (axis == Axis.X_AXIS) {  // Left to right gradient
            for (int i = x; i <= x + w; i++) {
                float inter = map(i, x, x + w, 0, 1);
                int c = lerpColor(color(c1.R,c1.G,c1.B), color(c2.R,c2.G,c2.B), inter);
                stroke(c);
                line(i, y, i, y + h);
            }
        }
    }

    /**
     * Draws a column graph
     * @param data (IChartable) data points array
     * @param x start X position
     * @param y start Y position
     * @param width Width
     * @param height Height
     * @param columnMargin Margin between columns
     */
    public void columnGraph(IChartable[] data, int x, int y, float width, float height,float columnMargin ){
        float graphWidth=width;//used for width check
        if(data.length*10> width){graphWidth = data.length*10;}

        float columnW = (graphWidth-(columnMargin*data.length))/data.length;
        float columnH = height;

        float dataXMax = 0;
        float dataXMin = 0;
        for(IChartable point : data){
            if (point.getYAxis()>dataXMax) {dataXMax = point.getXAxis();}
            else if (point.getXAxis()<dataXMin) {dataXMin = point.getXAxis();}
        }
        float dataYMax = 0;
        float dataYMin = 0;
        for(IChartable point : data){
            if (point.getYAxis()>dataYMax) {dataYMax = point.getYAxis();}
            else if (point.getYAxis()<dataYMin) {dataYMin = point.getYAxis();}
        }
        Arrays.sort(data);

        float columnX=x;
       for (int i = 0; i<data.length;i++){
            float columnHeight = map(data[i].getYAxis(),dataYMin,dataYMax,0,columnH)*-1;
           fill((255/data.length)*i,255-(255/data.length)*i,255);
            rect (columnX,y,columnW,columnHeight);
           columnX= columnX+columnW+columnMargin;
        }



    }
}
