package ecs;



class Position extends Component {
    public float x, y, z;
}



class Velocity extends Component {
    public float x, y, z;
}



class Image extends Component {
    public int imageId;
}


public class Test {

    public static void test() {
        EntityType physicsEnt = new EntityType();
        physicsEnt.add(Position.class);
        physicsEnt.add(Velocity.class);

        var e = physicsEnt.create();
        var position = e.<Position>get(Position.class);
        var vel = e.<Velocity>get(Velocity.class);


        EntityType drawEnt = new EntityType();
        drawEnt.add(Position.class);
        drawEnt.add(Image.class);

        EntityType bothEnt = new EntityType();
        bothEnt.add(Position.class);
        bothEnt.add(Velocity.class);
        bothEnt.add(Image.class);


        int nPhys = 100;
        int nDraw = 250;
        int nBoth = 50;

        for (int i=0; i<nPhys; i++) {
            physicsEnt.create();
        }

        for (int i=0; i<nDraw; i++) {
            drawEnt.create();
        }

        for (int i=0; i<nBoth; i++) {
            bothEnt.create();
        }

        Group drawGroup = new Group(Position.class, Image.class);

        Group physicsGroup = new Group(Position.class, Velocity.class);

        assert drawGroup.size() == (nDraw + nBoth);
        assert physicsGroup.size() == (nPhys + nBoth);

        System.out.println("Tests completed and passed.");
    }
}


