package com.thrashplay.jounce.entity.ai;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.entity.Ball;
import com.thrashplay.jounce.entity.Paddle;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class BalancedAiPaddleController extends RandomBehaviorPaddleController {
    public BalancedAiPaddleController(Jounce jounce, Paddle paddle, Ball ball) {
        super(paddle, ball);

//        addBehavior(new MoveUpBehavior(), 1, 100, 500);
//        addBehavior(new MoveDownBehavior(), 1, 100, 500);
//        addBehavior(new DoNothingBehavior(), 1, 100, 500);

        addBehavior(new ReactionTimeLimitedBallChasingBehavior(jounce, 100, 350), 65, 100, 2000);
//        addBehavior(new AvoidTheBallBehavior(250), 5, 50, 250);
//        addBehavior(new DoNothingBehavior(), 30, 250, 500);
    }
}
