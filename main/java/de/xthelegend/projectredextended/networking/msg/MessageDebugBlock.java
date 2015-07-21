package de.xthelegend.projectredextended.networking.msg;

import net.minecraft.entity.player.EntityPlayer;
import uk.co.qmunity.lib.network.LocatedPacket;


/**
 *
 * @author MineMaarten, amadornes
 */

public class MessageDebugBlock extends LocatedPacket<MessageDebugBlock> {

    public MessageDebugBlock() {

    }

    public MessageDebugBlock(int x, int y, int z) {

        super(x, y, z);
    }

    @Override
    public void handleClientSide(EntityPlayer player) {

        
    }

    @Override
    public void handleServerSide(EntityPlayer player) {

    }

}