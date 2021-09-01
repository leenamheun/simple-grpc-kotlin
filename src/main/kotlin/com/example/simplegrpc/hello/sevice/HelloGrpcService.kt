package com.example.simplegrpc.hello.sevice


import com.examples.grpc.Hello
import com.examples.grpc.HelloServiceGrpcKt
import org.lognet.springboot.grpc.GRpcService


@GRpcService
class HelloGrpcService(
) : HelloServiceGrpcKt.HelloServiceCoroutineImplBase() {
    override suspend fun sayHello(request: Hello.HelloRequest): Hello.HelloResponse {
        println("grpc request : ${request.name}")
        return Hello.HelloResponse.newBuilder().setHello("good").build()
    }
}

