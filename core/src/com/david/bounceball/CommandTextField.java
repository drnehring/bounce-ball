package com.david.bounceball;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by David on 11/15/2016.
 */
//Used for a rudimentary temporary command line interface
public class CommandTextField extends TextField {

    private CommandParser parser;
    private ArrayList<String> pastCommands;
    private String currentCommand;
    private int commandIndex = -1;

    public CommandTextField (String text, Skin skin) {
        super(text, skin);
        setup();
    }

    public CommandTextField (String text, Skin skin, String styleName) {
        super(text, skin, styleName);
        setup();
    }

    public CommandTextField (String text, TextFieldStyle style) {
        super(text, style);
        setup();
    }

    private void setup() {
        parser = new CommandParser();
        setBounds(0, 0, 200, 30);
        setVisible(false);
        addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof InputEvent) {
                    InputEvent inputEvent = (InputEvent) event;
                    if (inputEvent.getType() == InputEvent.Type.keyDown) {
                        switch (inputEvent.getKeyCode()) {
                            case Input.Keys.ENTER:
                                if (!text.equals(""))
                                    executeCommand();
                                break;
                            case Input.Keys.UP:
                                incrementCommandIndex();
                                break;
                            case Input.Keys.DOWN:
                                decrementCommandIndex();
                                break;
                        }
                    }
                }
                return false;
            }
        });
        pastCommands = new ArrayList<String>();
    }

    private void executeCommand() {
        pastCommands.add(0, text);
        setText(parser.parse(text));
        commandIndex = -1;
        currentCommand = "";
    }

    private void incrementCommandIndex() {
        if (commandIndex == -1) { //-1 indicates the command that is being typed but has not been entered
            if (pastCommands.size() > 0) {
                currentCommand = text; //Store the non-entered command
                commandIndex = 0;
                setText(pastCommands.get(commandIndex));
            }
        } else {
            if (commandIndex < pastCommands.size() - 1) {
                commandIndex += 1;
                setText(pastCommands.get(commandIndex));
            }
        }
    }

    private void decrementCommandIndex() {
        if (commandIndex == 0) {
            commandIndex = -1;
            setText(currentCommand);
        } else if (commandIndex > 0) {
            commandIndex -= 1;
            setText(pastCommands.get(commandIndex));
        }
    }

    public void setScreen(ScreenWithListener screen) {
        parser.setScreen(screen);
    }


}
