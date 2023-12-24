package io.restless.enigma.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BombeStatus {

    EnigmaSettings currentSetting;
    String numberOfAttempts;
    String elapsedTime;

}
