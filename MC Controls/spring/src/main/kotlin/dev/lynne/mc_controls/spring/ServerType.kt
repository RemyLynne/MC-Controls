package dev.lynne.mc_controls.spring

enum class ServerType {
    CLI,
    SERVER;

    /**
     * @return profile key
     */
    operator fun invoke() = "${javaClass.name}@$name"
}