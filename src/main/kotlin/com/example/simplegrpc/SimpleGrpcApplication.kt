package com.example.simplegrpc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SimpleGrpcApplication

fun main(args: Array<String>) {
	runApplication<SimpleGrpcApplication>(*args)
}
