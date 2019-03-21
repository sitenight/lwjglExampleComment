package example.demo05;

import java.util.Random;
import org.lwjgl.glfw.GLFW;

public class Player {

    public static final float GUN_OFFSET = -0.0875f;

    public static final float SCALE = 0.0625f;
    public static final float SIZEY = SCALE;
    public static final float SIZEX = (float)((double)SIZEY / (1.0379746835443037974683544303797 * 2.0));
    public static final float START = 0;
    
    public static final float OFFSET_X = 0.0f; //0.05f
    public static final float OFFSET_Y = 0.0f; //0.01f

    public static final float TEX_MIN_X = -OFFSET_X;
    public static final float TEX_MAX_X = -1 - OFFSET_X;
    public static final float TEX_MIN_Y = -OFFSET_Y;
    public static final float TEX_MAX_Y = 1 - OFFSET_Y;

    private static final float MOUSE_SENSITIVITY = 0.33f;
    private static final float MOVE_SPEED = 5f;
    public static final float PLAYER_SIZE = 0.2f;
    public static final float SHOOT_DISTANCE = 1000.0f;
    private static final Vector3f zeroVector = new Vector3f(0,0,0);
    public static final int DAMAGE_MIN = 20;
    public static final int DAMAGE_MAX = 60;
    public static final int MAX_HEALTH = 100;

    private static Mesh mesh;
    private static Material gunMaterial;

    private Transform gunTransform;
    private Camera camera;
    private Random rand;
    private int health;
    
    public float oldMouseX = 0, oldMouseY = 0, newMouseX = 0, newMouseY = 0, mouseSensitivity = 0.5f;

    //private static boolean mouseLocked = false;
    //private Vector2f centerPosition = new Vector2f(Window.getWidth()/2, Window.getHeight()/2);
    private Vector3f movementVector;
	
    public Player(Vector3f position) {
	if(mesh == null) {
            Vertex[] vertices = new Vertex[]{new Vertex(new Vector3f(-SIZEX,START,START), new Vector2f(TEX_MAX_X,TEX_MAX_Y)),
		new Vertex(new Vector3f(-SIZEX,SIZEY,START), new Vector2f(TEX_MAX_X,TEX_MIN_Y)),
		new Vertex(new Vector3f(SIZEX,SIZEY,START), new Vector2f(TEX_MIN_X,TEX_MIN_Y)),
		new Vertex(new Vector3f(SIZEX,START,START), new Vector2f(TEX_MIN_X,TEX_MAX_Y))};

            int[] indices = new int[]{0,1,2,0,2,3};

            mesh = new Mesh(vertices, indices);
	}

	if(gunMaterial == null) 
            gunMaterial = new Material(new Texture("PISGB0.png"));
	
        camera = new Camera(position, new Vector3f(0,0,-1), new Vector3f(0,1,0));
	rand = new Random();
	health = MAX_HEALTH;
	gunTransform = new Transform();
	gunTransform.setTranslation(new Vector3f(7,0,7));
	movementVector = zeroVector;
    }

    public void damage(int amt) {
	health -= amt;

	if(health > MAX_HEALTH)
            health = MAX_HEALTH;

	System.out.println(health);

	if(health <= 0) {
            Game.setIsRunning(false);
            System.out.println("You just died! GAME OVER!");
	}
    }

    public int getDamage() {
	return rand.nextInt(DAMAGE_MAX - DAMAGE_MIN) + DAMAGE_MIN;
    }

    public int getHealth() {
	return health;
    }

    public int getMaxHealth() {
	return MAX_HEALTH;
    }
	
