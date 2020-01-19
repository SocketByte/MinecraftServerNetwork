package pl.socketbyte.opengui;

public class FinalItemJob {

    private final int slot;
    private final GUIExtenderItem guiExtenderItem;

    public FinalItemJob(int slot, GUIExtenderItem guiExtenderItem) {
        this.slot = slot;
        this.guiExtenderItem = guiExtenderItem;
    }

    public FinalItemJob(GUIExtenderItem guiExtenderItem) {
        this.slot = -1;
        this.guiExtenderItem = guiExtenderItem;
    }

    public int getSlot() {
        return slot;
    }

    public GUIExtenderItem getGuiExtenderItem() {
        return guiExtenderItem;
    }
}
