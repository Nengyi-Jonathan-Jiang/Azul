package game.backend;

import engine.core.GameObject;
import engine.core.Vec2;

import java.util.ArrayList;
import java.util.List;

public class Middle {
    private final List<Factory> factories;
    private final Center center;

    private final GameObject gameObject;

    public Middle(int numFactories){
        gameObject = new GameObject();
        factories = new ArrayList<>();
        center = new Center();
        for(int i = 0; i < numFactories; i++){
            Factory fact = new Factory();
            factories.add(fact);
            gameObject.addChild(fact.getGameObject());
        }

        for(int i = 0; i < numFactories; i++){
            Factory fact = factories.get(i);

            Vec2 offset = new Vec2(
                    Math.sin(2 * Math.PI * i / numFactories),
                    Math.cos(2 * Math.PI * i / numFactories)
            );

            fact.getGameObject().setPosition(offset.scaledBy(250));
        }

        gameObject.addChild(center.getGameObject());
    }

    public void positionFactories(double rot){
        int numFactories = factories.size();
        for(int i = 0; i < numFactories; i++){
            Factory fact = factories.get(i);

            Vec2 offset = new Vec2(
                    Math.sin(2 * Math.PI * (i + rot) / numFactories),
                    Math.cos(2 * Math.PI * (i + rot) / numFactories)
            );

            fact.getGameObject().setPosition(offset.scaledBy(250));
        }
    }

    public List<Factory> getFactories(){
        return factories;
    }
    public Center getCenter(){
        return center;
    }

    public GameObject getGameObject(){
        return gameObject;
    }
}