    public void input(Window window) {
	if(window.input.isKeyPressed(GLFW.GLFW_KEY_E))
            Game.getLevel().openDoors(camera.getPos(), true);
        if(window.input.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) 
            window.input.unlockMouse();

        newMouseX = (float) window.input.getMouseX();
        newMouseY = (float) window.input.getMouseY();
        
        float dx = newMouseX - oldMouseX;
        float dy = newMouseY - oldMouseY;
        
        oldMouseX = newMouseX;
        oldMouseY = newMouseY;
        
        boolean rotY = dx != 0;
        boolean rotX = dy != 0;
			
        if(rotY)
            camera.rotateY(dy * mouseSensitivity);
        if(rotX)
            camera.rotateX(-dx * mouseSensitivity);
        
        
	float movAmt = (float)(10 * Time.getDelta());
        
        if(window.input.isKeyDown(GLFW.GLFW_KEY_W))
            movementVector = movementVector.add(camera.getForward());
	if(window.input.isKeyDown(GLFW.GLFW_KEY_S))
            movementVector = movementVector.sub(camera.getForward());
	if(window.input.isKeyDown(GLFW.GLFW_KEY_A))
            movementVector = movementVector.add(camera.getLeft());
	if(window.input.isKeyDown(GLFW.GLFW_KEY_D))
            movementVector = movementVector.add(camera.getRight());
	/* if(window.input.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            Input.setCursor(true);
            mouseLocked = false;
	}
	
        if(window.input.isMousePressed(GLFW.GLFW_MOUSE_BUTTON_1)) {
            if(!mouseLocked) {
		Input.setMousePosition(centerPosition);
		Input.setCursor(false);
		mouseLocked = true;
            } else {
		Vector2f lineStart = new Vector2f(camera.getPos().x, camera.getPos().z);
		Vector2f castDirection = new Vector2f(camera.getForward().x, camera.getForward().z).normalized();
		Vector2f lineEnd = lineStart.add(castDirection.mul(SHOOT_DISTANCE));

		Game.getLevel().checkIntersections(lineStart, lineEnd, true);
            }
	}

	movementVector = zeroVector;

	if(window.input.isKeyPressed(GLFW.GLFW_KEY_W))
            movementVector = movementVector.add(camera.getForward());//camera.move(camera.getForward(), movAmt);
	if(window.input.isKeyPressed(GLFW.GLFW_KEY_S))
            movementVector = movementVector.sub(camera.getForward());//camera.move(camera.getForward(), -movAmt);
	if(window.input.isKeyPressed(GLFW.GLFW_KEY_A))
            movementVector = movementVector.add(camera.getLeft());//camera.move(camera.getLeft(), movAmt);
	if(window.input.isKeyPressed(GLFW.GLFW_KEY_D))
            movementVector = movementVector.add(camera.getRight());//camera.move(camera.getRight(), movAmt);
		
	if(mouseLocked) {
            Vector2f deltaPos = new Vector2f(window.input.getMouseX(), window.input.getMouseY());
			
            boolean rotY = deltaPos.x != 0;
            boolean rotX = deltaPos.y != 0;
			
            if(rotY)
		camera.rotateY(deltaPos.x * MOUSE_SENSITIVITY);
            if(rotX)
		camera.rotateX(-deltaPos.y * MOUSE_SENSITIVITY);
				
            if(rotY || rotX)
		Input.setMousePosition(centerPosition);
	}
*/
    }
	
    public void update() {
	float movAmt = (float)(MOVE_SPEED * Time.getDelta());

	movementVector.y = 0;

	if(movementVector.length() > 0)
		movementVector = movementVector.normalized();

	Vector3f oldPos = camera.getPos();
	Vector3f newPos = oldPos.add(movementVector.mul(movAmt));

	Vector3f collisionVector = Game.getLevel().checkCollision(oldPos, newPos, PLAYER_SIZE, PLAYER_SIZE);
	movementVector = movementVector.mul(collisionVector);

	if(movementVector.length() > 0)
            camera.move(movementVector, movAmt);

	//Gun movement
	gunTransform.setTranslation(camera.getPos().add(camera.getForward().normalized().mul(0.105f)));
	gunTransform.getTranslation().y = gunTransform.getTranslation().y + GUN_OFFSET;

	Vector3f directionToCamera = Transform.getCamera().getPos().sub(gunTransform.getTranslation());

	float angleToFaceTheCamera = (float)Math.toDegrees(Math.atan(directionToCamera.z / directionToCamera.x));

	if(directionToCamera.x < 0)
            angleToFaceTheCamera += 180;

	gunTransform.getRotation().y = angleToFaceTheCamera + 90;
    }
	
    public void render() {
	Shader shader = Game.getLevel().getShader();
	shader.updateUniforms(gunTransform.getTransformation(), gunTransform.getProjectedTransformation(), gunMaterial);
	mesh.draw();
    }
	
    public Camera getCamera() {
	return camera;
    }
}

