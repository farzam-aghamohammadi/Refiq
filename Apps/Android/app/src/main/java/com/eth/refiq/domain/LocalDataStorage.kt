package com.eth.refiq.domain

interface LocalDataStorage {
    fun saveValue(key: String, value: String)
    fun getValue(key: String): String?
    fun saveValue(key: String, value: Boolean)
    fun getBooleanValue(key: String): Boolean
}