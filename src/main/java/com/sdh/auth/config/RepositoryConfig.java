package com.sdh.auth.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories("com.sdh.auth.repository")
@EnableTransactionManagement
public class RepositoryConfig {
}
