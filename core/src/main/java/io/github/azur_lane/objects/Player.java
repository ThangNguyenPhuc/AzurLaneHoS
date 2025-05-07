package io.github.azur_lane.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player {
    // Body for physics, Sprite for visuals
    public Body body;
    public Sprite sprite;

    private World world;
    private Texture texture;

    // Behavior attributes
    private boolean onGround = false;

    private final float MOVE_FORCE = 30f;
    private final float JUMP_FORCE = 20f;
    private final float WIDTH = 150f;
    private final float HEIGHT = 150f;
    private final float PPM = 100f;

    public Player(World world, float X, float Y, String texturePath) {
        this.world = world;
        createBody(X, Y);
        createSprite(texturePath);

        body.setUserData(this);
    }

    private void createBody(float startX, float startY) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(startX / PPM, startY / PPM);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((WIDTH / 2) / PPM, (HEIGHT / 2) / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    private void createSprite(String texturePath) {
        texture = new Texture(texturePath);
        sprite = new Sprite(texture);
        sprite.setSize(WIDTH / PPM, HEIGHT / PPM);
        sprite.setOriginCenter();
    }

    public void updateSprite() {
        Vector2 pos = body.getPosition();
        sprite.setPosition(pos.x - sprite.getWidth() / 2, pos.y - sprite.getHeight() / 2);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void action(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            this.moveLeft(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            this.moveRight(delta);
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            this.jump(delta);
    }

    public void moveLeft(float delta) {
        body.applyForceToCenter(new Vector2(-MOVE_FORCE * delta, 0), true);
    }

    public void moveRight(float delta) {
        body.applyForceToCenter(new Vector2(MOVE_FORCE * delta, 0), true);
    }

    public void jump(float delta) {
        if (this.isOnGround()) {
            body.applyLinearImpulse(new Vector2(0, JUMP_FORCE * delta), body.getWorldCenter(), true);
        }
    }

    public void setOnGround(boolean value) {
        this.onGround = value;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void dispose() {
        this.world.dispose();
        this.texture.dispose();
    }
}
