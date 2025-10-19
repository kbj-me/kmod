package com.kerwhite.kmod.gui.widget;

public class AssetOffsets
{
    public static class OffsetPair
    {
        private final float xOffset;
        private final float yOffset;
        private final float xOffset_hov;
        private final float yOffset_hov;
        public float getxOffset()
        {
            return xOffset;
        }
        public float getyOffset()
        {
            return yOffset;
        }
        public float getxOffset_hov()
        {
            return xOffset_hov;
        }
        public float getyOffset_hov()
        {
            return yOffset_hov;
        }
        public OffsetPair(float xOffset, float yOffset, float xOffset_hov, float yOffset_hov)
        {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.xOffset_hov = xOffset_hov;
            this.yOffset_hov = yOffset_hov;
        }
    }
    public static final OffsetPair DIRECTION_OFFSET = new OffsetPair(100.0f,8.0f,116.0f,8.0f);
    public static final OffsetPair TRI_COLOR_OFFSET_BLUE = new OffsetPair(100.0f,72.0f,116.0f,72.0f);
    public static final OffsetPair TRI_COLOR_OFFSET_WHITE = new OffsetPair(132.0f,14.0f,148.0f,14.0f);
    public static final OffsetPair TRI_COLOR_OFFSET_RED = new OffsetPair(132.0f,30.0f,148.0f,30.0f);
}
