package com.example.tier_challeng_jonathancheli.Helper

interface DirectionFinderListener {
    fun onDirectionFinderStart()
    fun onDirectionFinderSuccess(route: MutableList<Route>)
}