package moe.iacg.miraiboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Github")
@Slf4j
public class GithubController {

    @RequestMapping("/webHook")

    public void webHook(@RequestBody String payload) {

        log.info(payload);

    }
}
