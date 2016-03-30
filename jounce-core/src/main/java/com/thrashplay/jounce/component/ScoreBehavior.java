package com.thrashplay.jounce.component;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.luna.api.engine.GameObject;
import com.thrashplay.luna.api.component.Position;
import com.thrashplay.luna.api.component.UpdateableComponent;
import com.thrashplay.luna.api.engine.GameObjectManager;
import com.thrashplay.luna.api.geom.Rectangle;
import com.thrashplay.luna.api.sound.SoundEffect;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class ScoreBehavior implements UpdateableComponent {

    private Jounce jounce;
    private GameObjectManager gameObjectManager;
    private SoundEffect outOfBoundsSound;

    public ScoreBehavior(Jounce jounce, GameObjectManager gameObjectManager) {
        this.jounce = jounce;
        this.gameObjectManager = gameObjectManager;
        outOfBoundsSound = jounce.getSoundManager().createSoundEffect("sfx/out_of_bounds.mp3");
    }

    @Override
    public void update(GameObject gameObject, float delta) {
        Rectangle gameBoardDimensions = jounce.getGameBoardDimensions();

        if (!alreadyScored(gameObject)) {
            Position position = gameObject.getComponent(Position.class);
            if (position.getX() < gameBoardDimensions.getLeft()) {
                jounce.addPointForRightPlayer();
                gameObject.addComponent(new BallFadeOutAnimator(gameObjectManager));
                outOfBoundsSound.play(1.0f);
            }

            if (position.getX() > gameBoardDimensions.getRight()) {
                jounce.addPointForLeftPlayer();
                gameObject.addComponent(new BallFadeOutAnimator(gameObjectManager));
                outOfBoundsSound.play(1.0f);
            }
        }
    }

    private boolean alreadyScored(GameObject gameObject) {
        return gameObject.getComponent(BallFadeOutAnimator.class) != null;
    }
}
