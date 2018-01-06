package Lars.INFDEV08;

import Lars.Util.*;
import Lars.Util.FloodData.TopDownChart;
import processing.core.PGraphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Lars on 6/16/2015.
 */
public class Opdr3_FloodMap extends PAppletExtended {
    public  String filePath;


    public ArrayList<ColorPoint3D> points = new ArrayList<ColorPoint3D>();
    public Vector3D pointsMax;
    public Vector3D pointsMin;
    public float previousWaterLevel;
    public float currentWaterLevel;
    public int lastWaterIndex=0;
    public Timer increaseWaterLevel = new Timer("Increase Water Level");

    public PGraphics graphicImage;

    public ColorPoint3D centerPoint;

    boolean play_animation = false;
    public float step = 1.0f;
    public int timeCounter=0;

    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    Date time = new Date(12*60*60*1000);

    float sizeCorrection;



    private TopDownChart chart;

    public Opdr3_FloodMap(String path, float sizeCorrection) {
        this.filePath = path;
        this.sizeCorrection = sizeCorrection;

        constructData();

        //create the top down height graph
        chart = new TopDownChart(
                new Vector2D((int)((pointsMax.getX() - pointsMin.getX())/sizeCorrection),(int) ((pointsMax.getY() - pointsMin.getY())/sizeCorrection)),
                points,
                pointsMin,
                pointsMax,
                TopDownChart.LAYOUT.XY
                ,sizeCorrection
        );



    }



    @Override
    public void draw() {
        graphicImage.clear();
        clear();


        background(255);
        ReCalculateColors();

        //Start the draw on the PGraphics
        graphicImage.beginDraw();
        graphicImage.background(255);
        chart.Draw(graphicImage);//draw chart on graphicImage


        graphicImage.pushMatrix();
        graphicImage.translate(50,20);
        drawWaterLevel();//draw water level in text
        graphicImage.popMatrix();

        graphicImage.pushMatrix();
        graphicImage.translate(400,20);
        drawTimeElapsed();//draw time elapsed. starts a 12:00 and follows 24h cycle
        graphicImage.popMatrix();

        graphicImage.endDraw();

        image(graphicImage, 0, 0);
        if(play_animation){
            //if animation is still playing schedule the next loop
            resetTimer();
        }

        //not drawn on image:
        pushMatrix();
        translate(100+(int)((pointsMax.getX() - pointsMin.getX())/sizeCorrection),50);
        drawLegend();
        popMatrix();


        noLoop();
    }

    @Override
    public void setup() {
        super.setup();

        graphicImage = createGraphics((int)SIZE.X,(int)SIZE.Y);//create PGraphics
        graphicImage.beginDraw();
        background(255);
        graphicImage.background(255);
        chart.Draw(graphicImage);


        graphicImage.pushMatrix();
        graphicImage.translate(50,20);
        drawWaterLevel();
        graphicImage.popMatrix();

        graphicImage.pushMatrix();
        graphicImage.translate(400,20);
        drawTimeElapsed();
        graphicImage.popMatrix();

        graphicImage.endDraw();


        image(graphicImage, 0, 0);

        //not drawn on image:
        pushMatrix();
        translate(100+(int)((pointsMax.getX() - pointsMin.getX())/sizeCorrection),50);
        drawLegend();
        popMatrix();

    }

