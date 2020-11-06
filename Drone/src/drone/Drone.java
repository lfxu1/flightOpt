package drone;

import java.io.FileNotFoundException;

/**
 * 无人机类，表示一个无人机。继承自TransformGroup
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

	private Point3f location;// location表示无人机位置
	private Point3f sprayLocation;// sprayLocation表示喷雾位置

	private Transform3D transform;

	// 无参构造函数，location初始化为(0,0,0)，在该位置生成一个无人机。
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
	 * 参数location为无人机的生成位置，
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
	 * 更新location位置，暂时假设无人机只能在同一平面内前后左右移动，只有x坐标和z坐标发生变化。
	 * 用Constant类中的静态常量X和Z来更新location，键盘事件触发时更新X和Z的值。
	 */
	public void updateLocation() {
		location.setX(X);
		location.setZ(Z);

		computeSprayLocation();
	}

	// 根据location坐标移动无人机
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
	 * 根据无人机坐标location计算雾滴喷洒坐标sprayLocation。
	 */
	private void computeSprayLocation() {
		sprayLocation.setX(location.x);
		sprayLocation.setY(location.y - 0.04f);
		sprayLocation.setZ(location.z + 0.1f);
	}
}
