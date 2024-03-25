package net.benh3n.color_utils.client;

import net.benh3n.color_utils.event.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;

public class ColorUtilsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
    }
}
