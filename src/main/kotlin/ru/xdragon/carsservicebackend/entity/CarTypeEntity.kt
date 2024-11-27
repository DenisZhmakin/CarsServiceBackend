package ru.xdragon.carsservicebackend.entity

import jakarta.persistence.*

@Entity(name = "sdo_type")
data class CarTypeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1,
    val value: String,
    @OneToMany(mappedBy = "carType", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val cars: Set<CarEntity> = emptySet()
)