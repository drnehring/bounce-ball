package com.david.bounceball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by David on 11/15/2016.
 */
public class CommandParser {

    private ScreenWithListener screen;

    public CommandParser() {

    }

    public CommandParser(ScreenWithListener screen) {
        this.screen = screen;
    }

    public void setScreen(ScreenWithListener screen) {
        this.screen = screen;

    }

    public String parse(String command) {
        boolean setting;
        String[] split = command.split(" ");
        if (split.length < 1)
            return null;
        setting = (split.length > 1);

        String objectAndFields = split[0];
        String[] objectAndFieldsSplit = objectAndFields.split("\\.");
        if (objectAndFieldsSplit.length == 1) //Must always have a Object.Field, not just Object
            return null;
        String[] fields = new String[objectAndFieldsSplit.length - 1];
        //Create a new String array containing the fields without the object
        for (int i = 0; i < objectAndFieldsSplit.length - 1; i++) {
            fields[i] = objectAndFieldsSplit[i + 1];
        }
        String objectString = objectAndFieldsSplit[0];
        String[] values = new String[split.length - 1];
        //Create a new String array containing the values without the object and fields
        for (int i = 0; i < split.length - 1; i++) {
            values[i] = split[i + 1];
        }

        String unsplitValue = "";
        if (setting) {
           unsplitValue = command.split(" ", 2)[1];
        }

        Object screenObject = screen.getObjectByName(objectString);
        //The screenObject is null if we are referring to a class rather than an object
        if (screenObject == null) {
                if (fields[0].equals("style")) {
                    if (setting) {
                        Assets.setStyle(screen, objectString, values[0]);
                    } else {
                        return Assets.getSuffix(objectString);
                    }
                } else if (objectString.equals("Ball")) {
                    if (fields[0].equals("speed")) {
                        if (setting) {
                            Ball.SPEED = Float.parseFloat(values[0]);
                        } else {
                            return Float.toString(Ball.SPEED);
                        }
                    } else if (fields[0].equals("COL_OFF")) {
                        if (setting) {
                            Ball.COLLISION_OFFSET = Float.parseFloat(values[0]);
                        } else {
                            return Float.toString(Ball.COLLISION_OFFSET);
                        }
                    } else if (fields[0].equals("B_L_E_D")) {
                        if (setting) {
                            Ball.BOUNCE_LINE_ENTRY_DISTANCE = Float.parseFloat(values[0]);
                        } else {
                            return Float.toString(Ball.BOUNCE_LINE_ENTRY_DISTANCE);
                        }
                    } else if (fields[0].equals("RAD_VAR")) {
                        if (setting) {
                            Ball.RADIUS_VARIABLE = Float.parseFloat(values[0]);
                            if (Assets.getBallStyle().equals("_var")) {
                                Assets.setStyle(screen, "Ball", "_var");
                            }
                        } else {
                            return Float.toString(Ball.RADIUS_VARIABLE);
                        }
                    } else if (fields[0].equals("AR_X_OFF")) {
                        if (setting) {
                            Ball.ARROW_X_OFFSET = Float.parseFloat(values[0]);
                        } else {
                            return Float.toString(Ball.ARROW_X_OFFSET);
                        }
                    } else if (fields[0].equals("AR_WIDTH")) {
                        if (setting) {
                            Ball.ARROW_WIDTH = Float.parseFloat(values[0]);
                        } else {
                            return Float.toString(Ball.ARROW_WIDTH);
                        }
                    } else if (fields[0].equals("AR_HEIGHT")) {
                        if (setting) {
                            Ball.ARROW_HEIGHT = Float.parseFloat(values[0]);
                        } else {
                            return Float.toString(Ball.ARROW_HEIGHT);
                        }
                    } else if (fields[0].equals("ADJ_SPD")) {
                        if (setting) {
                            Ball.ADJUST_SPEED = Boolean.parseBoolean(values[0]);
                        } else {
                            return Boolean.toString(Ball.ADJUST_SPEED);
                        }
                    }
                }
        } else {
            if (screenObject instanceof TextureRegion) {
                TextureRegion textureRegion = (TextureRegion) screenObject;
                if (fields[0].equals("texture")) {
                    textureRegion.setTexture(new Texture(Gdx.files.internal(values[0])));
                }
            } else if (screenObject instanceof Stage) {
                Stage stage = (Stage) screenObject;
                if (fields[0].equals("viewport")) {
                    if (fields.length > 1) {
                        if (fields[1].equals("worldWidth")) {
                            if (setting) {
                                stage.getViewport().setWorldWidth(Float.parseFloat(values[0]));
                            } else {
                                return Float.toString(stage.getViewport().getWorldWidth());
                            }
                        } else if (fields[1].equals("worldHeight")) {
                            if (setting) {
                                stage.getViewport().setWorldHeight(Float.parseFloat(values[0]));
                            } else {
                                return Float.toString(stage.getViewport().getWorldHeight());
                            }
                        } else if (fields[1].equals("worldSize")) {
                            if (setting) {
                                stage.getViewport().setWorldSize(Float.parseFloat(values[0]), Float.parseFloat(values[1]));
                            } else {
                                return Float.toString(stage.getViewport().getWorldWidth()) + " " + Float.toString(stage.getViewport().getWorldHeight());
                            }
                        }
                    }
                }
            } else if (screenObject instanceof BitmapFont) {
                BitmapFont bitmapFont = (BitmapFont) screenObject;
                if (fields[0].equals("font")) {
                    bitmapFont = new BitmapFont(Gdx.files.internal(unsplitValue));
                }
            } else if (screenObject instanceof Actor) {
                Actor actor = (Actor) screenObject;
                if (fields[0].equals("x")) {
                    if (setting) {
                        actor.setX(Float.parseFloat(values[0]));
                    } else {
                        return Float.toString(actor.getX());
                    }
                } else if (fields[0].equals("y")) {
                    if (setting) {
                        actor.setY(Float.parseFloat(values[0]));
                    } else {
                        return Float.toString(actor.getY());
                    }
                } else if (fields[0].equals("width")) {
                    if (setting) {
                        actor.setWidth(Float.parseFloat(values[0]));
                    } else {
                        return Float.toString(actor.getWidth());
                    }
                } else if (fields[0].equals("height")) {
                    if (setting) {
                        actor.setHeight(Float.parseFloat(values[0]));
                    } else {
                        return Float.toString(actor.getHeight());
                    }
                } else if (fields[0].equals("position")) {
                    if (setting) {
                        actor.setPosition(Float.parseFloat(values[0]), Float.parseFloat(values[1]));
                    } else {
                        return Float.toString(actor.getX()) + " " + Float.toString(actor.getY());
                    }
                } else if (fields[0].equals("size")) {
                    if (setting) {
                        actor.setSize(Float.parseFloat(values[0]), Float.parseFloat(values[1]));
                    } else {
                        return Float.toString(actor.getWidth()) + " " + Float.toString(actor.getHeight());
                    }
                } else if (fields[0].equals("bounds")) {
                    if (setting) {
                        actor.setBounds(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]));
                    } else {
                        return Float.toString(actor.getX()) + " " + Float.toString(actor.getY()) + " " + Float.toString(actor.getWidth()) + " " + Float.toString(actor.getHeight());
                    }
                } else {
                    if (screenObject instanceof TextButton) {
                        TextButton textButton = (TextButton) screenObject;
                        if (fields[0].equals("skin")) {
                            if (setting) {
                                Skin skin = new Skin(Gdx.files.internal(unsplitValue));
                                textButton.setStyle(skin.get(TextButton.TextButtonStyle.class));
                                textButton.setSkin(skin);
                            }
                        } else if (fields[0].equals("text")) {
                            if (setting) {
                                textButton.setText(unsplitValue);
                            } else {
                                return textButton.getText().toString();
                            }
                        }
                    } else if (screenObject instanceof Image) {
                        Image image = (Image) screenObject;
                        if (fields[0].equals("texture")) {
                            image.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(unsplitValue)))));
                        } else if (fields[0].equals("color")) {
                            if (setting) {
                                image.setColor(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]));
                            } else {
                                return image.getColor().r + " " + image.getColor().g + " " + image.getColor().b + " " + image.getColor().a;
                            }
                        } else if (fields[0].equals("r")) {
                            if (setting) {
                                image.setColor(Float.parseFloat(values[0]), image.getColor().g, image.getColor().b, image.getColor().a);
                            } else {
                                return Float.toString(image.getColor().r);
                            }
                        } else if (fields[0].equals("g")) {
                            if (setting) {
                                image.setColor(image.getColor().r, Float.parseFloat(values[0]), image.getColor().b, image.getColor().a);
                            } else {
                                return Float.toString(image.getColor().g);
                            }
                        } else if (fields[0].equals("b")) {
                            if (setting) {
                                image.setColor(image.getColor().r, image.getColor().g, Float.parseFloat(values[0]), image.getColor().a);
                            } else {
                                return Float.toString(image.getColor().b);
                            }
                        } else if (fields[0].equals("a")) {
                            if (setting) {
                                image.setColor(image.getColor().r, image.getColor().g, image.getColor().b, Float.parseFloat(values[0]));
                            } else {
                                return Float.toString(image.getColor().a);
                            }
                        }
                    }
                }

            }
        }

        return null;
    }

}
