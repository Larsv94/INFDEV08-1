package Lars.Util;

/**
 * Created by Lars on 6/9/2015.
 * Color utility because color isn't native to java but only to processing
 */
public class color {
    public int R,G,B;

    public static final color RED(){return new color(255,0,0);}
    public static final color GREEN(){return new color(0,255,0);}
    public static final color BLUE(){return new color(0,0,255);}
    public static final color BLACK(){return new color(0,0,0);}
    public static final color WHITE(){return new color(255,255,255);}

    public color(int r, int g, int b) {
        R = r;
        G = g;
        B = b;
    }

    public color() {
        R = 0;
        G = 0;
        B = 0;
    }


    @Override
    public String toString() {
        return "("+R+","+G+","+B+")";
    }
}
