package com.thrashplay.jounce.entity.ai;

import com.thrashplay.jounce.entity.Ball;
import com.thrashplay.jounce.entity.Paddle;
import com.thrashplay.luna.api.engine.Updateable;
import com.thrashplay.luna.api.graphics.Graphics;
import com.thrashplay.luna.math.Random;

import java.util.LinkedList;
import java.util.List;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class RandomBehaviorPaddleController implements Updateable {

    public static interface Behavior {
        public void updatePaddle(Paddle paddle, Ball ball);
    }

    private List<BehaviorConfiguration> behaviors = new LinkedList<BehaviorConfiguration>();

    private Paddle paddle;
    private Ball ball;

    private Behavior currentBehavior;
    private long behaviorEndTime;

    public RandomBehaviorPaddleController(Paddle paddle, Ball ball) {
        this.paddle = paddle;
        this.ball = ball;
    }

    public void addBehavior(Behavior behavior, int weight, int minimumExecutionDuration, int maximumExecutionDuration) {
        behaviors.add(new BehaviorConfiguration(behavior, weight, minimumExecutionDuration, maximumExecutionDuration));
    }

    @Override
    public void update(Graphics graphics) {
        if (System.currentTimeMillis() > behaviorEndTime) {
            changeBehavior();
        }
        currentBehavior.updatePaddle(paddle, ball);
    }

    private void changeBehavior() {
        int totalWeight = getTotalWeight();
        int behaviorRoll = Random.getInteger(totalWeight);
        int currentWeight = 0;
        for (BehaviorConfiguration behaviorConfiguration : behaviors) {
            currentWeight += behaviorConfiguration.weight;
            if (behaviorRoll < currentWeight) {
                System.out.println("Changing behavior to: " + behaviorConfiguration.behavior);
                currentBehavior = behaviorConfiguration.behavior;
                behaviorEndTime = System.currentTimeMillis()
                        + behaviorConfiguration.minimumExecutionDuration
                        + Random.getInteger(behaviorConfiguration.maximumExecutionDuration - behaviorConfiguration.minimumExecutionDuration);

                break;
            }
        }
    }

    private int getTotalWeight() {
        int totalWeight = 0;
        for (BehaviorConfiguration behaviorConfiguration : behaviors) {
            totalWeight += behaviorConfiguration.weight;
        }
        return totalWeight;
    }

    private static class BehaviorConfiguration {
        private Behavior behavior;
        private int weight;
        private int minimumExecutionDuration;
        private int maximumExecutionDuration;

        public BehaviorConfiguration(Behavior behavior, int weight, int minimumExecutionDuration, int maximumExecutionDuration) {
            this.behavior = behavior;
            this.weight = weight;
            this.minimumExecutionDuration = minimumExecutionDuration;
            this.maximumExecutionDuration = maximumExecutionDuration;
        }
    }
}
