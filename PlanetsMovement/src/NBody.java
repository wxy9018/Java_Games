public class NBody{
	public static double readRadius(String str) {
		In in = new In(str);
		int firstItemInFile = in.readInt();
		double secondItemInFile = in.readDouble();
		return secondItemInFile;
	}
	
	public static Planet[] readPlanets(String str){
		In in = new In(str);
		int firstItemInFile = in.readInt();
		int k = 0;
		Planet[] x = new Planet[firstItemInFile];
		double secondItemInFile = in.readDouble();
		while(k<firstItemInFile){
			double planetXPos = in.readDouble();
			double planetYPos = in.readDouble();
			double planetXVel = in.readDouble();
			double planetYVel = in.readDouble();
			double planetMass = in.readDouble();
			String planetName = in.readString();
			x[k] = new Planet(planetXPos, planetYPos, planetXVel, planetYVel, planetMass, planetName);
			k++;
		}
		return x;
	}
	
	
	public static void main(String[] args){
		// load the command line arguments and read in the values
		double T = Double.parseDouble(args[0]);
		double dT = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = readRadius(filename);
		Planet[] planets = readPlanets(filename);
		// drawing the background
		//double radius = 2.5e11;
		//StdDraw.setCanvasSize(radius, radius);
		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(-radius, radius);
		StdDraw.clear();
		StdDraw.picture(0,0,"images/starfield.jpg");
		for (Planet p : planets){
			p.draw();
		}
		StdDraw.show();
		// Adding sound
		StdAudio.loop("audio/2001.mid");
		// animation
		int waitTimeMilliseconds = 10;
		double timeCounter = 0;
		double[] xForces = new double[planets.length];
		double[] yForces = new double[planets.length];
		while(timeCounter < T){ // for each time step
			StdDraw.picture(0,0,"images/starfield.jpg"); // draw canvas
			for (int i = 0; i < planets.length; i++){ // for every planet in Planets[]
				// calculate x forces and y forces
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
			}
			for (int i = 0; i < planets.length; i++){
				// update the status of each planet
				planets[i].update(dT, xForces[i], yForces[i]);
				// draw the planet on the canvas
				planets[i].draw();
			}
			StdDraw.show();
			StdDraw.pause(waitTimeMilliseconds);
			timeCounter += dT;
		}
	}
}