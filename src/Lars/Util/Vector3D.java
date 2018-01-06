package Lars.Util;

/**
 * Created by Lars on 6/16/2015.
 * class to store three coordinate points
 */
public class Vector3D {

    protected float X;
    protected float Y;
    protected float Z;

    public Vector3D(float x, float y, float z) {
        X = x;
        Y = y;
        Z = z;
    }

    public static Vector3D ZERO(){
        return new Vector3D(0,0,0);
    }

    public float getX() {
        return X;
    }

    public float getY() {
        return Y;
    }

    public float getZ() {
        return Z;
    }

    public void setX(float x) {
        X = x;
    }

    public void setY(float y) {
        Y = y;
    }

    public void setZ(float z) {
        Z = z;
    }

    @Override
    public String toString() {
        return "X:"+X+", Y:"+Y+", Z:"+Z;
    }
}
