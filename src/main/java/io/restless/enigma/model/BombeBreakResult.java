package io.restless.enigma.model;

import lombok.Data;

@Data
public class BombeBreakResult {

    String numberOfAttempts;
    String decodedMessage;
    EnigmaSettings settings;
    String elapsedTime;

}
