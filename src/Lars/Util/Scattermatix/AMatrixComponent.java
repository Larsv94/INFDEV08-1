package Lars.Util.Scattermatix;

import Lars.Util.Vector2D;
import processing.core.PApplet;

/**
 * Created by Lars on 6/11/2015.
 */
public abstract class AMatrixComponent {

    protected Vector2D size = new Vector2D(200,200);
    public static enum YOrientation{UP,DOWN};
    public static enum XOrientation{LEFT,RIGHT};

    /**
     * Draw everything from this component
     * @param applet applet to draw on
     */
    public abstract void DrawComponent(PApplet applet);

    /**
     * Draw X-axis information from this component
     * @param applet applet to draw on
     */
    public abstract void drawXAxisComponents(PApplet applet,XOrientation orientation);

    /**
     * Draw Y-Axis information from this component
     * @param applet applet to draw on
     */
    public abstract void drawYAxisComponents(PApplet applet, YOrientation orientation);

    /**
     * Draw graph points on this component
     * @param applet applet to draw on
     */
    public abstract void drawPointsOnGraph(PApplet applet);

    /**
     * Override the pre-determined size of this component
      * @param newSize the new size at which to draw this component
     */
    public void OverrideComponentSize(Vector2D newSize){
        size = newSize;
    }

    public void drawBoundingBox(PApplet applet){
        applet.noFill();
        applet.stroke(0);
        applet.rect(0,0,size.X,size.Y);
    }

    public Vector2D getSize() {
        return size;
    }

}
