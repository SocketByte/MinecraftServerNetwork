package org.mcservernetwork.commons.net;

public enum Channel {
    TRANSFER, PROXY, LOGGER, TELEPORT, ACCEPT, VERIFY;
    public static String SECTOR(int id) {
        return "SECTOR:" + id;
    }
}
