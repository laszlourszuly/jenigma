package com.echsylon.enigma;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Enigma {
    private final List<DataListener> dataChain;
    private int outputCharCount;


    public Enigma() {
        dataChain = new ArrayList<>();
        outputCharCount = 0;
    }

    public void addReflector(Reflector reflector) {
        dataChain.clear();
        dataChain.add(reflector);
    }

    public void addStaticRotor(SilentRotor rotor) {
        int lastIndex = dataChain.size() - 1;
        DataListener dataListener = dataChain.get(lastIndex);

        if (dataListener instanceof Reflector) {
            Reflector reflector = (Reflector) dataListener;
            reflector.setPeer(rotor);

            rotor.setLeftPeer(reflector);
            dataChain.add(rotor);
        }
    }

    public void addRotor(TickingRotor rotor) {
        int lastIndex = dataChain.size() - 1;
        DataListener dataListener = dataChain.get(lastIndex);

        if (dataListener instanceof TickingRotor ||
                dataListener instanceof SilentRotor) {

            SilentRotor leftPeer = (SilentRotor) dataListener;
            leftPeer.setRightPeer(rotor);

            rotor.setLeftPeer(leftPeer);
            rotor.setTickListener(leftPeer);
            dataChain.add(rotor);
        }
    }

    public void addPlugBoard(PlugBoard plugBoard) {
        int lastIndex = dataChain.size() - 1;
        DataListener dataListener = dataChain.get(lastIndex);

        if (dataListener instanceof TickingRotor) {
            TickingRotor leftPeer = (TickingRotor) dataListener;
            leftPeer.setRightPeer(plugBoard);

            plugBoard.setLeftPeer(leftPeer);
            plugBoard.setTickListener(leftPeer);
            dataChain.add(plugBoard);
        }
    }

    public void scramble(final InputStream inputStream, final OutputStream outputStream) {
        int lastIndex = dataChain.size() - 1;
        DataListener dataListener = dataChain.get(lastIndex);

        if (dataListener instanceof PlugBoard) {
            PlugBoard plugBoard = (PlugBoard) dataListener;
            plugBoard.setRightPeer((character, isReflected) -> writeResult(character, outputStream));
            readAll(inputStream, plugBoard);
        }
    }


    private void writeResult(char character, OutputStream outputStream) {
        try {
            // FIXME: This int->char conversion is nasty. Kill it with fire!
            outputStream.write(character);
            outputCharCount++;

            if (outputCharCount % 5 == 0) {
                outputStream.write(' ');
            }

            if (outputCharCount % 25 == 0) {
                outputStream.write('\n');
            }

            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readAll(InputStream inputStream, PlugBoard plugBoard) {
        int data;
        try {
            while ((data = inputStream.read()) != -1) {
                // FIXME: This int->char conversion is nasty. Kill it with fire!
                char c = Character.toUpperCase((char) data);
                if (DataListener.ALPHABET.indexOf(c) == -1) continue;

                plugBoard.onTick();
                plugBoard.onDataReceived(c, false);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

