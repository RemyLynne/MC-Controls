package dev.lynne.mc_controls.bootstrap.mode

class ModeResolver(
    private val modeDetector: ModeDetector = ModeDetector(),
    private val modeConfig: ModeConfig = ModeConfig()
) {
    fun resolve(args: Array<String>): ResolvedMode {
        val mode = modeDetector.detect(args)
        return modeConfig.forMode(mode)
    }
}