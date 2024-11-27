package ru.xdragon.carsservicebackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CarsServiceBackendApplication

fun main(args: Array<String>) {
    runApplication<CarsServiceBackendApplication>(*args)
}
