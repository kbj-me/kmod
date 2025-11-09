package com.kerwhite.kmod.mixin;

import com.kerwhite.kmod.kmod;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin
{
   @Inject(method = "getTextureLocation(Lnet/minecraft/client/player/AbstractClientPlayer;)Lnet/minecraft/resources/ResourceLocation;",cancellable = true,at=@At("HEAD"))
   public void getTextureLocation(AbstractClientPlayer p_117783_, CallbackInfoReturnable<ResourceLocation> cir)
   {
       if(kmod.ENABLE_SKIN_OVERRIDE)
       {
           cir.setReturnValue(new ResourceLocation(kmod.MODID, "textures/skins/kbj.png"));
       }

   }

}
