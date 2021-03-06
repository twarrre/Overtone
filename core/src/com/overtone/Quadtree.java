package com.overtone;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.overtone.Notes.OvertoneNote;
import java.util.ArrayList;

/**
 * Quadtree data structure used to sort the notes in each quadrant.
 * Created by trevor on 2016-07-01.
 */
public class Quadtree
{
    /**The number of levels in the quadtree*/
    public static final int NUM_LEVELS = 2;

    /**Represents a node in the tree*/
    protected class Node
    {
        public Node              topLeft;     // Top left quad
        public Node              topRight;    // Top right quad
        public Node              bottomLeft;  // Bottom left quad
        public Node              bottomRight; // Bottom right quad
        public Node              parent;      // The parent of this node
        private final Rectangle _bounds;      // The bounds of the quad
        private ArrayList<OvertoneNote> _objects;     // The objects stored in this quad

        /**
         * Constructor
         * @param bounds The bounds of the quad
         * @param parent Reference to the parent node of this one
         */
        public Node(Rectangle bounds, Node parent)
        {
            _bounds     = bounds;
            _objects    = new ArrayList<OvertoneNote>();
            this.parent = parent;
        }
    }

    private final Node              _root;  // The root of the tree
    private ArrayList<OvertoneNote> _notes; // Storage when getting notes from all of the nodes

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
    public boolean Insert(OvertoneNote n)
    {
        return Insert(n, _root);
    }

    /**
     * Gets all elements in a particular quad where the point is located
     * @param point The point used to get the elements at a particular quad
     * @return
     */
    public ArrayList<OvertoneNote> Get(Vector2 point)
    {
        _notes = new ArrayList<OvertoneNote>();
        Get(point, _root);
        return _notes;
    }

    /**
     * Removes a particular element from the quadtree
     * @param n The element to be removed
     * @return Returns true if the item was removed, false otherwise
     */
    public boolean Remove(OvertoneNote n)
    {
        return Remove(n, _root);
    }

    /**
     * Updates every element in the quadtree
     * @param deltaTime The time since the last frame
     */
    public ArrayList<OvertoneNote> Update(float deltaTime)
    {
        return Update(deltaTime, _root);
    }

    /**
     * Gets all of the elements in the quadtree into a list
     * @return A list of all elements in the tree
     */
    public ArrayList<OvertoneNote> GetAll()
    {
        ArrayList<OvertoneNote> notes = new ArrayList<OvertoneNote>();
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

        // if we reached the number of desired levels then stop partitioning, else partition
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

            node.topLeft            = new Node(new Rectangle(topLeftX,     topLeftY,     childWidthLeft,  childHeightTop),    node);
            node.topRight           = new Node(new Rectangle(topRightX,    topRightY,    childWidthRight, childHeightTop),    node);
            node.bottomLeft         = new Node(new Rectangle(bottomLeftX,  bottomLeftY,  childWidthLeft,  childHeightBottom), node);
            node.bottomRight        = new Node(new Rectangle(bottomRightX, bottomRightY, childWidthRight, childHeightBottom), node);

            level++;

            // Partition all of the children nodes
            Partition(level, node.topLeft);
            Partition(level, node.topRight);
            Partition(level, node.bottomLeft);
            Partition(level, node.bottomRight);
        }
    }

    /**
     * Recursively tries to insert the element into the highest level node
     * @param note The element to be inserted
     * @param node The node that the element is trying to be inserted into
     * @return True is successfully inserted, false otherwise
     */
    private boolean Insert(OvertoneNote note, Node node)
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
    private ArrayList<OvertoneNote> Update(float deltaTime, Node node)
    {
        // If the node is not visible anymore, then remove it from the tree
        ArrayList<OvertoneNote> toBeRemoved = new ArrayList();

        if (node == null)
            return toBeRemoved;

        // Update all of the elements at this node
        for (OvertoneNote n : node._objects)
        {
            n.Update(deltaTime);
            if(!n.IsVisible())
                toBeRemoved.add(n);
        }

        // Update all nodes in the children nodes
        toBeRemoved.addAll(Update(deltaTime, node.topLeft));
        toBeRemoved.addAll(Update(deltaTime, node.topRight));
        toBeRemoved.addAll(Update(deltaTime, node.bottomLeft));
        toBeRemoved.addAll(Update(deltaTime, node.bottomRight));

        // If there are elements to be removed, remove them now
        for(OvertoneNote n : toBeRemoved)
            Remove(n, node);

        return toBeRemoved;
    }

    /**
     * Get all elements in the quadtree
     * @param n The node we are processing now
     * @return A list of all elements at this node
     */
    private ArrayList<OvertoneNote> GetAll(Node n)
    {
        ArrayList<OvertoneNote> notes = new ArrayList<OvertoneNote>();

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
     * @return True if the object was removed, false if it was not there
     */
    private boolean Remove(OvertoneNote note, Node node)
    {
        if(note == null)
            return false;

        if (!node._bounds.contains(note.GetPosition().x, note.GetPosition().y))
            return false;

        // Check if it is in the children
        if(node.bottomLeft != null)
        {
            boolean b1 = Remove(note, node.bottomLeft);
            boolean b2 = Remove(note, node.bottomRight);
            boolean b3 = Remove(note, node.topRight);
            boolean b4 = Remove(note, node.topLeft);

            if(!b1 && !b2 && !b3 && !b4)
                return false;
            else
                return true;
        }
        else
        {
            if(node._objects.remove(note))
            {
                note.SetVisibility(false);
                return true;
            }
            else
                return false;
        }
    }
}
