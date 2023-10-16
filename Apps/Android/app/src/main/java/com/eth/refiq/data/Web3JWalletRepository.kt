package com.eth.refiq.data

import com.eth.refiq.domain.LocalDataStorage
import com.eth.refiq.domain.WalletRepository

class Web3JWalletRepository constructor(private val localDataStorage: LocalDataStorage) :WalletRepository {
    override suspend fun saveSecretPhrase(secretPhrase: String) {
        localDataStorage.saveValue(SECRET_PHRASE,secretPhrase)
    }

    companion object {
        private const val SECRET_PHRASE="secret_phrase"
    }
}