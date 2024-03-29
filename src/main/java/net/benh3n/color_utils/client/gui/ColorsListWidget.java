package net.benh3n.color_utils.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ColorsListWidget extends ElementListWidget<ColorsListWidget.ColorsEntry> {

    public ColorsListWidget(MinecraftClient minecraftClient, int width, int height, int y, int element_height) {
        super(minecraftClient, width, height, y, element_height);
    }

    public void addEntry(Identifier id, int rgb) {
        addEntry(new ColorsEntry(id, rgb));
    }

    @Override
    protected int getScrollbarPositionX() {
        return width-5;
    }

    class ColorsEntry extends Entry<ColorsEntry> {
        final List<ClickableWidget> children = new ArrayList<>();

        public ColorsEntry(Identifier id, int rgb) {
            children.add(ButtonWidget
                    .builder(Text.literal(id.toString()), button -> System.out.println(id))
                    .dimensions(40, 0, width-45, 20)
                    .build());
            children.add(new TextureIcon(id, 0, 0, 20, 20));
            children.add(new ColorIcon(rgb, 20, 0, 20, 20));
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

    static class TextureIcon extends ClickableWidget {
        Identifier id;

        TextureIcon(Identifier id, int x, int y, int width, int height) {
            super(x, y, width, height, Text.literal(id.toString()));
            this.id = id;
        }

        @Override
        protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            context.drawTexture(id, getX(), getY(), 0, 0, getWidth(), getHeight(), getWidth(), getHeight());
        }

        @Override
        public void setY(int y) {
            super.setY(y);
        }

        @Override
        protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        }
    }

    static class ColorIcon extends ClickableWidget {
        int rgb;

        ColorIcon(int rgb, int x, int y, int width, int height) {
            super(x, y, width, height, Text.literal(Integer.toHexString(rgb)));
            this.rgb = rgb;
        }

        @Override
        protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            context.fill(getX(), getY(), getX() + getWidth(), getY() + getHeight(), (0xff << 24) | rgb);
        }

        @Override
        public void setY(int y) {
            super.setY(y);
        }

        @Override
        protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        }
    }
}
