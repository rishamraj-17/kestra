package io.kestra.core.junit.annotations;

import io.kestra.core.junit.extensions.KestraTestExtension;
import io.micronaut.context.ApplicationContextBuilder;
import io.micronaut.context.annotation.Executable;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import io.micronaut.test.annotation.TransactionMode;
import io.micronaut.test.condition.TestActiveCondition;
import org.junit.jupiter.api.extension.ExtendWith;
import java.lang.annotation.*;

/**
 * Custom test annotation for Kestra test configurations.
 * 
 * Provides comprehensive test environment setup and configuration capabilities 
 * for Kestra's testing infrastructure.
 * 
 * Key Features:
 * - Flexible application and context configuration
 * - Comprehensive transaction management
 * - Extensible test environment setup
 * 
 * Usage Examples:
 * <pre>
 * @KestraTest(
 *     application = MyKestraApplication.class,
 *     environments = {"test", "local"},
 *     packages = {"io.kestra.core", "io.kestra.plugins"},
 *     transactional = true
 * )
 * public class MyKestraTest { ... }
 * </pre>
 * 
 * Contribution Guidelines:
 * 1. Ensure annotation doesn't increase test complexity
 * 2. Maintain backward compatibility
 * 3. Document new configuration options thoroughly
 * 4. Add comprehensive unit tests for new features
 * 
 * @see KestraTestExtension
 * @see TransactionMode
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@ExtendWith(KestraTestExtension.class)
@Factory
@Inherited
@Requires(condition = TestActiveCondition.class)
@Executable
public @interface KestraTest {
    /**
     * Specify the main application class for the test context.
     * Defaults to void.class if not specified.
     * 
     * @return Application class to be used in test context
     */
    Class<?> application() default void.class;

    /**
     * Define active environments for the test.
     * Useful for conditional test configurations.
     * 
     * @return Array of environment names
     */
    String[] environments() default {};

    /**
     * Specify packages to be included in test scanning.
     * 
     * @return Array of package names to scan
     */
    String[] packages() default {};

    /**
     * Configure additional property sources for the test context.
     * 
     * @return Array of property source locations
     */
    String[] propertySources() default {};

    /**
     * Determine if database transactions should be rolled back after test.
     * 
     * @return Whether to rollback transactions (default: true)
     */
    boolean rollback() default true;

    /**
     * Enable transactional test execution.
     * 
     * @return Whether tests are transactional (default: false)
     */
    boolean transactional() default false;

    /**
     * Force rebuilding of application context for each test.
     * 
     * @return Whether to rebuild context (default: false)
     */
    boolean rebuildContext() default false;

    /**
     * Custom context builder classes for advanced configuration.
     * 
     * @return Array of ApplicationContextBuilder implementations
     */
    Class<? extends ApplicationContextBuilder>[] contextBuilder() default {};

    /**
     * Configure transaction management mode.
     * 
     * @return Transaction mode (default: SEPARATE_TRANSACTIONS)
     */
    TransactionMode transactionMode() default TransactionMode.SEPARATE_TRANSACTIONS;

    /**
     * Control automatic application startup during testing.
     * 
     * @return Whether to start application (default: true)
     */
    boolean startApplication() default true;

    /**
     * Enable automatic parameter resolution for test methods.
     * 
     * @return Whether to resolve test method parameters (default: true)
     */
    boolean resolveParameters() default true;
}
