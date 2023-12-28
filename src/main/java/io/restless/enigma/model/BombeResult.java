package io.restless.enigma.model;

import lombok.Data;

@Data
public class BombeResult {

    String numberOfAttempts;
    String decodedMessage;
    EnigmaSettings settings;
    String elapsedTime;
    BombeResultStatusEnum bombeResultStatus;

}
