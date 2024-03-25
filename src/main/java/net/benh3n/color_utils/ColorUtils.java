package net.benh3n.color_utils;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.registry.Registry;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Map;

public class ColorUtils implements ModInitializer {
    public static final String MOD_ID = "color_utils";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info(MOD_ID + " Mod Initializing");

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return new Identifier(MOD_ID, "textures");
            }

            @Override
            public void reload(ResourceManager manager) {
                Map<Identifier, Resource> resources = manager.findResources("textures/block", path -> path.getPath().endsWith(".png"));
                for (Map.Entry<Identifier, Resource> entry : resources.entrySet()) {
                    Identifier id = entry.getKey();
                    if (!id.getPath().equals("textures/block/red_mushroom_block.png")) {
                        continue;
                    }

                    Resource resource = entry.getValue();
                    try (InputStream stream = resource.getInputStream()) {
                        BufferedImage texture = ImageIO.read(stream);
                        double tot_r = 0.0;
                        double tot_g = 0.0;
                        double tot_b = 0.0;
                        double weight = 0.0;

                        int width = texture.getWidth();
                        int height = texture.getHeight();
                        for (int x = 0; x < width; x++) {
                            for (int y = 0; y < height; y++) {
                                int argb = texture.getRGB(x, y);
                                int a = (argb >> 24) & 0xff;
                                int r = (argb >> 16) & 0xff;
                                int g = (argb >> 8)  & 0xff;
                                int b =  argb        & 0xff;

                                double normal_a = a / 255.0;
                                double weighted_r = r * normal_a;
                                double weighted_g = g * normal_a;
                                double weighted_b = b * normal_a;

                                tot_r += weighted_r;
                                tot_g += weighted_g;
                                tot_b += weighted_b;
                                weight += normal_a;
                            }
                        }

                        int ave_r = (int)Math.round(tot_r / weight);
                        int ave_g = (int)Math.round(tot_g / weight);
                        int ave_b = (int)Math.round(tot_b / weight);
                        LOGGER.info(ave_r + ", " + ave_g + ", " + ave_b);
                    } catch (Exception e) {
                        LOGGER.error("Error occurred while loading texture file " + resource, e);
                    }
                }
            }
        });
    }
}
