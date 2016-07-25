package com.overtone.Notes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.overtone.Overtone;

/**
 * Represents a target for notes to go towards
 * Created by trevor on 2016-07-20.
 */
public class Target
{
    /**The diameter of the target zone.*/
    public static float Diameter =  Gdx.graphics.getWidth() * 0.025f * 2.0f;

    public final Vector2             Position; // The position of where the zone is
    public final Overtone.TargetZone Type;     // The type of target zone

    /**
     * Constructor
     * @param type The type of zone this is
     */
    public Target(Overtone.TargetZone type)
    {
        this.Type = type;

        // Bases on type, set the position of the target zone
        switch (type.ordinal())
        {
            case 0:
                Position = new Vector2(Gdx.graphics.getWidth() * 0.12f , Gdx.graphics.getHeight() * 0.83f);
                break;
            case 1:
                Position = new Vector2(Gdx.graphics.getWidth() * 0.88f,  Gdx.graphics.getHeight() * 0.83f);
                break;
            case 2:
                Position = new Vector2 (Gdx.graphics.getWidth() * 0.12f, Gdx.graphics.getHeight() * 0.17f);
                break;
            case 3:
                Position = new Vector2(Gdx.graphics.getWidth() * 0.88f, Gdx.graphics.getHeight() * 0.17f);
                break;
            default:
                Position = new Vector2(Gdx.graphics.getWidth() * 0.88f, Gdx.graphics.getHeight() * 0.17f);
                break;
        }
    }

    /**
     * @return Returns the bottom left corner of the target zone for drawing
     */
    public Vector2 GetDrawingPosition()
    {
        return new Vector2(Position.x -(Gdx.graphics.getWidth() * 0.0225f), Position.y - (Gdx.graphics.getWidth() * 0.0225f));
    }
}
