package com.overtone.Ratings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Renders the ratings on the screen if there are any to be rendered.
 * Created by trevor on 2016-07-07.
 */
public class RatingRenderer
{
    // Textures for all of the ratings
    private final Texture[] _ratingTextures;

    /**
     * Constructor
     */
    public RatingRenderer()
    {
        _ratingTextures    = new Texture[5];
        _ratingTextures[0] = new Texture(Gdx.files.internal("Textures\\Ratings\\Perfect.png"));
        _ratingTextures[1] = new Texture(Gdx.files.internal("Textures\\Ratings\\Great.png"));
        _ratingTextures[2] = new Texture(Gdx.files.internal("Textures\\Ratings\\okay.png"));
        _ratingTextures[3] = new Texture(Gdx.files.internal("Textures\\Ratings\\bad.png"));
        _ratingTextures[4] = new Texture(Gdx.files.internal("Textures\\Ratings\\miss.png"));
    }

    /**
     * Draws all of the notes on screen
     * @param ratings All of the on screen ratings to be rendered
     * @param batch The sprite batch to draw to.
     */
    public void Draw(ArrayList<Rating> ratings, SpriteBatch batch)
    {
        for(Rating r : ratings)
        {
            if(r.IsVisible())
            {
                batch.draw(_ratingTextures[r.GetRating().ordinal()],
                        r.GetCenter().x - (_ratingTextures[r.GetRating().ordinal()].getWidth() / 4.0f),
                        r.GetCenter().y - (_ratingTextures[r.GetRating().ordinal()].getHeight() / 4.0f),
                        _ratingTextures[r.GetRating().ordinal()].getWidth() / 2.0f,
                        _ratingTextures[r.GetRating().ordinal()].getHeight() / 2.0f
                );
            }
        }
    }
}
