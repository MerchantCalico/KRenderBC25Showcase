package net.merchantcalico.krendershowcase.client.util;

import net.minecraft.core.Direction;

import java.util.Arrays;

/**
 * MIT License
 * <p>
 * Copyright (c) 2024 Cyan Kneelawk
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * <p>
 * Utilities for getting relative texture directions.
 */
public class TexDirectionUtils {
    private static final Direction[] UPS = {
        Direction.SOUTH,
        Direction.NORTH,
        Direction.UP,
        Direction.UP,
        Direction.UP,
        Direction.UP
    };

    private static final Direction[] DOWNS = Arrays.stream(UPS).map(Direction::getOpposite).toArray(Direction[]::new);

    private static final Direction[] RIGHTS = {
        Direction.EAST,
        Direction.EAST,
        Direction.WEST,
        Direction.EAST,
        Direction.SOUTH,
        Direction.NORTH
    };

    private static final Direction[] LEFTS =
        Arrays.stream(RIGHTS).map(Direction::getOpposite).toArray(Direction[]::new);

    /**
     * Gets the direction in a texture's up direction.
     *
     * @param normal the texture normal.
     * @return the texture's up direction.
     */
    public static Direction texUp(Direction normal) {
        return UPS[normal.get3DDataValue()];
    }

    /**
     * Gets the direction in a texture's down direction.
     *
     * @param normal the texture normal.
     * @return the texture's down direction.
     */
    public static Direction texDown(Direction normal) {
        return DOWNS[normal.get3DDataValue()];
    }

    /**
     * Gets the direction in the texture's right direction.
     *
     * @param normal the texture normal.
     * @return the texture's right direction.
     */
    public static Direction texRight(Direction normal) {
        return RIGHTS[normal.get3DDataValue()];
    }

    /**
     * Gets the direction in the texture's left direction.
     *
     * @param normal the texture normal.
     * @return the texture's left direction.
     */
    public static Direction texLeft(Direction normal) {
        return LEFTS[normal.get3DDataValue()];
    }
}