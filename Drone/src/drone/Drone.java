package drone;

import java.io.FileNotFoundException;

/**
 * ���˻��࣬��ʾһ�����˻����̳���TransformGroup
 * @author dell
 *
 */

import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;

import static drone.Constant.*;

public class Drone extends TransformGroup {

	private Point3f location;// location��ʾ���˻�λ��
	private Point3f sprayLocation;// sprayLocation��ʾ����λ��

	private Transform3D transform;

	// �޲ι��캯����location��ʼ��Ϊ(0,0,0)���ڸ�λ������һ�����˻���
	public Drone() {
		transform = new Transform3D();
		transform.setScale(0.25);
		this.setTransform(transform);
		location = new Point3f(0, 0, 0);
		sprayLocation = new Point3f();

		computeSprayLocation();
		createDrone();
	}

	/**
	 * ����locationΪ���˻�������λ�ã�
	 * 
	 * @param location
	 */
	public Drone(Point3f location) {
		this();
		this.location = location;

		computeSprayLocation();
		transform();
	}

	private void createDrone() {
		String filename = "GlobalHawkOBJ.obj";
		int flags = ObjectFile.RESIZE;
		ObjectFile f = new ObjectFile(flags, 1f);

		Scene s = null;
		try {
			s = f.load(filename);
		} catch (FileNotFoundException e) {
			System.err.println(e);
			System.exit(1);
		} catch (ParsingErrorException e) {
			System.err.println(e);
			System.exit(1);
		} catch (IncorrectFormatException e) {
			System.err.println(e);
			System.exit(1);
		}

		this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.addChild(s.getSceneGroup());
	}

	/**
	 * ����locationλ�ã���ʱ�������˻�ֻ����ͬһƽ����ǰ�������ƶ���ֻ��x�����z���귢���仯��
	 * ��Constant���еľ�̬����X��Z������location�������¼�����ʱ����X��Z��ֵ��
	 */
	public void updateLocation() {
		location.setX(X);
		location.setZ(Z);

		computeSprayLocation();
	}

	// ����location�����ƶ����˻�
	public void transform() {
		transform.setTranslation(new Vector3f(location.x, location.y, location.z));
		this.setTransform(transform);
	}

	public Point3f getLocation() {
		return location;
	}

	public Point3f getSprayLocation() {
		return sprayLocation;
	}

	/**
	 * �������˻�����location���������������sprayLocation��
	 */
	private void computeSprayLocation() {
		sprayLocation.setX(location.x);
		sprayLocation.setY(location.y - 0.04f);
		sprayLocation.setZ(location.z + 0.1f);
	}
}
