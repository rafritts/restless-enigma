package io.restless.enigma.endpoints.model;

import io.restless.enigma.model.EnigmaSettings;
import lombok.Data;

@Data
public class BombeBreakResponse {

  String decodedMessage;
  EnigmaSettings settings;
  String numberOfAttempts;
  String elapsedTime;
}
