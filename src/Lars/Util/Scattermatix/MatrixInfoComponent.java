package Lars.Util.Scattermatix;

import Lars.Util.color;
import processing.core.PApplet;

/**
 * Created by Lars on 6/15/2015.
 */
public class MatrixInfoComponent extends AMatrixComponent {

    public color backgroundColor;
    public String componentName;

    public MatrixInfoComponent(color backgroundColor, String componentName) {
        this.backgroundColor = backgroundColor;
        this.componentName = componentName;
    }

    public MatrixInfoComponent(color color) {
        this.backgroundColor = color;
    }

    @Override
    public void DrawComponent(PApplet applet) {
        applet.fill(applet.color(backgroundColor.R,backgroundColor.G,backgroundColor.B));
        applet.rect(0,0,size.X,size.Y);
        drawBoundingBox(applet);
        drawPointsOnGraph(applet);

    }

    @Override
    public void drawXAxisComponents(PApplet applet, XOrientation orientation) {
        applet.println("Not used in this component");
    }

    @Override
    public void drawYAxisComponents(PApplet applet, YOrientation orientation) {
        applet.println("Not used in this component");
    }

    @Override
    public void drawPointsOnGraph(PApplet applet) {
         int charCorrection = componentName.toCharArray().length/2;
        applet.fill(0);
        applet.text(componentName,(size.X/2)-charCorrection,size.Y/2);
    }
}
