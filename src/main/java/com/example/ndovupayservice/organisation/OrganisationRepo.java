package com.example.ndovupayservice.organisation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface OrganisationRepo extends JpaRepository<Organisation, UUID> {

    Organisation findByOrgEmail(String orgEmail);

}
