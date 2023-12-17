package io.restless.enigma.model;

import lombok.Getter;

public class RotorSet {

  private Character[] rotor1mapping = {
    'U', 'Q', 'B', 'O', 'J', 'H', 'W', 'Z', 'I', 'K', 'D', 'V', 'A', 'S', 'T', 'E', 'L', 'R', 'M',
    'P', 'F', 'G', 'N', 'Y', 'X', 'C'
  };
  private Character[] rotor2mapping = {
    'X', 'U', 'A', 'P', 'I', 'E', 'J', 'K', 'Y', 'L', 'T', 'B', 'F', 'Z', 'N', 'H', 'V', 'D', 'G',
    'C', 'M', 'R', 'W', 'S', 'O', 'Q'
  };
  private Character[] rotor3mapping = {
    'J', 'Z', 'N', 'S', 'A', 'I', 'O', 'C', 'P', 'T', 'H', 'B', 'W', 'X', 'R', 'Y', 'F', 'G', 'U',
    'M', 'K', 'V', 'E', 'D', 'Q', 'L'
  };

  private Character rotor1notch = 'Q';
  private Character rotor2notch = 'E';
  private Character rotor3notch = 'V';

  @Getter Rotor rotor1 = new Rotor(rotor1mapping, rotor1notch);
  @Getter Rotor rotor2 = new Rotor(rotor2mapping, rotor2notch);
  @Getter Rotor rotor3 = new Rotor(rotor3mapping, rotor3notch);
}
