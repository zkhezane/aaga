package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;

public class Esclave implements Callable<ArrayList<Point>> {

	private ArrayList<Point> points;
	private int edge;
	private int escNumber;
	
	public Esclave(ArrayList<Point> points, int edge,int esc) {
		super();
		this.points = points;
		this.edge = edge;
		this.escNumber=esc;
	}

	@Override
	public ArrayList<Point> call() throws Exception {

		
		return this.calculConnectedDominatingSet(points, edge);
	}

	
	 public ArrayList<Point> calculConnectedDominatingSet(ArrayList<Point> points, int edgeThreshold) {
		    
		  ArrayList<Point> stable = new ArrayList<>();
		  
		  Random r = new Random();
		  ArrayList<Point> rest = (ArrayList<Point>) points.clone();

		  while(! this.isValidMaximal(points, stable, edgeThreshold)) {
			  

			  Point pp = rest.get(r.nextInt(rest.size()));
			  stable.add(pp);
			  rest.remove(pp);
			  rest.removeAll(this.voisins(points, pp, edgeThreshold));
			  
			  
		  }
		  

		  
		  
		  System.out.println("Stable : "+stable.size());
	    return stable;
	  }
	  
	  

	  
	  public Point plusProche(ArrayList<Point> points,Point p,int edge ) {
		  double d=10000;
		  
		  Point pp=null;
		  for(Point q: points) {
			  if(q.equals(p))continue;
			  if(q.distance(p)<d) {
				  d= q.distance(p);
				  pp=q;
			  }
				 
		  }
		  return pp;
	  }
	  
	  public boolean isValidStable(ArrayList<Point> stable,int edge) {
		  if(stable.isEmpty()) return false;
		  for(Point p : stable) {
			  for(Point q : stable) {
				  if(q.equals(p))continue;
				  if(p.distance(q)<edge)
					  return false;
			  }
		  }
		  
		  return true;
	  }
	  
	  public boolean isValidConnexe(ArrayList<Point> stable,int edge) {
		  boolean trouve=false;
		  for(Point p : stable) {
			  for(Point q :stable) {
				  if(p.equals(q))continue;
				   trouve = false;
				  if(q.distance(p)<edge) {
					  trouve = true;
					  break;
				  }
				  
			  }
			  
			  if(!trouve)return false;
		  }
		  
		  return true;
		  
	  }
	  
	  public boolean isValidMaximal(ArrayList<Point> points,ArrayList<Point> stable,int edge) {
		  
		  ArrayList<Point> rest = (ArrayList<Point>) points.clone();
		  rest.removeAll(stable);
		  
		  ArrayList<Point> stableBis = (ArrayList<Point>) stable.clone();
		  
		  for(Point p : rest) {
			  stableBis.add(p);
			  if(this.isValidStable(stableBis, edge)) return false;
			  stableBis.remove(p);
		  }
		  
		  return true;
		  
		 
	  }
	  
	  
	  public ArrayList<Point> voisins(ArrayList<Point> points, Point p, int edge) {
			int s = 0;
			ArrayList<Point> voisins = new ArrayList<>();
			for (Point q : points) {
				if (p.equals(q))
					continue;
				if (p.distance(q) < edge)
					voisins.add(q);
			}

			return voisins;
		}
	  
}
