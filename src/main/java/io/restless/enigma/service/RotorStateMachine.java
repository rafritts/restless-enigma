package io.restless.enigma.service;

import io.restless.enigma.model.EnigmaSettings;
import io.restless.enigma.model.RotorSet;

public class RotorStateMachine {

    RotorSet rotorSet = new RotorSet();

    EnigmaSettings settings;

    int rotor1offset;
    int rotor2offset;
    int rotor3offset;

    private Character[] reflectorMapping = {'Y', 'R', 'U', 'H', 'Q', 'S', 'L', 'D', 'P', 'X', 'N', 'G', 'O', 'K', 'M', 'I', 'E', 'B', 'F', 'Z', 'C', 'W', 'V', 'J', 'A', 'T'};

    public RotorStateMachine(EnigmaSettings enigmaSettings) {
        settings = enigmaSettings;
        initialASCIIOffsetCalibration();
    }

    public void resetSettings() {
        initialASCIIOffsetCalibration();
    }

    public Character resolveCharacter(Character input) {
        input = settings.getPlugboardSwaps().getOrDefault(input, input);
        Character forward = resolveForwards(input);
        Character reflected = reflectorMapping[(forward - 'A')];
        Character backwards = resolveBackwards(reflected);
        Character resolved = settings.getPlugboardSwaps().getOrDefault(backwards, backwards);
        rotateRotors();
        return resolved;
    }

    private Character resolveForwards(Character input) {
        Character rotor1resolved = rotorSet.getRotor1().mappings()[(input - 'A' + rotor1offset) % 26];
        Character rotor2resolved = rotorSet.getRotor2().mappings()[(rotor1resolved - 'A' + rotor2offset) % 26];
        return rotorSet.getRotor3().mappings()[(rotor2resolved - 'A' + rotor3offset) % 26];
    }

    private Character resolveBackwards(Character input) {
        int indexForRotor3 = findIndexOfMappedChar(rotorSet.getRotor3().mappings(), input);
        Character rotor3resolved = (char) ((indexForRotor3 - rotor3offset + 26) % 26 + 'A');

        int indexForRotor2 = findIndexOfMappedChar(rotorSet.getRotor2().mappings(), rotor3resolved);
        Character rotor2resolved = (char) ((indexForRotor2 - rotor2offset + 26) % 26 + 'A');

        int indexForRotor1 = findIndexOfMappedChar(rotorSet.getRotor1().mappings(), rotor2resolved);
        return (char) ((indexForRotor1 - rotor1offset + 26) % 26 + 'A');
    }

    private int findIndexOfMappedChar(Character[] mapping, Character c) {
        for (int i = 0; i < mapping.length; i++) {
            if (mapping[i] == c) {
                return i;
            }
        }
        throw new IllegalStateException("Character not found in rotor mapping");
    }

    private void rotateRotors() {
        rotor3offset = (rotor3offset + 1) % 26;
        if (rotor3offset == (rotorSet.getRotor3().notch() - 'A')) {
            rotor2offset = (rotor2offset + 1) % 26;
            if (rotor2offset == (rotorSet.getRotor2().notch() - 'A')) {
                rotor1offset = (rotor1offset + 1) % 26;
            }
        }
    }

    private void initialASCIIOffsetCalibration() {
        this.rotor1offset = this.settings.getRotor1Position() - 'A';
        this.rotor2offset = this.settings.getRotor2Position() - 'A';
        this.rotor3offset = this.settings.getRotor3Position() - 'A';
    }

}
