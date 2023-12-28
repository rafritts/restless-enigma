package io.restless.enigma.util;

import io.restless.enigma.model.EnigmaSettings;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class PlugboardUtil {

    private static List<HashMap<Character, Character>> plugboardSettingsCache;

    public static List<HashMap<Character, Character>> generatePlugboardSettings() {
        if (plugboardSettingsCache != null) return plugboardSettingsCache;
        List<HashMap<Character, Character>> allSettings = new ArrayList<>();
        List<Character[]> allPairs = generateLetterPairs();
        for (int numCables = 0; numCables <= EnigmaSettings.MAX_PLUBGBOARD_CABLES; numCables++) {
            allSettings.addAll(combinePairs(allPairs, numCables, 0, new HashMap<>()));
        }

        plugboardSettingsCache = allSettings;
        return plugboardSettingsCache;
    }

    private static List<Character[]> generateLetterPairs() {
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        // Generate all pairs of letters
        List<Character[]> allPairs = new ArrayList<>();
        for (int i = 0; i < alphabet.length; i++) {
            for (int j = i + 1; j < alphabet.length; j++) {
                allPairs.add(new Character[]{alphabet[i], alphabet[j]});
            }
        }
        return allPairs;
    }

    private static List<HashMap<Character, Character>> combinePairs(List<Character[]> allPairs, int numCables, int start, HashMap<Character, Character> current) {
        List<HashMap<Character, Character>> combinations = new ArrayList<>();
        if (numCables == 0) {
            combinations.add(new HashMap<>(current));
            return combinations;
        }

        for (int i = start; i <= allPairs.size() - numCables; i++) {
            HashMap<Character, Character> newCurrent = new HashMap<>(current);
            Character[] pair = allPairs.get(i);
            newCurrent.put(pair[0], pair[1]);
            newCurrent.put(pair[1], pair[0]);
            combinations.addAll(combinePairs(allPairs, numCables - 1, i + 1, newCurrent));
        }

        return combinations;
    }
}
