package com.larryngo.retrofuture.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class Player {
    private static final int GRAVITY = -15;
    private static final int GROUND_LEVEL = 25;
    private static final int MOVEMENT_SPEED = 100;
    private int state; //0 for run, 1 for jump

    private Vector3 position;
    private Vector3 velocity;
    private Animation animation;
    private Sound jump_sound;

    private Texture player;
    private Texture run_texture;
    private Texture jump_texture;

    public Player (int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);

        player = new Texture("player/player.png");
        jump_sound = Gdx.audio.newSound(Gdx.files.internal("player/audio/jump.wav"));

        run_texture = new Texture("player/run.png");
        jump_texture = new Texture("player/jump.png");
        animation = new Animation(new TextureRegion(run_texture), 6, 0.5f);
        state = 0;
    }

    public void update(float dt) {
        animation.update(dt);

        if(position.y > 0) {
            velocity.add(0, GRAVITY, 0);
        }

        velocity.add(0, GRAVITY, 0);
        velocity.scl(dt);
        position.add(MOVEMENT_SPEED * dt, velocity.y, 0);
        if(position.y <= GROUND_LEVEL) {
            position.y = GROUND_LEVEL;
            changeState(state);
        }

        velocity.scl(1/dt);

    }

    public void changeState(int state) {
        if(state == 1) {
            animation = new Animation(new TextureRegion(run_texture), 6, 0.5f);
            this.state = 0;
        }
    }

    public boolean canJump() {
        if(position.y > GROUND_LEVEL) {
            return false;
        } else {
            return true;
        }
    }

    public void jump() {
        animation = new Animation(new TextureRegion(jump_texture), 8, 0.5f);
        jump_sound.play(0.25f);
        state = 1;
        velocity.y = 600;
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getPlayer() {
        return animation.getFrame();
    }

    public void dispose() {
        player.dispose();
        jump_texture.dispose();
        run_texture.dispose();
        jump_sound.dispose();
    }
}
