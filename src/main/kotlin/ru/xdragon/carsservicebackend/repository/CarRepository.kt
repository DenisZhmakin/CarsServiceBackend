package ru.xdragon.carsservicebackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.xdragon.carsservicebackend.entity.CarEntity

interface CarRepository : JpaRepository<CarEntity, Long>