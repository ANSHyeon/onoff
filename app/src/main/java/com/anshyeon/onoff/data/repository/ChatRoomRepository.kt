package com.anshyeon.onoff.data.repository

import com.anshyeon.onoff.data.model.ChatRoom
import com.anshyeon.onoff.network.FireBaseApiClient
import com.anshyeon.onoff.network.model.ApiResponse
import com.anshyeon.onoff.network.model.ApiResultException
import javax.inject.Inject

class ChatRoomRepository @Inject constructor(
    private val fireBaseApiClient: FireBaseApiClient
) : BaseRepository() {

    suspend fun getChatRoom(
    ): ApiResponse<Map<String, ChatRoom>> {
        return try{
            fireBaseApiClient.getChatRoom(
                getIdToken()
            )
        } catch (e: Exception) {
            ApiResultException(e)
        }
    }
}