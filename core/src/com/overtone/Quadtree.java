package com.overtone;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.overtone.Notes.Note;

import java.util.ArrayList;

/**
 * Created by trevor on 2016-07-01.
 */
public class Quadtree
{
    public static final int NUM_LEVELS = 2;

    protected class Node
    {
        public Node topLeft;
        public Node topRight;
        public Node bottomLeft;
        public Node bottomRight;
        public Node parent;

        private Rectangle _bounds;
        private ArrayList<Note> objects;

        public Node(Rectangle bounds, ArrayList<Note> notes, Node parent )
        {
            _bounds = bounds;
            objects = notes;
            this.parent = parent;
        }
    }

    private Node _root;
    private ArrayList<Note> _notes;

    public Quadtree(Rectangle bounds)
    {
        _notes = new ArrayList();
        _root = new Node(bounds, new ArrayList<Note>(), null);
        Partition(1, _root);
    }

    public boolean Insert(Note n)
    {
        return Insert(n, _root);
    }

    public ArrayList<Note> Get(Vector2 point)
    {
        _notes.clear();
        Get(point, _root);
        return _notes;
    }

    public void Update(float deltaTime)
    {
        Update(deltaTime, _root);
    }

    public ArrayList<Note> GetAll()
    {
        ArrayList<Note> notes = new ArrayList<Note>();
        notes.addAll(GetAll(_root));
        return notes;
    }

    private void Partition(int level, Node node)
    {
        if (node == null)
            return;

        // if we reached the number of desired levels then stop partiioning, else partition
        if (level < NUM_LEVELS)
        {
            float childWidthLeft    = node._bounds.getWidth() / 2.0f;
            float childHeightTop    = node._bounds.getHeight() / 2.0f;

            float childWidthRight   = node._bounds.getWidth() - childWidthLeft;
            float childHeightBottom = node._bounds.getHeight() - childHeightTop;

            float topLeftX          = node._bounds.x;
            float topLeftY          = node._bounds.y + childHeightTop;

            float topRightX         = node._bounds.x + childWidthLeft;
            float topRightY         = node._bounds.y + childHeightTop;

            float bottomLeftX       = node._bounds.x;
            float bottomLeftY       = node._bounds.y;

            float bottomRightX      = node._bounds.x + childWidthLeft;
            float bottomRightY      = node._bounds.y ;

            node.topLeft         = new Node(new Rectangle(topLeftX,     topLeftY,     childWidthLeft,  childHeightTop), new ArrayList<Note>(), node);
            node.topRight        = new Node(new Rectangle(topRightX,    topRightY,    childWidthRight, childHeightTop), new ArrayList<Note>(), node);
            node.bottomLeft      = new Node(new Rectangle(bottomLeftX,  bottomLeftY,  childWidthLeft,  childHeightBottom), new ArrayList<Note>(), node);
            node.bottomRight     = new Node(new Rectangle(bottomRightX, bottomRightY, childWidthRight, childHeightBottom), new ArrayList<Note>(), node);

            level++;

            Partition(level, node.topLeft);
            Partition(level, node.topRight);
            Partition(level, node.bottomLeft);
            Partition(level, node.bottomRight);
        }
    }

    private boolean Insert(Note note, Node node)
    {
        if (!node._bounds.contains(note.GetCenter().x, note.GetCenter().y))
            return false;


        if (node.topLeft == null)
        {
            node.objects.add(note);
            return true;
        }

        if (Insert(note, node.topLeft)) return true;
        if (Insert(note, node.topRight)) return true;
        if (Insert(note, node.bottomLeft)) return true;
        if (Insert(note, node.bottomRight)) return true;

        return false;
    }

    private void Get(Vector2 point, Node node)
    {
        if (!node._bounds.contains(point.x, point.y))
            return;

        if (node.topLeft == null)
        {
           _notes = node.objects;
            return;
        }

        Get(point, node.topLeft);
        Get(point, node.topRight);
        Get(point, node.bottomLeft);
        Get(point, node.bottomRight);
    }

    // Updates all objects and moves them to new quads if necessary
    private void Update(float deltaTime, Node node)
    {
        if (node == null)
            return;

        for (Note n : node.objects)
        {
            n.Update(deltaTime);
        }

        Update(deltaTime, node.topLeft);
        Update(deltaTime, node.topRight);
        Update(deltaTime, node.bottomLeft);
        Update(deltaTime, node.bottomRight);
    }

    private ArrayList<Note> GetAll(Node n)
    {
        ArrayList<Note> notes = new ArrayList<Note>();

        if(n == null)
            return notes;

        notes.addAll(GetAll(n.bottomLeft));
        notes.addAll(GetAll(n.bottomRight));
        notes.addAll(GetAll(n.topLeft));
        notes.addAll(GetAll(n.topRight));
        notes.addAll(n.objects);
        return notes;
    }
}
