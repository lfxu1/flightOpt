package drone;


import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;

/*
 * 无人机喷雾演示, 与以前的版本不同, 用PointArray来表示雾滴场景中的所有雾滴粒子，降低内存消耗。
 */

public class DroneDemo extends Applet{
	
	private Canvas3D canvas;
	private Drone drone;
	
	public void init() {
		setLayout(new BorderLayout());
		canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		add(canvas, BorderLayout.CENTER);
		SimpleUniverse universe = new SimpleUniverse(canvas);
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(initBranchGroup());
		
		canvas.addKeyListener(new KeyAdapter() {
			
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				
				switch(keyCode) {
				case KeyEvent.VK_UP:
					
				}
			}
		});
	}
	
	private BranchGroup initBranchGroup() {
		BranchGroup root = new BranchGroup();
		BoundingSphere bounds = new BoundingSphere();
		
		Background background = new Background(0f, 0f, 0f);
		background.setApplicationBounds(bounds);
		root.addChild(background);
		
		Color3f color1 = new Color3f(0.7f, 0.7f, 0.7f);
		Vector3f direction1 = new Vector3f(4f, -7f, -12f);
		Color3f color2 = new Color3f(0.7f, 0.7f, 0.7f);
		Vector3f direction2 = new Vector3f(-6f, -2f, -1f);
		Color3f ambientColor = new Color3f(0.2f, 0f, 0f);

		AmbientLight ambientLight = new AmbientLight(ambientColor);
		ambientLight.setInfluencingBounds(bounds);
		root.addChild(ambientLight);

		DirectionalLight light1 = new DirectionalLight(color1, direction1);
		light1.setInfluencingBounds(bounds);
		root.addChild(light1);
		DirectionalLight light2 = new DirectionalLight(color2, direction2);
		light2.setInfluencingBounds(bounds);
		root.addChild(light2);

		drone = new Drone(new Point3f(0, 0.2f, 0));
		root.addChild(drone);
		
		Droplet drop = new Droplet(drone.getSprayLocation());
		root.addChild(drop);
		
		return root;
	}

	public static void main(String[] args) {
		new MainFrame(new DroneDemo(), 800, 800);
	}

}
