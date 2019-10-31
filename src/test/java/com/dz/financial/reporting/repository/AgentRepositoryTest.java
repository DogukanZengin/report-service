package com.dz.financial.reporting.repository;

import com.dz.financial.reporting.model.db.Agent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("integrationtest")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("Agent CRUD Tests")
public class AgentRepositoryTest {

    @Autowired
    private AgentRepository agentRepository;

    @Test
    @DisplayName("Query Agent with ID")
    public void testQueryAgentWithId() {
        Optional<Agent> byId = agentRepository.findById(999401L);
        assertAll(
                () -> assertTrue(byId.isPresent(), "Query byId(999401L) does not return Agent"),
                () -> assertTrue(byId.get().getCustomerIp().equalsIgnoreCase("213.74.172.226")),
                () -> assertTrue(byId.get().getCustomerUserAgent().equalsIgnoreCase("PostmanRuntime/7.3.0")),
                () -> assertTrue(byId.get().getMerchantIp().equalsIgnoreCase("213.74.172.226")),
                () -> assertTrue(byId.get().getMerchantUserAgent().equalsIgnoreCase("PostmanRuntime/7.3.0"))
        );
    }

    @Test
    @DisplayName("Create Agent")
    public void testCreateAgent() {
        Agent agent = Agent.builder().customerIp("111.11.111.111")
                .customerUserAgent("PostmanRuntime/7.3.1")
                .merchantIp("222.22.222.222")
                .merchantUserAgent("PostmanRuntime/7.3.2").build();
        Agent savedAgent = agentRepository.saveAndFlush(agent);
        assertAll(
                () -> assertNotNull(savedAgent),
                () -> assertNotNull(savedAgent.getCreatedAt()),
                () -> assertNotNull(savedAgent.getModifiedAt()),
                () -> assertTrue(savedAgent.getCustomerIp().equals("111.11.111.111")),
                () -> assertTrue(savedAgent.getCustomerUserAgent().equals("PostmanRuntime/7.3.1")),
                () -> assertTrue(savedAgent.getMerchantIp().equals("222.22.222.222")),
                () -> assertTrue(savedAgent.getMerchantUserAgent().equals("PostmanRuntime/7.3.2"))
        );
    }

    @Test
    @DisplayName("Update Agent")
    public void testUpdateAgent() {
        Optional<Agent> byId = agentRepository.findById(999401L);
        assertTrue(byId.isPresent(), "Query byId(999401L) does not return Agent");
        Agent agent = byId.get();
        Date modifiedAt = agent.getModifiedAt();
        agent.setCustomerIp("213.74.172.225");
        Agent updatedAgent = agentRepository.saveAndFlush(agent);
        assertAll(
                () -> assertNotNull(updatedAgent),
                () -> assertTrue(modifiedAt.before(updatedAgent.getModifiedAt())),
                () -> assertTrue(updatedAgent.getCustomerIp().equals("213.74.172.225"))
        );
    }
}