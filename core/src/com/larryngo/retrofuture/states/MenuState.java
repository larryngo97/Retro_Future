package com.larryngo.retrofuture.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.larryngo.retrofuture.Game;
import com.larryngo.retrofuture.GifDecoder;

public class MenuState extends State {
    private Stage stage;
    private Animation<TextureRegion> background;
    private float elapsed;

    private Texture title;

    //static buttons
    private Texture button_play;
    private Texture button_options;
    private Texture button_exit;

    //selected buttons
    private Texture button_play_s;
    private Texture button_options_s;
    private Texture button_exit_s;

    private Music music;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        background = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("menu/bg.gif").read());
        title = new Texture("menu/title.png");
        button_play = new Texture("menu/buttons/btn_play.png");
        button_play_s = new Texture("menu/buttons/btn_play_s.png");

        button_options = new Texture("menu/buttons/btn_options.png");
        button_options_s = new Texture("menu/buttons/btn_options_s.png");

        button_exit = new Texture ("menu/buttons/btn_quit.png");
        button_exit_s = new Texture ("menu/buttons/btn_quit_s.png");


        music = Gdx.audio.newMusic(Gdx.files.internal("audio/music/menu_music.mp3"));
        music.setLooping(true);
        music.setVolume(0.15f);
        music.play();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
            music.stop();
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();

        elapsed += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(1, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.draw(background.getKeyFrame(elapsed), 0, 0, Game.WIDTH, Game.HEIGHT);
        sb.draw(title, (float)((Game.WIDTH - title.getWidth()) / 2), (float)Game.HEIGHT / 1.5f);
        sb.draw(button_play, (float)((Game.WIDTH - button_play.getWidth()) / 2), (float)Game.HEIGHT / 2 - ((float)Game.HEIGHT / 10));
        sb.draw(button_options, (float)((Game.WIDTH - button_options.getWidth()) / 2), (float)Game.HEIGHT / 2 - ((float)Game.HEIGHT / 10) - button_play.getHeight() - 25);
        sb.draw(button_exit, (float)((Game.WIDTH - button_exit.getWidth()) / 2), (float)Game.HEIGHT / 2 - ((float)Game.HEIGHT / 10) - button_play.getHeight() - button_options.getHeight() - 50);
        sb.end();
    }

    @Override
    public void dispose() {
        button_play.dispose();
        button_play_s.dispose();

        button_options.dispose();
        button_options_s.dispose();

        button_exit.dispose();
        button_exit_s.dispose();
    }
}
