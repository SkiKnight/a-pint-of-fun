package game.utility.components;

public class Collider {
    
    public float bounds[];
    /*{rightBound, topBound, leftBound, lowBound} *"distance"(magnitude) from Origin of the gameObject
    *also is the approx boundary
    */
    public Collider(float rightBound, float topBound, float leftBound, float lowBound)
    {
        bounds = new float[4];
        bounds[0] = rightBound;
        bounds[1] = topBound;
        bounds[2] = leftBound;
        bounds[3] = lowBound;
    }
}
