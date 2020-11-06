package drone;

import java.awt.Color;

import javax.media.j3d.Appearance;
import javax.media.j3d.PointArray;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

/*
 * ����࣬�������������е�������ӡ�ÿ�����������һ���㣬��PointArray����ʾ��
 */

public class Droplet extends TransformGroup{
	
	private int ptsNum = 10000;
	private PointArray pts;
	private Point3f[] ptsCoords;
	private Color3f ptsColor;
	
	private Appearance ptsAppearance;
	private PointAttributes pa;
	
	private Shape3D shape;
	
	public Droplet(Point3f p) {
		
		initGeometry(p);
		initAppearance();
		initShape();
	}
	
	private void initGeometry(Point3f p) {
		pts = new PointArray(ptsNum, PointArray.COORDINATES|PointArray.COLOR_3);
		ptsCoords = new Point3f[ptsNum];
		ptsColor = new Color3f(Color.white);
		
		for(int i=0;i<ptsNum;i++) {
			ptsCoords[i] = p;
			pts.setColor(0, ptsColor);
		}
		pts.setCoordinates(0, ptsCoords);
	}
	
	private void initAppearance() {
		ptsAppearance = new Appearance();
		pa = new PointAttributes();
		pa.setPointAntialiasingEnable(true);//��������Ϊ����
		pa.setPointSize(2f);//���õ�Ĵ�С
		ptsAppearance.setPointAttributes(pa);
	}
	
	private void initShape() {
		shape = new Shape3D();
		shape.addGeometry(pts);
		shape.setAppearance(ptsAppearance);
	}

}
