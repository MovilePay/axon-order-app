package com.movilepay

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OrderCommandApplication

fun main(args: Array<String>) {
	runApplication<OrderCommandApplication>(*args)
}
