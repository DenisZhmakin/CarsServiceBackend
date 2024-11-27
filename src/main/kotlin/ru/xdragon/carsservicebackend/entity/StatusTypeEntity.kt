package ru.xdragon.carsservicebackend.entity

import jakarta.persistence.*

@Entity(name="statuses")
data class StatusTypeEntity(
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    val id: Long,
    val value: String,
    @OneToMany(mappedBy = "statusType", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val statusEvents: Set<StatusEventsEntity>
)
