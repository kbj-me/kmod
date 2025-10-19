package com.kerwhite.kmod.corelib.math;

import org.joml.Quaternionf;
import org.joml.Vector3f;

public class QuaternionfHelper
{
    public static Quaternionf getRotationQuationf(int x,int y,int z,int angle)
    {
        return new Quaternionf().fromAxisAngleDeg(new Vector3f(x,y,z),angle);
    }
}
