package Lars.INFDEV08;

import Lars.Util.Chart.QuakePoints;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import processing.core.PImage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import Lars.Util.*;

/**
 * Created by Lars on 6/10/2015.
 */
public class Opdr1_IcelandSketch extends PAppletExtended {

    public static Vector2D ImageSize;

    public Vector2D middlePoint;
    public PImage IcelandMap;
    public QuakePoints[] data;


    public Opdr1_IcelandSketch() {

        IcelandMap = loadImage("400px-Iceland_location_map.svg.png");
        int mapWidth=IcelandMap.width;
        int mapHeight=IcelandMap.height;
        ImageSize = new Vector2D(mapWidth+100,mapHeight+150);
        this.middlePoint = new Vector2D(ImageSize.X/2,ImageSize.Y/2);

        InitData();
    }

    @Override
    public void setup() {

        super.setup();//call to setup() in super class (PAplletextended)

        setupIcelandChart();//Init the map of iceland

        pushMatrix();
        translate(IcelandMap.width+10,20);
        drawLegend();//draw the legend
        popMatrix();

        pushMatrix();
        translate(20,IcelandMap.height+20);
        drawDepthGraph();//draw graph with depth data
        popMatrix();

        pushMatrix();
        translate((IcelandMap.width/2),20);
        drawTitle();//draw title
        popMatrix();


    }

    public void setupIcelandChart(){
        pushMatrix();
        translate(10,10);
        image(IcelandMap,0,0);//draw image on sketch
        float sizeMax = 0;
        float sizeMin = 0;

        //Calculate the min and max size of the earthquake
        for(QuakePoints point : data){
            if (point.size>sizeMax) {sizeMax = point.size;}
            else if (point.size<sizeMin) {sizeMin = point.size;}
        }

        //Draw earthquake points ontop of image
        int i=0;
        for (QuakePoints point : data){
            i++;
            point.normalizeCoords(new Vector2D(-25f,-13f),new Vector2D(63.1f,66.8f), new Vector2D(IcelandMap.width,IcelandMap.height));
            fill((255/data.length)*i,255-(255/data.length)*i,255);
            point.drawPointOnSketch(this, 5, new Vector2D(sizeMin,sizeMax));
        }
        popMatrix();
    }

    public void drawLegend(){
        float dataMax = 0;
        float dataMin = 0;

        //Calculate the min and max size of the earthquake
        for(QuakePoints point : data){
            if (point.size>dataMax) {dataMax = point.size;}
            else if (point.size<dataMin) {dataMin = point.size;}
        }

        //Draw all data necessary for the legend on sketch
        fill(0);
        text("Size: "+dataMin,30,10);
        rect(0,10,20,20,0);
        text("Size: "+dataMax/2,30,50);
        rect(0,50,20,20,7.5f);
        text("Size: "+dataMax,30,100);
        rect(0,100,20,20,15);
    }

    public void drawTitle(){
        textSize(20);//set new text size
        text("Earthquake map - Iceland",0,0);
        textSize(12);//reset text size
    }

    public void drawDepthGraph(){
        float depthMax = 0;
        float depthMin = 0;
        for(QuakePoints point : data){
            if (point.depth>depthMax) {depthMax = point.depth;}
            else if (point.depth<depthMin) {depthMin = point.depth;}
        }
        text("Depth: "+depthMin, 20,0);
        columnGraph(data,20,100,IcelandMap.width,80,10);
        fill(0);
        text("Depth: "+depthMax, IcelandMap.width,0);

    }

    /**
     * Retrieves data from file and stores it in a array
     */
    public void InitData(){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();//use external library GSON
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/data/ijsland-metingen.json"));
            data = gson.fromJson(bufferedReader, QuakePoints[].class);//parse json data to Quakepoints array(instance of Quakepoints.class)

        } catch (IOException e) {
            e.printStackTrace();
        }
        Arrays.sort(data);//sort array
    }

}
