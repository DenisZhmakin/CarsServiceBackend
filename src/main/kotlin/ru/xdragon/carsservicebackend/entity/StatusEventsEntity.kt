package ru.xdragon.carsservicebackend.entity

import jakarta.persistence.*
import java.util.*

@Entity(name = "status_events")
data class StatusEventsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val index: Int,
    val timeOfRegistration: Date,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    val car: CarEntity,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_type_id", nullable = false)
    val statusType: StatusTypeEntity
)