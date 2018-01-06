package Lars.Util.Chart;

import Lars.Util.Vector2D;
import com.google.gson.annotations.SerializedName;
import processing.core.PApplet;

import java.util.Date;

/**
 * Created by Lars on 6/8/2015.
 * Parsing class for quake points
 */
public class QuakePoints implements IChartable, Comparable<QuakePoints> {

    @SerializedName("timestamp")
    public Date timestamp;
    @SerializedName("latitude")
    public float latitude;
    @SerializedName("longitude")
    public float longitude;
    @SerializedName("depth")
    public float depth;
    @SerializedName("size")
    public float size;
    @SerializedName("quality")
    public float quality;
    @SerializedName("humanReadableLocation")
    public String readable;


    @Override
    public int compareTo(QuakePoints o) {
        return (int)(this.depth*100f - o.depth*100f);
    }

    float normX;
    float normY;

    @Override
    public String toString() {
        return timestamp+"\n"+latitude+"\n"+longitude+"\n"+depth+"\n"+size+"\n"+quality+"\n"+readable;
    }

    /**
     * Normelize coordinates to X-Y system with (0,0) origin top left
     * @param xRange min and max from all X values
     * @param yRange min and max from all Y values
     * @param mapSize The width and height from the current map in use
     */
    public void normalizeCoords(Vector2D xRange, Vector2D yRange, Vector2D mapSize){
        normX = PApplet.map(this.longitude,xRange.X,xRange.Y,0,mapSize.X);
        normY = PApplet.map(this.latitude,yRange.X,yRange.Y,mapSize.Y,0);
    }

    /**
     * Draws this point on the given PApplet
     * @param applet PApplet to draw on
     * @param multiplier Size multiplier
     * @param sizeRange The min and max values from the size of all quakepoints
     */
    public void drawPointOnSketch(PApplet applet, int multiplier, Vector2D sizeRange){

        float ellipseSize = (2+this.size)*multiplier;
        float sizeRadius = PApplet.map(this.size,sizeRange.X,sizeRange.Y,15,0);
        applet.rect(normX, normY, ellipseSize, ellipseSize, sizeRadius);
    }

    @Override
    public float getYAxis() {
        return depth;
    }

    @Override
    public float getXAxis() {
        int time = (int)(timestamp.getTime()/1000);
        return (float)time;
    }
}
