package de.xthelegend.projectredextended.lib;

import net.minecraft.entity.player.EntityPlayer;

public interface ICustomButtonInterface {
    
    public void onButtonPress(EntityPlayer player, int messageId, int value);
}