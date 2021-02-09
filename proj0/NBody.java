import java.lang.reflect.Array;

public class NBody {
    public static double readRadius(String s){
        In in = new In(s);
        int h = in.readInt();
        double r = in.readDouble();
        return r;
    }
    public static Planet[] readPlanets(String s){
        In in= new In(s);
        int num = in.readInt();
        in.readDouble();
        Planet[] planets = new Planet[num];
        int i = 0;
        for (;i<num;i++){
            planets[i] = new Planet(in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble(),in.readString());

        }
        return planets;
    }


}

