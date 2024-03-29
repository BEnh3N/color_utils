package net.benh3n.color_utils.client;

import net.benh3n.color_utils.ColorUtils;
import net.benh3n.color_utils.client.gui.ColorsListWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class TestScreen extends Screen {

    public TestScreen() {
        super(Text.literal("Test Screen"));
    }

    @Override
    protected void init() {
        ColorsListWidget colorsListWidget = addDrawableChild(new ColorsListWidget(client, width, height, 0, 20));
        for (Map.Entry<Identifier, Integer> entry : ColorUtils.BLOCK_COLORS.entrySet()) {
            colorsListWidget.addEntry(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
