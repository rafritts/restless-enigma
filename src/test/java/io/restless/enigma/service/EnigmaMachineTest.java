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
public class EnigmaMachineTest {

  @Autowired EnigmaMachine enigmaMachine;

  EnigmaSettings testSettings = new DefaultEnigmaSettings().getDefaultSettings();

  @BeforeEach
  public void setup() {
    testSettings.setRotor1Position('A');
    testSettings.setRotor2Position('B');
    testSettings.setRotor3Position('C');
    HashMap<Character, Character> plugboardSwaps = new HashMap<>();
    plugboardSwaps.put('A', 'B');
    plugboardSwaps.put('B', 'A');
    plugboardSwaps.put('E', 'G');
    plugboardSwaps.put('G', 'E');
    testSettings.setPlugboardSwaps(plugboardSwaps);
  }

  @Test
  public void testEncodeMessageWithCrib() {
    String encodedMessage =
        enigmaMachine.encodeMessageWithCrib("Hello, my name is Ryan", testSettings);
    assertEquals("MQFPCOMLSE ZTUSP, HJ GXOG AY UTOP", encodedMessage);
  }

  @Test
  public void testDecodeMessageWithCrib() {
    String decodedMessage =
        enigmaMachine.decodeMessage("MQFPCOMLSE ZTUSP, HJ GXOG AY UTOP", testSettings);
    assertEquals("HELLO, MY NAME IS RYAN", decodedMessage);
  }

  @Test
  public void testEncodeMessageWithoutCrib() {
    String encodedMessage = enigmaMachine.encodeMessage("Hello, my name is Ryan", testSettings);
    assertEquals("ZUUPJ, NX QXHA UO HSIV", encodedMessage);
  }

  @Test
  public void testDecodeMessageWithoutCrib() {
    String decodedMessage = enigmaMachine.decodeMessage("ZUUPJ, NX QXHA UO HSIV", testSettings);
    assertEquals("HELLO, MY NAME IS RYAN", decodedMessage);
  }

  @Test
  public void testEncodeMessageFullParagraph() {
    String encodedMessage =
        enigmaMachine.encodeMessage(
            "The Imitation Game is a historical drama that chronicles the life of Alan Turing, a pioneering mathematician and cryptanalyst. Set against the backdrop of World War II, the film focuses on Turing's pivotal role in cracking Nazi Germany's Enigma code, which was thought to be unbreakable. Leading a team of code-breakers at Britain's top-secret Government Code and Cypher School at Bletchley Park, Turing designs and builds an innovative machine, a precursor to modern computers, to decipher the complex Enigma messages. The film delves into Turing's personal and professional struggles, highlighting his crucial contributions to the war effort and his subsequent persecution due to his homosexuality. The narrative interweaves Turing's groundbreaking work with flashbacks of his earlier life and post-war repercussions, culminating in a poignant portrayal of a tormented genius who played a key role in shaping the outcome of the war.",
            testSettings);
    assertEquals("KVM FDCKOFWVB PEDV FY X PZMFKHGXVH ZQIXD YXDY RNOEXRWTJB NBD DLWU ZV XEGJ LVZEZO, M AKKHNOLMPU XEWNCYIUWZHBH QEB ANMEEQKVWDAP. HPO QMJWVYV LQV NBTGGYRJ WH FAYBN UQP RY, AUC ZWTS XVGHYSW QL BHTQFK'E IVQLGOX LEBC TT XWFXRYAT GZDK XTDFUGI'O PGYTEB JBAN, IBZNB URV GDVZKCN GV HT OTHKRJRCXAW. UMCJJDI K RWWB YA PSLC-KMGRGGAM HJ DCMNHYT'M RXY-FGJTGE BKYRXJOHEB KYZR DWN KOTNVE JLSJRG DX UYUXGMGIC GFQW, PZOZHB NADGOTQ QFM GVHYWL SX XRBRAWNEGM PMOVYIO, K XTVUQLPCL BC OUUILE LBVBWXSGV, CP GSLQFYPW PKP RIYTZLP VVYLQQ PVQTLZQO. NLX TWMQ BBUJGX TCZD CEWFBU'H XIGHCUJM SCJ XYFCVJUGVGHZ MUYWHIYTB, FLAFRFKZZBAS JSH SCISTNN SYLBQHYVXAMGB MF KTB HIT MXEFBH JKK YKA WLAYYWAQDL OHVWGSABEGG CGX OW JLL WFIMGDDHZSJRA. LCM KEZPBGFFD PMICUICCFJD ZYVHAR'K FGTZPKOZNHUMGR VAKC VPAC UMYVADNECL KY DOX CSOBGQL JDUW SDC WVHK-VLX QQFUIUMNEXYRJ, LFNPORTHWLS WE V EERYBMPR VKCPLNCRR ND V YVLPSJCMP STPMWC RYI EMVMPZ F NXM WZEP QS LVPBGJF QQM WJDLFEH FR JVO UNB.",
            encodedMessage);
  }

