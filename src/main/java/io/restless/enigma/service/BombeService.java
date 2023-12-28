package io.restless.enigma.service;

import io.restless.enigma.model.*;
import io.restless.enigma.util.PlugboardUtil;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Service
@Slf4j
public class BombeService {

  private static final Integer LOG_EVERY_X_TESTS = 100000;
  private final NumberFormat formatter = NumberFormat.getInstance();
  private final EnigmaMachineService enigmaMachineService;
  private final SimpMessagingTemplate messagingTemplate;
  private BombeInternalStatusEnum internalStatus = BombeInternalStatusEnum.STARTED;
  private final List<HashMap<Character, Character>> allPossiblePlugboardCombinations =
      PlugboardUtil.generatePlugboardSettings();

  @Autowired
  public BombeService(EnigmaMachineService enigmaMachineService, SimpMessagingTemplate template) {
    this.enigmaMachineService = enigmaMachineService;
    this.messagingTemplate = template;
  }

  public BombeResult breakEnigma(String message, String crib) {
    internalStatus = BombeInternalStatusEnum.STARTED;
    crib = (!crib.isBlank()) ? crib : DefaultEnigmaSettings.CRIB;
    crib = crib.toUpperCase();

    int logCounter = 0;
    BigInteger totalAttempts = BigInteger.ZERO;

    StopWatch stopWatch = new StopWatch("bombe");
    stopWatch.start();

    EnigmaSettings testSettings = new EnigmaSettings();
    logInternalStatus(logCounter, stopWatch, totalAttempts, testSettings);
    /*
       While I know this can be done with lambda functions, I genuinely
       believe its more readable this way, even if it's uglier.
    */
    internalStatus = BombeInternalStatusEnum.IN_PROGRESS;
    for (int rotor1Position = 0; rotor1Position < 26; rotor1Position++) {
      for (int rotor2Position = 0; rotor2Position < 26; rotor2Position++) {
        for (int rotor3Position = 0; rotor3Position < 26; rotor3Position++) {
          testSettings.setRotor1Position((char) ('A' + rotor1Position));
          testSettings.setRotor2Position((char) ('A' + rotor2Position));
          testSettings.setRotor3Position((char) ('A' + rotor3Position));

          for (HashMap<Character, Character> plugboardPair : allPossiblePlugboardCombinations) {
            // disregard any incomplete plugboard settings
            if (plugboardPair.size() < EnigmaSettings.MAX_PLUBGBOARD_CABLES * 2) continue;

            testSettings.setPlugboardSwaps(plugboardPair);
            String decodedMessage = enigmaMachineService.encodeMessage(message, testSettings);

            if (decodedMessage.contains(crib)) {
              internalStatus = BombeInternalStatusEnum.COMPLETE;
              logInternalStatus(logCounter, stopWatch, totalAttempts, testSettings);
              return getBombeResult(
                  testSettings,
                  totalAttempts,
                  decodedMessage,
                  stopWatch,
                  BombeResultStatusEnum.SUCCESS);
            }

            if (internalStatus == BombeInternalStatusEnum.USER_INTERRUPTED) {
              logInternalStatus(logCounter, stopWatch, totalAttempts, testSettings);
              return getBombeResult(
                  testSettings,
                  totalAttempts,
                  decodedMessage,
                  stopWatch,
                  BombeResultStatusEnum.INTERRUPTED);
            }
            logCounter = (++logCounter > LOG_EVERY_X_TESTS) ? 0 : logCounter;
            totalAttempts = totalAttempts.add(BigInteger.ONE);
            logInternalStatus(logCounter, stopWatch, totalAttempts, testSettings);
          }
        }
      }
    }
    // No settings found
    internalStatus = BombeInternalStatusEnum.COMPLETE;
    logInternalStatus(logCounter, stopWatch, totalAttempts, testSettings);
    return getBombeResult(
        testSettings, totalAttempts, message, stopWatch, BombeResultStatusEnum.FAILURE);
  }

  public void requestInterrupt() {
    this.internalStatus = BombeInternalStatusEnum.USER_INTERRUPTED;
  }

  private BombeResult getBombeResult(
      EnigmaSettings testSettings,
      BigInteger totalAttempts,
      String decodedMessage,
      StopWatch stopWatch,
      BombeResultStatusEnum bombeResultStatus) {
    BombeResult result = new BombeResult();
    result.setNumberOfAttempts(formatter.format(totalAttempts));
    result.setSettings(testSettings);
    result.setDecodedMessage(decodedMessage);
    result.setElapsedTime(getElapsedTimeHumanReadable(stopWatch));
    result.setBombeResultStatus(bombeResultStatus);
    return result;
  }

  private String getElapsedTimeHumanReadable(StopWatch stopWatch) {
    stopWatch.stop();
    String elapsedTime = stopWatch.shortSummary().replace("StopWatch 'bombe': ", "");
    stopWatch.start();
    return elapsedTime;
  }

  private void logInternalStatus(
      int logCounter, StopWatch stopWatch, BigInteger totalAttempts, EnigmaSettings testSettings) {
    if (logCounter >= LOG_EVERY_X_TESTS || internalStatus != BombeInternalStatusEnum.IN_PROGRESS) {
      BombeStatus status =
          BombeStatus.builder()
              .elapsedTime(getElapsedTimeHumanReadable(stopWatch))
              .numberOfAttempts(formatter.format(totalAttempts))
              .currentSetting(testSettings)
              .internalStatus(internalStatus)
              .build();
      log.info(status.toString());
      messagingTemplate.convertAndSend("/topic/bombe-status", status);
    }
  }
}
