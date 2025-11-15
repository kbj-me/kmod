package com.kerwhite.kmod.mixin;

import com.kerwhite.kmod.kmod;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin
{
    @Shadow
    protected abstract PlayerInfo getPlayerInfo();

    @Inject(method = "getSkinTextureLocation",at=@At("HEAD"),cancellable = true)
    public void getSkinTextureLocation(CallbackInfoReturnable<ResourceLocation> cir)
    {
        if(kmod.ENABLE_SKIN_OVERRIDE)
        {
            cir.setReturnValue(new ResourceLocation(kmod.MODID, "textures/skins/kbj.png"));
        }
        else if(kmod.DISABLE_OTHER_MOD_SKIN_OVERRIDE)
        {
            PlayerInfo playerinfo = this.getPlayerInfo();
            UUID uuid = ((AbstractClientPlayer)(Object)this).getUUID();
            cir.setReturnValue(playerinfo == null ? DefaultPlayerSkin.getDefaultSkin(uuid) : playerinfo.getSkinLocation());
        }
    }
}
