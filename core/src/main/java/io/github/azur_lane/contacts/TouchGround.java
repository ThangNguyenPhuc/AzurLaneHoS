package io.github.azur_lane.contacts;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import io.github.azur_lane.objects.Ground;
import io.github.azur_lane.objects.Player;

public class TouchGround implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Object a = contact.getFixtureA().getBody().getUserData();
        Object b = contact.getFixtureB().getBody().getUserData();

        if (a instanceof Player && b instanceof Ground) {
            ((Player) a).setOnGround(true);
        } else if (a instanceof Ground && b instanceof Player) {
            ((Player) b).setOnGround(true);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Object a = contact.getFixtureA().getBody().getUserData();
        Object b = contact.getFixtureB().getBody().getUserData();

        if (a instanceof Player && b instanceof Ground) {
            ((Player) a).setOnGround(false);
        } else if (a instanceof Ground && b instanceof Player) {
            ((Player) b).setOnGround(false);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
