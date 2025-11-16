package com.kerwhite.kmod.mixin;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TheEndPortalRenderer.class)
public class TheEndPortalRendererMixin
{
    @Inject(method = "renderType", at = @At("HEAD"), cancellable = true)
    protected void renderType(CallbackInfoReturnable<RenderType> cir)
    {
        //cir.setReturnValue(ModRenderType.END_PROTAL);
    }
}
