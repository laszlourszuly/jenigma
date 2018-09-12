package com.echsylon.enigma;

public class ConfigurationHelper {

    public static TickingRotor newRotorOfTypeI(char startCharacter) {
        return new TickingRotor("I", "EKMFLGDQVZNTOWYHXUSPAIBRCJ", "R", startCharacter);
    }

    public static TickingRotor newRotorOfTypeII(char startCharacter) {
        return new TickingRotor("II", "AJDKSIRUXBLHWTMCQGZNPYFVOE", "F", startCharacter);
    }

    public static TickingRotor newRotorOfTypeIII(char startCharacter) {
        return new TickingRotor("III", "BDFHJLCPRTXVZNYEIWGAKMUSQO", "W", startCharacter);
    }

    public static TickingRotor newRotorOfTypeIV(char startCharacter) {
        return new TickingRotor("IV", "ESOVPZJAYQUIRHXLNFTGKDCMWB", "K", startCharacter);
    }

    public static TickingRotor newRotorOfTypeV(char startCharacter) {
        return new TickingRotor("V", "VZBRGITYUPSDNHLXAWMJQOFECK", "A", startCharacter);
    }

    public static TickingRotor newRotorOfTypeVI(char startCharacter) {
        return new TickingRotor("VI", "JPGVOUMFYQBENHZRDKASXLICTW", "AN", startCharacter);
    }

    public static TickingRotor newRotorOfTypeVII(char startCharacter) {
        return new TickingRotor("VII", "NZJHGRCXMYSWBOUFAIVLPEKQDT", "AN", startCharacter);
    }

    public static TickingRotor newRotorOfTypeVIII(char startCharacter) {
        return new TickingRotor("VIII", "FKQHTLXOCBJSPDZRAMEWNIUYGV", "AN", startCharacter);
    }

    public static SilentRotor newStaticRotorOfTypeBeta(char startCharacter) {
        return new SilentRotor("BETA", "LEYJVCNIXWPBQMDRTAKZGFUHOS", startCharacter);
    }

    public static SilentRotor newStaticRotorOfTypeGamma(char startCharacter) {
        return new SilentRotor("GAMMA", "FSOKANUERHMBTIYCWLQPZXVGJD", startCharacter);
    }

    public static Reflector newReflectorOfTypeB() {
        return new Reflector("B", "YRUHQSLDPXNGOKMIEBFZCWVJAT");
    }

    public static Reflector newReflectorOfTypeC() {
        return new Reflector("C", "FVPJIAOYEDRZXWGCTKUQSBWXHL");
    }

    public static Reflector newReflectorOfTypeLeanB() {
        return new Reflector("b", "ENKQAUYWJICOPBLMDXZVFTHRGS");
    }

    public static Reflector newReflectorOfTypeLeanC() {
        return new Reflector("c", "RDOBJNTKVEHMLFCWZAXGYIPSUQ");
    }

    public static PlugBoard newPlugBoard() {
        return new PlugBoard();
    }

}
