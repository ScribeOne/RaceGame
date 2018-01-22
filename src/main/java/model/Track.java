package model;

/**
 * Class to represent the race track with 2 Ellipses.
 */
public class Track {

    Vector2D center;
    Vector2D innerRadius;
    Vector2D outerRadius;

    public Track(Vector2D center, Vector2D innerRadius, Vector2D outerRadius) {
        this.center = center;
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
    }

    public Vector2D getInnerCorner() {
        Vector2D helper = new Vector2D();
        return helper;
    }

    public Vector2D getCenter() {
        return center;
    }

    public void setCenter(Vector2D center) {
        this.center = center;
    }

    public Vector2D getInnerRadius() {
        return innerRadius;
    }

    public void setInnerRadius(Vector2D innerRadius) {
        this.innerRadius = innerRadius;
    }

    public Vector2D getOuterRadius() {
        return outerRadius;
    }

    public void setOuterRadius(Vector2D outerRadius) {
        this.outerRadius = outerRadius;
    }
}
