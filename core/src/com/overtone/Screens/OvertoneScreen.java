package com.overtone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.overtone.Overtone;

/**
 * Base class for all screens in Overtone.
 * Stores some variables that will be used by all screens
 * Created by trevor on 2016-06-30.
 */
public class OvertoneScreen implements OvertoneScreenInterface
{
    protected final SpriteBatch _batch;       // Sprite batch to draw to

    // Fonts
    protected final GlyphLayout _glyphLayout; // Stores font date
    protected final Skin        _skin;        // Stores the color and layout of the fonts
    protected final BitmapFont  _font12;      // 12 point font
    protected final BitmapFont  _font18;      // 18 point font
    protected final BitmapFont  _font24;      // 24 point font
    protected final BitmapFont  _font30;      // 30 point font
    protected final BitmapFont  _font36;      // 36 point font

    // Textures
    protected final Texture     _yes;         // Texture for the yes button
    protected final Texture     _yesHover;    // Texture for the yes button hover
    protected final Texture     _yesDown;     // Texture for the yes button down
    protected final Texture     _no;          // Texture for the no button
    protected final Texture     _noHover;     // Texture for the no button hover
    protected final Texture     _noDown;      // Texture for the no button down
    protected final Texture     _next;        // Texture for the next button
    protected final Texture     _nextHover;   // Texture for the next button hover
    protected final Texture     _nextDown;    // Texture for the next button down
    protected final Texture     _back;        // Texture for the back button
    protected final Texture     _backHover;   // Texture for the back button hover
    protected final Texture     _backDown;    // Texture for the back button down

    // Sounds
    protected final Sound       _accept;      // Sound effect for accepting
    protected final Sound       _decline;     // Sound effect for declining
    protected final Sound       _buttonPress; // Sound effect for a button press
    protected final Sound       _warning;     // Sound effect for a waring to be displayed
    protected final Sound       _countdown;   // Sound effect for the pause menu countdown

