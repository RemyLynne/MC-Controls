package dev.lynne.mc_controls.iam.domain.exception

class NoSuchUserException(val username: String) : Exception("User $username not found")