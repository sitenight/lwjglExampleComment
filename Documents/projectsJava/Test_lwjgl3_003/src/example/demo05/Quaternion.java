package example.demo05;

public class Quaternion {

    public float x;
    public float y;
    public float z;
    public float w;
	
    public Quaternion(float x, float y, float z, float w) {
	this.x = x;
	this.y = y;
	this.z = z;
	this.w = w;
    }

    public float length() {
	return (float)Math.sqrt(x * x + y * y + z * z + w * w);
    }
	
    public Quaternion normalize() {
	float length = length();
		
	return new Quaternion(x / length, y / length, z / length, w / length);
    }
	
    public Quaternion conjugate() {
	return new Quaternion(-x, -y, -z, w);
    }
	
    public Quaternion mul(Quaternion r) {
	float dw = w * r.w - x * r.x - y * r.y - z * r.z;
	float dx = x * r.w + w * r.x + y * r.z - z * r.y;
	float dy = y * r.w + w * r.y + z * r.x - x * r.z;
	float dz = z * r.w + w * r.z + x * r.y - y * r.x;
		
	return new Quaternion(dx, dy, dz, dw);
    }
	
    public Quaternion mul(Vector3f r) {
	float dw = -x * r.x - y * r.y - z * r.z;
	float dx =  w * r.x + y * r.z - z * r.y;
	float dy =  w * r.y + z * r.x - x * r.z;
	float dz =  w * r.z + x * r.y - y * r.x;
		
	return new Quaternion(dx, dy, dz, dw);
    }
}

