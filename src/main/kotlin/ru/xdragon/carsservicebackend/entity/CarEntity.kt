package ru.xdragon.carsservicebackend.entity

import jakarta.persistence.*

@Entity(name="sdo")
data class CarEntity(
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_id", nullable = false)
    val carType: CarTypeEntity,
    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val carStatusEvents: Set<CarStatusEventsEntity> = setOf()
)