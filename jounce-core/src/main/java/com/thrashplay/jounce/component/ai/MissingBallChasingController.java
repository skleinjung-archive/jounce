package com.thrashplay.jounce.component.ai;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.luna.api.component.GameObject;
import com.thrashplay.luna.api.engine.EntityManager;
import com.thrashplay.luna.api.math.Random;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class MissingBallChasingController extends PerfectBallChasingPaddleController {

    private Jounce jounce;
    private EntityManager entityManager;

    private long delayEndTime = -1;

    public MissingBallChasingController(Jounce jounce, EntityManager entityManager) {
        super(jounce, entityManager);
        this.jounce = jounce;
        this.entityManager = entityManager;
    }

    @Override
    protected int getNextDestination(GameObject gameObject) {
        int nextDestination = super.getNextDestination(gameObject);
        nextDestination += Random.getInteger(-60, 60);
        return nextDestination;
    }
}
