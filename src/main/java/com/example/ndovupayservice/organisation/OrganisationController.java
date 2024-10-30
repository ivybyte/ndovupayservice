package com.example.ndovupayservice.organisation;

import com.example.ndovupayservice.utils.StandardJsonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class OrganisationController {

    private final OrganisationService organisationService;

    @PostMapping("create-organisation")
    public StandardJsonResponse createOrganisation(@RequestBody OrganisationDto orgDto) {
       return organisationService.createOrganisation(orgDto);
    }


}
