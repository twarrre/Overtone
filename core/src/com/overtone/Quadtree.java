package com.overtone;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.overtone.Notes.Note;
import java.util.ArrayList;

/**
 * Quadtree data structure
 * Created by trevor on 2016-07-01.
 */
public class Quadtree
{
    /**
     * The number of levels in the quadtree
     */
    public static final int NUM_LEVELS = 2;

    /**
     * Represents a node in the tree
     */
    protected class Node
    {

        public Node topLeft;
        public Node topRight;
        public Node bottomLeft;
        public Node bottomRight;
        public Node parent;

        // The bounds of the quad
        private final Rectangle _bounds;

        // The objects stored in this quad
        private ArrayList<Note> _objects;

        /**
         * Constructor
         * @param bounds The bounds of the quad
         * @param parent Reference to the parent node of this one
         */
        public Node(Rectangle bounds, Node parent)
        {
            _bounds = bounds;
            _objects = new ArrayList<Note>();
            this.parent = parent;
        }
    }

    // The root of the tree
    private final Node _root;

    // Storage when getting notes from all of the nodes
    private ArrayList<Note> _notes;

    /**
     * Constructor
     * @param bounds The overall bounds of the quadtree
     */
    public Quadtree(Rectangle bounds)
    {
        _notes = new ArrayList();
        _root  = new Node(bounds, null);

        // partition the tree NUM_LEVELS
        Partition(1, _root);
    }

    /**
     * Inserts an element into the tree
     * @param n the element to be inserted
     * @return returns true if successfully inserted, false otherwise
     */
    public boolean Insert(Note n)
    {
        return Insert(n, _root);
    }

    /**
     * Gets all elements in a particular quad where the point is located
     * @param point The point used to get the elements at a particular quad
     * @return
     */
    public ArrayList<Note> Get(Vector2 point)
    {
        _notes = new ArrayList<Note>();
        Get(point, _root);
        return _notes;
    }

    /**
     * Removes a particular element from the quadtree
     * @param n The element to be removed
     */
    public void Remove(Note n)
    {
        Remove(n, _root);
    }

    /**
     * Updates every element in the quadtree
     * @param deltaTime The time since the last frame
     */
    public ArrayList<Vector2> Update(float deltaTime)
    {
        return Update(deltaTime, _root);
    }

    /**
     * Gets all of the elements in the quadtree into a list
     * @return A list of all elements in the tree
     */
    public ArrayList<Note> GetAll()
    {
        ArrayList<Note> notes = new ArrayList<Note>();
        notes.addAll(GetAll(_root));
        return notes;
    }

    /**
     * Recursively breaks each quad into 4 new quads until it has reached the maximum number of levels
     * @param level The current level of the tree
     * @param node The current note to be partitioned
     */
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

            node.topLeft         = new Node(new Rectangle(topLeftX,     topLeftY,     childWidthLeft,  childHeightTop),    node);
            node.topRight        = new Node(new Rectangle(topRightX,    topRightY,    childWidthRight, childHeightTop),    node);
            node.bottomLeft      = new Node(new Rectangle(bottomLeftX,  bottomLeftY,  childWidthLeft,  childHeightBottom), node);
            node.bottomRight     = new Node(new Rectangle(bottomRightX, bottomRightY, childWidthRight, childHeightBottom), node);

            level++;

            // Partition all of the children nodes
            Partition(level, node.topLeft);
            Partition(level, node.topRight);
            Partition(level, node.bottomLeft);
            Partition(level, node.bottomRight);
        }
    }

    /**
     * Recusivly tries to insert the element into the highest level node
     * @param note The element to be inserted
     * @param node The node that the element is trying to be inserted into
     * @return True is successfully inserted, false otherwise
     */
    private boolean Insert(Note note, Node node)
    {
        if (!node._bounds.contains(note.GetCenter().x, note.GetCenter().y))
            return false;


        if (node.topLeft == null)
        {
            node._objects.add(note);
            return true;
        }

        // Try to insert it to the children
        if (Insert(note, node.topLeft)) return true;
        if (Insert(note, node.topRight)) return true;
        if (Insert(note, node.bottomLeft)) return true;
        if (Insert(note, node.bottomRight)) return true;

        return false;
    }

    /**
     * Gets all elements from a particular quad
     * @param point The point to find the quad
     * @param node The node we are check if the point is located in
     */
    private void Get(Vector2 point, Node node)
    {
        if (!node._bounds.contains(point.x, point.y))
            return;

        if (node.topLeft == null)
        {
           _notes = node._objects;
            return;
        }

        // Check if the element is in any of the children
        Get(point, node.topLeft);
        Get(point, node.topRight);
        Get(point, node.bottomLeft);
        Get(point, node.bottomRight);
    }

    /**
     * Recursively updates all of the elements in the quad
     * @param deltaTime The time since the last frame
     * @param node The node we are currently updating
     */
    private ArrayList<Vector2> Update(float deltaTime, Node node)
    {
        // If the node is not visible anymore, then remove it from the tree
        ArrayList<Note> toBeRemoved = new ArrayList();
        ArrayList<Vector2> removed = new ArrayList();

        if (node == null)
            return removed;

        // Update all of the elements at this node
        for (Note n : node._objects)
        {
            n.Update(deltaTime);
            if(!n.IsVisible())
            {
                toBeRemoved.add(n);
                removed.add(n.GetTarget());
            }
        }

        // Update all nodes in the children nodes
        removed.addAll(Update(deltaTime, node.topLeft));
        removed.addAll(Update(deltaTime, node.topRight));
        removed.addAll(Update(deltaTime, node.bottomLeft));
        removed.addAll(Update(deltaTime, node.bottomRight));

        // If there are elements to be removed, remove them now
        for(Note n : toBeRemoved)
            Remove(n, node);

        return removed;
    }

    /**
     * Get all elements in the quadtree
     * @param n The node we are processing now
     * @return A list of all elements at this node
     */
    private ArrayList<Note> GetAll(Node n)
    {
        ArrayList<Note> notes = new ArrayList<Note>();

        if(n == null)
            return notes;

        // Get all elements from the children nodes
        notes.addAll(GetAll(n.bottomLeft));
        notes.addAll(GetAll(n.bottomRight));
        notes.addAll(GetAll(n.topLeft));
        notes.addAll(GetAll(n.topRight));
        notes.addAll(n._objects);
        return notes;
    }

    /**
     * Remove a particular element from the tree
     * @param note The element to be removed
     * @param node The node we are checking to see if it has this element
     */
    private void Remove(Note note, Node node)
    {
        if(note == null)
            return;

        if (!node._bounds.contains(note.GetPosition().x, note.GetPosition().y))
            return;

        // Check if it is in the children
        if(node.bottomLeft != null)
        {
            Remove(note, node.bottomLeft);
            Remove(note, node.bottomRight);
            Remove(note, node.topRight);
            Remove(note, node.topLeft);
        }
        else
            node._objects.remove(note);
    }
}
