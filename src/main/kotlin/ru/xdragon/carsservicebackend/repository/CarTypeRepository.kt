package ru.xdragon.carsservicebackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.xdragon.carsservicebackend.entity.CarTypeEntity

interface CarTypeRepository : JpaRepository<CarTypeEntity, Long>