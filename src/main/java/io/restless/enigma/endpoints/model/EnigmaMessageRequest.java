package io.restless.enigma.endpoints.model;

import io.restless.enigma.model.EnigmaSettings;
import lombok.Data;

@Data
public class EnigmaMessageRequest {

    String message;
    EnigmaSettings settings;

}
