package com.echsylon.enigma;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Application {

    private static final String TEST_INPUT = "FROM his shoulder Hiawatha\n" +
            "Took the camera of rosewood\n" +
            "Made of sliding folding rosewood\n" +
            "Neatly put it all together\n" +
            "In its case it lay compactly\n" +
            "Folded into nearly nothing\n" +
            "But he opened out the hinges\n" +
            "Pushed and pulled the joints\n" +
            "and hinges\n" +
            "Till it looked all squares\n" +
            "and oblongs\n" +
            "Like a complicated figure\n" +
            "In the Second Book of Euclid";

    public static void main(String[] args) {
        Enigma enigma = new Enigma();
        enigma.addReflector(ConfigurationHelper.newReflectorOfTypeLeanB());
        enigma.addStaticRotor(ConfigurationHelper.newStaticRotorOfTypeBeta('A'));
        enigma.addRotor(ConfigurationHelper.newRotorOfTypeIII('X'));
        enigma.addRotor(ConfigurationHelper.newRotorOfTypeIV('L'));
        enigma.addRotor(ConfigurationHelper.newRotorOfTypeI('E'));
        enigma.addPlugBoard(ConfigurationHelper.newPlugBoard()
                .map('H', 'Q')
                .map('E', 'X')
                .map('I', 'P')
                .map('T', 'R')
                .map('B', 'Y'));

        byte[] bytes = TEST_INPUT.getBytes(StandardCharsets.UTF_8);
        InputStream input = new ByteArrayInputStream(bytes);
        OutputStream output = System.out;

        enigma.scramble(input, output);
    }
}

/*
    QVPQS OKOIL PUBKJ ZPISF XDW
    BHCNS CXNUO AATZX SRCFY DGU
    FLPNX GXIXT YJUJR CAUGE UNCFM KUF
    WJFGK CIIRG XODJG VCGPQ OH
    ALWEB UHTZM OXIIV XUEFP RPR
    KCGVP FPYKI KITLB URVGT SFU
    SMBNK FRIIM PDOFJ VTTUG RZM
    UVCYL FDZPG IBXRE WXUEB ZQJO
    YMHIP GRRE
    GOHET UXDTW LCMMW AVNVJ VH
    OUFAN TQACK
    KTOZZ RDABQ NNVPO IEFQA FS
    VVICV UDUER EYNPF FMNBJ VGQ
*/