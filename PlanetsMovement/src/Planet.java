import java.lang.*;

public class Planet{
	
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	
	public static final double G = 6.67e-11;
	
	public Planet(double xP, double yP, double xV, double yV, double m, String img) { // build a Planet object based on parameters
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}
	
	public Planet(Planet p) { // takes in a Planet object and clones another one
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}
	
	public double calcDistance(Planet p){ // returns the distance between two planets
		double distance = Math.sqrt((p.xxPos - this.xxPos) * (p.xxPos - this.xxPos) + (p.yyPos - this.yyPos) * (p.yyPos - this.yyPos));
		return distance;
	}
	
	public double calcForceExertedBy(Planet p){ // returns the force exerted on this planet by another planet
		double F = G * p.mass * this.mass / (calcDistance(p) * calcDistance(p));
		return F;
	}
	
	public double calcForceExertedByX(Planet p){ // returns the x-dimension forceon this plant by another planet
		double Fx = calcForceExertedBy(p)*(p.xxPos-this.xxPos)/calcDistance(p);
		return Fx;
	}
	
	public double calcForceExertedByY(Planet p){ // returns the y-dimension forceon this plant by another planet
		double Fy = calcForceExertedBy(p)*(p.yyPos-this.yyPos)/calcDistance(p);
		return Fy;
	}
	
	public double calcNetForceExertedByX(Planet[] PList){ // returns the total x-dimension force by an array of planets
		double totalFx = 0;
		for (int i = 0; i < PList.length; i++) {
			if (!this.equals(PList[i])) {
				totalFx += calcForceExertedByX(PList[i]);
			}
		}
		return totalFx;
	}
	
	public double calcNetForceExertedByY(Planet[] PList){ // returns the total y-dimension force by an array of planets
		double totalFy = 0;
		for (int i = 0; i < PList.length; i++){
			if (!this.equals(PList[i])) {
				totalFy += calcForceExertedByY(PList[i]);
			}
		}
		return totalFy;
	}
	
	public void update(double dt, double fX, double fY){ // update the velocity and position variables after forces fX and fY are applied after small period dt
		double aX = fX/this.mass;
		double aY = fY/this.mass;
		this.xxVel += aX*dt;
		this.yyVel += aY*dt;
		this.xxPos += dt*this.xxVel;
		this.yyPos += dt*this.yyVel;
	}
	
	public void draw(){
		String filename = "images/"+ imgFileName;
		StdDraw.picture(xxPos, yyPos, filename);
	}
}

