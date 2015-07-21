package de.xthelegend.projectredextended;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public class ItemAthame extends ItemSword 
{
    
    private float damageDealt;
    private static ToolMaterial athameMaterial = EnumHelper.addToolMaterial("SILVER", 0, 100, 6.0F, 2.0F, 10);
    
    public ItemAthame() {
    
        super(athameMaterial);
        this.setCreativeTab(MainClass.tab_exploration);
        this.setMaxDamage(100);
        this.setUnlocalizedName(MainClass.MODID + ":" + "athame");
        this.setTextureName(MainClass.MODID + ":" + "atheme");
        this.maxStackSize = 1;
        this.setFull3D();
    }
    
    @Override
    public float func_150931_i() {
    
        return this.damageDealt;
    }
    
    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase entity, EntityLivingBase player) {
    
        this.damageDealt = athameMaterial.getDamageVsEntity();
        if ((entity instanceof EntityEnderman) || (entity instanceof EntityDragon)) {
            this.damageDealt += 18.0F;
        }
        entity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) player), this.damageDealt);
        return super.hitEntity(stack, entity, player);
    }


    @Override
    public boolean isRepairable() {

        return canRepair && isDamageable();
    }

    @Override
    public boolean getIsRepairable(ItemStack is1, ItemStack is2) {

        return ((is1.getItem() == this || is2.getItem() == this) && (is1.getItem() == Items.iron_ingot|| is2.getItem() == Items.iron_ingot));
    }
}
