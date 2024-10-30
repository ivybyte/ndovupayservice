package com.example.ndovupayservice.organisation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v4/org")
@RequiredArgsConstructor
public class OrganisationSecuredController {



    @GetMapping("health")
    public String healthCheck(HttpServletRequest request) {
        return "OK";
    }
}