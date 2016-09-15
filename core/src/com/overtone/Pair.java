package com.overtone;

/**
 * Creates a pair of values to store two values.
 * Created by trevor on 2016-09-10.
 */
public class Pair<F, S>
{
    // Constructor
    public Pair(F f, S s)
    {
        first = f;
        second = s;
    }

    /** The first element in the pair.*/
    public F first;

    /** The second element in the pair.*/
    public S second;
}

