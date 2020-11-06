package drone;

import javax.vecmath.Vector3f;

public class Vec3f {
	private float x;
	private float y;
	private float z;

	/**
	 * Ĭ�Ϲ��캯����������ʼ��ΪV = (0, 0, 0)
	 */
	public Vec3f() {
		x = 0;
		y = 0;
		z = 0;
	}

	/**
	 * ֱ��ʹ����ά��������������������V = (x, y, z)
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vec3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * ʹ�������ĳ����Լ�������������ļн�����������������V = (L*cosangleX, L*cosangleY, L*cosangleZ)
	 * @param length
	 * @param angleX
	 * @param angleY
	 * @param angleZ
	 */
	public Vec3f(float length, float angleX, float angleY, float angleZ) {
		x = length*(float)Math.cos(angleX);
		y = length*(float)Math.cos(angleY);
		z = length*(float)Math.cos(angleZ);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public void setX(float numX) {
		x = numX;
	}

	public void setY(float numY) {
		y = numY;
	}

	public void setZ(float numZ) {
		z = numZ;
	}

	/**
	 * ��������λ����
	 */
	public void normalize() {
		x = x / getLength();
		y = y / getLength();
		z = z / getLength();
	}

	/**
	 * �����������ȡ�
	 * @return float
	 */
	public float getLength() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public void plus(Vec3f v) {
		x += v.getX();
		y += v.getY();
		z += v.getZ();
	}

	public void multiply(float l) {
		x = x * l;
		y = y * l;
		z = z * l;
	}
	
	public Vector3f transformVector3f() {
		return new Vector3f(x, y, z);
	}
}

