package uk.co.qmunity.lib.client.gui.widget;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.lang3.text.WordUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.FMLClientHandler;

public class GuiAnimatedStat extends BaseWidget implements IGuiAnimatedStat, IGuiWidget {

    private IGuiAnimatedStat affectingStat;
    private ItemStack iStack;
    private String texture = "";
    private final GuiScreen gui;
    private final List<String> textList = new ArrayList<String>();
    private int baseX;
    private int baseY;
    private int affectedY;
    private int width;
    private int height;

    private int oldBaseX;
    private int oldAffectedY;
    private int oldWidth;
    private int oldHeight;
    private boolean isClicked = false;
    private int minWidth = 17;
    private int minHeight = 17;
    private final int backGroundColor;
    private String title;
    private boolean leftSided; // this boolean determines if the stat is going
    // to expand to the left or right.
    private boolean doneExpanding;
    private RenderItem itemRenderer;
    private float textSize;
    private float textScale = 1F;
    public static final int MAX_CHAR = 28;
    public static final int ANIMATED_STAT_SPEED = 10;
    private ResourceLocation iconResLoc;
    private IWidgetListener listener;

    public GuiAnimatedStat(GuiScreen gui, String title, int xPos, int yPos, int backGroundColor, IGuiAnimatedStat affectingStat,
            boolean leftSided) {

        super(-1, xPos, yPos, yPos, backGroundColor);

        this.gui = gui;
        baseX = xPos;
        baseY = yPos;
        this.affectingStat = affectingStat;
        width = minWidth;
        height = minHeight;
        this.backGroundColor = backGroundColor;
        setTitle(title);
        texture = "";
        this.leftSided = leftSided;
        if (gui != null) {
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth,
                    Minecraft.getMinecraft().displayHeight);
            if (sr.getScaledWidth() < 520) {
                textSize = (sr.getScaledWidth() - 220) * 0.0033F;
            } else {
                textSize = 1F;
            }
        } else {
            textSize = 1;
        }

