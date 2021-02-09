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
    public static void main(String[] args){
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        In in = new In(filename);
        int num = in.readInt();;
        Planet[] planets = new Planet[num];
        double radius;
        planets = NBody.readPlanets(filename);
        radius = NBody.readRadius(filename);


        String image = "images/starfield.jpg";
        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        StdDraw.picture(0, 0, image);

        int i=0;
        for (;i<num;i++){
            planets[i].draw();
        }

        StdDraw.enableDoubleBuffering();
        double time = 0;
        while(time<T){
            double[] xForces = new double[num];
            double[] yForces = new double[num];
            int index = 0;
            for (;index < num; index++){
                xForces[index] = planets[index].calcNetForceExertedByX(planets);
                yForces[index] = planets[index].calcNetForceExertedByY(planets);

            }
            index = 0;
            for (;index < num; index++){
                planets[index].update(dt,xForces[index],yForces[index]);
            }
            StdDraw.clear();
            StdDraw.setScale(-radius, radius);
            StdDraw.picture(0, 0, image);
            for (index = 0;index<num;index++){
                planets[index].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for ( i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}

