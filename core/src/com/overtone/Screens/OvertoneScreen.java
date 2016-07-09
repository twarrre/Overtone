package com.overtone.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Base class for all screens in Overtone.
 * Stores some variables that will be used by all screens
 * Created by trevor on 2016-06-30.
 */
public class OvertoneScreen implements OvertoneScreenInterface
{
    // The background image for the screen
    protected final String      _backgroundImagePath;
    protected final Texture     _backgroundImage;
    protected final SpriteBatch _batch;
    protected final BitmapFont  _font;
    protected final GlyphLayout _glyphLayout;
    protected final Skin        _skin;
    protected float             _screenWidth;
    protected float             _screenHeight;

    /**
     * Constructor
     * @param backgroundImagePath The path the the background image
     * @param screenWidth The screen width
     * @param screenHeight The screen height
     */
    public OvertoneScreen(String backgroundImagePath, float screenWidth, float screenHeight)
    {
        _screenWidth         = screenWidth;
        _screenHeight        = screenHeight;
        _backgroundImagePath = backgroundImagePath;

        _batch           = new SpriteBatch();
        _backgroundImage = new Texture(backgroundImagePath);

        _font        = new BitmapFont();
        _glyphLayout = new GlyphLayout();

        _skin  = new Skin();

        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        _font.getData().scale(1);
        _skin.add("default", _font);
        _skin.add("white", new Texture(pixmap));

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up      = _skin.newDrawable("white", new Color(0.157f, 0.325f, 0.424f, 0.75f));
        textButtonStyle.down    = _skin.newDrawable("white", new Color(0.012f, 0.137f, 0.212f, 0.75f));
        textButtonStyle.over    = _skin.newDrawable("white", new Color(0.067f, 0.224f, 0.318f, 0.75f));

        textButtonStyle.font = _skin.getFont("default");
        _skin.add("default", textButtonStyle);
    }

    public TextButton CreateTextButton(String label, String style, float width, float height, Vector2 pos, Stage stage)
    {
        final TextButton button = new TextButton(label, _skin.get(style, TextButton.TextButtonStyle.class));
        button.setWidth(width);
        button.setHeight(height);
        button.setPosition(pos.x,pos.y);
        stage.addActor(button);

        return button;
    }

    public void show ()
    {

    }

    public void render (float deltaTime)
    {
        // Draws the background image
        _batch.begin();
        _batch.draw(_backgroundImage, 0, 0, _screenWidth, _screenHeight);
        _batch.end();
    }

    public void update(float deltaTime)
    {

    }

    public void resize (int width, int height)
    {
        _screenWidth = width;
        _screenHeight = height;
    }

    public void pause ()
    {

    }

    public void resume ()
    {

    }

    public void hide ()
    {

    }

    public void dispose ()
    {
        _batch.dispose();
        _backgroundImage.dispose();
        _batch.dispose();
        _skin.dispose();
        _font.dispose();
    }
}
