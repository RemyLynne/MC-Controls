package dev.lynne.mc_controls.spring

enum class ServerType(private val profile: String) {
    CLI(ServerTypeProfiles.CLI),
    SERVER(ServerTypeProfiles.SERVER);

    /**
     * @return profile key
     */
    operator fun invoke() = profile
}