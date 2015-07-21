package uk.co.qmunity.lib.client.gui.widget;

import java.awt.Rectangle;
import java.util.List;

public interface IGuiWidget {

    public void setListener(IWidgetListener gui);

    public int getID();

    public void render(int mouseX, int mouseY, float partialTick);

    public void onMouseClicked(int mouseX, int mouseY, int button);

    public void addTooltip(int mouseX, int mouseY, List<String> curTip, boolean shiftPressed);

    public void update();

    public Rectangle getBounds();
}
