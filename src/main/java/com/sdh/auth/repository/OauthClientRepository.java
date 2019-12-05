package com.sdh.auth.repository;

import com.sdh.auth.domain.OauthClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthClientRepository extends JpaRepository<OauthClient, String> {
}
