package com.example.ndovupayservice.organisation;

import com.example.ndovupayservice.user.UserService;
import com.example.ndovupayservice.utils.StandardJsonResponse;
import com.example.ndovupayservice.wallet.CustomerWalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrganisationService {

    private final OrganisationRepo organisationRepo;
    private final UserService userService;
    private final CustomerWalletService customerWalletService;

    public StandardJsonResponse createOrganisation(OrganisationDto orgDto) {

        // create organisation
        StandardJsonResponse resp = new StandardJsonResponse();
        Organisation organisation = Organisation.builder().orgName(orgDto.getOrgName()).orgBusinessType(orgDto.getOrgBusinessType()).orgEmail(orgDto.getOrgEmail()).orgMobile(orgDto.getOrgMobile()).orgTelephone(orgDto.getOrgMobile()).orgLocation(orgDto.getOrgLocation()).orgPostalAddress(orgDto.getOrgPostalAddress()).orgKraPin(orgDto.getOrgKraPin()).orgCounty(orgDto.getOrgCounty()).orgTown(orgDto.getOrgTown()).orgDesc(orgDto.getOrgName()).orgCreatedDate(LocalDate.now()).orgCreatedTime(LocalDateTime.now()).build();
        Organisation savedOrganisation = organisationRepo.saveAndFlush(organisation);

        // create user
        userService.createDefaultOrgAdmin(orgDto, savedOrganisation);

        // create default wallet
        customerWalletService.createDefultOrgWallet(savedOrganisation);

        resp.setData("result", savedOrganisation, resp);
        resp.setMessage("message", "Organisation Created Successfully", resp);

        return resp;


    }


}
