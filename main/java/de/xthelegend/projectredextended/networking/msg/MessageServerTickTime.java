package de.xthelegend.projectredextended.networking.msg;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import uk.co.qmunity.lib.network.Packet;


public class MessageServerTickTime extends Packet<MessageServerTickTime> {
    private double tickTime;

    public MessageServerTickTime() {
    }

    public MessageServerTickTime(double tickTime) {
        this.tickTime = tickTime;
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
       
    }

    @Override
    public void handleServerSide(EntityPlayer player) {

    }

    @Override
    public void read(DataInput buffer) throws IOException {
        tickTime = buffer.readDouble();
    }

    @Override
    public void write(DataOutput buffer) throws IOException {
        buffer.writeDouble(tickTime);
    }

}