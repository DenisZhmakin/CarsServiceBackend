package ru.xdragon.carsservicebackend.service

import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.beans.factory.annotation.Autowired

import ru.xdragon.carsservicebackend.entity.CarEntity
import ru.xdragon.carsservicebackend.repository.CarRepository
import ru.xdragon.carsservicebackend.repository.CarTypeRepository

import CarServiceOuterClass.CarRequest
import CarServiceOuterClass.CarResponse
import CarServiceGrpc.CarServiceImplBase


@GrpcService
class CarService : CarServiceImplBase() {
    @Autowired
    private lateinit var carRepository: CarRepository

    @Autowired
    private lateinit var carTypeRepository: CarTypeRepository

    override fun addNewCar(request: CarRequest, responseObserver: StreamObserver<CarResponse>) {
        val carTypeEntity = carTypeRepository.findById(request.carTypeId).get()

        val carEntity = carRepository.save(
            CarEntity(
                name = request.name,
                carType = carTypeEntity
            )
        )

        val response = CarResponse
            .newBuilder()
            .setId(carEntity.id!!)
            .setName(carEntity.name)
            .setCarTypeId(carTypeEntity.id!!)
            .build()
        
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}