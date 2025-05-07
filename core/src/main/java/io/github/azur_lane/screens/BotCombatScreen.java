package io.github.azur_lane.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.azur_lane.Main;
import io.github.azur_lane.contacts.TouchGround;
import io.github.azur_lane.objects.Ground;
import io.github.azur_lane.objects.Player;

public class BotCombatScreen implements Screen {
    private final boolean DEBUG = false;

    // Game systems
    private Main GAME;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private final float PPM = 100f;

    // Game Box2D
    private World world;
    private Box2DDebugRenderer debugRenderer;

    // Screen components
    private Texture backgroundTexture;
    private Player player;
    private Ground ground;

    public BotCombatScreen(Main game) {
        this.GAME = game;
        this.batch = game.batch;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GAME.SCREEN_WIDTH / PPM, GAME.SCREEN_HEIGHT / PPM);
        viewport = new FitViewport(GAME.SCREEN_WIDTH / PPM, GAME.SCREEN_HEIGHT / PPM, camera);

        // World settings
        world = new World(new Vector2(0, -20), true);
        debugRenderer = new Box2DDebugRenderer();
        // Set contact listeners
        world.setContactListener(new TouchGround());

        // Grounds
        ground = new Ground(world, 0, 0, GAME.SCREEN_WIDTH, 100);

        // Players
        player = new Player(world, 100, 200, "images/characters/Akagi_char.png");

        // Background
        backgroundTexture = new Texture(Gdx.files.internal("images/background/map_HQ.png"));
    }

    @Override
    public void render(float delta) {
        world.step(1 / 60f, 6, 2);

        player.updateSprite();
        draw();

        if (DEBUG)
            debugRenderer.render(world, viewport.getCamera().combined);

        player.action(delta / delta);
    }

    private void draw() {
        ScreenUtils.clear(1, 1, 1, 1);

        viewport.apply();
        camera.update();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, GAME.SCREEN_WIDTH / PPM, GAME.SCREEN_HEIGHT / PPM);
        player.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        world.dispose();
        player.dispose();
        ground.dispose();
        backgroundTexture.dispose();
        debugRenderer.dispose();
    }
}
