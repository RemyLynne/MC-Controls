package dev.lynne.mc_controls.spring.annotations

import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Component

/**
 * Indicates that an annotated class is a "Mapper".
 *
 * <p>Typically used to mark classes (or interfaces) that map between layers
 * (for example, DTOs and domain objects) or provide mapping-related behavior.
 *
 * <p>This annotation also serves as a specialization of {@link Component @Component},
 * allowing for implementation classes to be autodetected through classpath scanning.
 *
 * @see Component
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Component
annotation class Mapper(
    /**
     * Alias for {@link Component#value}.
     */
    @get:AliasFor(annotation = Component::class)
    val value: String = ""
)
