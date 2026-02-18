package dev.lynne.mc_controls.iam.domain.exception

class NoCredentialsException(val username: String) : Exception("User $username does not have any credentials")