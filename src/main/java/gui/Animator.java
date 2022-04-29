package gui;

import ecs.Component;
import java.util.HashMap;

public class Animator extends Component {
    public HashMap<String, Animation> animations = new HashMap<>();

    public void addAnimation (Animation animation) {
        if (animations.containsKey(animation.name)) {
            assert false : "Animator: Animation '" + animation.name + "' already exists!";
        } else {
            animations.put(animation.name, animation);
        }
    }

    public void animate (String name) {
        if (animations.containsKey(name)) {
            stopCurrentAnimations();
            animations.get(name).startAnimation();
        } else {
            assert false : "Animator: Animation '" + name + "' doesn't exist!";
        }
    }

    public void stopCurrentAnimations () {
        for (Animation animation : animations.values()) {
            if (animation.active) {
                animation.cancelAnimation();
            }
        }
    }
}
