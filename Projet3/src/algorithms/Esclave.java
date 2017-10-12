package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.Callable;

public class Esclave implements Callable<ArrayList<Point>> {

	private ArrayList<Point> points;
	private int edge;
	private int escNumber;
	
	ArrayList<ColoredPoint> allInColor(ArrayList<ColoredPoint> points, ColoredPoint.Color color){
		for(ColoredPoint p : points)
			p.setColor(color);
		return points;
	}

	public Esclave(ArrayList<Point> points, int edge,int esc) {
		super();
		this.points = points;
		this.edge = edge;
		this.escNumber=esc;
		points.removeAll(this.seul(points, edge));
	}

	@Override
	public ArrayList<Point> call() throws Exception {

		
		
		return this.computeMISValid(points,edge);
	}

	
	 public ArrayList<Point> calculConnectedDominatingSet(ArrayList<Point> points, int edgeThreshold) {
		    
		  ArrayList<Point> stable = new ArrayList<>();
		  
		  Random r = new Random();
		  ArrayList<Point> rest = (ArrayList<Point>) points.clone();


		  
		  
		  System.out.println("Stable : "+stable.size());
	    return stable;
	  }


	public ArrayList<Point> computeMISValid(ArrayList<Point> points, int edge) {
		ArrayList<ColoredPoint> whitePoints = new ArrayList<>();
		
		for(Point p : points)
			whitePoints.add(new ColoredPoint(p, ColoredPoint.Color.WHITE));
		System.out.println("whitePoints "+whitePoints.size());
		
	
		
		ArrayList<ColoredPoint> MIS = new ArrayList<>();
		ArrayList<ColoredPoint> listGreys = new ArrayList<>();
		ArrayList<ColoredPoint> tmpGreys = new ArrayList<>();

		ColoredPoint currentPoint = max(whitePoints, edge);
		currentPoint.setColor(ColoredPoint.Color.BLACK);
		MIS.add(currentPoint);
		System.out.println("MIS: "+ MIS.size());
		tmpGreys=voisins(whitePoints,currentPoint,edge);	
		System.out.println("tmpGreys: "+ tmpGreys.size());
		listGreys.addAll(allInColor(tmpGreys, ColoredPoint.Color.GREY));
		System.out.println("listGreys: "+ listGreys.size());
		whitePoints.removeAll(tmpGreys);
		System.out.println("whitePoints: "+ whitePoints.size());
		whitePoints.remove(currentPoint);
		System.out.println("whitePoints: "+ whitePoints.size());
		int size =0;
		while(!whitePoints.isEmpty() && whitePoints.size()!= size){
			size=whitePoints.size();
			
			//	System.out.println("grey size :"+listGreys.size());
			
//			for(ColoredPoint p : listGreys) {
//				ArrayList<ColoredPoint> v = this.voisins(listWhite, p, edge);
//				if(!v.isEmpty())
//					currentPoint = v.get(0);
//			}
			
			//currentPoint = max(voisinsDeGris(whitePoints, currentPoint, edge),edge);
			currentPoint=null;
			for (ColoredPoint p : whitePoints) {
				if(isVoisinGrey(listGreys, p, edge))
					currentPoint=p;
				
			}
			if(currentPoint==null)
				break;
			currentPoint.setColor(ColoredPoint.Color.BLACK);
			MIS.add(currentPoint);
			tmpGreys=voisins(whitePoints,currentPoint,edge);	
			listGreys.addAll(allInColor(tmpGreys, ColoredPoint.Color.GREY));
			whitePoints.removeAll(tmpGreys);
			whitePoints.remove(currentPoint);
			
		}


		
		System.out.println("MIS size "+MIS.size());





		ArrayList<Point> result = new ArrayList<>();
		for(Point p : MIS){
			result.add(new Point(p.x,p.y));
		}
		return result;
	}

	public boolean isVoisinGrey(ArrayList<ColoredPoint> greyPoints,ColoredPoint point,int edge) {
		
		for (ColoredPoint p : greyPoints) {
			if(p.equals(point)) continue;
			if(p.distance(point)<edge)
				return true;
		}
		return false;
	}
	public ArrayList<ColoredPoint> voisinsDeGris(ArrayList<ColoredPoint> pointsWhite,ColoredPoint point,int edge){
		HashSet<ColoredPoint> result = new HashSet<>();
		
		ArrayList<ColoredPoint> v = voisins(pointsWhite,point,edge);
		for(ColoredPoint p : v) {
			for(ColoredPoint q : pointsWhite) {
				if(p.equals(q))continue;
				if(p.distance(q)<edge)
					result.add(p);
			}
		}
		return new ArrayList<>(result);
		
	}

	public Point plusProche(ArrayList<Point> points, Point p, int edge ) {
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
	  
	  
	  public ArrayList<ColoredPoint> voisins(ArrayList<ColoredPoint> points, ColoredPoint p, int edge) {
			ArrayList<ColoredPoint> voisins = new ArrayList<>();
			for (ColoredPoint q : points) {
				if (p.equals(q))
					continue;
				if (p.distance(q) < edge)
					voisins.add(q);
			}

			return voisins;
		}
	  
	  public ArrayList<Point> seul(ArrayList<Point> points,int edge){
		  ArrayList<Point> seuls = new ArrayList<>();
		  for(Point p : points) {
			  if(voisinsP(points, p, edge).size()<1)
				  seuls.add(p);
		  }
		  return seuls;
	  }
	  
	  public ArrayList<Point> voisinsP(ArrayList<Point> points, Point p, int edge) {
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
	  
	  public ColoredPoint max(ArrayList<ColoredPoint> points,int edge) {
		  
		  int s=voisins(points,points.get(0),edge).size();
		  ColoredPoint pp=points.get(0);
		  for(ColoredPoint p : points) {
			  if(voisins(points, p, edge).size()>s) {
				  s=voisins(points,p,edge).size();
				  pp=p;
			  }
		  }
		  
		  return pp;
	  }
}
