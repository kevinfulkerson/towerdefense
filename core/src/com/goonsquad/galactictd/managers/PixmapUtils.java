package com.goonsquad.galactictd.managers;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class PixmapUtils {

    public static Texture generateRandomRepeatedTexture(FileHandle imageToRepeat, int newTexWidth, int newTexHeight) {
        Pixmap mapToRepeat = new Pixmap(imageToRepeat);
        Pixmap generatedMap = new Pixmap(newTexWidth, newTexHeight, Pixmap.Format.RGBA8888);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                putPixmapRandomlyOnPixmap(mapToRepeat, generatedMap, i, i);
            }
        }

        Texture returnTex = new Texture(generatedMap);
        mapToRepeat.dispose();
        generatedMap.dispose();
        return returnTex;
    }

    public static Texture generateRandomRepeatedTintedTexture(FileHandle imageToRepeat, int newTexWidth, int newTexHeight, Color... tints) {
        ArrayList<Pixmap> tintedMaps = getTintedPixmaps(imageToRepeat, tints);
        Pixmap generatedMap = new Pixmap(newTexWidth, newTexHeight, Pixmap.Format.RGBA8888);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                putPixmapRandomlyOnPixmap(getRandomIndex(tintedMaps), generatedMap, i, i);
            }
        }

        Texture returnTex = new Texture(generatedMap);
        for (Pixmap map : tintedMaps) {
            map.dispose();
        }
        generatedMap.dispose();
        return returnTex;
    }

    private static <E> E getRandomIndex(ArrayList<E> list) {
        int randomLoc = MathUtils.random(0, list.size() - 1);
        return list.get(randomLoc);
    }

    public static void putPixmapRandomlyOnPixmap(Pixmap placedMap, Pixmap baseMap) {
        putPixmapRandomlyOnPixmap(placedMap, baseMap, placedMap.getWidth(), placedMap.getHeight());
    }

    public static void putPixmapRandomlyOnPixmap(Pixmap placedMap, Pixmap baseMap, int width, int height) {
        int repeatX = MathUtils.random(0, baseMap.getWidth());
        int repeatY = MathUtils.random(0, baseMap.getHeight());
        baseMap.drawPixmap(
                placedMap, 0, 0, placedMap.getWidth(), placedMap.getHeight(),
                repeatX, repeatY, width, height);
    }

//    PixmapUtils.tintPixmap(mapToRepeat, Color.BLUE);

    public static ArrayList<Pixmap> getTintedPixmaps(FileHandle imageToRepeat, Color... colors) {
        ArrayList<Pixmap> pixmaps = new ArrayList<Pixmap>();
        for (Color color : colors) {
            Pixmap tintedMap = new Pixmap(imageToRepeat);
            pixmaps.add(tintPixmap(tintedMap, color));
        }
        return pixmaps;
    }

    public static Pixmap tintPixmap(Pixmap map, Color color) {
        if (map.getFormat() != Pixmap.Format.RGBA8888)
            throw new GdxRuntimeException("Wrong format.");

        ByteBuffer pixels = map.getPixels();
        int colorInt = color.toIntBits();
        byte b = (byte) (colorInt >> 16);
        byte g = (byte) (colorInt >> 8);
        byte r = (byte) (colorInt);

        for (int i = 0; i < pixels.capacity(); ) {
            pixels.put(i, r);
            i++;
            pixels.put(i, g);
            i++;
            pixels.put(i, b);
            i++;
            i++;
        }

        return map;
    }
}
