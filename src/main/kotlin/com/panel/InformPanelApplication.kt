package com.panel

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LogingServiceApplication

fun main(args: Array<String>) {
	runApplication<com.panel.LogingServiceApplication>(*args)
}
