package org.passorder.domain

interface PassDataStore {
    var accessToken: String
    var refreshToken: String
    var userUUID: String
    var storeUUID: String
    fun clear()
}