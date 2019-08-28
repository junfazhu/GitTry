public class NBody{
	
	public static void main(String[] args){
		// Get T, dt and filename
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		// Read the planet and the universe radius
		double radiusOfUniverse = NBody.readRadius(filename);
		Planet[] planetList = NBody.readPlanets(filename);
		// enable double buffering
		StdDraw.enableDoubleBuffering();
		// draw the universe background
		StdDraw.setScale(-1 * (radiusOfUniverse), radiusOfUniverse);
		StdDraw.clear();
		StdDraw.picture(0,0,"images/starfield.jpg", radiusOfUniverse*2, radiusOfUniverse*2);
		// draw the planet
		for(Planet p : planetList) {
			p.draw();
		}
		StdDraw.show();
		StdDraw.pause(10);
		// planet animination 
		int time = 0;
		while(time <= T) {
			double[] xForces = new double[planetList.length];
			double[] yForces = new double[planetList.length];
			for(int i = 0; i < planetList.length; i++) {
				// calc the net x and y forces for each planet
				double xForceII = planetList[i].calcNetForceExertedByX(planetList);
				double yForceII = planetList[i].calcNetForceExertedByY(planetList);
				xForces[i] = xForceII;
				yForces[i] = yForceII;
				planetList[i].update(time, xForceII, yForceII);
			}
			StdDraw.clear();
			StdDraw.picture(0,0,"images/starfield.jpg", radiusOfUniverse*2, radiusOfUniverse*2);
			for(Planet p : planetList) {
				p.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
			time += dt;
		}
		// print out final state
		StdOut.printf("%d\n", planetList.length);
		StdOut.printf("%.2e\n", radiusOfUniverse);
		for (int i = 0; i < planetList.length; i++) {
		    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		                  planetList[i].xxPos, planetList[i].yyPos, planetList[i].xxVel,
		                  planetList[i].yyVel, planetList[i].mass, planetList[i].imgFileName);   
		}
	}

	public static double readRadius(String fileName){
		In in = new In(fileName);
		int nOfPlanets = in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	public static Planet[] readPlanets(String fileName){
		In in = new In(fileName);
		int nOfPlanets = in.readInt();
		double radius = in.readDouble();
		// this line only creates an array of pointer but did not initialize
		// the object so check the constructor
		Planet[] planetList = new Planet[nOfPlanets];
		for(int i = 0; i < nOfPlanets; i++){
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String imgFileName = in.readString();
			planetList[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
		}
		return planetList;
	}
}