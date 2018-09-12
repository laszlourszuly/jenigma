package com.echsylon.enigma;

class SilentRotor implements DataListener, TickListener {
    private final String type;
    private final String mapping;
    int offset;
    private DataListener leftPeer;
    private DataListener rightPeer;


    SilentRotor(String type, String mapping, char startCharacter) {
        this.offset = 0;
        this.type = type;
        this.mapping = mapping;

        int characterIndex = ALPHABET.indexOf(startCharacter);
        this.offset = characterIndex != -1 ? characterIndex : 0;
    }


    @Override
    public void onDataReceived(char input, boolean isReflected) {
        if (isReflected) {
            char c = scramble(input, mapping, ALPHABET);
            rightPeer.onDataReceived(c, true);
        } else {
            char c = scramble(input, ALPHABET, mapping);
            leftPeer.onDataReceived(c, false);
        }
    }

    @Override
    public void onTick() {
        offset = (offset + 1) % ALPHABET_LENGTH;
    }


    String getType() {
        return type;
    }

    void setLeftPeer(DataListener listener) {
        leftPeer = listener;
    }

    void setRightPeer(DataListener listener) {
        rightPeer = listener;
    }


    private char scramble(char input, String from, String to) {
        // Find zero based index
        int inputCharIndex = ALPHABET.indexOf(input);
        if (inputCharIndex == -1) return '\0';

        // Adjust for rotor offset
        int adjustedInputCharIndex = adjustForOffset(inputCharIndex, offset);
        char adjustedInputChar = ALPHABET.charAt(adjustedInputCharIndex);

        // Find the corresponding mapped char
        char outputChar = to.charAt(from.indexOf(adjustedInputChar));

        // Readjust for the previously applied rotor offset
        int outputCharIndex = ALPHABET.indexOf(outputChar);
        int adjustedOutputCharIndex = adjustForOffset(outputCharIndex, -offset);

        return ALPHABET.charAt(adjustedOutputCharIndex);
    }

    private int adjustForOffset(int index, int offset) {
        // Java %-operator will only calculate a trailing modulus.
        // We will have to manually adjust for heading modulus to
        // get the mathematically correct result, e.g. -1 % 3 = 2.
        int mod = (index + offset) % ALPHABET_LENGTH;
        return mod < 0 ? mod + ALPHABET_LENGTH : mod;
    }

}
