package com._8attery.seesaw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @RequestMapping("/check")
        public ResponseEntity<String> healthCheck() {
            return ResponseEntity.ok("OK");
        }
}
