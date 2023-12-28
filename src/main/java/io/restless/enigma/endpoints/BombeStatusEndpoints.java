package io.restless.enigma.endpoints;

import io.restless.enigma.service.BombeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bombe")
public class BombeStatusEndpoints {

  private final BombeService bombeService;

  public BombeStatusEndpoints(BombeService bombeService) {
    this.bombeService = bombeService;
  }

  @PostMapping("/interrupt-bombe")
  public void interruptBombe() {
    bombeService.requestInterrupt();
  }
  ;
}
