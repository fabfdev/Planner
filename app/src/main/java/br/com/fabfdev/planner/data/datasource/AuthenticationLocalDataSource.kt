package br.com.fabfdev.planner.data.datasource

import kotlinx.coroutines.flow.Flow

interface AuthenticationLocalDataSource {
    val token: Flow<String>
    val expirationDatetime: Flow<Long>
    suspend fun insertToken(token: String)
}