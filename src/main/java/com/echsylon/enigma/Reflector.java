package com.echsylon.enigma;

public class Reflector implements DataListener {

    private final String type;
    private final String mapping;

    private DataListener peer;


    Reflector(String type, String mapping) {
        this.type = type;
        this.mapping = mapping;
    }


    @Override
    public void onDataReceived(char character, boolean isReflected) {
        int charIndex = ALPHABET.indexOf(character);
        if (charIndex == -1) return;
        char scrambledChar = mapping.charAt(charIndex);
        peer.onDataReceived(scrambledChar, true);
    }


    void setPeer(DataListener peer) {
        this.peer = peer;
    }

    String getType() {
        return type;
    }

}
