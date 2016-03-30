package com.thrashplay.jounce.entity;

import com.thrashplay.luna.api.engine.GameObject;
import com.thrashplay.luna.api.engine.GameObjectManager;
import com.thrashplay.luna.api.engine.Updateable;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class BallSpawner implements Updateable {
    private GameObjectManager gameObjectManager;
    private GameObjectFactory gameObjectFactory;
    private long respawnDelay;

    private long respawnTime = -1;

    public BallSpawner(GameObjectManager gameObjectManager, GameObjectFactory gameObjectFactory) {
        this(gameObjectManager, gameObjectFactory, 1000);
    }

    public BallSpawner(GameObjectManager gameObjectManager, GameObjectFactory gameObjectFactory, long respawnDelay) {
        this.gameObjectManager = gameObjectManager;
        this.gameObjectFactory = gameObjectFactory;
        this.respawnDelay = respawnDelay;
    }

    public long getRespawnDelay() {
        return respawnDelay;
    }

    public void setRespawnDelay(long respawnDelay) {
        this.respawnDelay = respawnDelay;
    }

    @Override
    public void update(float delta) {
        if (shouldSpawnBall()) {
            gameObjectManager.register(gameObjectFactory.createBall());
            respawnTime = -1;
        } else if (respawnTime == -1 && !ballExists()) {
            respawnTime = System.currentTimeMillis() + respawnDelay;
        }
    }

    private boolean ballExists() {
        for (GameObject gameObject : gameObjectManager.getGameObjects()) {
            if (GameObjectFactory.ID_BALL.equals(gameObject.getId())) {
                return true;
            }
        }
        return false;
    }

    private boolean shouldSpawnBall() {
        return respawnTime != -1 && respawnTime < System.currentTimeMillis();
    }
}
