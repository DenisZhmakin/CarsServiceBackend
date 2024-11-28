package ru.xdragon.carsservicebackend.service

import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.beans.factory.annotation.Autowired

import ru.xdragon.carsservicebackend.entity.CarTypeEntity
import ru.xdragon.carsservicebackend.repository.CarTypeRepository

import CarTypeServiceOuterClass.CarTypeRequest
import CarTypeServiceOuterClass.CarTypeResponse
import CarTypeServiceOuterClass.CarTypeEmptyRequest
import CarTypeServiceOuterClass.ListCarTypeResponse
import CarTypeServiceGrpc.CarTypeServiceImplBase as CarTypeServiceBase


@GrpcService
class CarTypeService : CarTypeServiceBase() {
    @Autowired
    private lateinit var carTypeRepository: CarTypeRepository

    override fun addNewCarType(request: CarTypeRequest, responseObserver: StreamObserver<CarTypeResponse>) {
        val carTypeEntity = carTypeRepository.save(
            CarTypeEntity(
                value = request.value,
            )
        )

        val response = CarTypeResponse
            .newBuilder()
            .setId(carTypeEntity.id!!)
            .setValue(carTypeEntity.value)
            .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun getAllCarTypes(request: CarTypeEmptyRequest, responseObserver: StreamObserver<ListCarTypeResponse>) {
        val result: MutableList<CarTypeResponse> = mutableListOf()

        carTypeRepository.findAll().forEach { carTypeEntity ->
            result.add(
                CarTypeResponse
                    .newBuilder()
                    .setId(carTypeEntity.id!!)
                    .setValue(carTypeEntity.value)
                    .build()
            )
        }

        val response = ListCarTypeResponse
            .newBuilder()
            .addAllCarTypes(result)
            .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}