package com.thrashplay.jounce.component.ai;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.luna.api.engine.GameObject;
import com.thrashplay.luna.api.engine.GameObjectManager;
import com.thrashplay.luna.api.math.Random;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class MissingBallChasingController extends PerfectBallChasingPaddleController {

    private Jounce jounce;
    private GameObjectManager gameObjectManager;

    private long delayEndTime = -1;

    public MissingBallChasingController(Jounce jounce, GameObjectManager gameObjectManager) {
        super(jounce, gameObjectManager);
        this.jounce = jounce;
        this.gameObjectManager = gameObjectManager;
    }

    @Override
    protected int getNextDestination(GameObject gameObject) {
        int nextDestination = super.getNextDestination(gameObject);
        nextDestination += Random.getInteger(-60, 60);
        return nextDestination;
    }
}
