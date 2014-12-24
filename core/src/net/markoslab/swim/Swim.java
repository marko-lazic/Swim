package net.markoslab.swim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class Swim extends ApplicationAdapter {
	public static final String SWIM_DEV_JPG = "swim-dev.jpg";
	private SpriteBatch batch;
	private Sprite swimSprite;
	private OrthographicCamera camera;

	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(300, 300 * (h / w));
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();

		swimSprite = new Sprite(new Texture(SWIM_DEV_JPG));
		swimSprite.setPosition(0, 0);
		swimSprite.setScale(-0.8F);
		batch = new SpriteBatch();

	}

	@Override
	public void render () {
		handleInput();
		camera.update();
		batch.setProjectionMatrix(camera.projection);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		swimSprite.draw(batch);
		batch.end();
	}

	private void handleInput() {
		if (Gdx.input.isTouched()) {
			Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			Vector3 world = camera.unproject(mouse);
			if (swimSprite.getBoundingRectangle().contains(world.x, world.y)) {
				Gdx.app.exit();
			}
		}

		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			camera.zoom += 0.02;
			//If the A Key is pressed, add 0.02 to the Camera's Zoom
		}
		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
			camera.zoom -= 0.02;
			//If the Q Key is pressed, subtract 0.02 from the Camera's Zoom
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			camera.translate(-3, 0, 0);
			//If the LEFT Key is pressed, translate the camera -3 units in the X-Axis
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			camera.translate(3, 0, 0);
			//If the RIGHT Key is pressed, translate the camera 3 units in the X-Axis
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			camera.translate(0, -3, 0);
			//If the DOWN Key is pressed, translate the camera -3 units in the Y-Axis
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			camera.translate(0, 3, 0);
			//If the UP Key is pressed, translate the camera 3 units in the Y-Axis
		}

		camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 10000 / camera.viewportWidth);

		float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
		float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

		camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, 10000 - effectiveViewportWidth / 2f);
		camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, 10000 - effectiveViewportHeight / 2f);
	}

	@Override
	public void dispose() {
		swimSprite.getTexture().dispose();
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		camera.viewportWidth = 300f;
		camera.viewportHeight = 300f * height/width;
		camera.update();
	}
}
