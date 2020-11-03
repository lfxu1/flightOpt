package pso;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.IndexedLineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;

import static pso.Constant.scale;

public class Area extends Applet {

	private BranchGroup root;

	public void init() {
		this.setLayout(new BorderLayout());
		Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		this.add(canvas, BorderLayout.CENTER);
		SimpleUniverse universe = new SimpleUniverse(canvas);
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(create());
	}

	private BranchGroup create() {
		root = new BranchGroup();
		BoundingSphere bounds = new BoundingSphere();

		Background bg = new Background(1, 1, 1);
		bg.setApplicationBounds(bounds);
		root.addChild(bg);

		// 边界
		IndexedLineArray boundary = new IndexedLineArray(4, IndexedLineArray.COORDINATES | IndexedLineArray.COLOR_3, 8);
		Point3d[] boundaryPts = new Point3d[4];
		boundaryPts[0] = new Point3d(-0.8, -0.8, 0);
		boundaryPts[1] = new Point3d(0.8, -0.8, 0);
		boundaryPts[2] = new Point3d(0.8, 0.8, 0);
		boundaryPts[3] = new Point3d(-0.8, 0.8, 0);
		boundary.setCoordinates(0, boundaryPts);
		int[] boundaryIndices = { 0, 1, 1, 2, 2, 3, 3, 0 };
		boundary.setCoordinateIndices(0, boundaryIndices);
		Color3f[] boundaryColor = new Color3f[4];
		for (int i = 0; i < boundaryColor.length; i++) {
			boundaryColor[i] = new Color3f(Color.BLACK);
		}
		boundary.setColors(0, boundaryColor);
		boundary.setColorIndices(0, boundaryIndices);

		LineAttributes la = new LineAttributes();
		la.setLineWidth(4);
		Appearance lineApp = new Appearance();
		lineApp.setLineAttributes(la);

		Shape3D boundaryShape = new Shape3D();
		boundaryShape.addGeometry(boundary);
		boundaryShape.setAppearance(lineApp);
		root.addChild(boundaryShape);
		
		int ptsNum = scale*scale;
		
		HPSOTest hpso = new HPSOTest(ptsNum);
		hpso.iterator();
		Path optimal = hpso.getCp();
		System.out.println("最优路径:"+optimal.getOrder());
		System.out.println("适应度值："+optimal.fitness());
		
		ArrayList<Integer> order = optimal.getOrder();

		IndexedLineArray path = new IndexedLineArray(ptsNum, IndexedLineArray.COORDINATES | IndexedLineArray.COLOR_3, (ptsNum - 1) * 2);
		Point3d[] pathPts = new Point3d[ptsNum];
		for(int i = 0; i < scale; i++) { 
			for(int j = 0; j < scale; j++) { 
				pathPts[scale*i + j] = new Point3d(-0.75 + 1.6 / scale * j, -0.75 + 1.6 / scale * i, 0);
			}
		}
		path.setCoordinates(0, pathPts);
		int[] pathIndices = new int[(ptsNum - 1) * 2];
		for(int i = 0; i < ptsNum - 1; i++) {
			pathIndices[i*2] = order.get(i); 
			pathIndices[i*2 + 1] = order.get(i+1); 
		}
		path.setCoordinateIndices(0, pathIndices);
		
		Color3f[] pathColor = new Color3f[ptsNum];
		for (int i = 0; i < pathColor.length; i++) {
			pathColor[i] = new Color3f(Color.BLUE);
		}
		path.setColors(0, pathColor);
		path.setColorIndices(0, pathIndices);
		
		Shape3D pathShape = new Shape3D();
		pathShape.addGeometry(path);
		pathShape.setAppearance(lineApp);
		root.addChild(pathShape);
		
		return root;
	}
	
	public static void main(String[] args) {
		new MainFrame(new Area(), 800, 800);
	}
}
