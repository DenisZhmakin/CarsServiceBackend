package ru.xdragon.carsservicebackend.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "status_events")
data class CarStatusEventsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val index: Long,
    val timeOfRegistration: LocalDateTime,
    val activeStatusSeconds: Long,
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    val car: CarEntity,
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "status_type_id", nullable = false)
    val statusType: CarStatusTypeEntity
)