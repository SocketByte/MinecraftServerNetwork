package org.mcservernetwork.commons.net;

public enum Channel {
    TRANSFER_REQUEST, TRANSFER_ACCEPT,
    PROXY,
    LOGGER,
    TELEPORT,
    PING, PONG,
    VERIFY;
    public static String SECTOR(String name) {
        return "SECTOR:" + name;
    }
}
