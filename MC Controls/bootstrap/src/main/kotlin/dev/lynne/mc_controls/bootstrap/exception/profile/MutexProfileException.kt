package dev.lynne.mc_controls.bootstrap.exception.profile

import dev.lynne.mc_controls.domain.validation.MutexRuleViolation

class MutexProfileException(val violations: List<MutexRuleViolation>) : Exception("incompatible profiles are active: \n- ${violations.joinToString("\n- ") {"${it.incompatible.joinToString()} (group: ${it.mutex.joinToString()}})"}}")