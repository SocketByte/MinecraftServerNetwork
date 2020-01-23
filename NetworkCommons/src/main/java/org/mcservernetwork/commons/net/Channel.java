package org.mcservernetwork.commons.net;

public enum Channel {
    TRANSFER_REQUEST, TRANSFER_ACCEPT,
    PROXY,
    LOGGER,
    STATUS,
    VERIFY;
    public static String sector(String name) {
        return "SECTOR:" + name;
    }
}
