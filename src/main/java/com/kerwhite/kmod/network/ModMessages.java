package com.kerwhite.kmod.network;
import com.kerwhite.kmod.kmod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id()
    {
        return packetId++;
    }
    public static void register()
    {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(kmod.MODID,"messages"))
                .networkProtocolVersion(()->"1.0")
                .clientAcceptedVersions(s->true)
                .serverAcceptedVersions(s->true)
                .simpleChannel();

        INSTANCE = net;
        net.messageBuilder(KUpdatePacket.class,id(), NetworkDirection.PLAY_TO_SERVER).decoder(KUpdatePacket::new).encoder(KUpdatePacket::toBytes).consumerMainThread(KUpdatePacket::handle).add();
        net.messageBuilder(KRequestPack.class,id(), NetworkDirection.PLAY_TO_SERVER).decoder(KRequestPack::new).encoder(KRequestPack::toBytes).consumerMainThread(KRequestPack::handle).add();
        net.messageBuilder(KFeedBackPack.class,id(), NetworkDirection.PLAY_TO_CLIENT).decoder(KFeedBackPack::new).encoder(KFeedBackPack::toBytes).consumerMainThread(KFeedBackPack::handle).add();
        net.messageBuilder(KSpeedUpdatePack.class,id(), NetworkDirection.PLAY_TO_SERVER).decoder(KSpeedUpdatePack::new).encoder(KSpeedUpdatePack::toBytes).consumerMainThread(KSpeedUpdatePack::handle).add();
        //net.messageBuilder(KUpdateFeedBack.class,id(), NetworkDirection.PLAY_TO_CLIENT).decoder(KUpdateFeedBack::new).encoder(KUpdateFeedBack::toBytes).consumerMainThread(KUpdateFeedBack::handle).add();
    }

    public static <MSG> void sendToServer(MSG message)
    {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player)
    {
        INSTANCE.sendTo(message,player.connection.connection,NetworkDirection.PLAY_TO_CLIENT);
        //INSTANCE.send(PacketDistributor.PLAYER.with(()->player),message);
        //INSTANCE.send(PacketDistributor.ALL);
    }
    public static <MSG> void sendToAll(MSG message)
    {
        INSTANCE.send(PacketDistributor.ALL.noArg(),message);
    }
}