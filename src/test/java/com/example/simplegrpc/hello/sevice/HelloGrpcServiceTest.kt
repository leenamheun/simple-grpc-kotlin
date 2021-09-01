package com.example.simplegrpc.hello.sevice

import com.examples.grpc.Hello
import com.examples.grpc.HelloServiceGrpcKt
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class HelloGrpcServiceTest(
) {
    companion object {
        private val stub = HelloServiceGrpcKt.HelloServiceCoroutineStub(
            ManagedChannelBuilder
                .forAddress("localhost", 6565)
                .usePlaintext()
                .build()
        )
    }

    suspend fun simpleTest() {
        delay(5000)
        val request = Hello.HelloRequest.newBuilder().setName("hello !!!!!!").build()
        val response = stub.sayHello(request).hello
        println("response ${response}")

    }

    @Test
    fun testSimpleTest() = runBlocking {
        simpleTest()
        println("call - simpleTest")
    }

    suspend fun multiResponseTest() {
        delay(5000)
        val request = Hello.HelloRequest.newBuilder().setName("hello !!!!!!").build()
        val response = stub.lotsOfResponse(request).let {
            it.collect { value -> println("response ${value}") }
        }
    }

    @Test
    fun callMultiResponseTest() = runBlocking {
        multiResponseTest()
    }

    suspend fun biMultiTest() {
        delay(5000)
        val request1 = Hello.HelloRequest.newBuilder().setName("hi ").build()
        val request2 = Hello.HelloRequest.newBuilder().setName("this is test").build()
        val request3 = Hello.HelloRequest.newBuilder().setName("hahaha :0 ) ").build()

        val requests = mutableListOf(request1, request2, request3).asFlow()

        val response = stub.bidiHello(requests).let {
            it.collect { value -> println("response ${value}") }
        }
    }

    @Test
    fun callBiMultiTest() = runBlocking {
        biMultiTest()
    }
}
