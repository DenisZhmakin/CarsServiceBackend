package ru.xdragon.carsservicebackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.xdragon.carsservicebackend.entity.CarEntity
import ru.xdragon.carsservicebackend.entity.CarStatusEventsEntity

interface CarStatusEventsRepository : JpaRepository<CarStatusEventsEntity, Long> {
    fun countByCar(car: CarEntity): Long
    fun findAllByCar(car: CarEntity): MutableList<CarStatusEventsEntity>
    fun findOneByCarAndIndex(car: CarEntity, index: Long): CarStatusEventsEntity
}