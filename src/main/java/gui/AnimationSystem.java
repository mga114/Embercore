package gui;

import embercore.Window;
import events.EventID;
import events.Events;

import java.util.ArrayList;

public class AnimationSystem {
    public static ArrayList<Animation> animations = new ArrayList<>();

    public AnimationSystem () {
        Events.on(EventID.UPDATE, (dt) -> {
            update(Window.getDeltaTime());
        });
    }

    public void update (float dt) {
        for (int i = 0; i < animations.size(); i++) {
            animations.get(i).update(dt);
        }
    }
}
