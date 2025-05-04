package io.github.azur_lane.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.azur_lane.Main;

public class BotCombatScreen implements Screen {

    private Main game;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    private Texture playerTexture;
    private float playerX, playerY;
    private float speed = 200f; // pixel/second

    public BotCombatScreen(Main game) {
        this.game = game;
        this.batch = game.batch;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480); // Kích thước màn hình

        // Load hình vuông nhân vật (bạn có thể thay thành file ảnh player.png)
        playerTexture = new Texture(Gdx.files.internal("images/player.png"));

        playerX = 100;
        playerY = 100;
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1); // Clear màn hình với màu đen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(playerTexture, playerX, playerY);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
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
        playerTexture.dispose();
    }
}
