package drone;

import java.awt.*;
import java.applet.*;

import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class PointArrayDemo extends Applet {

	private BranchGroup root;

	public void init() {
		setLayout(new BorderLayout());
		Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		add(canvas, BorderLayout.CENTER);
		SimpleUniverse universe = new SimpleUniverse(canvas);
		universe.getViewingPlatform().setNominalViewingTransform();
		initBranchGroup();
		universe.addBranchGraph(root);
	}

	public void initBranchGroup() {
		root = new BranchGroup();
		BoundingSphere bounds = new BoundingSphere();

		Background background = new Background(0f, 0f, 0f);
		background.setApplicationBounds(bounds);
		root.addChild(background);
		
		Color3f color = new Color3f(Color.white);
		int ptsNum = 100;
		PointArray pts = new PointArray(ptsNum, PointArray.COORDINATES|PointArray.COLOR_3);
		Point3f[] p = new Point3f[ptsNum];

		for (int i = 0; i < ptsNum; i++) {
			p[i] = new Point3f(((float) i )/ ptsNum, (float) i * i/ptsNum, 0);
			pts.setColor(i, color);
		}
		
		pts.setCoordinates(0, p);
		
		Shape3D shape = new Shape3D();
		shape.addGeometry(pts);
		
		Appearance ptsApp = new Appearance();
		PointAttributes pa = new PointAttributes();
		pa.setPointSize(2f);//定义点的大小
		pa.setPointAntialiasingEnable(true);//将点设置为球形
		ptsApp.setPointAttributes(pa);
		
		//pts.
		
		shape.setAppearance(ptsApp);
		root.addChild(shape);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainFrame(new PointArrayDemo(), 600, 600);
	}

}
