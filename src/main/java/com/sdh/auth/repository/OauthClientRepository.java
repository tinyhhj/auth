package com.sdh.auth.repository;

import com.sdh.auth.domain.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthClientRepository extends JpaRepository<OauthClientDetails, String> {
}
