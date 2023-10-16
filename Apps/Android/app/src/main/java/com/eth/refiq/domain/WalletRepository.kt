package com.eth.refiq.domain

interface WalletRepository {
   suspend fun saveSecretPhrase(secretPhrase:String)

}