package com.larryngo.retrofuture.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.larryngo.retrofuture.Game;
import com.larryngo.retrofuture.GifDecoder;
import com.larryngo.retrofuture.sprites.Player;

public class PlayState extends State{
    private static final int GROUND_Y_OFFSET = -75;

    private Player player;
    private Animation<TextureRegion> background;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private float elapsed;

    private Music music;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        background = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("stage/game_bg.gif").read());
        ground = new Texture("stage/ground.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);

        player = new Player(75, 25);
        cam.setToOrtho(false, (float)Game.WIDTH / 4, (float)Game.HEIGHT / 4);

        music = Gdx.audio.newMusic(Gdx.files.internal("audio/music/play_music.mp3"));
        music.setLooping(true);
        music.setVolume(0.15f);
        music.play();
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched() && player.canJump()) {
            player.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        player.update(dt);

        cam.position.x = player.getPosition().x + 100;

        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        elapsed += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(1, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.draw(background.getKeyFrame(elapsed), cam.position.x - (cam.viewportWidth / 2), 0, cam.viewportWidth, cam.viewportHeight);
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.draw(player.getPlayer(), player.getPosition().x, player.getPosition().y);

        sb.end();
    }

    @Override
    public void dispose() {
        music.dispose();
        player.dispose();
    }

    private void updateGround() {
        if(cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if(cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }
}
