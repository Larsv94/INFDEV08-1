package Lars.Util;

/**
 * Created by Lars on 6/8/2015.
 * class to store two coordinate points
 */
public class Vector2D  {

    public float X;
    public float Y;



    public Vector2D(float x, float y) {
        X = x;
        Y = y;
    }

    @Override
    public String toString() {
        return "X = "+X + "|Y = "+Y;
    }
}
