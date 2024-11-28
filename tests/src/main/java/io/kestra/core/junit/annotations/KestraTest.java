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
 * Custom annotation for configuring and executing Kestra-based tests within a Micronaut application.
 * This annotation allows flexible configuration of the application context and test behavior.
 *
 * <p>When applied to a test class or method, this annotation extends the functionality with the {@link KestraTestExtension}.
 * It supports various features such as environment customization, property sources, transactional testing, and more.</p>
 */
@Retention(RetentionPolicy.RUNTIME) // Ensures the annotation is retained at runtime.
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE}) // Specifies the applicable targets: methods, annotations, and types.
@ExtendWith(KestraTestExtension.class) // Integrates the custom test extension for Kestra.
@Factory // Indicates the annotated element is a factory for producing beans.
@Inherited // Ensures subclasses inherit this annotation.
@Requires(condition = TestActiveCondition.class) // Enables the annotation only if the "TestActiveCondition" is satisfied.
@Executable // Marks the annotated method as executable within Micronaut.
public @interface KestraTest {
    /**
     * Specifies the application class for the test.
     *
     * @return the application class.
     */
    Class<?> application() default void.class;

    /**
     * Defines the environments to activate during testing.
     *
     * @return an array of environment names.
     */
    String[] environments() default {};

    /**
     * Specifies the packages to scan for beans and components.
     *
     * @return an array of package names.
     */
    String[] packages() default {};

    /**
     * Defines the property sources to include in the application context.
     *
     * @return an array of property source names.
     */
    String[] propertySources() default {};

    /**
     * Indicates whether to roll back transactions after each test execution.
     * 
     * <p>Useful for isolating test cases and preventing side effects.</p>
     *
     * @return {@code true} if transactions should be rolled back; {@code false} otherwise.
     */
    boolean rollback() default true;

    /**
     * Determines whether the test should run in a transactional context.
     *
     * @return {@code true} if transactional context is enabled; {@code false} otherwise.
     */
    boolean transactional() default false;

    /**
     * Indicates whether to rebuild the application context before each test execution.
     * 
     * <p>Use this option cautiously as rebuilding the context can be resource-intensive.</p>
     *
     * @return {@code true} if the context should be rebuilt; {@code false} otherwise.
     */
    boolean rebuildContext() default false;

    /**
     * Specifies custom {@link ApplicationContextBuilder} classes to configure the application context.
     *
     * @return an array of {@link ApplicationContextBuilder} classes.
     */
    Class<? extends ApplicationContextBuilder>[] contextBuilder() default {};

    /**
     * Configures the transaction mode to be used during the test.
     *
     * <p>Possible values are defined in {@link TransactionMode}, such as
     * {@code SEPARATE_TRANSACTIONS} or {@code SINGLE_TRANSACTION}.</p>
     *
     * @return the transaction mode.
     */
    TransactionMode transactionMode() default TransactionMode.SEPARATE_TRANSACTIONS;

    /**
     * Indicates whether to start the application during the test setup.
     *
     * @return {@code true} if the application should start; {@code false} otherwise.
     */
    boolean startApplication() default true;

    /**
     * Determines whether to resolve method parameters for test cases automatically.
     * 
     * <p>This can simplify test setup when using dependency injection or argument resolvers.</p>
     *
     * @return {@code true} if parameter resolution is enabled; {@code false} otherwise.
     */
    boolean resolveParameters() default true;
}
