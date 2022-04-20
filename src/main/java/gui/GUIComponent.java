package gui;

import embercore.Window;
import org.joml.Vector2f;

public class GUIComponent {
     private GUIComponent parent;
     private GUIConstraint constraint;
     private double x;
     private double y;
     private double width;
     private double height;

     private Vector2f position;

    public GUIComponent() {
    }

    public float getX () {
        return (float)(x * 2) - 1;
    }

    public float getY () {
        return (float)(y * 2) - 1;
    }

    public float getWidth () {
        return (float) width * 2;
    }

    public float getHeight () {return (float)(height * 2);}

    public float rawX () {return (float)x;}

    public float rawY () {return (float) y;}

    public float rawWidth() {return (float)(width);}

    public float rawHeight () {return (float)(height);}

    public void setParent (GUIComponent parent) {
        this.parent = parent;
    }

    public void addChild (GUIComponent child) {
        child.setParent(this);
    }

    public void setX (ConstraintType type, float x) {
        switch (type) {
            case RELATIVE:
                if (parent != null) {
                    this.x = (x * parent.rawWidth()) + parent.rawX();
                } else {
                    this.x = x;
                }
                break;
            case PIXEL:
                this.x = x / Window.getWidth();
                break;
            case GLOBAL_RELATIVE:
                this.x = x;
                break;
        }
    }

    public void setY (ConstraintType type, float y) {
        switch (type) {
            case RELATIVE:
                if (parent != null) {
                    this.y = (y * parent.rawHeight()) + parent.rawY();
                } else {
                    this.y = y;
                }
                break;
            case PIXEL:
                this.y = y / Window.getHeight();
                break;
            case GLOBAL_RELATIVE:
                this.y = y;
                break;
        }
    }

    public void setWidth (ConstraintType type, float width) {
        switch (type) {
            case RELATIVE:
                if (parent != null) {
                    this.width = parent.rawWidth() * width;
                } else {
                    this.width = width;
                }
                break;
            case PIXEL:
                this.width = width / Window.getWidth();
                break;
            case GLOBAL_RELATIVE:
                this.width = width;
                break;
        }
    }

    public void setHeight (ConstraintType type, float height) {
        switch (type) {
            case RELATIVE:
                if (parent != null) {
                    this.height = parent.rawHeight() * height;
                } else {
                    this.height = height;
                }
                break;
            case PIXEL:
                this.height = height / Window.getHeight();
                break;
            case GLOBAL_RELATIVE:
                this.height = height;
                break;
        }
    }
}
