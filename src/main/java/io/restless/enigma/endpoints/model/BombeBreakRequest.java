package io.restless.enigma.endpoints.model;

import lombok.Data;

@Data
public class BombeBreakRequest {

    String encodedMessage;
    String searchPhrase;

}
