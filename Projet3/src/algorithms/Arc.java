package algorithms;

import java.awt.Point;
import java.util.Comparator;

public class Arc implements Comparable {
	private Point first; // first member of pair
	private Point second; // second member of pair

	private int tag = -1;
	
	public Arc(Point first, Point second) {
		this.first = first;
		this.second = second;
	}

	public void setFirst(Point first) {
		this.first = first;
	}

	public void setSecond(Point second) {
		this.second = second;
	}

	public Point getFirst() {
		return first;
	}

	public Point getSecond() {
		return second;
	}

	public boolean equals(Point p1, Point p2) {
		return (p1.equals(first) && p2.equals(second))
				|| (p2.equals(first) && p1.equals(second));
	}
	
	public boolean contien(Point p){
		return p.equals(first) || p.equals(second);
	}

	@Override
	public boolean equals(Object obj) {
		Arc temp = (Arc) obj;
		return equals(temp.first, temp.second);
	}

	@Override
	public int compareTo(Object o) {
		Arc temp = (Arc) o;
		double d1 = temp.first.distance(temp.second), d2 = first
				.distance(second);
		if (d1 == d2)
			return 0;
		if (d2 > d1)
			return 1;
		return -1;
	}

	
	@Override
	public String toString() {
		
		return "distance = "+first.distance(second)+" tag = "+tag+" P1 = ("+first.x+", "+first.y+"), P2 =("+second.x+", "+second.y+")\n";
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}


}