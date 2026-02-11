package dev.lynne.mc_controls.domain

interface BiMapper<A, B> {
    fun mapForward(param: A): B
    fun mapBackward(param: B): A
}