    /**
     * Constructor
     */
    public OvertoneScreen()
    {
        _batch        = new SpriteBatch();
        _glyphLayout  = new GlyphLayout();
        _skin         = new Skin();

        // Load Sounder
        _accept      = Gdx.audio.newSound(Gdx.files.internal("Sounds\\accept.wav"));
        _decline     = Gdx.audio.newSound(Gdx.files.internal("Sounds\\decline.wav"));
        _buttonPress = Gdx.audio.newSound(Gdx.files.internal("Sounds\\press.wav"));
        _warning     = Gdx.audio.newSound(Gdx.files.internal("Sounds\\warning.wav"));
        _countdown   = Gdx.audio.newSound(Gdx.files.internal("Sounds\\countdown.wav"));

        // Load Fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts\\Furore.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        _font12 = generator.generateFont(parameter);
        parameter.size = 18;
        _font18 = generator.generateFont(parameter);
        parameter.size = 24;
        _font24 = generator.generateFont(parameter);
        parameter.size = 30;
        _font30 = generator.generateFont(parameter);
        parameter.size = 36;
        _font36 = generator.generateFont(parameter);
        generator.dispose();

        // load Textures
        _yes          = new Texture(Gdx.files.internal("Textures\\yes.png"));
        _yesHover     = new Texture(Gdx.files.internal("Textures\\yesHover.png"));
        _yesDown      = new Texture(Gdx.files.internal("Textures\\yesDown.png"));
        _no           = new Texture(Gdx.files.internal("Textures\\no.png"));
        _noHover      = new Texture(Gdx.files.internal("Textures\\noHover.png"));
        _noDown       = new Texture(Gdx.files.internal("Textures\\noDown.png"));
        _next         = new Texture(Gdx.files.internal("Textures\\next.png"));
        _nextHover    = new Texture(Gdx.files.internal("Textures\\nextHover.png"));
        _nextDown     = new Texture(Gdx.files.internal("Textures\\nextDown.png"));
        _back         = new Texture(Gdx.files.internal("Textures\\back.png"));
        _backHover    = new Texture(Gdx.files.internal("Textures\\backHover.png"));
        _backDown     = new Texture(Gdx.files.internal("Textures\\backDown.png"));

        // Create button arrangements
        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        _skin.add("default", _font30);
        _skin.add("small", _font18);
        _skin.add("white", new Texture(pixmap));
        _skin.add("yes", _yes);
        _skin.add("yesHover", _yesHover);
        _skin.add("yesDown", _yesDown);
        _skin.add("no", _no);
        _skin.add("noHover", _noHover);
        _skin.add("noDown", _noDown);
        _skin.add("next", _next);
        _skin.add("nextHover", _nextHover);
        _skin.add("nextDown", _nextDown);
        _skin.add("back", _back);
        _skin.add("backHover", _backHover);
        _skin.add("backDown", _backDown);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up      = _skin.newDrawable("white", new Color(0.157f, 0.325f, 0.424f, 0.75f));
        textButtonStyle.down    = _skin.newDrawable("white", new Color(0.012f, 0.137f, 0.212f, 0.75f));
        textButtonStyle.over    = _skin.newDrawable("white", new Color(0.067f, 0.224f, 0.318f, 0.75f));

        textButtonStyle.font = _skin.getFont("default");
        _skin.add("default", textButtonStyle);

        TextButton.TextButtonStyle textButtonStyleSmall = new TextButton.TextButtonStyle();
        textButtonStyleSmall.up      = _skin.newDrawable("white", new Color(0.157f, 0.325f, 0.424f, 0.75f));
        textButtonStyleSmall.down    = _skin.newDrawable("white", new Color(0.012f, 0.137f, 0.212f, 0.75f));
        textButtonStyleSmall.over    = _skin.newDrawable("white", new Color(0.067f, 0.224f, 0.318f, 0.75f));

        textButtonStyleSmall.font = _skin.getFont("small");
        _skin.add("small", textButtonStyleSmall);

        TextButton.TextButtonStyle buttonGroupStyle = new TextButton.TextButtonStyle();
        buttonGroupStyle.up         = _skin.newDrawable("white", new Color(0.157f, 0.325f, 0.424f, 0.75f));
        buttonGroupStyle.down       = _skin.newDrawable("white", new Color(0.012f, 0.137f, 0.212f, 0.75f));
        buttonGroupStyle.over       = _skin.newDrawable("white", new Color(0.067f, 0.224f, 0.318f, 0.75f));
        buttonGroupStyle.checked    = _skin.newDrawable("white", new Color(0.467f, 0.224f, 0.318f, 0.75f));

        buttonGroupStyle.font = _skin.getFont("default");
        _skin.add("group", buttonGroupStyle);

        ImageButton.ImageButtonStyle imageButtonStyleYes = new ImageButton.ImageButtonStyle();
        imageButtonStyleYes.up   = _skin.newDrawable("yes", new Color(1f, 1f, 1f, 1f));
        imageButtonStyleYes.over = _skin.newDrawable("yesHover", new Color(1f, 1f, 1f, 1f));
        imageButtonStyleYes.down = _skin.newDrawable("yesDown", new Color(1f, 1f, 1f, 1f));
        _skin.add("yesButtons", imageButtonStyleYes);

        ImageButton.ImageButtonStyle imageButtonStyleNo = new ImageButton.ImageButtonStyle();
        imageButtonStyleNo.up   = _skin.newDrawable("no", new Color(1f, 1f, 1f, 1f));
        imageButtonStyleNo.over = _skin.newDrawable("noHover", new Color(1f, 1f, 1f, 1f));
        imageButtonStyleNo.down = _skin.newDrawable("noDown", new Color(1f, 1f, 1f, 1f));
        _skin.add("noButtons", imageButtonStyleNo);

        ImageButton.ImageButtonStyle imageButtonStyleNext = new ImageButton.ImageButtonStyle();
        imageButtonStyleNext.up   = _skin.newDrawable("next", new Color(1f, 1f, 1f, 1f));
        imageButtonStyleNext.over = _skin.newDrawable("nextHover", new Color(1f, 1f, 1f, 1f));
        imageButtonStyleNext.down = _skin.newDrawable("nextDown", new Color(1f, 1f, 1f, 1f));
        _skin.add("nextButton", imageButtonStyleNext);

        ImageButton.ImageButtonStyle imageButtonStyleBack = new ImageButton.ImageButtonStyle();
        imageButtonStyleBack.up   = _skin.newDrawable("back", new Color(1f, 1f, 1f, 1f));
        imageButtonStyleBack.over = _skin.newDrawable("backHover", new Color(1f, 1f, 1f, 1f));
        imageButtonStyleBack.down = _skin.newDrawable("backDown", new Color(1f, 1f, 1f, 1f));
        _skin.add("backButton", imageButtonStyleBack);
    }

    /**
     * Creates a new button
     * @param label The label of the button, null if image button
     * @param style The style of the button
     * @param width The width of the button
     * @param height The height of the button
     * @param pos The position of the button
     * @param stage The stage to add the button to
     * @return Returns a new text button
     */
    public Button CreateButton(String label, String style, float width, float height, Vector2 pos, Stage stage)
    {
        if (label == null)
        {
            final ImageButton button = new ImageButton(_skin.get(style, ImageButton.ImageButtonStyle.class));
            button.setWidth(width);
            button.setHeight(height);
            button.setPosition(pos.x,pos.y);
            stage.addActor(button);

            return button;
        }
        else
        {
            final TextButton button = new TextButton(label, _skin.get(style, TextButton.TextButtonStyle.class));
            button.setWidth(width);
            button.setHeight(height);
            button.setPosition(pos.x,pos.y);
            stage.addActor(button);
            return button;
        }
    }

    public void resize (int width, int height)
    {
        Overtone.ScreenWidth  = width;
        Overtone.ScreenHeight = height;
    }

    public void dispose ()
    {
        _batch.dispose();
        _skin.dispose();
        _font12.dispose();
        _font18.dispose();
        _font24.dispose();
        _font30.dispose();
        _font36.dispose();
        _yes.dispose();
        _yesHover.dispose();
        _yesDown.dispose();
        _no.dispose();
        _noHover.dispose();
        _noDown.dispose();
        _next.dispose();
        _nextHover.dispose();
        _nextDown.dispose();
        _back.dispose();
        _backHover.dispose();
        _backDown.dispose();
        _accept.dispose();
        _decline.dispose();
        _buttonPress.dispose();
        _warning.dispose();
        _countdown.dispose();
    }

    public void pause () {}
    public void resume () {}
    public void hide () {}
    public void show () {}
    public void render (float deltaTime) {}
    public void update(float deltaTime) {}
}
