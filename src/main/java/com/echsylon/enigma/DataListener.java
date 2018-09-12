package com.echsylon.enigma;

public interface DataListener {
    String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    int ALPHABET_LENGTH = ALPHABET.length();

    void onDataReceived(char character, boolean isReflected);

}
