package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DefaultTeam {
  public ArrayList<Point> calculConnectedDominatingSet(ArrayList<Point> points, int edgeThreshold) throws Exception, ExecutionException {
    
	  ArrayList<Point> stable = null;
		int pool=20;
		ExecutorService service = Executors.newFixedThreadPool(pool);

		List<Future<ArrayList<Point>>> results = new ArrayList<>();
	
		
		for (int i = 0; i < pool; i++) {
			Callable<ArrayList<Point>> callable = new Esclave(points, edgeThreshold,i);
			Future<ArrayList<Point>> resultat = service.submit(callable);
			results.add(resultat);

		}
		int s = 1000;
		Future<ArrayList<Point>> pr =null;
		for (int i = 0; i < pool; i++) {
			
			 pr = results.get(i);
			
			if (pr.get().size() < s) {
				s=pr.get().size();
				stable = pr.get();
			}

		}
		
		System.out.println("dominant size : " +stable.size());
		
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
  
  //FILE PRINTER
  private void saveToFile(String filename,ArrayList<Point> result){
    int index=0;
    try {
      while(true){
        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(filename+Integer.toString(index)+".points")));
        try {
          input.close();
        } catch (IOException e) {
          System.err.println("I/O exception: unable to close "+filename+Integer.toString(index)+".points");
        }
        index++;
      }
    } catch (FileNotFoundException e) {
      printToFile(filename+Integer.toString(index)+".points",result);
    }
  }
  private void printToFile(String filename,ArrayList<Point> points){
    try {
      PrintStream output = new PrintStream(new FileOutputStream(filename));
      int x,y;
      for (Point p:points) output.println(Integer.toString((int)p.getX())+" "+Integer.toString((int)p.getY()));
      output.close();
    } catch (FileNotFoundException e) {
      System.err.println("I/O exception: unable to create "+filename);
    }
  }

  //FILE LOADER
  private ArrayList<Point> readFromFile(String filename) {
    String line;
    String[] coordinates;
    ArrayList<Point> points=new ArrayList<Point>();
    try {
      BufferedReader input = new BufferedReader(
          new InputStreamReader(new FileInputStream(filename))
          );
      try {
        while ((line=input.readLine())!=null) {
          coordinates=line.split("\\s+");
          points.add(new Point(Integer.parseInt(coordinates[0]),
                Integer.parseInt(coordinates[1])));
        }
      } catch (IOException e) {
        System.err.println("Exception: interrupted I/O.");
      } finally {
        try {
          input.close();
        } catch (IOException e) {
          System.err.println("I/O exception: unable to close "+filename);
        }
      }
    } catch (FileNotFoundException e) {
      System.err.println("Input file not found.");
    }
    return points;
  }
}
