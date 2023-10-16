package com.eth.refiq.domain

interface Web3Repository {
    suspend fun createWallet(password: String): String
}