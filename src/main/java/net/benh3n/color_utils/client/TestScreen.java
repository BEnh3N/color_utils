package net.benh3n.color_utils.client;

import net.benh3n.color_utils.ColorUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
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

    public static class ColorsListWidget extends ElementListWidget<ColorsListWidget.ColorsEntry> {

        public ColorsListWidget(MinecraftClient minecraftClient, int width, int height, int y, int element_height) {
            super(minecraftClient, width, height, y, element_height);
        }

        public void addEntry(Identifier id, int rgb) {
            addEntry(new ColorsEntry(id, rgb));
        }

        private class ColorsEntry extends ElementListWidget.Entry<ColorsEntry> {
            final List<ClickableWidget> children = new ArrayList<>();

            public ColorsEntry(Identifier id, int rgb) {
                children.add(ButtonWidget
                        .builder(Text.literal(id.getPath()), button -> System.out.println(id.getPath()))
                        .dimensions(0, 0, width / 2, 20)
                        .build());
                children.add(ButtonWidget
                        .builder(Text.literal(Integer.toHexString(rgb)), button -> System.out.println(Integer.toHexString(rgb)))
                        .dimensions(width / 2, 0, width / 2, 20)
                        .build());
            }

            @Override
            public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
                children.forEach(widget -> {
                    widget.setY(y);
                    widget.render(context, mouseX, mouseY, tickDelta);
                });
            }

            @Override
            public List<ClickableWidget> selectableChildren() {
                return children;
            }

            @Override
            public List<ClickableWidget> children() {
                return children;
            }
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
