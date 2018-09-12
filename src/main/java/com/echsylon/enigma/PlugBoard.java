package com.echsylon.enigma;

import java.util.HashMap;

public class PlugBoard implements DataListener, TickListener {
    private final HashMap<Character, Character> mapping;

    private DataListener leftPeer;
    private DataListener rightPeer;
    private TickListener tickListener;


    PlugBoard() {
        this.mapping = new HashMap<>();
    }


    @Override
    public void onDataReceived(char character, boolean isReflected) {
        char c = mapping.getOrDefault(character, character);
        if (isReflected) {
            rightPeer.onDataReceived(c, true);
        } else {
            leftPeer.onDataReceived(c, false);
        }
    }

    @Override
    public void onTick() {
        if (tickListener == null) return;
        tickListener.onTick();
    }


    public PlugBoard map(char from, char to) {
        mapping.remove(from);
        mapping.remove(to);
        mapping.put(from, to);
        mapping.put(to, from);

        return this;
    }


    void setLeftPeer(DataListener listener) {
        leftPeer = listener;
    }

    void setRightPeer(DataListener listener) {
        rightPeer = listener;
    }

    void setTickListener(TickListener listener) {
        tickListener = listener;
    }

}