  @Test
  public void testDecodeMessageFullParagraph() {
    String encodedMessage =
            enigmaMachine.encodeMessage(
                    "KVM FDCKOFWVB PEDV FY X PZMFKHGXVH ZQIXD YXDY RNOEXRWTJB NBD DLWU ZV XEGJ LVZEZO, M AKKHNOLMPU XEWNCYIUWZHBH QEB ANMEEQKVWDAP. HPO QMJWVYV LQV NBTGGYRJ WH FAYBN UQP RY, AUC ZWTS XVGHYSW QL BHTQFK'E IVQLGOX LEBC TT XWFXRYAT GZDK XTDFUGI'O PGYTEB JBAN, IBZNB URV GDVZKCN GV HT OTHKRJRCXAW. UMCJJDI K RWWB YA PSLC-KMGRGGAM HJ DCMNHYT'M RXY-FGJTGE BKYRXJOHEB KYZR DWN KOTNVE JLSJRG DX UYUXGMGIC GFQW, PZOZHB NADGOTQ QFM GVHYWL SX XRBRAWNEGM PMOVYIO, K XTVUQLPCL BC OUUILE LBVBWXSGV, CP GSLQFYPW PKP RIYTZLP VVYLQQ PVQTLZQO. NLX TWMQ BBUJGX TCZD CEWFBU'H XIGHCUJM SCJ XYFCVJUGVGHZ MUYWHIYTB, FLAFRFKZZBAS JSH SCISTNN SYLBQHYVXAMGB MF KTB HIT MXEFBH JKK YKA WLAYYWAQDL OHVWGSABEGG CGX OW JLL WFIMGDDHZSJRA. LCM KEZPBGFFD PMICUICCFJD ZYVHAR'K FGTZPKOZNHUMGR VAKC VPAC UMYVADNECL KY DOX CSOBGQL JDUW SDC WVHK-VLX QQFUIUMNEXYRJ, LFNPORTHWLS WE V EERYBMPR VKCPLNCRR ND V YVLPSJCMP STPMWC RYI EMVMPZ F NXM WZEP QS LVPBGJF QQM WJDLFEH FR JVO UNB.",
                    testSettings);
    assertEquals("THE IMITATION GAME IS A HISTORICAL DRAMA THAT CHRONICLES THE LIFE OF ALAN TURING, A PIONEERING MATHEMATICIAN AND CRYPTANALYST. SET AGAINST THE BACKDROP OF WORLD WAR II, THE FILM FOCUSES ON TURING'S PIVOTAL ROLE IN CRACKING NAZI GERMANY'S ENIGMA CODE, WHICH WAS THOUGHT TO BE UNBREAKABLE. LEADING A TEAM OF CODE-BREAKERS AT BRITAIN'S TOP-SECRET GOVERNMENT CODE AND CYPHER SCHOOL AT BLETCHLEY PARK, TURING DESIGNS AND BUILDS AN INNOVATIVE MACHINE, A PRECURSOR TO MODERN COMPUTERS, TO DECIPHER THE COMPLEX ENIGMA MESSAGES. THE FILM DELVES INTO TURING'S PERSONAL AND PROFESSIONAL STRUGGLES, HIGHLIGHTING HIS CRUCIAL CONTRIBUTIONS TO THE WAR EFFORT AND HIS SUBSEQUENT PERSECUTION DUE TO HIS HOMOSEXUALITY. THE NARRATIVE INTERWEAVES TURING'S GROUNDBREAKING WORK WITH FLASHBACKS OF HIS EARLIER LIFE AND POST-WAR REPERCUSSIONS, CULMINATING IN A POIGNANT PORTRAYAL OF A TORMENTED GENIUS WHO PLAYED A KEY ROLE IN SHAPING THE OUTCOME OF THE WAR.",
            encodedMessage);
  }
}
