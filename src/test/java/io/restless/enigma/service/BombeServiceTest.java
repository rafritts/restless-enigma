package io.restless.enigma.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restless.enigma.model.BombeBreakResult;
import io.restless.enigma.model.DefaultEnigmaSettings;
import io.restless.enigma.model.EnigmaSettings;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BombeServiceTest {

  @Autowired EnigmaMachineService enigmaMachineService;

  @Autowired BombeService bombeService;

  EnigmaSettings testSettings = new DefaultEnigmaSettings().getDefaultSettings();

  String testStringShortOriginal = "This is a test message";
  String testStringShortEncoded;
  String testStringLongOriginal =
      "The Enigma machine, used by Germany during World War II, was an electro-mechanical device that encrypted messages using a complex series of rotors and a plugboard to create a vast number of possible settings, making its ciphers extremely difficult to break. Each day, the machine's settings were changed, based on secret key lists. Alan Turing, a brilliant mathematician and cryptanalyst, played a pivotal role in breaking the Enigma code. At Bletchley Park in the UK, Turing and his team developed the Bombe machine, a device that could rapidly test different settings of the Enigma machine. By exploiting known flaws in the Enigma's method and using captured key information, the Bombe was able to significantly reduce the number of potential settings that needed to be checked manually, eventually allowing the Allies to intercept and decrypt German communications, which was crucial in turning the tide of the war.\n";
  String testStringLongEncoded;

  @BeforeEach
  public void setup() {
    testSettings.setRotor1Position('A');
    testSettings.setRotor2Position('A');
    testSettings.setRotor3Position('D');
    HashMap<Character, Character> plugboardSwaps = new HashMap<>();
    plugboardSwaps.put('A', 'B');
    plugboardSwaps.put('B', 'A');
    plugboardSwaps.put('C', 'D');
    plugboardSwaps.put('D', 'C');
    testSettings.setPlugboardSwaps(plugboardSwaps);
    testStringShortEncoded =
        enigmaMachineService.encodeMessageWithDefaultCrib(testStringShortOriginal, testSettings);
    testStringLongEncoded =
        enigmaMachineService.encodeMessageWithDefaultCrib(testStringLongOriginal, testSettings);
  }

  @Test
  public void testShortStringBreakEnigmaWithDefaultCrib() {
    BombeBreakResult result =
        bombeService.breakEnigma(testStringShortEncoded, DefaultEnigmaSettings.CRIB);
    assertEquals(testSettings, result.getSettings());
    assertEquals(DefaultEnigmaSettings.CRIB + testStringShortOriginal.toUpperCase(), result.getDecodedMessage());
  }

  @Test
  public void testShortStringBreakEnigmaWithUserCrib() {
    BombeBreakResult result =
        bombeService.breakEnigma(testStringShortEncoded, "TEST MESSAGE");
    assertEquals(testSettings, result.getSettings());
  }

  @Test
  public void testBreakEnigmaFullParagraphWithUserCrib() {
    BombeBreakResult result =
        bombeService.breakEnigma(testStringLongEncoded, "BLETCHLEY PARK");
    assertEquals(testSettings, result.getSettings());
  }
}
