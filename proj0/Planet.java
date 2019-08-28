import java.lang.Math;

public class Planet{
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	private static final double GRAVIT = 6.67e-11;

	public Planet(double xP, double yP, double xV,
              double yV, double m, String img){
		this.xxPos = xP;
		this.yyPos = yP;
		this.xxVel = xV;
		this.yyVel = yV;
		this.mass = m;
		this.imgFileName = img;
	}

	public Planet(Planet p){
		this.xxPos = p.xxPos;
		this.yyPos = p.yyPos;
		this.xxVel = p.xxVel;
		this.yyVel = p.yyVel;
		this.mass = p.mass;
		this.imgFileName = p.imgFileName;
	}


	public double calcDistance(Planet planetGiven){
		double distance = Math.hypot((xxPos - planetGiven.xxPos), (yyPos - planetGiven.yyPos));
		return distance;
	}

	public double calcForceExertedBy(Planet planetGiven){
		double force = GRAVIT * this.mass * planetGiven.mass / Math.pow(this.calcDistance(planetGiven), 2);
		return force;
	}

	public double calcForceExertedByX(Planet planetGiven){
		double totalForce = this.calcForceExertedBy(planetGiven);
		double distance = this.calcDistance(planetGiven);
		return (totalForce * (planetGiven.xxPos - this.xxPos) / distance);
	}

	public double calcForceExertedByY(Planet planetGiven){
		double totalForce = this.calcForceExertedBy(planetGiven);
		double distance = this.calcDistance(planetGiven);
		return (totalForce * (planetGiven.yyPos - this.yyPos) / distance);
	}
	public double calcNetForceExertedByX(Planet[] allPlanetGiven){
		double netForceExertedByX = 0;
		for(Planet p : allPlanetGiven){
			if(!this.equals(p)){
				netForceExertedByX += this.calcForceExertedByX(p);
			}
		}
		return netForceExertedByX;
	}
	public double calcNetForceExertedByY(Planet[] allPlanetGiven){
		double netForceExertedByY = 0;
		for(Planet p : allPlanetGiven){
			if(!this.equals(p)){
				netForceExertedByY += this.calcForceExertedByY(p);
			}
		}
		return netForceExertedByY;
	}

	public void update(double time, double xForce, double yForce){
		double accerationX = xForce / this.mass;
		double accerationY = yForce / this.mass;
		this.xxVel = this.xxVel + time * accerationX;
		this.yyVel = this.yyVel + time * accerationY;
		this.xxPos = this.xxPos + time * this.xxVel;
		this.yyPos = this.yyPos + time * this.yyVel;
	}
	
	public void draw() {
		StdDraw.picture(xxPos, yyPos, ("images/" + imgFileName));
	}
}