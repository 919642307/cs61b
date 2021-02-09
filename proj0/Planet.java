public class Planet{
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }
    public Planet(Planet p){
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }
    public double calcDistance(Planet o){
        double dx = this.xxPos - o.xxPos;
        double dy = this.yyPos - o.yyPos;
        return Math.sqrt(dx*dx+dy*dy);
    }
    public double calcForceExertedBy(Planet o){
        double distance = this.calcDistance(o);
        double force;
        double G=6.67e-11;
        force = G*this.mass*o.mass/distance/distance;
        return force;

    }
    public double calcForceExertedByX(Planet o) {
        double dx = o.xxPos - this.xxPos;
        double r = this.calcDistance(o);
        double f = this.calcForceExertedBy(o);

        return dx * f / r;
    }
    public double calcForceExertedByY(Planet o){
        double dy = o.yyPos - this.yyPos;
        double r = this.calcDistance(o);
        double f = this.calcForceExertedBy(o);

        return dy * f / r;
    }
    public double calcNetForceExertedByX(Planet[] all){
        int i ;
        double sumx = 0;
        for (i = 0;i<all.length;i++){
            if (this.equals(all[i])){
                continue;
            }
            else{
                sumx = sumx + calcForceExertedByX(all[i]);
            }
        }
        return sumx;
    }
    public double calcNetForceExertedByY(Planet[] all){
        int i ;
        double sumy = 0;
        for (i = 0;i<all.length;i++){
            if (this.equals(all[i])){
                continue;
            }
            else{
                sumy = sumy + calcForceExertedByY(all[i]);
            }
        }
        return sumy;
    }
    public void update(double dt,double fx,double fy){
        double ax = fx/this.mass;
        double ay = fy/this.mass;
        this.xxVel = this.xxVel + dt * ax;
        this.yyVel = this.yyVel + dt * ay;
        this.xxPos = this.xxPos+dt*this.xxVel;
        this.yyPos = this.yyPos+dt*this.yyVel;
    }
}