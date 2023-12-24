package io.restless.enigma.endpoints;

import io.restless.enigma.endpoints.model.EnigmaMessageRequest;
import io.restless.enigma.endpoints.model.EnigmaMessageResponse;
import io.restless.enigma.service.EnigmaMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("enigma")
public class EnigmaMachineEndpoints {

  private final EnigmaMachineService enigmaMachineService;

  @Autowired
  public EnigmaMachineEndpoints(EnigmaMachineService enigmaMachineService) {
    this.enigmaMachineService = enigmaMachineService;
  }

  @PostMapping(value ="encode-decode", produces = MediaType.APPLICATION_JSON_VALUE)
  public EnigmaMessageResponse encodeMessage(@RequestBody EnigmaMessageRequest request) {
     EnigmaMessageResponse response = new EnigmaMessageResponse();
     response.setEncodedMessage(enigmaMachineService.encodeMessage(request.getMessage(), request.getSettings()));
     return response;
  }

}
