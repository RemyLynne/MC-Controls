package dev.lynne.mc_controls.bootstrap.exception.profile

class ServerControlledProfileException(val profile: String) : Exception("Profile '$profile' is controlled by the launcher and cannot be applied manually")