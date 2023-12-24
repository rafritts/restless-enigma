package io.restless.enigma.model;

import java.util.HashMap;

public class DefaultEnigmaSettings {

  public static final String CRIB = "LNXLWSOSBQ ";

  public EnigmaSettings getDefaultSettings() {
    EnigmaSettings defaultSettings = new EnigmaSettings();
    defaultSettings.setRotor1Position('G');
    defaultSettings.setRotor2Position('E');
    defaultSettings.setRotor3Position('M');
    HashMap<Character, Character> plugboardSwaps = new HashMap<>();
    plugboardSwaps.put('A', 'T');
    plugboardSwaps.put('T', 'A');
    plugboardSwaps.put('E', 'L');
    plugboardSwaps.put('L', 'E');
    // These extra plugboard swaps make the computational requirements
    // to break enigma not feasible for a cloud demo app.
    //        plugboardSwaps.put('I', 'M');
    //        plugboardSwaps.put('M', 'I');
    //        plugboardSwaps.put('O', 'P');
    //        plugboardSwaps.put('P', 'O');
    //        plugboardSwaps.put('S', 'U');
    //        plugboardSwaps.put('U', 'S');
    //        plugboardSwaps.put('D', 'F');
    //        plugboardSwaps.put('F', 'D');
    //        plugboardSwaps.put('H', 'J');
    //        plugboardSwaps.put('J', 'H');
    //        plugboardSwaps.put('C', 'R');
    //        plugboardSwaps.put('R', 'C');
    //        plugboardSwaps.put('B', 'N');
    //        plugboardSwaps.put('N', 'B');
    //        plugboardSwaps.put('K', 'Q');
    //        plugboardSwaps.put('Q', 'K');
    defaultSettings.setPlugboardSwaps(plugboardSwaps);
    return defaultSettings;
  }
}
