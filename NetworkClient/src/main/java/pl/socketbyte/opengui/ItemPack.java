package pl.socketbyte.opengui;

import pl.socketbyte.opengui.event.ElementResponse;

public class ItemPack {

    private int slot;
    private ItemBuilder itemBuilder;
    private ElementResponse elementResponse;

    public ItemPack(int slot, ItemBuilder itemBuilder) {
        this.itemBuilder = itemBuilder;
        this.slot = slot;
    }

    public ItemPack(int slot, ItemBuilder itemBuilder, ElementResponse elementResponse) {
        this.itemBuilder = itemBuilder;
        this.slot = slot;
        this.elementResponse = elementResponse;
    }

    public ItemPack(ItemBuilder itemBuilder) {
        this.itemBuilder = itemBuilder;
    }

    public ItemPack(ItemBuilder itemBuilder, ElementResponse elementResponse) {
        this.itemBuilder = itemBuilder;
        this.elementResponse = elementResponse;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public ItemBuilder getItemBuilder() {
        return itemBuilder;
    }

    public void setItemBuilder(ItemBuilder itemBuilder) {
        this.itemBuilder = itemBuilder;
    }

    public ElementResponse getElementResponse() {
        return elementResponse;
    }

    public void setElementResponse(ElementResponse elementResponse) {
        this.elementResponse = elementResponse;
    }
}
