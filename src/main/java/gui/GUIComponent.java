package gui;

import embercore.Window;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;

public class GUIComponent {
     private GUIComponent parent;
     private ArrayList<GUIComponent> children;
     protected ConstraintType[] types;
     private GUIConstraint constraint;

     private Vector2f position = new Vector2f();
     private Vector2f size = new Vector2f();
     private Vector4f color;

    public GUIComponent(GUIConstraint constraint) {
        this.constraint = constraint;
        this.color = new Vector4f(0.2f, 0.2f, 0.2f, 0.8f);
        children = new ArrayList<>();
        getValues ();
    }

    public float getX () {
        return (position.x * 2) - 1;
    }

    public float getY () { return (position.y * 2) - 1; }

    public float getWidth () {
        return size.x * 2;
    }

    public float getHeight () {return (size.y * 2);}

    public float rawX () {return position.x;}

    public float rawY () {return position.y;}

    public float rawWidth() {return size.x;}

    public float rawHeight () {return size.y;}

    public void setParent (GUIComponent parent) {
        this.parent = parent;
    }

    public void addChild (GUIComponent child) {
        children.add(child);
        child.setParent(this);
        child.getValues();
    }

    public void setX (ConstraintType type, float x) {
        types[0] = type;
        switch (type) {
            case RELATIVE:
                if (parent != null) {
                    this.position.x = (x * parent.rawWidth()) + parent.rawX();
                } else {
                    this.position.x = x;
                }
                break;
            case PIXEL:
                this.position.x = x / Window.getWidth();
                break;
            case GLOBAL_RELATIVE:
                this.position.x = x;
                break;
        }
        updateChildren();
    }

    public void setY (ConstraintType type, float y) {
        types[1] = type;
        switch (type) {
            case RELATIVE:
                if (parent != null) {
                    this.position.y = (y * parent.rawHeight()) + parent.rawY();
                } else {
                    this.position.y = y;
                }
                break;
            case PIXEL:
                this.position.y = y / Window.getHeight();
                break;
            case GLOBAL_RELATIVE:
                this.position.y = y;
                break;
        }
        updateChildren();
    }

    public void setWidth (ConstraintType type, float width) {
        types[2] = type;
        switch (type) {
            case RELATIVE:
                if (parent != null) {
                    this.size.x = parent.rawWidth() * width;
                } else {
                    this.size.x = width;
                }
                break;
            case PIXEL:
                this.size.x = width / Window.getWidth();
                break;
            case GLOBAL_RELATIVE:
                this.size.x = width;
                break;
        }
        updateChildren();
    }

    public void setHeight (ConstraintType type, float height) {
        types[3] = type;
        switch (type) {
            case RELATIVE:
                if (parent != null) {
                    System.out.println("height: " + height);
                    this.size.y = parent.rawHeight() * height;
                } else {
                    this.size.y = height;
                }
                break;
            case PIXEL:
                this.size.y = height / Window.getHeight();
                break;
            case GLOBAL_RELATIVE:
                this.size.y = height;
                break;
        }
        updateChildren();
    }

    protected void getValues () {
        this.position.set(constraint.getPosition());
        this.size.set(constraint.getSize());
        types = constraint.getTypes();

        setX (types[0], position.x);
        setY (types[1], position.y);
        setWidth (types[2], size.x);
        setHeight (types[3], size.y);
    }

    private void updateChildren() {
        for (GUIComponent child : children) {
            child.getValues();
            //System.out.println(child.getHeight());
        }
    }

    public Vector4f getColor () {
        return this.color;
    }
}
