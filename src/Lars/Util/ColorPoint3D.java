package Lars.Util;

/**
 * Created by Lars on 6/16/2015.
 * Vector 3D point with Color info component.
 */
public class ColorPoint3D extends Vector3D {


    private color Color;

    public ColorPoint3D(float x, float y, float z, color color) {
        super(x, y, z);
        Color = color;
    }

    public ColorPoint3D(float x, float y, float z) {
        super(x, y, z);
        Color = color.BLACK();
    }

    public void setColor(color color) {
        Color = color;
    }

    public color getColor() {
        return Color;
    }

    @Override
    public String toString() {
        return super.toString()+", Color: "+ Color;
    }
}
