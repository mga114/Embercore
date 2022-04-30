#Animations
Animations are used to manipulate Entities' Transform and Colour components based off of the desired duration, delay, and Easing function.

````java
import animation.*;
import animation.easing.Easing;
import ecs.EntityType;

public class AnimatedEntityFactory {

    public static generateAnimatableEntity() {
        //Define Entity type and entity. Note that the three classes below are required for the Animator to work safely
        EntityType animatableType = new EntityType().add(Transform.class).add(SpriteRenderer.class).add(Animator.class);
        Entity ent = animatableType.create();
        //...
        //Set entities component values
        //...
        
        //Define the transition
        //This will create a new transition that will change the X component of the entity by 100.0 units, with the smoothing curve given by Easing.easeOutBounce()
        Transition transition = Transition.newTransition().xDriver(Easing.easeOutBounce(), 100.0f);
        
        //Get the Animator for the entity
        Animator animator = ent.get(Animator.class);

        //Create and add the animation to the entities Animator
        float duration = 2.0f;
        float delay = 1.5f;
        animator.addAnimation(new Animation("Enter Animation", transition, duration, delay, ent));
        
        //Start the animation
        animator.animate("Enter Animation");
    }
}
````

Each Entity can only have one Animation occurring at the same time. If another one is started when the current Animation is still active, the Animator component will stop and finish the current 
animation and start the new one.

The Animations are controlled by an AnimationSystem, which is required in all scenes where animations are present by defining:
```java
AnimationSystem animSystem = new AnimationSystem();
```

##Animator Component:
Every Entity that should be animated should have an Animator component predefined. (See ECS.md). This component is unique to each Entity and holds the data for the animations belonging to that Entity, as well as the name of the animation. 

##Transitions 
Transitions can be shared between animations, or unique. They are the defined way that the Entity will react to the animation. Shown below is a table of each of the parts that make up a transition, and what they do. Note that any or all of these can be null. 

| Method Driver | Effect of Method |
| --- | --- |
| xDriver | Transform.position.x |
| yDriver | Transform.position.y |
| wDriver | Transform.scale.x |
| hDriver | Transform.scale.y |
| alphaDriver | color.a |

Adding any or all of these Method Drivers to the base transition will affect the corresponding value of the entity. The way that the value changes is determined by an implementation of Curve, with some simple easing functions defined in the Easing class.
This is the first value that the Method Driver will take in, with the second being the required change in the corresponding value.
See [Easing functions](https://easings.net/) for more details about the defined Curves.