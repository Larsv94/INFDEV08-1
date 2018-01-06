package Lars.INFDEV08;

import Lars.Util.*;
import Lars.Util.Scattermatix.AMatrixComponent;
import Lars.Util.Scattermatix.MatrixInfoComponent;
import Lars.Util.Scattermatix.ScatterPlotComponent;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


/**
 * Created by Lars on 6/11/2015.
 */
public class Opdr2_ScatterMatrix extends PAppletExtended {
    public static final String filePath = "src/data/Data-voor-scattermatrix.txt";


    public ArrayList<Pair<String,ArrayList<Float>>> dataSets = new ArrayList<Pair<String, ArrayList<Float>>>();




    public Opdr2_ScatterMatrix() {
        retrieveData();//retrive data from file at creation of class.
    }

    @Override
    public void setup() {
        super.setup();//call to setup() in super class

        //generate en position scatterplots based on given columns of data
        for (int j = 0; j < 4 ; j++) {
            for (int i = 0; i < 4; i++) {
                pushMatrix();
                translate(150 + ((200 + 20) * i),100 + ((200 + 20) * j));
                if (i != j) {

                    ScatterPlotComponent component = new ScatterPlotComponent(dataSets.get(j), dataSets.get(i));//create new scatterplot
                    component.DrawComponent(this);//draw the base component
                    component.drawPointsOnGraph(this);//draw the points
                    //determine which axis information has to be drawn
                    if (j==0){
                       component.drawYAxisComponents(this, AMatrixComponent.YOrientation.UP);
                    }else if(i==0){
                        component.drawXAxisComponents(this, AMatrixComponent.XOrientation.LEFT);
                    }

                }
                //if data for X and Y axis is equal
                //draw a informative component instead of scatterplot
                else {
                    MatrixInfoComponent infoComponent = new MatrixInfoComponent(new color(255,218,218),dataSets.get(j).getKey());
                    infoComponent.DrawComponent(this);
                }
                popMatrix();
            }
        }





    }

    public void retrieveData()
    {
        try{
            BufferedReader file = new BufferedReader(new FileReader(filePath));
            String line;

            //Fill dataset array based on given amount of columns as datasets
            //assumes first line is the name of de data column
            for (String str : file.readLine().split("\\t")){
                dataSets.add(new Pair<String, ArrayList<Float>>(str,new ArrayList<Float>()));
            }
            //Store all data in correct data set and convert to float
            while ((line = file.readLine())!=null){
                line = line.replaceAll(",",".");
                StringTokenizer st = new StringTokenizer(line);
                int i = 0;
                while (st.hasMoreTokens()){
                    dataSets.get(i).getValue().add(Float.parseFloat(st.nextToken()));
                    i++;
                }
            }

        }catch (IOException e){
            e.printStackTrace();

        }

    }
}
