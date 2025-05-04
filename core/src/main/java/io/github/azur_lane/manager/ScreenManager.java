package io.github.azur_lane.manager;

import io.github.azur_lane.Main;
import io.github.azur_lane.screens.*;

import io.github.azur_lane.utils.ScreenType;
import com.badlogic.gdx.Screen;

public class ScreenManager {
    private static ScreenManager instance;

    private Main game;

    private BotCombatScreen botCombatScreen;

    private ScreenManager() {
    }

    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public void initialize(Main game) {
        this.game = game;
    }

    public void showScreen(ScreenType screenType) {
        switch (screenType) {
            case BOT_COMBAT:
                if (botCombatScreen == null)
                    botCombatScreen = new BotCombatScreen(game);
                game.setScreen(botCombatScreen);
                break;

        }
    }
}