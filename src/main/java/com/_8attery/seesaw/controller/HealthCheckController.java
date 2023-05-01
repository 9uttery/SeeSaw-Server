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


//    @GetMapping("/illegal-argument")
//    public ResponseEntity<String> triggerIllegalArgumentException() {
//        throw new IllegalArgumentException("IllegalArgumentException triggered.");
//    }
//
//    @GetMapping("/resource-not-found")
//    public ResponseEntity<String> triggerResourceNotFoundException() {
//        throw new ResourceNotFoundException("ResourceNotFoundException triggered.");
//    }
//
//    @GetMapping("/user-unauthorized")
//    public ResponseEntity<String> triggerUserUnauthorizedException() {
//        throw new UserUnauthorizedException("UserUnauthorizedException triggered.");
//    }
//
//    @GetMapping("/null-pointer")
//    public ResponseEntity<String> triggerNullPointerException() {
//        throw new NullPointerException("NullPointerException triggered.");
//    }
//
//    @GetMapping("/invalid-request/{id}")
//    public ResponseEntity<String> triggerInvalidRequestException(@PathVariable("id") Integer id) {
//        if (id < 0) {
//            throw new ConflictRequestException("ConflictRequestException triggered.");
//        }
//        return ResponseEntity.ok("Valid request.");
//    }
}
