package io.restless.enigma.model;

import java.util.HashMap;
import lombok.Data;

@Data
public class EnigmaSettings {

  public static final Integer MAX_PLUBGBOARD_CABLES = 1;

  Character rotor1Position;
  Character rotor2Position;
  Character rotor3Position;

  HashMap<Character, Character> plugboardSwaps;
}
