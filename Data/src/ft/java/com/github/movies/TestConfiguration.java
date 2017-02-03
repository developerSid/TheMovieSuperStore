package com.github.movies;

import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by developerSid on 2/2/17.
 *
 * Configures the test JPA harness.
 */
@ComponentScan(basePackages = "com.github.movies.db")
@AutoConfigureDataJpa
public class TestConfiguration
{
}
