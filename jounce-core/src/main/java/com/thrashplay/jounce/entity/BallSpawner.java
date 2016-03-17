package com.thrashplay.jounce.entity;

import com.thrashplay.luna.api.component.GameObject;
import com.thrashplay.luna.api.engine.EntityManager;
import com.thrashplay.luna.api.engine.Updateable;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class BallSpawner implements Updateable {
    private EntityManager entityManager;
    private GameObjectFactory gameObjectFactory;
    private long respawnDelay;

    private long respawnTime = -1;

    public BallSpawner(EntityManager entityManager, GameObjectFactory gameObjectFactory) {
        this(entityManager, gameObjectFactory, 1000);
    }

    public BallSpawner(EntityManager entityManager, GameObjectFactory gameObjectFactory, long respawnDelay) {
        this.entityManager = entityManager;
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
    public void update(long delta) {
        if (shouldSpawnBall()) {
            entityManager.addEntity(gameObjectFactory.createBall());
            respawnTime = -1;
        } else if (respawnTime == -1 && !ballExists()) {
            respawnTime = System.currentTimeMillis() + respawnDelay;
        }
    }

    private boolean ballExists() {
        for (Object entity : entityManager.getEntities()) {
            if (entity instanceof GameObject) {
                if (GameObjectFactory.ID_BALL.equals(((GameObject) entity).getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean shouldSpawnBall() {
        return respawnTime != -1 && respawnTime < System.currentTimeMillis();
    }
}
