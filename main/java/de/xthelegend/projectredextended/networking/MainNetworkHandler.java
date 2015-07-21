package de.xthelegend.projectredextended.networking;



import de.xthelegend.projectredextended.networking.msg.MessageDebugBlock;
import de.xthelegend.projectredextended.networking.msg.MessageGuiUpdate;
import de.xthelegend.projectredextended.networking.msg.MessageServerTickTime;
import cpw.mods.fml.relauncher.Side;
import de.xthelegend.projectredextended.MainClass;
import uk.co.qmunity.lib.network.NetworkHandler;

public class MainNetworkHandler {

    public static final NetworkHandler INSTANCE = new NetworkHandler(MainClass.MODID);

    public static void initNetwork() {

        INSTANCE.registerPacket(MessageGuiUpdate.class, MessageGuiUpdate.class, Side.SERVER);
        INSTANCE.registerPacket(MessageServerTickTime.class, Side.CLIENT);
        INSTANCE.registerPacket(MessageDebugBlock.class, MessageDebugBlock.class, Side.CLIENT);
    }

}