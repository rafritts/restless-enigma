package io.restless.enigma.service;

import io.restless.enigma.model.BombeBreakResult;
import io.restless.enigma.model.BombeStatus;
import io.restless.enigma.model.DefaultEnigmaSettings;
import io.restless.enigma.model.EnigmaSettings;
import io.restless.enigma.util.EnigmaUtil;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Service
@Slf4j
public class BombeService {

  private static final Integer LOG_EVERY_MILLION_TESTS = 1000000;

  private final NumberFormat formatter = NumberFormat.getInstance();

  private final EnigmaMachineService enigmaMachineService;

  private final SimpMessagingTemplate messagingTemplate;

  @Autowired
  public BombeService(EnigmaMachineService enigmaMachineService, SimpMessagingTemplate template) {
    this.enigmaMachineService = enigmaMachineService;
    this.messagingTemplate = template;
  }

  public BombeBreakResult breakEnigma(String message, String crib) {
    crib = (!crib.isBlank()) ? crib : DefaultEnigmaSettings.CRIB;

    int logCounter = 0;
    BigInteger totalAttempts = BigInteger.ZERO;

    log.info("Beginning decode: crib = [" + crib + "] | message = [" + message + "]");

    StopWatch stopWatch = new StopWatch("bombe");
    stopWatch.start();

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
            String decodedMessage = enigmaMachineService.encodeMessage(message, testSettings);

            if (logCounter >= LOG_EVERY_MILLION_TESTS) {
              BombeStatus status =
                  BombeStatus.builder()
                      .elapsedTime(getElapsedTimeHumanReadable(stopWatch))
                      .numberOfAttempts(formatter.format(totalAttempts))
                      .currentSetting(testSettings)
                      .build();
              log.info(status.toString());
              messagingTemplate.convertAndSend("/topic/bombe-status", status);
              logCounter = 0;
            }
            logCounter++;
            totalAttempts = totalAttempts.add(BigInteger.ONE);

            if (decodedMessage.contains(crib)) {
              return getBombeBreakResult(testSettings, totalAttempts, decodedMessage, stopWatch);
            }
          }
        }
      }
    }
    BombeBreakResult failureResult = new BombeBreakResult();
    failureResult.setElapsedTime(getElapsedTimeHumanReadable(stopWatch));
    failureResult.setDecodedMessage(
        "Unable to find valid Enigma settings for search string: " + crib);
    failureResult.setSettings(new EnigmaSettings());
    return failureResult;
  }

  private BombeBreakResult getBombeBreakResult(
      EnigmaSettings testSettings,
      BigInteger totalAttempts,
      String decodedMessage,
      StopWatch stopWatch) {

    BombeBreakResult result = new BombeBreakResult();
    result.setNumberOfAttempts(formatter.format(totalAttempts));
    result.setSettings(testSettings);
    result.setDecodedMessage(decodedMessage);
    result.setElapsedTime(getElapsedTimeHumanReadable(stopWatch));

    log.info("Found successful settings for given crib: " + result);
    return result;
  }

  private String getElapsedTimeHumanReadable(StopWatch stopWatch) {
    stopWatch.stop();
    String elapsedTime = stopWatch.shortSummary().replace("StopWatch 'bombe': ", "");
    stopWatch.start();
    return elapsedTime;
  }
}
