package com.hola.location

interface LocationListener {
    fun onCallback(client: ILocationClient, loc: Location)
}