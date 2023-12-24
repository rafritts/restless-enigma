package io.restless.enigma.service;

import io.restless.enigma.model.DefaultEnigmaSettings;
import io.restless.enigma.model.EnigmaSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EnigmaMachineService {

  public String encodeMessage(String message, EnigmaSettings userSettings) {
    return encodeDecode(message, userSettings);
  }

  public String encodeMessageWithDefaultCrib(String message, EnigmaSettings userSettings) {
    message = DefaultEnigmaSettings.CRIB + message;
    return encodeDecode(message, userSettings);
  }

  public String decodeMessage(String message, EnigmaSettings userSettings) {
    return encodeDecode(message, userSettings).replace(DefaultEnigmaSettings.CRIB, "");
  }

  private String encodeDecode(String message, EnigmaSettings userSettings) {
    userSettings =
        (userSettings != null) ? userSettings : new DefaultEnigmaSettings().getDefaultSettings();
    RotorStateMachine rotorStateMachine = new RotorStateMachine(userSettings);
    message = message.toUpperCase();
    StringBuilder encodedMessageBuilder = new StringBuilder();
    for (Character character : message.toCharArray()) {
      if (Character.isAlphabetic(character)) {
        Character encodedCharacter = rotorStateMachine.resolveCharacter(character);
        encodedMessageBuilder.append(encodedCharacter);
      } else {
        encodedMessageBuilder.append(character);
      }
    }
    rotorStateMachine.resetSettings();
    return encodedMessageBuilder.toString();
  }
}