    private void drawLegend(){
        float threshold1 = points.get((points.size() / 4)).getZ();
        float threshold2 = points.get((points.size() / 4) * 2).getZ();
        float threshold3 = points.get((points.size() / 4) * 3).getZ();

        fill(0);

        //draw gradient legends and appropriate height information per gradient.
        text("Hoogte: "+pointsMax.getZ()+" N.A.P",55,0);
        gradient(0, 0, 50, 100, new color(0, 0, 0), new color(255, 0, 0), Axis.Y_AXIS);//method gradient is in PAppletExtended
        text("Hoogte: "+threshold3+" N.A.P",55,100);
        gradient(0, 100, 50, 100, new color(255, 0, 0), new color(255, 255, 0), Axis.Y_AXIS);
        text("Hoogte: "+threshold2+" N.A.P",55,200);
        gradient(0, 200, 50, 100, new color(255, 255, 0), new color(0, 255, 0), Axis.Y_AXIS);
        text("Hoogte: "+threshold1+" N.A.P",55,300);
        gradient(0, 300, 50, 100, new color(0, 255, 0), new color(0, 255, 255), Axis.Y_AXIS);

        text("Hoogte: "+pointsMin.getZ()+" N.A.P",55,400);
        drawWaterLevelLine();
    }
    private void drawWaterLevelLine(){
        float threshold1 = points.get((points.size() / 4)).getZ();
        float threshold2 = points.get((points.size() / 4) * 2).getZ();
        float threshold3 = points.get((points.size() / 4) * 3).getZ();

        float waterleveloffset;

        //determine the waterlevel lapse per threshold
        if(currentWaterLevel<threshold1){
            waterleveloffset = map(currentWaterLevel,pointsMin.getZ(),threshold1,0,100);
        } else if(currentWaterLevel>=threshold1&&currentWaterLevel<threshold2){
            waterleveloffset = map(currentWaterLevel,threshold1,threshold2,100,200);
        }else if(currentWaterLevel>=threshold2&&currentWaterLevel<threshold3){
            waterleveloffset = map(currentWaterLevel,threshold2,threshold3,200,300);
        }else {
            waterleveloffset = map(currentWaterLevel,threshold3,pointsMax.getZ(),300,400);
        }
        stroke(0,0,255);
        strokeWeight(3);
        line(-10,400-waterleveloffset,200,400-waterleveloffset);
        text("Current Water level: "+currentWaterLevel+" N.A.P",200,400-waterleveloffset);
        stroke(0);
        strokeWeight(1);
    }

    private void drawWaterLevel(){
        graphicImage.fill(0);
        graphicImage.text("Current Water level: "+currentWaterLevel+" N.A.P", 0, 0);
    }

    private void drawTimeElapsed(){
        graphicImage.fill(0);
        graphicImage.text("Current Time: " + timeFormat.format(time), 0, 0);
    }

    private void constructData() {

        //tyr to get data from file path
        try {
            BufferedReader file = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = file.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, ";");
                points.add(new ColorPoint3D((int)Float.parseFloat(st.nextToken()), (int)Float.parseFloat(st.nextToken()),Float.parseFloat(st.nextToken())));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //set "random" start points for min and max values
        pointsMin = points.get(0);
        pointsMax = points.get(points.size()-1);
        setRanges();
        fillGaps(DataToMatrix());

        //sort the arraylist on custom comparator.
        Collections.sort(points, new Comparator<ColorPoint3D>() {
            @Override
            public int compare(ColorPoint3D o1, ColorPoint3D o2) {
                return (int)o1.getZ()-(int)o2.getZ();
            }
        });

        currentWaterLevel = pointsMin.getZ();//Start the current water level at lowest posible point

        calculateHeightColor();



    }

    /**
     * Transfers the arraylist to two dimensional array for gap detection
     *  By converting to two dimensional array the array will contain gaps on the places a point should have been
     * @return Two dimensional ColorPoint3D array created from the points arraylist
     */
    private ColorPoint3D[][] DataToMatrix(){
        ColorPoint3D[][] matrix = new ColorPoint3D[((int)pointsMax.getX()-(int)pointsMin.getX())+1][((int)pointsMax.getY()-(int)pointsMin.getY()+1)];
        for(ColorPoint3D point : points){
            matrix[(int)point.getX()-(int)pointsMin.getX()][(int)point.getY()-(int)pointsMin.getY()] = point;
        }
        return matrix;
    }

    /**
     * Create points where points are missing
     * @param matrix two dimensional array with the current points
     */
    private void fillGaps(ColorPoint3D[][] matrix){

        //loops go in steps of two because the coordinates also go in steps of two(1-3-5-7-9-etc)
        for (int i = 0; i <matrix.length;i=i+2) {
            for (int j = 0; j < matrix[i].length ; j=j+2) {
                 if(matrix[i][j]==null){
                    //create point if a point is missing
                    ColorPoint3D point = new ColorPoint3D(
                            i+pointsMin.getX(),
                            j+pointsMin.getY(),
                            getAvarageHeight(matrix,i,j),
                            new color(255,0,255
                            ));
                    matrix[i][j]=point;
                    points.add(point);
                }
            }
        }
    }

    /**
     * Returns height based on surrounding points
     * @param matrix two dimensional array with the current points
     * @param x current X position in array loop
     * @param y current Y position in array loop
     * @return the average height
     */
    private float getAvarageHeight(ColorPoint3D[][] matrix, int x, int y){
        float height=0;
        int Count = 0;
        for (int i = -2; i <=2; i+=2) {
            for (int j = -2; j <=2 ; j+=2) {

                if ((x + i) >= 0 && (y + j) >= 0&&(x + i) < matrix.length && (y + j) < matrix[x + i].length) {
                    ColorPoint3D tempPoint = matrix[x + i][y + j];
                    if (tempPoint != null) {
                        height += tempPoint.getZ();
                        Count++;
                    }
                }
            }
        }
        return height/Count;
    }

