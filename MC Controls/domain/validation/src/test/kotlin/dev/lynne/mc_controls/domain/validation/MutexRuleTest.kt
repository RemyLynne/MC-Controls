package dev.lynne.mc_controls.domain.validation

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class MutexRuleTest {
    @Test
    fun `findIncompatible returns null when no overlap`() {
        val input = setOf("a", "b")
        val mutex = setOf("x", "y")

        assertNull(MutexRule.findIncompatible(input, mutex))
    }

    @Test
    fun `findIncompatible returns null when exactly one mutex entry is used`() {
        val input = setOf("a", "b")
        val mutex = setOf("b", "x", "y")

        assertNull(MutexRule.findIncompatible(input, mutex))
    }

    @Test
    fun `findIncompatible returns used set when two or more mutex entries are used`() {
        val input = setOf("a", "b", "c")
        val mutex = setOf("b", "c", "x")

        val used = MutexRule.findIncompatible(input, mutex)

        assertEquals(setOf("b", "c"), used)
    }

    @Test
    fun `check returns empty list when no mutexes`() {
        val violations = MutexRule.check(setOf("a", "b"), emptyList())
        assertTrue(violations.isEmpty())
    }

    @Test
    fun `check returns one violation for a violated mutex`() {
        val input = setOf("dev", "prod")
        val mutexes = listOf(setOf("dev", "prod", "staging"))

        val violations = MutexRule.check(input, mutexes)

        assertEquals(1, violations.size)
        val v = violations.single()

        assertEquals(setOf("dev", "prod"), v.incompatible)
        assertEquals(setOf("dev", "prod", "staging"), v.mutex)
    }

    @Test
    fun `check returns violations only for violated mutex sets`() {
        val input = setOf("a", "b", "x")

        val m1 = setOf("a", "b")      // violated (a,b both present)
        val m2 = setOf("x", "y")      // not violated (only x present)
        val m3 = setOf("a", "x", "z") // violated (a,x both present)

        val violations = MutexRule.check(input, listOf(m1, m2, m3))

        assertEquals(2, violations.size)

        val mutexesViolated = violations.map { it.mutex }.toSet()
        assertEquals(setOf(m1, m3), mutexesViolated)
    }
}