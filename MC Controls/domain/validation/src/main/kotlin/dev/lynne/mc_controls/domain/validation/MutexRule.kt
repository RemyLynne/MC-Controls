package dev.lynne.mc_controls.domain.validation

object MutexRule {
    fun check(input: Set<String>, mutexes: List<Set<String>>): List<MutexRuleViolation> {
        val violations = mutableListOf<MutexRuleViolation>()
        for (mutex in mutexes) {
            val incompatible = findIncompatible(input, mutex)
            if (incompatible != null) {
                violations.add(
                    MutexRuleViolation(
                        incompatible,
                        mutex
                    )
                )
            }
        }
        return violations
    }
    fun findIncompatible(input: Set<String>, mutex: Set<String>): Set<String>? {
        val used = input.intersect(mutex)

        if (used.size <= 1)
            return null

        return used
    }
}