    /**
     * Calculates the min and max ranges from the given points
     */
    private void setRanges() {
        for (Vector3D point : points) {


            if (point.getX() > pointsMax.getX()) {
                pointsMax.setX(point.getX());
            } else if (point.getX() < pointsMin.getX()) {
                pointsMin.setX(point.getX());
            }

            if (point.getY() > pointsMax.getY()) {
                pointsMax.setY(point.getY());
            } else if (point.getY() < pointsMin.getY()) {
                pointsMin.setY(point.getY());
            }

            if (point.getZ() > pointsMax.getZ()) {
                pointsMax.setZ(point.getZ());
            } else if (point.getZ() < pointsMin.getZ()) {
                pointsMin.setZ(point.getZ());
            }

        }
    }

    /**
     * Calculates the appropriate colors per height based on given points and n*1/4th thresholds
     */
    private void calculateHeightColor() {
        float threshold1 = points.get((points.size() / 4)).getZ();
        float threshold2 = points.get((points.size() / 4) * 2).getZ();
        float threshold3 = points.get((points.size() / 4) * 3).getZ();

        for (ColorPoint3D point : points) {
            color Color;
                if (point.getZ() <= threshold1) {

                    float colorOffset = map(point.getZ(), pointsMin.getZ(), threshold1, 0, 255);//calculating the right color based on the height
                    Color = new color(0, 255, 255 - (int) colorOffset);

                } else if (point.getZ() > threshold1 && point.getZ() < threshold2) {

                    float colorOffset = map(point.getZ(), threshold1, threshold2, 0, 255);
                    Color = new color((int) colorOffset, 255, 0);

                } else if (point.getZ() > threshold2 && point.getZ() < threshold3) {

                    float colorOffset = map(point.getZ(), threshold2, threshold3, 0, 255);
                    Color = new color(255, 255 - (int) colorOffset, 0);

                } else {

                    float colorOffset = map(point.getZ(), threshold3, pointsMax.getZ(), 0, 255);
                    Color = new color(255 - (int) colorOffset, 0, 0);

                }



            point.setColor(Color);
        }

    }

    /**
     * Recalculate colors based on current waterlevel and last position in the recalculation
     */
    private void ReCalculateColors(){
        for (int i = lastWaterIndex; i < points.size()&&points.get(i).getZ()<=currentWaterLevel ; i++) {
            points.get(i).setColor(color.BLUE());
            lastWaterIndex = i-1;
        }
    }


    /**
     * Set the amount in which the waterlevel increases each cycle
     * @param step new step value
     */
    public void setStep(float step) {
        this.step = step;
    }

    /**
     * (re)starts drawing cycle
     */
    public void playDraw(){
        play_animation=true;
        resetTimer();
    }

    /**
     * pauzes drawing cycle
     */
    public void pauzeDraw(){
        play_animation= false;
        increaseWaterLevel.cancel();
    }

    /**
     * Stops and resets drawing cycle
     */
    public void stopDraw(){
        play_animation=false;
        increaseWaterLevel.cancel();

        increaseWaterLevel = new Timer("Increase Water Level");
        increaseWaterLevel.schedule(new TimerTask() {
            @Override
            public void run() {
                currentWaterLevel = pointsMin.getZ() - 1;
                calculateHeightColor();
                loop();

            }
        }, 1000);

    }

    /**
     * initiates image save
     */
    public void saveImage(){
        Timer tempTimer = new Timer("temp");
        //saves image 600 milliseconds after initiation to give drawing cycle time to complete drawing.
        tempTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
                Date date = new Date();
                graphicImage.save("images/"+dateFormat.format(date));
            }
        },600);

    }

    /**
     * sets timer for next drawingcycle in 500 milliseconds
     */
    private void resetTimer(){

        increaseWaterLevel = new Timer("Increase Water Level");
        increaseWaterLevel.schedule(new TimerTask() {
            @Override
            public void run() {
                if (currentWaterLevel < pointsMax.getZ()) {
                    previousWaterLevel=currentWaterLevel;
                    currentWaterLevel += step;
                    timeCounter++;
                    time = new Time(time.getTime()+(60*60*1000));
                    println(time);
                    loop();
                }

            }
        }, 500);
    }










}
