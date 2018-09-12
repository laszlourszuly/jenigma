package com.echsylon.enigma;

class TickingRotor extends SilentRotor implements TickListener {
    private final String tickers;

    private TickListener tickListener;


    TickingRotor(String type, String mapping, String tickers, char startCharacter) {
        super(type, mapping, startCharacter);
        this.tickers = tickers;
    }


    @Override
    public void onTick() {
        super.onTick();

        if (tickers == null || tickListener == null) return;

        char c = ALPHABET.charAt(offset);
        if (tickers.contains(Character.toString(c))) {
            tickListener.onTick();
        }
    }


    void setTickListener(TickListener listener) {
        tickListener = listener;
    }

}
