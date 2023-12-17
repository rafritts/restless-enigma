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
public class BombeTest {

  @Autowired EnigmaMachine enigmaMachine;

  @Autowired Bombe bombe;

  EnigmaSettings actualSettings = new DefaultEnigmaSettings().getDefaultSettings();

  @BeforeEach
  public void setup() {
    actualSettings.setRotor1Position('A');
    actualSettings.setRotor2Position('B');
    actualSettings.setRotor3Position('C');
    HashMap<Character, Character> plugboardSwaps = new HashMap<>();
    plugboardSwaps.put('A', 'B');
    plugboardSwaps.put('B', 'A');
    plugboardSwaps.put('E', 'G');
    plugboardSwaps.put('G', 'E');
    actualSettings.setPlugboardSwaps(plugboardSwaps);
  }

  @Test
  public void testBreakEnigmaWithDefaultCrib() {
    EnigmaSettings enigmaSettings =
        bombe.breakEnigma("MQFPCOMLSE ZTUSP, HJ GXOG AY UTOP", "LNXLWSOSBQ");
    assertEquals(actualSettings, enigmaSettings);
  }

  @Test
  public void testBreakEnigmaWithUserCrib() {
    EnigmaSettings enigmaSettings = bombe.breakEnigma("ZUUPJ, NX QXHA UO HSIV", "HELLO, MY");
    assertEquals(actualSettings, enigmaSettings);
  }

  @Test
  public void testBreakEnigmaFullParagraphWithUserCrib() {
    EnigmaSettings enigmaSettings = bombe.breakEnigma("KVM FDCKOFWVB PEDV FY X PZMFKHGXVH ZQIXD YXDY RNOEXRWTJB NBD DLWU ZV XEGJ LVZEZO, M AKKHNOLMPU XEWNCYIUWZHBH QEB ANMEEQKVWDAP. HPO QMJWVYV LQV NBTGGYRJ WH FAYBN UQP RY, AUC ZWTS XVGHYSW QL BHTQFK'E IVQLGOX LEBC TT XWFXRYAT GZDK XTDFUGI'O PGYTEB JBAN, IBZNB URV GDVZKCN GV HT OTHKRJRCXAW. UMCJJDI K RWWB YA PSLC-KMGRGGAM HJ DCMNHYT'M RXY-FGJTGE BKYRXJOHEB KYZR DWN KOTNVE JLSJRG DX UYUXGMGIC GFQW, PZOZHB NADGOTQ QFM GVHYWL SX XRBRAWNEGM PMOVYIO, K XTVUQLPCL BC OUUILE LBVBWXSGV, CP GSLQFYPW PKP RIYTZLP VVYLQQ PVQTLZQO. NLX TWMQ BBUJGX TCZD CEWFBU'H XIGHCUJM SCJ XYFCVJUGVGHZ MUYWHIYTB, FLAFRFKZZBAS JSH SCISTNN SYLBQHYVXAMGB MF KTB HIT MXEFBH JKK YKA WLAYYWAQDL OHVWGSABEGG CGX OW JLL WFIMGDDHZSJRA. LCM KEZPBGFFD PMICUICCFJD ZYVHAR'K FGTZPKOZNHUMGR VAKC VPAC UMYVADNECL KY DOX CSOBGQL JDUW SDC WVHK-VLX QQFUIUMNEXYRJ, LFNPORTHWLS WE V EERYBMPR VKCPLNCRR ND V YVLPSJCMP STPMWC RYI EMVMPZ F NXM WZEP QS LVPBGJF QQM WJDLFEH FR JVO UNB.",
            "THE IMITATION GAME");
    assertEquals(actualSettings, enigmaSettings);
  }


}
