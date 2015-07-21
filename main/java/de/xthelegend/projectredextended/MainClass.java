package de.xthelegend.projectredextended;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.xthelegend.projectredextended.projectred.api.pr_items;
import de.xthelegend.projectredextended.alloy_furnace.IAlloyFurnaceRegistry;
import de.xthelegend.projectredextended.alloy_furnace.TileAlloyFurnace;
import de.xthelegend.projectredextended.api.RedExtendedAPI;
import de.xthelegend.projectredextended.api.projectRedExtendedAPI;
import de.xthelegend.projectredextended.lib.GUIHandler;
import de.xthelegend.projectredextended.lib.base.GUI_NUMBER_IDS;
import de.xthelegend.projectredextended.networking.MainNetworkHandler;
import de.xthelegend.projectredextended.project_table.TileProjectTable;
import de.xthelegend.projectredextended.seed_bag.ItemSeedBag;
import mrtjp.core.color.Colors;
import de.xthelegend.projectredextended.api.PRColors;
import de.xthelegend.projectredextended.enchantments.EnchantmentDisjunction;
import de.xthelegend.projectredextended.enchantments.EnchantmentVorpal;

@Mod(modid = MainClass.MODID, version = MainClass.VERSION, name = MainClass.MOD_NAME)
public class MainClass

{
	/*public static CreativeTabs tab_exploration = (mrtjp.projectred.ProjectRedExploration.tabExploration());
	public static CreativeTabs tab_expansion = (mrtjp.projectred.ProjectRedExpansion.tabExpansion());*/
	
