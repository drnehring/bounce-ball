package com.david.bounceball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.david.bounceball.screens.GameScreen;

import java.util.List;

/**
 * Created by David on 10/15/2016.
 */
public class Assets {

    static public Skin uiskin = new Skin(Gdx.files.internal("uiskin.json"));
    static public CommandTextField command = new CommandTextField("", uiskin);

    private static String ballStyle = "_in_fancy";
    private static String basicWallStyle = "_hole";
    private static String fixedWallStyle = "_hole";
    private static String reactionWallStyle = "_hole";
    private static String spinningWallStyle = "_hole";
    private static String goalStyle = "_in";
    private static String keyStyle = "";
    private static String spikeStyle = "";

    //The altas containing all the sprites
    private static TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("game_elements.txt"));

    public static Sprite getSprite(String spriteName) {
        return textureAtlas.createSprite(spriteName);
    }

    public static Sprite getSprite(String mainName, Object target) {
        return textureAtlas.createSprite(getSpriteFullName(mainName, target));
    }

    public static Sprite getSprite(String mainName, Object target, int index) {
        return textureAtlas.createSprite(getSpriteFullName(mainName, target), index);
    }

    //A sprite's full name is its main name together with its style
    private static String getSpriteFullName(String mainName, Object target) {
        if (target instanceof Ball) {
            if (ballStyle.equals("_var")) {
                return mainName + "_in_fancy";
            } else {
                return mainName + ballStyle;
            }
        } else if (target instanceof Wall) {
            //_line style weak walls have not been created
            if (((Wall) target).strength == 0) {
                if (target instanceof BasicWall) {
                    return mainName + basicWallStyle;
                } else if (target instanceof FixedWall) {
                    return mainName + fixedWallStyle;
                } else if (target instanceof ReactionWall) {
                    return mainName +reactionWallStyle;
                } else if (target instanceof SpinningWall) {
                    return mainName + spinningWallStyle;
                }
            } else {
                return mainName + "_hole";
            }
        } else if (target instanceof Goal) {
            return mainName + goalStyle;
        } else if (target instanceof Key) {
            return mainName + keyStyle;
        } else if (target instanceof Spike) {
            return mainName + spikeStyle;
        }
        return null;
    }


    public static void setStyle(ScreenWithListener screen, String target, String style) {
        if (target.equals("Wall")) {
            basicWallStyle = style;
            fixedWallStyle = style;
            reactionWallStyle = style;
            spinningWallStyle = style;
            if (screen instanceof GameScreen) {
                GameScreen gameScreen = (GameScreen) screen;
                List<Wall> walls = gameScreen.getLevel().getGrid().getWalls();
                for (int i = 0; i < walls.size(); i++) {
                    walls.get(i).setSprite();
                }
            }
        } else if (target.equals("BasicWall")) {
            basicWallStyle = style;
            if (screen instanceof GameScreen) {
                GameScreen gameScreen = (GameScreen) screen;
                List<BasicWall> basicWalls = gameScreen.getLevel().getGrid().getBasicWalls();
                for (int i = 0; i < basicWalls.size(); i++) {
                    basicWalls.get(i).setSprite();
                }
            }
        } else if (target.equals("FixedWall")) {
            fixedWallStyle = style;
            if (screen instanceof GameScreen) {
                GameScreen gameScreen = (GameScreen) screen;
                List<FixedWall> fixedWalls = gameScreen.getLevel().getGrid().getFixedWalls();
                for (int i = 0; i < fixedWalls.size(); i++) {
                    fixedWalls.get(i).setSprite();
                }
            }
        } else if (target.equals("ReactionWall")) {
            reactionWallStyle = style;
            if (screen instanceof GameScreen) {
                GameScreen gameScreen = (GameScreen) screen;
                List<ReactionWall> reactionWalls = gameScreen.getLevel().getGrid().getReactionWalls();
                for (int i = 0; i < reactionWalls.size(); i++) {
                    reactionWalls.get(i).setSprite();
                }
            }
        } else if (target.equals("SpinningWall")) {
            spinningWallStyle = style;
            if (screen instanceof GameScreen) {
                GameScreen gameScreen = (GameScreen) screen;
                List<SpinningWall> spinningWalls = gameScreen.getLevel().getGrid().getSpinningWalls();
                for (int i = 0; i < spinningWalls.size(); i++) {
                    spinningWalls.get(i).setSprite();
                }
            }
        } else if (target.equals("Ball")) {
            ballStyle = style;
            if (screen instanceof GameScreen) {
                GameScreen gameScreen = (GameScreen) screen;
                List<Ball> balls = gameScreen.getLevel().getGrid().getBalls();
                for (int i = 0; i < balls.size(); i++) {
                    balls.get(i).setSprite();
                }
            }
        } else if (target.equals("Goal")) {
            goalStyle = style;
            if (screen instanceof GameScreen) {
                GameScreen gameScreen = (GameScreen) screen;
                List<Goal> goals = gameScreen.getLevel().getGrid().getGoals();
                for (int i = 0; i < goals.size(); i++) {
                    goals.get(i).setSprite();
                }
            }
        } else if (target.equals("Key")) {
            keyStyle = style;
            if (screen instanceof GameScreen) {
                GameScreen gameScreen = (GameScreen) screen;
                List<Key> keys = gameScreen.getLevel().getGrid().getKeys();
                for (int i = 0; i < keys.size(); i++) {
                    keys.get(i).setSprite();
                }
            }
        } else if (target.equals("Spike")) {
            spikeStyle = style;
            if (screen instanceof GameScreen) {
                GameScreen gameScreen = (GameScreen) screen;
                List<SpikeBall> spikeBalls = gameScreen.getLevel().getGrid().getSpikeBalls();
                for (int i = 0; i < spikeBalls.size(); i++) {
                    spikeBalls.get(i).setSpikeSprites();
                }
            }
        }
    }

    public static String getSuffix(String object) {
        if (object.equals("Ball")) {
            return ballStyle;
        } else if (object.equals("BasicWall")) {
            return basicWallStyle;
        } else if (object.equals("FixedWall")) {
            return fixedWallStyle;
        } else if (object.equals("ReactionWall")) {
            return reactionWallStyle;
        } else if (object.equals("SpinningWall")) {
            return spinningWallStyle;
        } else if (object.equals("Goal")) {
            return goalStyle;
        } else if (object.equals("Key")) {
            return keyStyle;
        } else if (object.equals("Spike")) {
            return spikeStyle;
        }
        return "";
    }

    public static String getBallStyle() {
        return ballStyle;
    }

    public static String getBasicWallStyle() {
        return basicWallStyle;
    }

    public static String getFixedWallStyle() {
        return fixedWallStyle;
    }

    public static String getReactionWallStyle() {
        return reactionWallStyle;
    }

    public static String getSpinningWallStyle() {
        return spinningWallStyle;
    }

    public static String getGoalStyle() {
        return goalStyle;
    }

    public static String getKeyStyle() {
        return keyStyle;
    }

    public static String getSpikeStyle() {
        return spikeStyle;
    }

}
