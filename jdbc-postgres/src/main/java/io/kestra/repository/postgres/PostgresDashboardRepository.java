package io.kestra.repository.postgres;

import io.kestra.core.events.CrudEvent;
import io.kestra.core.models.dashboards.Dashboard;
import io.kestra.jdbc.repository.AbstractJdbcDashboardRepository;
import io.micronaut.context.event.ApplicationEventPublisher;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.jooq.Condition;

/**
 * PostgreSQL-specific implementation of the Dashboard repository for Kestra.
 * 
 * This repository handles database operations for Dashboard entities using PostgreSQL,
 * extending the abstract JDBC dashboard repository with PostgreSQL-specific configurations.
 * 
 * Key Features:
 * - Singleton scoped repository for managing Dashboard entities
 * - Supports PostgreSQL-specific query conditions
 * - Integrates with Micronaut dependency injection
 * - Publishes CRUD events for dashboard operations
 * 
 * @see AbstractJdbcDashboardRepository
 * @see Dashboard
 * @see PostgresRepository
 */
@Singleton
@PostgresRepositoryEnabled
public class PostgresDashboardRepository extends AbstractJdbcDashboardRepository {
    
    /**
     * Constructor for PostgresDashboardRepository.
     * 
     * Initializes the repository with a PostgreSQL-specific repository implementation
     * and an event publisher for CRUD operations on Dashboard entities.
     * 
     * @param repository PostgreSQL-specific repository for Dashboard entities
     * @param eventPublisher Application event publisher for Dashboard CRUD events
     */
    @Inject
    public PostgresDashboardRepository(
        @Named("dashboards") PostgresRepository<Dashboard> repository,
        ApplicationEventPublisher<CrudEvent<Dashboard>> eventPublisher
    ) {
        super(repository, eventPublisher);
    }
    
    /**
     * Generates a database query condition for finding Dashboard entities.
     * 
     * Delegates to PostgresDashboardRepositoryService to create a specific
     * query condition based on the input query string.
     * 
     * @param query Search query string to generate condition for
     * @return Jooq Condition representing the query filter
     */
    @Override
    protected Condition findCondition(String query) {
        return PostgresDashboardRepositoryService.findCondition(this.jdbcRepository, query);
    }
}
