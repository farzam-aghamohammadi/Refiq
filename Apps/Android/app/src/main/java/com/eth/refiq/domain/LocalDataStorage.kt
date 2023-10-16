package com.eth.refiq.domain

interface LocalDataStorage {
    fun saveValue(key: String, value: String)
    fun getValue(key: String): String?
}