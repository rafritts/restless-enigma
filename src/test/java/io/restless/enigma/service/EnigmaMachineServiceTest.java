package io.restless.enigma.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restless.enigma.model.DefaultEnigmaSettings;
import io.restless.enigma.model.EnigmaSettings;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EnigmaMachineServiceTest {

  @Autowired
  EnigmaMachineService enigmaMachineService;

  EnigmaSettings testSettings = new DefaultEnigmaSettings().getDefaultSettings();

  String testStringShortOriginal = "This is a test message";
  String testStringLongOriginal =
      "The Enigma machine, used by Germany during World War II, was an electro-mechanical device that encrypted messages using a complex series of rotors and a plugboard to create a vast number of possible settings, making its ciphers extremely difficult to break. Each day, the machine's settings were changed, based on secret key lists. Alan Turing, a brilliant mathematician and cryptanalyst, played a pivotal role in breaking the Enigma code. At Bletchley Park in the UK, Turing and his team developed the Bombe machine, a device that could rapidly test different settings of the Enigma machine. By exploiting known flaws in the Enigma's method and using captured key information, the Bombe was able to significantly reduce the number of potential settings that needed to be checked manually, eventually allowing the Allies to intercept and decrypt German communications, which was crucial in turning the tide of the war.";

  @BeforeEach
  public void setup() {
    testSettings.setRotor1Position('A');
    testSettings.setRotor2Position('A');
    testSettings.setRotor3Position('D');
    HashMap<Character, Character> plugboardSwaps = new HashMap<>();
    plugboardSwaps.put('A', 'B');
    plugboardSwaps.put('B', 'A');
    testSettings.setPlugboardSwaps(plugboardSwaps);
  }

  @Test
  public void testEncodeMessageWithCrib() {
    String encodedMessage =
        enigmaMachineService.encodeMessageWithDefaultCrib(testStringShortOriginal, testSettings);
    assertEquals("WJQUBYSCWX KQEG DY Y KXXV UKBORUR", encodedMessage);
  }

  @Test
  public void testDecodeMessageWithCrib() {
    String decodedMessage =
        enigmaMachineService.decodeMessage("WJQUBYSCWX KQEG DY Y KXXV UKBORUR", testSettings);
    assertEquals(testStringShortOriginal.toUpperCase(), decodedMessage);
  }

  @Test
  public void testEncodeMessageWithoutCrib() {
    String encodedMessage = enigmaMachineService.encodeMessage(testStringShortOriginal, testSettings);
    assertEquals("CIEP QY N KDDK ZIGTPEX", encodedMessage);
  }

  @Test
  public void testDecodeMessageWithoutCrib() {
    String decodedMessage = enigmaMachineService.decodeMessage("CIEP QY N KDDK ZIGTPEX", testSettings);
    assertEquals("THIS IS A TEST MESSAGE", decodedMessage);
  }

  @Test
  public void testEncodeMessageFullParagraph() {
    String encodedMessage = enigmaMachineService.encodeMessage(testStringLongOriginal, testSettings);
    assertEquals(
        "CII JJEWPP KYLJAJI, LMXM YH CRBLXZJ MLAEIQ QPYSM FMF YY, HIY PD IMNRMQC-XGWXDKKMZU ZPSDFY NBXN GJNXPBDAG AQMUPDSJ TMJGO M YMYDTAB DOLGAG TJ ITUILU SVE Q GBAXLGQXX VJ EZMXVM I GQKC OQTLCL RW RIWOPLSB ECMYHOCM, TZSOLS YMA PPALRUG WMZFTIDRJ WZVHBAZZM VS JJGLI. XLTL ZLI, RFN NLAGZIW'Q YPWIJLLV CKFG JCDQLGA, TILAM MD YSEQSX RUC AEERZ. WKCV YZGJTN, S KQYGVYSTU IREWVSBJZUVRC SIN LCIJRJQHPBEU, BSHLDS I QZYPVZR OFVJ GE MKCSWGKC PMM LYBYTS HISI. NN WNQWKQCSB BSVZ HB NWI RH, QVLYVE TIF QNA FQSP TLQDNMMQF CFF OSRYG DYNAVZQ, Y CQJVXF SXNB DXKGK YSXMRHR CTTW BKJYTYZMS OFSZAKQA BS ISB XYQLQQ VKQIPCN. QP GPQHNABLUS XOUUO HBZOM LK ZEA CEFHUW'C ISMZXU PLG VGFGA KPLQLQAK FNQ LEJQTGIRZJH, DWP VBAPD RCX BMIX NP CWQPFELSUEOCL NUWHLW GGV IFOWTP MU RMRNKQCCN LNQEFLDQ GWBF AQXYVS ZP EX JXXJTXY EBCOKCME, NTXMIROWMK HMDAQMAC JZR VZEMSA JY ERPVOBVWP LVU QNYSJUN NQIYFS OZPYRIEKFKUZOV, DDWRK BQC TXEMKWR WH WDJJGOV WJU WVFZ ZE SIH RDY.",
        encodedMessage);
  }

  @Test
  public void testDecodeMessageFullParagraph() {
    String encodedMessage =
        enigmaMachineService.encodeMessage(
            "CII JJEWPP KYLJAJI, LMXM YH CRBLXZJ MLAEIQ QPYSM FMF YY, HIY PD IMNRMQC-XGWXDKKMZU ZPSDFY NBXN GJNXPBDAG AQMUPDSJ TMJGO M YMYDTAB DOLGAG TJ ITUILU SVE Q GBAXLGQXX VJ EZMXVM I GQKC OQTLCL RW RIWOPLSB ECMYHOCM, TZSOLS YMA PPALRUG WMZFTIDRJ WZVHBAZZM VS JJGLI. XLTL ZLI, RFN NLAGZIW'Q YPWIJLLV CKFG JCDQLGA, TILAM MD YSEQSX RUC AEERZ. WKCV YZGJTN, S KQYGVYSTU IREWVSBJZUVRC SIN LCIJRJQHPBEU, BSHLDS I QZYPVZR OFVJ GE MKCSWGKC PMM LYBYTS HISI. NN WNQWKQCSB BSVZ HB NWI RH, QVLYVE TIF QNA FQSP TLQDNMMQF CFF OSRYG DYNAVZQ, Y CQJVXF SXNB DXKGK YSXMRHR CTTW BKJYTYZMS OFSZAKQA BS ISB XYQLQQ VKQIPCN. QP GPQHNABLUS XOUUO HBZOM LK ZEA CEFHUW'C ISMZXU PLG VGFGA KPLQLQAK FNQ LEJQTGIRZJH, DWP VBAPD RCX BMIX NP CWQPFELSUEOCL NUWHLW GGV IFOWTP MU RMRNKQCCN LNQEFLDQ GWBF AQXYVS ZP EX JXXJTXY EBCOKCME, NTXMIROWMK HMDAQMAC JZR VZEMSA JY ERPVOBVWP LVU QNYSJUN NQIYFS OZPYRIEKFKUZOV, DDWRK BQC TXEMKWR WH WDJJGOV WJU WVFZ ZE SIH RDY.",
            testSettings);
    assertEquals(testStringLongOriginal.toUpperCase(), encodedMessage);
  }
}
