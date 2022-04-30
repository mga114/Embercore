package gui;

import org.joml.Vector2f;

public class GUIConstraint {
    private ConstraintType[] types;

    private Vector2f position;
    private Vector2f size;

    public GUIConstraint () {
        position = new Vector2f();
        size = new Vector2f();
        types = new ConstraintType[4];
    }

    public void setX (ConstraintType type, float x) {
        position.x = x;
        types[0] = type;
    }

    public void setY (ConstraintType type, float y) {
        position.y = y;
        types[1] = type;
    }

    public void setWidth (ConstraintType type, float width) {
        size.x = width;
        types[2] = type;
    }

    public void setHeight (ConstraintType type, float height) {
        size.y = height;
        types[3] = type;
    }

    public ConstraintType[] getTypes () {
        return this.types;
    }

    public Vector2f getPosition () {return this.position;}

    public Vector2f getSize () {return this.size;}
}
