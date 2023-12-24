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
    plugboardSwaps.put('C', 'D');
    plugboardSwaps.put('D', 'C');
    testSettings.setPlugboardSwaps(plugboardSwaps);
  }

  @Test
  public void testEncodeMessageWithCrib() {
    String encodedMessage =
        enigmaMachineService.encodeMessageWithDefaultCrib(testStringShortOriginal, testSettings);
    assertEquals("WJQUBYSDWX KQEG CY Y KXXV UKBORUR", encodedMessage);
  }

  @Test
  public void testDecodeMessageWithCrib() {
    String decodedMessage =
        enigmaMachineService.decodeMessage("WJQUBYSDWX KQEG CY Y KXXV UKBORUR", testSettings);
    assertEquals(testStringShortOriginal.toUpperCase(), decodedMessage);
  }

  @Test
  public void testEncodeMessageWithoutCrib() {
    String encodedMessage = enigmaMachineService.encodeMessage(testStringShortOriginal, testSettings);
    assertEquals("DIEP QY N KCCK ZIGTPEX", encodedMessage);
  }

  @Test
  public void testDecodeMessageWithoutCrib() {
    String decodedMessage = enigmaMachineService.decodeMessage("DIEP QY N KCCK ZIGTPEX", testSettings);
    assertEquals("THIS IS A TEST MESSAGE", decodedMessage);
  }

  @Test
  public void testEncodeMessageFullParagraph() {
    String encodedMessage = enigmaMachineService.encodeMessage(testStringLongOriginal, testSettings);
    assertEquals(
        "DII JJEWPP KYAJAJI, LMXN YH DRBLXZJ WLAEIQ QPYSW FMF YY, HIY PC IMNSMQD-XGVXCKKZZU MPSCWY NBXN GJLXPBCAY AQMUPCSJ TMJGO M FMYCTAB COLGAG TJ ITUILU SVX Q GBAXLGQXH VJ WZMXVM I GQKD OQTLDL RW RIWOPLSB EDMYHODM, TZSOLS YMA NPALRUG WMZFTICRJ QZVHBHZZM VS JJGLI. XLRL ELI, RFN NLOGZIW'Q YPWIJLLV DKFG SDCQLGW, TILAA MC YSNQSX RUD AEERZ. WKDV YZGJTN, S KQYGVYSTU IREWVSBJZZVRD SIK RDIJRJQHPBEU, BSHLCB I QZYPVZR OFVJ GE MKDSWGKD PMM LYBYTS NITI. NN WNQWVQDSB BSVZ HB NWI RH, QVLYVE TIP QNA FQSP LLQCNMMQV DFF OSRYG CYAAVZQ, Y CQJVAF SXNB DXKGJ YSXMOHR DTTW OKJYTYZMS OFSZAKQA BS ISB XYQLQQ VKZIPDN. QP GPQHNABLUS XOUUO HBZOM LK ZEA DEFHUW'D ISMZXS PLS VGFGA GPLQLQAV FNQ LEJQTGIRZJH, CWP VBAPC RDX BMIX NP DWQPFELMUEODL NUVHFW GGV IFOWTP MU RMRNKQDDN LNQEFLCQ GWBF AQXKVG ZP EX GXXYTXW EBDOKDME, NTXMIROWMK HMCAQMAD JZR VZEMSA JY ERPVOYVWP LVY BNISJUN NQIYFS IZPYRIEIFKUZOV, CCWKK BQD PXEOKWR WH WCJJGOV WJU WVYZ ZE SIH RCY.",
        encodedMessage);
  }

  @Test
  public void testDecodeMessageFullParagraph() {
    String encodedMessage =
        enigmaMachineService.encodeMessage(
            "DII JJEWPP KYAJAJI, LMXN YH DRBLXZJ WLAEIQ QPYSW FMF YY, HIY PC IMNSMQD-XGVXCKKZZU MPSCWY NBXN GJLXPBCAY AQMUPCSJ TMJGO M FMYCTAB COLGAG TJ ITUILU SVX Q GBAXLGQXH VJ WZMXVM I GQKD OQTLDL RW RIWOPLSB EDMYHODM, TZSOLS YMA NPALRUG WMZFTICRJ QZVHBHZZM VS JJGLI. XLRL ELI, RFN NLOGZIW'Q YPWIJLLV DKFG SDCQLGW, TILAA MC YSNQSX RUD AEERZ. WKDV YZGJTN, S KQYGVYSTU IREWVSBJZZVRD SIK RDIJRJQHPBEU, BSHLCB I QZYPVZR OFVJ GE MKDSWGKD PMM LYBYTS NITI. NN WNQWVQDSB BSVZ HB NWI RH, QVLYVE TIP QNA FQSP LLQCNMMQV DFF OSRYG CYAAVZQ, Y CQJVAF SXNB DXKGJ YSXMOHR DTTW OKJYTYZMS OFSZAKQA BS ISB XYQLQQ VKZIPDN. QP GPQHNABLUS XOUUO HBZOM LK ZEA DEFHUW'D ISMZXS PLS VGFGA GPLQLQAV FNQ LEJQTGIRZJH, CWP VBAPC RDX BMIX NP DWQPFELMUEODL NUVHFW GGV IFOWTP MU RMRNKQDDN LNQEFLCQ GWBF AQXKVG ZP EX GXXYTXW EBDOKDME, NTXMIROWMK HMCAQMAD JZR VZEMSA JY ERPVOYVWP LVY BNISJUN NQIYFS IZPYRIEIFKUZOV, CCWKK BQD PXEOKWR WH WCJJGOV WJU WVYZ ZE SIH RCY.",
            testSettings);
    assertEquals(testStringLongOriginal.toUpperCase(), encodedMessage);
  }
}
