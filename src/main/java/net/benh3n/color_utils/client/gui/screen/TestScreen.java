package net.benh3n.color_utils.client.gui.screen;

import net.benh3n.color_utils.ColorUtils;
import net.benh3n.color_utils.client.gui.widget.ColorsListWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
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
    public void init() {
        ColorsListWidget colorsListWidget = addDrawableChild(new ColorsListWidget(client, width, height-30, 30, 20));
        for (Map.Entry<Identifier, Integer> entry : ColorUtils.BLOCK_COLORS.entrySet()) {
            colorsListWidget.addEntry(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 8, 0xFFFFFF);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
