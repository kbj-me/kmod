package com.kerwhite.kmod.mixin;

import com.kerwhite.kmod.kmod;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin
{
    @Inject(method = "getSkinTextureLocation",at=@At("HEAD"),cancellable = true)
    public void getSkinTextureLocation(CallbackInfoReturnable<ResourceLocation> cir)
    {
        if(true)
        {
            cir.setReturnValue(new ResourceLocation(kmod.MODID, "textures/skins/kbj.png"));
        }
    }
}
