package ru.xdragon.carsservicebackend.service

import io.grpc.stub.StreamObserver
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.beans.factory.annotation.Autowired

import ru.xdragon.carsservicebackend.repository.CarRepository
import ru.xdragon.carsservicebackend.repository.CarStatusEventsRepository

import CarStatusEventsServiceOuterClass.CarStatusEventsRequest
import CarStatusEventsServiceOuterClass.CarStatusEventsResponse
import CarStatusEventsServiceOuterClass.CarStatusEventsCarIdRequest
import CarStatusEventsServiceOuterClass.ListCarStatusEventsResponse
import CarStatusEventsServiceGrpc.CarStatusEventsServiceImplBase
import ru.xdragon.carsservicebackend.entity.CarStatusEventsEntity
import ru.xdragon.carsservicebackend.repository.CarStatusTypeRepository
import kotlin.math.abs


@GrpcService
class CarStatusEventsService : CarStatusEventsServiceImplBase() {
    @Autowired
    private lateinit var carRepository: CarRepository

    @Autowired
    private lateinit var carStatusTypeRepository: CarStatusTypeRepository

    @Autowired
    private lateinit var carStatusEventsRepository: CarStatusEventsRepository

    override fun addNewCarStatusEvents(request: CarStatusEventsRequest, responseObserver: StreamObserver<CarStatusEventsResponse>) {
        val now = LocalDateTime.now()

        val carEntity = carRepository.findById(request.carId).get()
        val carStatusTypeEntity = carStatusTypeRepository.findById(request.statusTypeId).get()

        val carCount = carStatusEventsRepository.countByCar(carEntity)

        val activeStatusSeconds: Long = if (carCount > 0) {
            abs(ChronoUnit.SECONDS.between(
                now,
                carStatusEventsRepository.findOneByCarAndIndex(carEntity, carCount).timeOfRegistration
            ))
        } else {
            0
        }

        val carStatusEventsEntity = carStatusEventsRepository.save(
            CarStatusEventsEntity(
                index = carCount + 1,
                timeOfRegistration = now,
                car = carEntity,
                statusType = carStatusTypeEntity,
                activeStatusSeconds = activeStatusSeconds
            )
        )

        val response = CarStatusEventsResponse
            .newBuilder()
            .setId(carStatusEventsEntity.id!!)
            .setIndex(carStatusEventsEntity.index)
            .setCarId(carStatusEventsEntity.car.id!!)
            .setCarName(carStatusEventsEntity.car.name)
            .setStatusTypeId(carStatusEventsEntity.statusType.id!!)
            .setStatusValue(carStatusEventsEntity.statusType.value)
            .setTimeOfRegistration(carStatusEventsEntity.timeOfRegistration.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            ))
            .setActiveStatusTime(activeStatusSeconds)
            .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun getAllCarStatusEventsByCarID(request: CarStatusEventsCarIdRequest, responseObserver: StreamObserver<ListCarStatusEventsResponse>) {
        val result: MutableList<CarStatusEventsResponse> = mutableListOf()

        val carEntity = carRepository.findById(request.carId).get()

        carStatusEventsRepository.findAllByCar(carEntity).forEach { carStatusEvent ->
            result.add(
                CarStatusEventsResponse
                    .newBuilder()
                    .setId(carStatusEvent.id!!)
                    .setIndex(carStatusEvent.index)
                    .setCarId(carStatusEvent.car.id!!)
                    .setCarName(carStatusEvent.car.name)
                    .setStatusTypeId(carStatusEvent.statusType.id!!)
                    .setStatusValue(carStatusEvent.statusType.value)
                    .setTimeOfRegistration(
                        carStatusEvent.timeOfRegistration.format(
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        )
                    )
                    .setActiveStatusTime(carStatusEvent.activeStatusSeconds)
                    .build()
            )
        }

        val response = ListCarStatusEventsResponse
            .newBuilder()
            .addAllStatuses(result.sortedBy { it.index })
            .build()
        
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}