        affectedY = baseY;
        if (affectingStat != null) {
            affectedY += affectingStat.getAffectedY() + affectingStat.getHeight();
        }
    }

    public GuiAnimatedStat(GuiScreen gui, int backgroundColor) {

        this(gui, "", 0, 0, backgroundColor, null, false);
    }

    public GuiAnimatedStat(GuiScreen gui, int backgroundColor, ItemStack icon) {

        this(gui, backgroundColor);
        iStack = icon;
    }

    public GuiAnimatedStat(GuiScreen gui, int backgroundColor, String texture) {

        this(gui, backgroundColor);
        this.texture = texture;
    }

    public GuiAnimatedStat(GuiScreen gui, String title, ItemStack icon, int xPos, int yPos, int backGroundColor,
            IGuiAnimatedStat affectingStat, boolean leftSided) {

        this(gui, title, xPos, yPos, backGroundColor, affectingStat, leftSided);
        iStack = icon;
    }

    public GuiAnimatedStat(GuiScreen gui, String title, String texture, int xPos, int yPos, int backGroundColor,
            IGuiAnimatedStat affectingStat, boolean leftSided) {

        this(gui, title, xPos, yPos, backGroundColor, affectingStat, leftSided);
        this.texture = texture;
    }

    @Override
    public void setParentStat(IGuiAnimatedStat stat) {

        affectingStat = stat;
    }

    @Override
    public Rectangle getButtonScaledRectangle(int origX, int origY, int width, int height) {

        int scaledX = (int) ((origX - baseX - (leftSided ? width : 0)) * textSize);
        int scaledY = (int) ((origY - affectedY) * textSize);

        // scaledX = (int)(origX * textSize);
        // scaledY = (int)(origY * textSize);
        return new Rectangle(scaledX + baseX + (leftSided ? (int) (width * textSize) : 0), scaledY + affectedY, (int) (width * textSize),
                (int) (height * textSize));
    }

    @Override
    public void scaleTextSize(float scale) {

        textSize *= scale;
        textScale = scale;
    }

    @Override
    public boolean isLeftSided() {

        return leftSided;
    }

    @Override
    public void setLeftSided(boolean leftSided) {

        this.leftSided = leftSided;
    }

    @Override
    public IGuiAnimatedStat setText(List<String> text) {

        textList.clear();
        for (String line : text) {
            for (String s : WordUtils.wrap(I18n.format(line), (int) (MAX_CHAR / textScale)).split(System.getProperty("line.separator"))) {
                textList.add(s);
            }
        }
        return this;
    }

    @Override
    public IGuiAnimatedStat setText(String text) {

        textList.clear();
        for (String s : WordUtils.wrap(I18n.format(text), (int) (MAX_CHAR / textScale)).split(System.getProperty("line.separator"))) {
            textList.add(s);
        }
        return this;
    }

    @Override
    public void setTextWithoutCuttingString(List<String> text) {

        textList.clear();
        textList.addAll(text);
    }

    @Override
    public void setMinDimensionsAndReset(int minWidth, int minHeight) {

        this.minWidth = minWidth;
        this.minHeight = minHeight;
        width = minWidth;
        height = minHeight;
    }

    @Override
    public void update() {

        oldBaseX = baseX;
        oldAffectedY = affectedY;
        oldWidth = width;
        oldHeight = height;

        FontRenderer fontRenderer = FMLClientHandler.instance().getClient().fontRenderer;
        doneExpanding = true;
        if (isClicked) {
            // calculate the width and height needed for the box to fit the
            // strings.
            int maxWidth = getMaxWidth(fontRenderer);
            int maxHeight = getMaxHeight(fontRenderer);
            // expand the box

            for (int i = 0; i < ANIMATED_STAT_SPEED; i++) {
                if (width < maxWidth) {
                    width++;
                    doneExpanding = false;
                }
                if (height < maxHeight) {
                    height++;
                    doneExpanding = false;
                }
                if (width > maxWidth)
                    width--;
                if (height > maxHeight)
                    height--;
            }

        } else {
            for (int i = 0; i < ANIMATED_STAT_SPEED; i++) {
                if (width > minWidth)
                    width--;
                if (height > minHeight)
                    height--;
            }
            doneExpanding = false;
        }

        affectedY = baseY;
        if (affectingStat != null) {
            affectedY += affectingStat.getAffectedY() + affectingStat.getHeight();
        }
    }

    protected int getMaxWidth(FontRenderer fontRenderer) {

        int maxWidth = fontRenderer.getStringWidth(title);

        for (String line : textList) {
            if (fontRenderer.getStringWidth(line) > maxWidth)
                maxWidth = fontRenderer.getStringWidth(line);
        }
        maxWidth = (int) (maxWidth * textSize);
        maxWidth += 20;
        return maxWidth;
    }

    protected int getMaxHeight(FontRenderer fontRenderer) {

        int maxHeight = 12;
        if (textList.size() > 0) {
            maxHeight += 4 + textList.size() * 10;
        }
        maxHeight = (int) (maxHeight * textSize);
        return maxHeight;
    }

    @Override
    public void render(FontRenderer fontRenderer, float zLevel, float partialTicks) {

        int renderBaseX = (int) (oldBaseX + (baseX - oldBaseX) * partialTicks);
        int renderAffectedY = (int) (oldAffectedY + (affectedY - oldAffectedY) * partialTicks);
        int renderWidth = (int) (oldWidth + (width - oldWidth) * partialTicks);
        int renderHeight = (int) (oldHeight + (height - oldHeight) * partialTicks);

        if (leftSided)
            renderWidth *= -1;
        Gui.drawRect(renderBaseX, renderAffectedY /* + 1 */, renderBaseX + renderWidth /*- 1*/, renderAffectedY + renderHeight,
                backGroundColor);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glLineWidth(3.0F);
        GL11.glColor4d(0, 0, 0, 1);
        Tessellator tess = Tessellator.instance;
        tess.startDrawing(GL11.GL_LINE_LOOP);
        tess.addVertex(renderBaseX, renderAffectedY, zLevel);
        tess.addVertex(renderBaseX + renderWidth, renderAffectedY, zLevel);
        tess.addVertex(renderBaseX + renderWidth, renderAffectedY + renderHeight, zLevel);
        tess.addVertex(renderBaseX, renderAffectedY + renderHeight, zLevel);
        tess.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        if (leftSided)
            renderWidth *= -1;
        // if done expanding, draw the information
        if (doneExpanding) {
            GL11.glPushMatrix();
            GL11.glTranslated(renderBaseX + (leftSided ? -renderWidth : 16), renderAffectedY, 0);
            GL11.glScaled(textSize, textSize, textSize);
            GL11.glTranslated(-renderBaseX - (leftSided ? -renderWidth : 16), -renderAffectedY, 0);
            fontRenderer.drawStringWithShadow(title, renderBaseX + (leftSided ? -renderWidth + 2 : 18), renderAffectedY + 2, 0xFFFF00);
            for (int i = 0; i < textList.size(); i++) {

                if (textList.get(i).contains("\u00a70") || textList.get(i).contains(EnumChatFormatting.DARK_RED.toString())) {
                    fontRenderer.drawString(textList.get(i), renderBaseX + (leftSided ? -renderWidth + 2 : 18), renderAffectedY + i * 10
                            + 12, 0xFFFFFF);
                } else {
                    fontRenderer.drawStringWithShadow(textList.get(i), renderBaseX + (leftSided ? -renderWidth + 2 : 18), renderAffectedY
                            + i * 10 + 12, 0xFFFFFF);
                }
            }

            GL11.glPopMatrix();
        }
        if (renderHeight > 16 && renderWidth > 16) {
            GL11.glColor4d(1, 1, 1, 1);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            if (iStack == null) {
                if (iconResLoc == null)
                    iconResLoc = new ResourceLocation(texture);
                drawTexture(iconResLoc, renderBaseX - (leftSided ? 16 : 0), renderAffectedY);
            } else if (gui != null || !(iStack.getItem() instanceof ItemBlock)) {
                renderItem(fontRenderer, renderBaseX - (leftSided ? 16 : 0), renderAffectedY, iStack);
            }
        }
    }

    protected void renderItem(FontRenderer fontRenderer, int x, int y, ItemStack stack) {

        if (itemRenderer == null)
            itemRenderer = new RenderItem();
        GL11.glPushMatrix();
        GL11.glTranslated(0, 0, -50);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.enableGUIStandardItemLighting();
        itemRenderer.renderItemAndEffectIntoGUI(fontRenderer, FMLClientHandler.instance().getClient().renderEngine, stack, x, y);
        itemRenderer.renderItemOverlayIntoGUI(fontRenderer, Minecraft.getMinecraft().getTextureManager(), stack, x, y);

        GL11.glEnable(GL11.GL_ALPHA_TEST);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    public static void drawTexture(ResourceLocation texture, int x, int y) {

        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + 16, 0, 0.0, 1.0);
        tessellator.addVertexWithUV(x + 16, y + 16, 0, 1.0, 1.0);
        tessellator.addVertexWithUV(x + 16, y, 0, 1.0, 0.0);
        tessellator.addVertexWithUV(x, y, 0, 0.0, 0.0);
        tessellator.draw();
    }

    /*
     * button: 0 = left 1 = right 2 = middle
     */
    @Override
    public void onMouseClicked(int x, int y, int button) {

        if (button == 0 && mouseIsHoveringOverStat(x, y)) {
            isClicked = !isClicked;
            listener.actionPerformed(this);
        }
    }

    @Override
    public void closeWindow() {

        isClicked = false;
    }

    @Override
    public void openWindow() {

        isClicked = true;
    }

    @Override
    public boolean isClicked() {

        return isClicked;
    }

    private boolean mouseIsHoveringOverIcon(int x, int y) {

        if (leftSided) {
            return x <= baseX && x >= baseX - 16 && y >= affectedY && y <= affectedY + 16;
        } else {
            return x >= baseX && x <= baseX + 16 && y >= affectedY && y <= affectedY + 16;
        }
    }

    private boolean mouseIsHoveringOverStat(int x, int y) {

        if (leftSided) {
            return x <= baseX && x >= baseX - width && y >= affectedY && y <= affectedY + height;
        } else {
            return x >= baseX && x <= baseX + width && y >= affectedY && y <= affectedY + height;
        }
    }

    @Override
    public int getAffectedY() {

        return affectedY;
    }

    @Override
    public int getBaseX() {

        return baseX;
    }

    @Override
    public int getBaseY() {

        return baseY;
    }

    @Override
    public int getHeight() {

        return height;
    }

    @Override
    public int getWidth() {

        return width;
    }

    @Override
    public void setBaseY(int y) {

        baseY = y;
    }

    @Override
    public void setTitle(String title) {

        this.title = I18n.format(title);
    }

    @Override
    public boolean isDoneExpanding() {

        return doneExpanding;
    }

    @Override
    public void setBaseX(int x) {

        baseX = x;
    }

    @Override
    public String getTitle() {

        return title;
    }

    @Override
    public void setListener(IWidgetListener gui) {

        listener = gui;
    }

    @Override
    public int getID() {

        return -1;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTick) {

        render(Minecraft.getMinecraft().fontRenderer, 0, partialTick);

    }

    @Override
    public void addTooltip(int mouseX, int mouseY, List<String> curTip, boolean shiftPressed) {

        if (mouseIsHoveringOverIcon(mouseX, mouseY)) {
            curTip.add(title);
        }
    }

    @Override
    public Rectangle getBounds() {

        return new Rectangle(baseX - (leftSided ? width : 0), affectedY, width, height);
    }

}
