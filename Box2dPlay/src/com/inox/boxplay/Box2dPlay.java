package com.inox.boxplay;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class Box2dPlay implements ApplicationListener {
	private static final float TIME_STEP = 1/60f;
	World world;
	Box2DDebugRenderer renderer;

	SpriteBatch batcher;
	OrthographicCamera cam;
	BitmapFont font;
	
	float startTime;
	
	Vector3 touchPoint;

	@Override
	public void create() {
		Vector2 gravity = new Vector2(0f, -10f);
		world = new World(gravity, true);
		renderer = new Box2DDebugRenderer();
		cam = new OrthographicCamera(50, 30);

		batcher = new SpriteBatch();
		font = new BitmapFont();

		startTime = 0;
		touchPoint = new Vector3();
		
		initWorld();
	}

	private void initWorld() {
		Box2dUtils.createEdgeShape(world, BodyType.StaticBody, -25, -14, 25, -14, 0);
		
		/*Body body1 = Box2dUtils.createBox(world, BodyType.DynamicBody, 5, 4, 1);
		Vector2[] vertices = {
			new Vector2(0, 0),
			new Vector2(5, 0),
			new Vector2(2.5f, 4)
		};
		Body body2 = Box2dUtils.createPolygon(world, BodyType.DynamicBody, vertices, 1);
		
		body1.setTransform(-5, 0, 0);
		body2.setTransform(5, 5, (float) (Math.PI / 4));
		
		DistanceJointDef jd = new DistanceJointDef();
		jd.bodyA = body1;
		jd.bodyB = body2;
		jd.collideConnected = true;
		jd.localAnchorA.set(0f, 0f);
		jd.localAnchorB.set(2.5f, 2);
		jd.frequencyHz = 4f;
		jd.dampingRatio = 0.5f;
		jd.length = 10;
		
		RevoluteJointDef rjd = new RevoluteJointDef();
		rjd.bodyA = body1;
		rjd.bodyB = body2;
		rjd.collideConnected = false;
		rjd.localAnchorA.set(0f, 0f);
		rjd.localAnchorB.set(2.5f, 2);*/
		
		//world.createJoint(rjd);
		
		Body temp;

		float box_height = 1.5f;
		
		for(int j=0; j<5; j++) {
			for(int i=0; i<15; i++) {
				temp = Box2dUtils.createBox(world, BodyType.DynamicBody, box_height, box_height, 1);
				temp.setTransform(-15 + j * 5 , -12 + i*box_height, 0);
			}
		}
	}
	
	private void throwBall(float y) {
		Body ball = Box2dUtils.createCircle(world, BodyType.DynamicBody, 0.5f, 1);
		ball.setBullet(true);
		ball.setTransform(-25, y, 0);
		ball.setLinearVelocity(200, 0);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		renderer.render(world, cam.combined);

		world.step(TIME_STEP, 4, 4);
		
		if(Gdx.input.justTouched()) {
			cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			throwBall(touchPoint.y);
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}