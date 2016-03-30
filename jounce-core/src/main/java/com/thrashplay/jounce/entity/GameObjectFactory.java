package com.thrashplay.jounce.entity;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.component.*;
import com.thrashplay.luna.api.component.Collider;
import com.thrashplay.luna.api.component.Movement;
import com.thrashplay.luna.api.component.Position;
import com.thrashplay.luna.api.component.UpdateableComponent;
import com.thrashplay.luna.api.engine.GameObject;
import com.thrashplay.luna.api.engine.GameObjectManager;
import com.thrashplay.luna.api.geom.Rectangle;
import com.thrashplay.luna.api.math.Angles;
import com.thrashplay.luna.api.math.Random;
import com.thrashplay.luna.api.sound.SoundManager;
import com.thrashplay.luna.collision.StaticBoundingBoxes;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class GameObjectFactory {
    public static final String ID_BALL = "ball";
    public static final String ID_TOP_WALL = "topWall";
    public static final String ID_BOTTOM_WALL = "bottomWall";
    public static final String ID_LEFT_PADDLE = "leftPaddle";
    public static final String ID_RIGHT_PADDLE = "rightPaddle";

    private Jounce jounce;
    private GameObjectManager gameObjectManager;

    public GameObjectFactory(Jounce jounce, GameObjectManager gameObjectManager) {
        this.jounce = jounce;
        this.gameObjectManager = gameObjectManager;
    }

    public GameObject createBall() {
        GameObject ball = new GameObject(ID_BALL);

        Rectangle gameBoardDimensions = jounce.getGameBoardDimensions();
        int diameter = (int) (20 * jounce.getWidthScalar());
        int radius = diameter / 2;

        Position position = new Position(0, 0, diameter, diameter);
        position.setY(Random.getInteger(gameBoardDimensions.getTop() + 15, gameBoardDimensions.getBottom() - 15));

        float speed = 20 * jounce.getWidthScalar();
        float angle;
        if (jounce.getLastPlayerToScore() == Player.Left) {
            // serve from the right
            position.setX(Random.getInteger(gameBoardDimensions.getCenterX() + 50 + radius, gameBoardDimensions.getRight() - 50));
//            angle = Random.getInteger(2) == 0 ? 135 : 225;
            angle = Random.getInteger(90) + 135;
        } else {
            // serve from the left
            position.setX(Random.getInteger(gameBoardDimensions.getLeft() + 50 + radius, gameBoardDimensions.getCenterX() - 50));
//            angle = Random.getInteger(2) == 0 ? 45 : 315;
            angle = Angles.normalize(45 - Random.getInteger(90));
        }
        double angleInRadians = Angles.toRadians(angle);

        SoundManager soundManager = jounce.getSoundManager();

        ball.addComponent(position);
        ball.addComponent(new Movement((float) (speed * Math.cos(angleInRadians)), -(float) (speed * Math.sin(angleInRadians))));
        ball.addComponent(new Collider(CollisionCategory.BALL, true));
        ball.addComponent(new StaticBoundingBoxes(new Rectangle(0, 0, diameter, diameter)));
        ball.addComponent(new CircleRenderer(0xffff0000, true));
        ball.addComponent(new BallCollisionHandler(soundManager.createSoundEffect("sfx/paddle_hit.mp3"), soundManager.createSoundEffect("sfx/wall_hit.mp3")));
        ball.addComponent(new ScoreBehavior(jounce, gameObjectManager));

        return ball;
    }

    public GameObject createTopWall() {
        Rectangle gameBounds = jounce.getGameBoardDimensions();

        GameObject topWall = new GameObject("topWall");
        topWall.addComponent(new Position(gameBounds.getLeft(), gameBounds.getTop(), gameBounds.getWidth(), 5));
        topWall.addComponent(new RectangleRenderer(0xffffffff, true));
        topWall.addComponent(new Collider(CollisionCategory.TOP_WALL, false));
        topWall.addComponent(new StaticBoundingBoxes(new Rectangle(0, 0, gameBounds.getWidth(), 5)));
        return topWall;
    }

    public GameObject createBottomWall() {
        Rectangle gameBounds = jounce.getGameBoardDimensions();

        GameObject bottomWall = new GameObject("bottomWall");
        bottomWall.addComponent(new Position(gameBounds.getLeft(), gameBounds.getBottom() - 5, gameBounds.getWidth(), 5));
        bottomWall.addComponent(new RectangleRenderer(0xffffffff, true));
        bottomWall.addComponent(new Collider(CollisionCategory.BOTTOM_WALL, false));
        bottomWall.addComponent(new StaticBoundingBoxes(new Rectangle(0, 0, gameBounds.getWidth(), 5)));
        return bottomWall;
    }

    public GameObject createLeftPaddle(UpdateableComponent controller) {
        return createPaddle(ID_LEFT_PADDLE, CollisionCategory.LEFT_PADDLE, jounce.getGameBoardDimensions().getLeft() + 8, controller);
    }

    public GameObject createRightPaddle(UpdateableComponent controller) {
        return createPaddle(ID_RIGHT_PADDLE, CollisionCategory.RIGHT_PADDLE, jounce.getGameBoardDimensions().getRight() - 8 - 15, controller);
    }

    private GameObject createPaddle(String id, int collisionCategory, int positionX, UpdateableComponent controller) {
        int paddleHeight = (int) (150 * jounce.getHeightScalar());

        GameObject paddle = new GameObject(id);
        paddle.addComponent(new Position(positionX, 50, 15, paddleHeight));
        Movement movement = new Movement();
        movement.setMaximumVelocityX(0);
        movement.setMaximumVelocityY(25 * jounce.getWidthScalar());
        paddle.addComponent(movement);
        paddle.addComponent(new RectangleRenderer(0xffffffff, true));
        paddle.addComponent(new Collider(collisionCategory, true));
        paddle.addComponent(new StaticBoundingBoxes(new Rectangle(0, 0, 15, paddleHeight)));
        paddle.addComponent(new PaddleCollisionHandler());
        paddle.addComponent(controller);
        return paddle;
    }
}
