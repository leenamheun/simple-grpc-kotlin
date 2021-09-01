package com.example.simplegrpc.hello.sevice


import com.examples.grpc.Hello
import com.examples.grpc.HelloServiceGrpcKt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.lognet.springboot.grpc.GRpcService


@GRpcService
class HelloGrpcService(
) : HelloServiceGrpcKt.HelloServiceCoroutineImplBase() {
    override suspend fun sayHello(request: Hello.HelloRequest): Hello.HelloResponse {
        println("grpc request : ${request.name}")
        return Hello.HelloResponse.newBuilder().setHello("good").build()
    }

    override fun lotsOfResponse(request: Hello.HelloRequest): Flow<Hello.HelloResponse> {
        val listFlow = mutableListOf<Hello.HelloResponse>()
        for (i in 0 until 5) {
            val resp = Hello.HelloResponse.newBuilder()
                .setHello("hello - $i")
                .build()
            listFlow.add(resp)
        }
        return listFlow.asFlow()
    }

    override fun bidiHello(requests: Flow<Hello.HelloRequest>): Flow<Hello.HelloResponse> {
        val listFlow = mutableListOf<Hello.HelloResponse>()
        runBlocking {
            requests.collect { value ->
                val resp = Hello.HelloResponse.newBuilder()
                    .setHello("hello -> ${value}")
                    .build()
                listFlow.add(resp)
            }
        }
        return listFlow.asFlow()
    }
}

