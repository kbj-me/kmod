package com.kerwhite.kmod.only.client.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.Vec3i;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class BakedQuadHelper
{
    public VertexConsumer vertexConsumer;

    public BakedQuadHelper(@NotNull VertexConsumer consumer)
    {
        this.vertexConsumer = consumer;
    }

    public void putBulkData(PoseStack.Pose p_85996_, BakedQuad p_85997_)
    {
        int[] aint1 = p_85997_.getVertices();
        Vec3i vec3i = p_85997_.getDirection().getNormal();
        Matrix4f matrix4f = p_85996_.pose();
        Vector3f vector3f = p_85996_.normal().transform(new Vector3f((float) vec3i.getX(), (float) vec3i.getY(), (float) vec3i.getZ()));
        int j = aint1.length / 8;

        try (MemoryStack memorystack = MemoryStack.stackPush())
        {
            ByteBuffer bytebuffer = memorystack.malloc(DefaultVertexFormat.BLOCK.getVertexSize());
            IntBuffer intbuffer = bytebuffer.asIntBuffer();

            for (int k = 0; k < j; ++k)
            {
                intbuffer.clear();
                intbuffer.put(aint1, k * 8, 8);
                float f = bytebuffer.getFloat(0);
                float f1 = bytebuffer.getFloat(4);
                float f2 = bytebuffer.getFloat(8);
                Vector4f vector4f = matrix4f.transform(new Vector4f(f, f1, f2, 1.0F));
                this.vertexConsumer.applyBakedNormals(vector3f, bytebuffer, p_85996_.normal());
                this.vertexConsumer.vertex(vector4f.x(), vector4f.y(), vector4f.z());
                this.vertexConsumer.endVertex();
            }
        }
    }

    public static BakedQuadHelper fromConsumer(VertexConsumer consumer)
    {
        return new BakedQuadHelper(consumer);
    }

    public static BakedQuadHelper fromRenderType(RenderType renderType)
    {
        return BakedQuadHelper.fromConsumer(Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(renderType));
    }
}
