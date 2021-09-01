package com.example.simplegrpc.hello.sevice

import com.examples.grpc.Hello
import com.examples.grpc.HelloServiceGrpcKt
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
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


    suspend fun suspendTest() {
        delay(500)
        println("suspendTest")
    }

    @Test
    fun callSuspendTest() = runBlockingTest {
        suspendTest()
        println("call - suspendTest")
    }


    @Test
    fun test() {
        println("zzzzzzzR")
    }
}
