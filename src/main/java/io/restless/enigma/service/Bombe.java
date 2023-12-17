package io.restless.enigma.service;

import io.restless.enigma.model.EnigmaSettings;
import io.restless.enigma.util.EnigmaUtil;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Bombe {

  private EnigmaMachine enigmaMachine;

  @Autowired
  public Bombe(EnigmaMachine enigmaMachine) {
    this.enigmaMachine = enigmaMachine;
  }

  public EnigmaSettings breakEnigma(String message, String crib) {
    NumberFormat formatter = NumberFormat.getInstance();
    int logEveryX = 1000000;
    int logCounter = 0;
    BigInteger totalAttempts = BigInteger.ZERO;

    crib = (!crib.isBlank()) ? crib : EnigmaMachine.CRIB;

    for (int rotor1Position = 0; rotor1Position < 26; rotor1Position++) {
      for (int rotor2Position = 0; rotor2Position < 26; rotor2Position++) {
        for (int rotor3Position = 0; rotor3Position < 26; rotor3Position++) {

          EnigmaSettings testSettings = new EnigmaSettings();
          testSettings.setRotor1Position((char) ('A' + rotor1Position));
          testSettings.setRotor2Position((char) ('A' + rotor2Position));
          testSettings.setRotor3Position((char) ('A' + rotor3Position));

          for (HashMap<Character, Character> plugboardPair :
              EnigmaUtil.generatePlugboardSettings()) {
            // disregard any incomplete plugboard settings
            if (plugboardPair.size() < EnigmaSettings.MAX_PLUBGBOARD_CABLES * 2) continue;

            testSettings.setPlugboardSwaps(plugboardPair);
            String decodedMessage = enigmaMachine.encodeMessage(message, testSettings);

            if (logCounter >= logEveryX) {
              log.info(
                  "Total Attempts: "
                      + formatter.format(totalAttempts)
                      + " | Current Test Setting: "
                      + testSettings);
              logCounter = 0;
            }
            logCounter++;
            totalAttempts = totalAttempts.add(BigInteger.ONE);

            if (decodedMessage.contains(crib)) {
              log.info("Found successful settings for given crib: " + testSettings);
              log.info("Total Attempts: " + formatter.format(totalAttempts));
              return testSettings;
            }
          }
        }
      }
    }
    return new EnigmaSettings();
  }
}
