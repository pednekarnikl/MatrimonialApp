package com.ddesk.sddemoapp.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MatchesApiServiceTest {

    private lateinit var service: MatchesApiService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MatchesApiService::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    private fun enqueueMockResponse(fileName:String){
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }


    //test Cases
    @Test
    fun getMatches_sendRequest_receivedExpected(){
        runBlocking {
            enqueueMockResponse("")
            val responseBody = service.getMatches(9).body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
        }
    }

    @Test
    fun getMatches_sendRequest_receivedData(){
        runBlocking {
            enqueueMockResponse("")
            val responseBody = service.getMatches(9).body()
            val listSize = responseBody?.results?.size
            assertThat(listSize).isEqualTo(9)
        }
    }
}














