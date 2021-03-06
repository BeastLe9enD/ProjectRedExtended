package de.beastlegend.prextended.util;

/**
 * Created by BeastLe9enD on 19.07.2017.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.beastlegend.prextended.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class AlloyFurnaceRegistry implements IAlloyFurnaceRegistry{
    private static AlloyFurnaceRegistry INSTANCE = new AlloyFurnaceRegistry();

    private final List<IAlloyFurnaceRecipe> alloyFurnaceRecipes = new ArrayList<IAlloyFurnaceRecipe>();
    private final List<ItemStack> bufferedRecyclingItems = new ArrayList<ItemStack>();
    private final Map<ItemStack, ItemStack> moltenDownMap = new HashMap<ItemStack, ItemStack>();
    private final List<String> blacklist = new ArrayList<String>();

    private AlloyFurnaceRegistry(){

    }

    public static AlloyFurnaceRegistry getInstance() {
        return INSTANCE;
    }

    @Override
    public void addRecipe(IAlloyFurnaceRecipe recipe) {
        alloyFurnaceRecipes.add(recipe);
    }

    @Override
    public void addRecipe(ItemStack craftingResult, Object... requiredItems) {
        if (craftingResult == null || craftingResult.getItem() == null)
            throw new NullPointerException("Can't register an Alloy Furnace recipe with a null output stack or item");
        ItemStack[] requiredStacks = new ItemStack[requiredItems.length];
        for (int i = 0; i < requiredStacks.length; i++) {
            if (requiredItems[i] instanceof ItemStack) {
                requiredStacks[i] = (ItemStack) requiredItems[i];
            } else if (requiredItems[i] instanceof Item) {
                requiredStacks[i] = new ItemStack((Item) requiredItems[i], 1, OreDictionary.WILDCARD_VALUE);
            } else if (requiredItems[i] instanceof Block) {
                requiredStacks[i] = new ItemStack(Item.getItemFromBlock((Block) requiredItems[i]), 1, OreDictionary.WILDCARD_VALUE);
            } else {
                throw new IllegalArgumentException("Alloy Furnace crafting ingredients can only be ItemStack, Item or Block!");
            }
        }
        addRecipe(new StandardAlloyFurnaceRecipe(craftingResult, requiredStacks));
    }

    public void generateRecyclingRecipes() {
        List<Item> blacklist = new ArrayList<Item>();
        for (String configString : this.blacklist) {
            Item item = GameData.getItemRegistry().getObject(new ResourceLocation(Reference.MODID, configString));
            if (item != null) {
                blacklist.add(item);
            } else {

            }
        }

        List<ItemStack> registeredRecycledItems = new ArrayList<ItemStack>();
        List<ItemStack> registeredResultItems = new ArrayList<ItemStack>();

        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
        for (IRecipe recipe : recipes) {
            int recyclingAmount = 0;
            ItemStack currentlyRecycledInto = null;
            for (ItemStack recyclingItem : bufferedRecyclingItems) {
                try {
                    if (recipe instanceof ShapedRecipes) {
                        ShapedRecipes shaped = (ShapedRecipes) recipe;
                        if (shaped.recipeItems != null) {
                            for (ItemStack input : shaped.recipeItems) {
                                if (input != null && ItemStackUtils.isItemFuzzyEqual(input, recyclingItem)) {
                                    ItemStack moltenDownItem = getRecyclingStack(recyclingItem);
                                    if (currentlyRecycledInto == null
                                            || ItemStackUtils.isItemFuzzyEqual(currentlyRecycledInto, moltenDownItem)) {
                                        currentlyRecycledInto = moltenDownItem;
                                        recyclingAmount += moltenDownItem.stackSize;
                                    }
                                }
                            }
                        }
                    } else if (recipe instanceof ShapelessRecipes) {
                        ShapelessRecipes shapeless = (ShapelessRecipes) recipe;
                        if (shapeless.recipeItems != null) {
                            for (ItemStack input : (List<ItemStack>) shapeless.recipeItems) {
                                if (input != null && ItemStackUtils.isItemFuzzyEqual(input, recyclingItem)) {
                                    ItemStack moltenDownItem = getRecyclingStack(recyclingItem);
                                    if (currentlyRecycledInto == null
                                            || ItemStackUtils.isItemFuzzyEqual(currentlyRecycledInto, moltenDownItem)) {
                                        currentlyRecycledInto = moltenDownItem;
                                        recyclingAmount += moltenDownItem.stackSize;
                                    }
                                }
                            }
                        }
                    } else if (recipe instanceof ShapedOreRecipe) {
                        ShapedOreRecipe shapedOreRecipe = (ShapedOreRecipe) recipe;
                        if (shapedOreRecipe.getInput() != null) {
                            for (Object input : shapedOreRecipe.getInput()) {
                                if (input != null) {
                                    List<ItemStack> itemList;
                                    if (input instanceof ItemStack) {
                                        itemList = new ArrayList<ItemStack>();
                                        itemList.add((ItemStack) input);
                                    } else {
                                        itemList = (List<ItemStack>) input;
                                    }
                                    for (ItemStack item : itemList) {
                                        if (item != null && ItemStackUtils.isItemFuzzyEqual(item, recyclingItem)) {
                                            ItemStack moltenDownItem = getRecyclingStack(recyclingItem);
                                            if (currentlyRecycledInto == null
                                                    || ItemStackUtils.isItemFuzzyEqual(currentlyRecycledInto, moltenDownItem)) {
                                                currentlyRecycledInto = moltenDownItem;
                                                recyclingAmount += moltenDownItem.stackSize;
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    } else if (recipe instanceof ShapelessOreRecipe) {
                        ShapelessOreRecipe shapeless = (ShapelessOreRecipe) recipe;
                        for (Object input : shapeless.getInput()) {
                            if (input != null) {
                                List<ItemStack> itemList;
                                if (input instanceof ItemStack) {
                                    itemList = new ArrayList<ItemStack>();
                                    itemList.add((ItemStack) input);
                                } else {
                                    itemList = (List<ItemStack>) input;
                                }
                                for (ItemStack item : itemList) {
                                    if (item != null && ItemStackUtils.isItemFuzzyEqual(item, recyclingItem)) {
                                        ItemStack moltenDownItem = getRecyclingStack(recyclingItem);
                                        if (currentlyRecycledInto == null
                                                || ItemStackUtils.isItemFuzzyEqual(currentlyRecycledInto, moltenDownItem)) {
                                            currentlyRecycledInto = moltenDownItem;
                                            recyclingAmount += moltenDownItem.stackSize;
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            if (recyclingAmount > 0 && recipe.getRecipeOutput().stackSize > 0) {
                boolean shouldAdd = true;
                for (int i = 0; i < registeredRecycledItems.size(); i++) {
                    if (ItemStackUtils.isItemFuzzyEqual(registeredRecycledItems.get(i), recipe.getRecipeOutput())) {
                        if (registeredResultItems.get(i).stackSize < recyclingAmount) {
                            shouldAdd = false;
                            break;
                        } else {
                            registeredResultItems.remove(i);
                            registeredRecycledItems.remove(i);
                            i--;
                        }
                    }
                }

                if (shouldAdd) {
                    if (blacklist.contains(recipe.getRecipeOutput().getItem())) {
                        continue;
                    }
                    ItemStack resultItem = new ItemStack(currentlyRecycledInto.getItem(), Math.min(64, recyclingAmount),
                            currentlyRecycledInto.getItemDamage());
                    registeredResultItems.add(resultItem);
                    registeredRecycledItems.add(recipe.getRecipeOutput());

                }
            }
        }
        for (int i = 0; i < registeredResultItems.size(); i++) {
            addRecipe(registeredResultItems.get(i), registeredRecycledItems.get(i));
        }

    }

    @Override
    public void addRecyclingRecipe(ItemStack recycledItem, String... blacklist) {
        if (recycledItem == null){
            throw new NullPointerException("Recycled item can't be null!");
        }

        bufferedRecyclingItems.add(recycledItem);
        if (blacklist.length > 0) {
            ModContainer mc = Loader.instance().activeModContainer();

            Collections.addAll(this.blacklist, blacklist);
        }
    }

    @Override
    public void addRecyclingRecipe(ItemStack recycledItem, ItemStack moltenDownItem, String... blacklist) {
        if (moltenDownItem == null){
            throw new NullPointerException("Molten down item can't be null!");
        }

        addRecyclingRecipe(recycledItem, blacklist);
        moltenDownMap.put(recycledItem, moltenDownItem);
    }

    private ItemStack getRecyclingStack(ItemStack original) {
        ItemStack moltenDownStack = moltenDownMap.get(original);
        return moltenDownStack != null ? moltenDownStack : original;
    }

    public IAlloyFurnaceRecipe getMatchingRecipe(ItemStack[] input, ItemStack outputSlot) {

        for (IAlloyFurnaceRecipe recipe : alloyFurnaceRecipes) {
            if (recipe.matches(input)) {
                if (outputSlot != null) {
                    ItemStack craftingResult = recipe.getCraftingResult(input);
                    if (!ItemStack.areItemStackTagsEqual(outputSlot, craftingResult) || !outputSlot.isItemEqual(craftingResult)) {
                        continue;
                    } else if (craftingResult.stackSize + outputSlot.stackSize > outputSlot.getMaxStackSize()) {
                        continue;
                    }
                }
                return recipe;
            }
        }
        return null;
    }


}
