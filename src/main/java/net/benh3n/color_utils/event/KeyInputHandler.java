package net.benh3n.color_utils.event;

import net.benh3n.color_utils.client.gui.screen.TestScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_COLOR_UTILS = "key.category.color_utils.color_utils";
    public static final String KEY_OPEN_COLORS = "key.color_utils.open_colors";

    public static KeyBinding openColorsKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openColorsKey.wasPressed()) {
                // This happens when our custom key is pressed
                client.setScreen(new TestScreen());
            }
        });
    }

    public static void register() {
        openColorsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            KEY_OPEN_COLORS,
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_O,
            KEY_CATEGORY_COLOR_UTILS
        ));

        registerKeyInputs();
    }
}
