package io.restless.enigma.endpoints;

import io.restless.enigma.endpoints.model.BombeBreakRequest;
import io.restless.enigma.endpoints.model.BombeBreakResponse;
import io.restless.enigma.model.BombeBreakResult;
import io.restless.enigma.service.BombeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bombe")
public class BombeEndpoints {

    BombeService bombeService;

    @Autowired
    public BombeEndpoints(BombeService bombeService) {
        this.bombeService = bombeService;
    }


    @PostMapping(value = "decode", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody BombeBreakResponse breakEnigma(@RequestBody BombeBreakRequest request) {
        BombeBreakResult result = bombeService.breakEnigma(request.getEncodedMessage(), request.getSearchPhrase());
        BombeBreakResponse response = new BombeBreakResponse();
        response.setSettings(result.getSettings());
        response.setDecodedMessage(result.getDecodedMessage());
        response.setNumberOfAttempts(result.getNumberOfAttempts());
        response.setElapsedTime(result.getElapsedTime());
        return response;
    }



}