	public static CreativeTabs tabProjectExtended = new CreativeTabs("tabProjectExtended") {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return Item.getItemFromBlock(BlockIndigoFlower);
        }
    };
	
	public static CreativeTabs tab_exploration = tabProjectExtended;
	public static CreativeTabs tab_expansion = tabProjectExtended;
	
	
	@Instance(value=MainClass.MODID)
	public static MainClass instance;
	
	@SidedProxy(clientSide = "de.xthelegend.projectredextended.ClientProxy", serverSide = "de.xthelegend.projectredextended.ServerProxy")
	public static ServerProxy proxy;

	
    public static final String MODID = "zprojectxtended";
    public static final String VERSION = "1.7.10-BETA-0.1";
    public static final String MOD_NAME = "ProjectRed Extended";
    
    public static WorldGenerator WorldGeneratorProjectRedExtended;
    
    public static Block BlockFlaxCrop = new BlockFlaxCrop();
    public static Block BlockIndigoCrop = new BlockIndigoCrop();
    
    public static Block BlockIndigoFlower = new BlockIndigoFlower("indigo_flower");
    public static Item ItemFlaxSeed = new ItemFlaxSeed(BlockFlaxCrop , Blocks.farmland);
    public static Item ItemIndigoSeed = new ItemIndigoSeed(BlockIndigoCrop, Blocks.farmland);
    
    public static Block BlockRubberWood = new BlockRubberWood(Material.wood);
    public static Block BlockRubberLeaves = new BlockRubberLeaves();
    
    public static Block BlockRubberSapling = new BlockRubberSapling();
    //public static Block BlockRubberSapling = null /*//unused import*/ ;
    
    public static Item ItemIndigoDye = new ItemIndigoDye();
    public static Item ItemAthame = new ItemAthame();
    
    public static Block BlockCarvedMarble = new BlockCarvedMarble();
    
  
    public static Block BlockProjectTable = new de.xthelegend.projectredextended.project_table.BlockProjectTable().setGuiId(GUI_NUMBER_IDS.PROJECT_TABLE);
    public static Block BlockAlloyFurnace = new de.xthelegend.projectredextended.alloy_furnace.BlockAlloyFurnace();
    
    public static Item ItemSeedBag = new ItemSeedBag("seed_bag");
    
    public static Item ItemVoltmeter = new ItemVoltmeter();
    
    
    ///CONFIG/////
    
    public static int vorpalEnchantmentId;
    public static int disjunctionEnchantmentId;
    
    //////////////
    
    public static boolean enableDebugger = true;
    public static final Item Namefinder = new de.xthelegend.projectredextended.debug.Namefinder();
    
    
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	config.load();
    	
    	vorpalEnchantmentId = config.get("enchantment ids", "vorpalEnchantmentId", 110).getInt();
        disjunctionEnchantmentId = config.get("enchantment ids", "disjunctionEnchantmentId", 111).getInt();
    	
    	config.save();
    	
    	
    	
    	
    	projectRedExtendedAPI.init(new RedExtendedAPI());
    	
    	config.load();
    	config.save();
    	MinecraftForge.addGrassSeed(new ItemStack(ItemFlaxSeed, 2-1, 1-1), 5);
    	MinecraftForge.addGrassSeed(new ItemStack(ItemIndigoSeed, 2-1 , 1-1), 1);
    	GameRegistry.registerBlock(BlockFlaxCrop, BlockFlaxCrop.getUnlocalizedName());
    	GameRegistry.registerBlock(BlockIndigoCrop,BlockIndigoCrop.getUnlocalizedName());
    	GameRegistry.registerItem(ItemFlaxSeed , ItemFlaxSeed.getUnlocalizedName());
    	GameRegistry.registerItem(ItemIndigoSeed, ItemIndigoSeed.getUnlocalizedName());
    	GameRegistry.registerBlock(BlockIndigoFlower, BlockIndigoFlower.getUnlocalizedName());
    	GameRegistry.registerBlock(BlockRubberWood, BlockRubberWood.getUnlocalizedName());
    	GameRegistry.registerBlock(BlockRubberLeaves, BlockRubberLeaves.getUnlocalizedName());
    	GameRegistry.registerBlock(BlockRubberSapling,BlockRubberSapling.getUnlocalizedName());
    	GameRegistry.registerItem(ItemIndigoDye,ItemIndigoDye.getUnlocalizedName());
    	GameRegistry.registerItem(ItemAthame, ItemAthame.getUnlocalizedName());
    	GameRegistry.registerBlock(BlockCarvedMarble, BlockCarvedMarble.getUnlocalizedName());
    	GameRegistry.registerBlock(BlockProjectTable, BlockProjectTable.getUnlocalizedName());
    	GameRegistry.registerBlock(BlockAlloyFurnace, BlockAlloyFurnace.getUnlocalizedName());
    	GameRegistry.registerItem(ItemSeedBag, ItemSeedBag.getUnlocalizedName());
    	//GameRegistry.registerItem(ItemVoltmeter,ItemVoltmeter.getUnlocalizedName());
    	
    	if(enableDebugger)
    		GameRegistry.registerItem(Namefinder,Namefinder.getUnlocalizedName());
    	
    
    }
    
    @EventHandler
    public void Init(FMLInitializationEvent event)
    {
    	MainNetworkHandler.initNetwork();
    	NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GUIHandler());
    	GameRegistry.registerTileEntity(TileProjectTable.class, MainClass.MODID + ".projectTable");
    	GameRegistry.registerTileEntity(TileAlloyFurnace.class, MainClass.MODID + ".alloyFurnace");
    	
    	
    	GameRegistry.registerWorldGenerator(new WorldGeneratorProjectRedExtended(), 0);
    	
    	
    	OreDictionaryManager();
    	CraftingMgr();
    	AlloyFurnaceMgr();
    	EnchantmentLoader();

    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	PostCrafting();
    }
    
    public static ServerConfigurationManager serverConfigManager = null;
    
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        MinecraftServer server = event.getServer();
        ServerConfigurationManager m = server.getConfigurationManager(); //line 36
        serverConfigManager = m;
        System.out.println( "Ready to send chat" );
        
    }
    
    
    public static void CraftingMgr()
    {
    	
    	
    		
    	
    	
    	//ItemStack SilverIngot = new ItemStack(mrtjp.projectred.ProjecwtRedCore.itemPart() ,54 ,0);
    	GameRegistry.addShapelessRecipe(new ItemStack(ItemIndigoDye,2,0), BlockIndigoFlower);
    	
    	GameRegistry.addRecipe(new ItemStack(BlockCarvedMarble,4,0),new Object[]{ "zc", "cz", 'c', new ItemStack(mrtjp.projectred.ProjectRedExploration.blockDecoratives(),1,0), 'z', new ItemStack(mrtjp.projectred.ProjectRedExploration.blockDecoratives(),1,1)} );
    	GameRegistry.addShapelessRecipe(new ItemStack(Items.stick,8,0), BlockRubberWood);
    	
    
    }
    
    
    
    public static void AlloyFurnaceMgr()
    {
    	IAlloyFurnaceRegistry AlloyFurnaceRegistry = projectRedExtendedAPI.getInstance().getAlloyFurnaceRegistry();
    	
    	//AlloyFurnaceRegistry.addRecipe(REDINGOT().makeStack(1), new ItemStack(Items.redstone, 4), Items.iron_ingot);
    	//AlloyFurnaceRegistry.addRecipe(OreDictionary.getOreNames(), new ItemStack(Items.redstone, 4), Items.iron_ingot);
    }
    
    public static void PostCrafting()
    {
    	ItemStack silver_ingot = new ItemStack(GameRegistry.findItem("ProjRed|Core", "projectred.core.part"), 1, (short) 54);
		
		GameRegistry.addRecipe(new ItemStack(ItemAthame), "A", "B", 'A', silver_ingot, 'B', Items.stick );
    }
    
    public static Enchantment vorpal;
	public static Enchantment disjunction;
	
    public static void EnchantmentLoader()
    {
    	vorpal = new EnchantmentVorpal(MainClass.vorpalEnchantmentId, 10);
    	disjunction = new EnchantmentDisjunction(MainClass.disjunctionEnchantmentId, 10);
    	
    }
    
    public static void OreDictionaryManager()
    {
    	OreDictionary.registerOre("dyeBlue", new ItemStack(MainClass.ItemIndigoDye));
    }
    
    
}
