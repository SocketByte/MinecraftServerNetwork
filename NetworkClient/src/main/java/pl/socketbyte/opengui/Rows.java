package pl.socketbyte.opengui;

public enum Rows {

    ONE(9),
    TWO(18),
    THREE(27),
    FOUR(36),
    FIVE(45),
    SIX(54);

    private int slots;

    Rows(int slots) {
        this.slots = slots;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }
}
