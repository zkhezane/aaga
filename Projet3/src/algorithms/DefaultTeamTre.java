package algorithms;

import java.awt.Point;
import java.awt.SecondaryLoop;
import java.util.ArrayList;
import java.util.Collections;

public class DefaultTeamTre {

	public Tree2D calculSteiner(ArrayList<Point> points) {
		/*******************
		 * PARTIE A EDITER *
		 *******************/
		/*
		 * Tree2D leafX = new Tree2D(new Point(700,400),new
		 * ArrayList<Tree2D>()); Tree2D leafY = new Tree2D(new
		 * Point(700,500),new ArrayList<Tree2D>()); Tree2D leafZ = new
		 * Tree2D(new Point(800,450),new ArrayList<Tree2D>());
		 * 
		 * ArrayList<Tree2D> subTrees = new ArrayList<Tree2D>();
		 * subTrees.add(leafX); subTrees.add(leafY); subTrees.add(leafZ); Tree2D*/		
		 

		return exercice2(points);
	}

	Tree2D exercice1(ArrayList<Point> points) {
		Point pointTemp;
		ArrayList<Point> listPoints = (ArrayList<Point>) points.clone();
		Tree2D arbreFinal = new Tree2D(listPoints.get(0),
				new ArrayList<Tree2D>()), arbreTemp1, arbreTemp2;
		arbreTemp1 = arbreFinal;
		listPoints.remove(0);
		while (points.size() != 0) {
			pointTemp = getPointPlusProche(arbreTemp1.getRoot(), points);
			arbreTemp2 = new Tree2D(pointTemp, new ArrayList<Tree2D>());
			arbreTemp1.getSubTrees().add(arbreTemp2);
			arbreTemp1 = arbreTemp2;
			points.remove(pointTemp);
		}
		return arbreFinal;
	}

	Point getPointPlusProche(Point p, ArrayList<Point> list) {
		ArrayList<Point> points1 = (ArrayList<Point>) list.clone();
		Point temp = points1.get(0);
		points1.remove(0);
		while (!points1.isEmpty()) {
			if (points1.get(0).distance(p) < p.distance(temp)) {
				temp = points1.get(0);
			}
			points1.remove(0);
		}

		return temp;
	}

	Tree2D exercice2(ArrayList<Point> points) {
		ArrayList<Point> listPoints = (ArrayList<Point>) points.clone();
		ArrayList<Arc> arcs = getListArcs(listPoints), solution = new ArrayList<Arc>();
		int tag = 0;
		Arc a1, a2;

		for (Arc arc : arcs) {
			a1 = getTag(arc.getFirst(), solution);
			a2 = getTag(arc.getSecond(), solution);
			// System.out.println(a1 +""+ a2 +""+arc);
			if (a1 == null && a2 == null) {
				arc.setTag(tag);
				solution.add(arc);
				tag++;
			} else if (a2 == null) {
				arc.setTag(a1.getTag());
				solution.add(arc);
			} else if (a1 == null) {
				arc.setTag(a2.getTag());
				solution.add(arc);
			} else if (a1.getTag() != a2.getTag()) {
				solution.add(arc);
				arc.setTag(a1.getTag());
				setTagAt(solution, a1.getTag(), a2.getTag());
			}
		}
		System.out.println(solution.size());
		return toTree(solution.get(0).getFirst(), solution);

	}

	Tree2D arryToTree(ArrayList<Arc> solution) {
		Arc arcTemp = solution.get(0);
		Point pointTemp = arcTemp.getFirst();
		ArrayList<Arc> listTemp;
		Tree2D arbreFinal = new Tree2D(pointTemp, new ArrayList<Tree2D>()), arbreTemp;
		arbreFinal.getSubTrees().add(
				new Tree2D(arcTemp.getSecond(), new ArrayList<Tree2D>()));
		arbreFinal.getSubTrees().remove(0);
		arbreTemp = arbreFinal;
		while (!solution.isEmpty()) {
			listTemp = getArcContain(pointTemp, solution);
			listTemp.stream().forEach(
					a -> {
						arbreTemp.getSubTrees().add(
								new Tree2D(getOther(pointTemp, a),
										new ArrayList<Tree2D>()));
					});

		}
		return null;
	}
//
//	Tree2D arryToTreeRec(ArrayList<Arc> solution, Point p) {
//		ArrayList<Arc> children = new ArrayList<Arc>();
//		if (solution.isEmpty()) {
//			return new Tree2D(children, p);
//		}
//		return null;
//	}

	Tree2D toTree(Point root, ArrayList<Arc> solution) {

		if (solution.isEmpty()) {
			return new Tree2D(root, new ArrayList<Tree2D>());
		}
		ArrayList<Tree2D> children = new ArrayList<Tree2D>();
		ArrayList<Arc> voisins = getArcContain(root, solution);

		for (Arc a : voisins) {
			children.add(toTree(
					a.getFirst() == root ? a.getSecond() : a.getFirst(),
					solution));
		}
		return new Tree2D(root, children);
	}

	private Point getOther(Point pointTemp, Arc a) {
		return pointTemp.equals(a.getFirst()) ? a.getSecond() : a.getFirst();
	}

	private ArrayList<Arc> getArcContain(Point temp, ArrayList<Arc> solution) {
		ArrayList<Arc> listTemp = new ArrayList<Arc>();
		solution.stream().forEach(a -> {
			if (a.contien(temp)) {
				listTemp.add(a);
			}
		});
		solution.removeAll(listTemp);
		return listTemp;
	}

	void setTagAt(ArrayList<Arc> arcs, int oldTag, int newTag) {
		arcs.stream().forEach(a -> {
			if (a.getTag() == oldTag)
				a.setTag(newTag);
		});
	}

	Arc getTag(Point p, ArrayList<Arc> arcs) {
		for (Arc arc : arcs) {
			if (arc.contien(p)) {
				return arc;
			}
		}
		return null;
	}

	private boolean listContain(ArrayList<Arc> solution,
			ArrayList<Point> listPoints) {
		return false;
	}

	ArrayList<Arc> getListArcs(ArrayList<Point> points) {
		ArrayList<Arc> arcs = new ArrayList<Arc>();
		points.stream().forEach(p -> {
			points.stream().forEach(p1 -> {
				if (!p1.equals(p)) {
					Arc temp = new Arc(p, p1);
					if (!arcs.contains(temp)) {
						arcs.add(temp);
					}
				}
			});
		});
		Collections.sort(arcs);

		return arcs;
	}
}
