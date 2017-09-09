package com.bergerkiller.bukkit.maplands;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.junit.Ignore;
import org.junit.Test;

import com.bergerkiller.bukkit.common.map.MapColorPalette;
import com.bergerkiller.bukkit.common.map.MapTexture;
import com.bergerkiller.bukkit.common.map.util.MapDebugWindow;
import com.bergerkiller.bukkit.common.utils.CommonUtil;

public class TestSprites {
    private int spriteIdx = 0;
    private IsometricBlockSprites[] sprites;

    static {
        CommonUtil.bootstrap();
    }

    @Ignore
    @Test
    public void testZoomMask() {
        ZoomLevel zoom = ZoomLevel.ZOOM64;
        MapTexture map = MapTexture.createEmpty((int) (2.0 * zoom.getWidth()) + 2, (int) (2.5 * zoom.getHeight()) + 2);

        map.draw(zoom.getMask(), zoom.getDrawX(2) + 1, zoom.getDrawZ(3) + 1, MapColorPalette.COLOR_BLUE);
        map.draw(zoom.getMask(), zoom.getDrawX(1) + 1, zoom.getDrawZ(6) + 2, MapColorPalette.COLOR_RED);
        map.draw(zoom.getMask(), zoom.getDrawX(3) + 1, zoom.getDrawZ(6) + 2, MapColorPalette.COLOR_GREEN);
        map.draw(zoom.getMask(), zoom.getDrawX(2) + 1, zoom.getDrawZ(9) + 3, MapColorPalette.COLOR_YELLOW);

        MapDebugWindow.showMapForever(map, 800 / map.getHeight());
    }

    @Ignore
    @Test
    public void testSprites() {
        ZoomLevel zoom = ZoomLevel.ZOOM64;
        MapTexture map = MapTexture.createEmpty(6 * zoom.getWidth(), 4 * zoom.getHeight());
        sprites = new IsometricBlockSprites[] {
                IsometricBlockSprites.getSprites(BlockFace.NORTH_EAST, zoom),
                IsometricBlockSprites.getSprites(BlockFace.NORTH_WEST, zoom),
                IsometricBlockSprites.getSprites(BlockFace.SOUTH_EAST, zoom),
                IsometricBlockSprites.getSprites(BlockFace.SOUTH_WEST, zoom)
        };

        map.fill(MapColorPalette.COLOR_RED);

        for (int n = 0; n < 6; n++) {
            drawSprite(map, n, n * 3);
        }

        for (int n = 3; n < 10; n++) {
            drawSprite(map, n, n);
        }

        MapDebugWindow.showMapForever(map, 1000 / map.getWidth());
    }

    private void drawSprite(MapTexture map, int x, int z) {
        if (spriteIdx >= sprites.length) {
            spriteIdx = 0;
        }
        IsometricBlockSprites sprite = sprites[spriteIdx];
        ZoomLevel zoom = sprite.getZoom();

        map.setRelativeBrushMask(zoom.getMask());
        map.draw(sprite.getSprite(Material.GRASS), zoom.getDrawX(x), zoom.getDrawZ(z));
        map.setRelativeBrushMask(null);

        map.fillRectangle(zoom.getScreenX(x) - 1, zoom.getScreenZ(z) - 1, 2, 2, MapColorPalette.COLOR_BLUE);
    }
}
