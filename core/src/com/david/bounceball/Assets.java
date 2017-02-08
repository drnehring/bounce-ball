package com.david.bounceball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.david.bounceball.screens.GameScreen;

import java.io.IOException;
import java.io.InputStream;
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

    private static int numberOfPacks;
    private static boolean[] packAvailable;
    private static int packNumber = -1;
    private static String[] levelsDataSplit = null;
    private static int numberOfLevels = -1;
    private static boolean[] levelAvailable = null;

    public static Sound goodClickDown = Gdx.audio.newSound(Gdx.files.internal("l_down_click.wav"));
    public static Sound goodClickUp = Gdx.audio.newSound(Gdx.files.internal("l_up_click.wav"));

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

    public static void loadPackData() {
        while (Gdx.files.internal("pack_" + numberOfPacks + ".txt").exists()) {
            numberOfPacks++;
        }
        packAvailable = new boolean[numberOfPacks];

        InputStream stream;
        for (int i = 0; i < numberOfPacks; i++) {
            stream = Gdx.files.internal("pack_" + i + ".txt").read();
            int packAvailableAsAsciiInt = -1;
            try {
                packAvailableAsAsciiInt = stream.read();
            } catch (IOException ex) {
                //Shouldn't happen
            }
            if (packAvailableAsAsciiInt == 48) { //0
                packAvailable[i] = false;
            } else if (packAvailableAsAsciiInt == 49) { //1
                packAvailable[i] = true;
            } else {
                //Shouldn't happen
                packAvailable[i] = false;
            }
        }
    }

    public static int numberOfPacks() {
        return numberOfPacks;
    }

    public static boolean packAvailable(int packNumber) {
        return packAvailable[packNumber];
    }

    public static void loadPack(int packNumber) {
        Assets.packNumber = packNumber;
        FileHandle handle = Gdx.files.internal("pack_" + packNumber + ".txt");
        levelsDataSplit = handle.readString().split("\r\n");
        numberOfLevels = (levelsDataSplit.length - 1) / (Level.LINES_PER_LEVEL + 1);
        levelAvailable = new boolean[numberOfLevels];
        for (int i = 0; i < numberOfLevels; i++) {
            if (levelsDataSplit[1 + i * (Level.LINES_PER_LEVEL + 1)].equals("0")) {
                levelAvailable[i] = false;
            } else if (levelsDataSplit[1 + i * (Level.LINES_PER_LEVEL + 1)].equals("1")) {
                levelAvailable[i] = true;
            } else {
                //Shouldn't happen
                levelAvailable[i] = false;
            }
        }
    }

    public static int numberOfLevels() {
        return numberOfLevels;
    }

    public static boolean levelAvailable(int levelNumber) {
        return levelAvailable[levelNumber];
    }

    public static String[] levelDataSplit(int levelNumber) {
        String[] levelDataSplit = new String[Level.LINES_PER_LEVEL];
        for (int i = 0; i < Level.LINES_PER_LEVEL; i++)
            levelDataSplit[i] = levelsDataSplit[2 + levelNumber * (Level.LINES_PER_LEVEL + 1) + i];
        return levelDataSplit;
    }

    public static void unloadPack() {
        FileHandle handle = Gdx.files.local("pack_" + packNumber + ".txt");
        handle.writeString(levelsDataSplit[0] + "\r\n", false);
        for (int i = 1; i < levelsDataSplit.length; i++)
            handle.writeString(levelsDataSplit[i] + "\r\n", true);
        packNumber = -1;
        numberOfLevels = -1;
        levelsDataSplit = null;
        levelAvailable = null;
    }

    public static void makePackAvailable(int packNumber) {
        int packNumberTemp = Assets.packNumber;
        if (packNumberTemp != -1)
            unloadPack();
        loadPack(packNumber);
        levelsDataSplit[0] = "1";
        packAvailable[packNumber] = true;
        unloadPack();
        if (packNumberTemp != - 1)
            loadPack(packNumberTemp);
    }

    public static void makeLevelAvailable(int levelNumber) {
        levelAvailable[levelNumber] = true;
        levelsDataSplit[1 + levelNumber * (Level.LINES_PER_LEVEL + 1)] = "1";
    }

    public static void resetProgress() {
        loadPack(0);
        levelsDataSplit[0] = "1";
        levelsDataSplit[1] = "1";
        packAvailable[0] = true;
        for (int j = 1; j < numberOfLevels; j++)
            levelsDataSplit[1 + j * (Level.LINES_PER_LEVEL + 1)] = "0";
        unloadPack();

        for (int i = 1; i < numberOfPacks; i++) {
            loadPack(i);
            levelsDataSplit[0] = "0";
            levelsDataSplit[1] = "1";
            packAvailable[i] = false;
            for (int j = 1; j < numberOfLevels; j++)
                levelsDataSplit[1 + j * (Level.LINES_PER_LEVEL + 1)] = "0";
            unloadPack();
        }
    }

}
