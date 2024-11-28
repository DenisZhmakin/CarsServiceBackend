package ru.xdragon.carsservicebackend.service

import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.beans.factory.annotation.Autowired

import CarStatusTypeServiceOuterClass.CarStatusTypeRequest
import CarStatusTypeServiceOuterClass.CarStatusTypeResponse
import CarStatusTypeServiceOuterClass.CarStatusTypeEmptyRequest
import CarStatusTypeServiceOuterClass.ListCarStatusTypeResponse
import CarStatusTypeServiceGrpc.CarStatusTypeServiceImplBase
import ru.xdragon.carsservicebackend.entity.CarStatusTypeEntity

import ru.xdragon.carsservicebackend.repository.CarStatusTypeRepository


@GrpcService
class CarStatusTypeService : CarStatusTypeServiceImplBase() {
    @Autowired
    private lateinit var carStatusTypeRepository: CarStatusTypeRepository

    override fun addNewCarStatusType(request: CarStatusTypeRequest, responseObserver: StreamObserver<CarStatusTypeResponse>) {
        val carStatusTypeEntity = carStatusTypeRepository.save(
            CarStatusTypeEntity(
                value = request.value
            )
        )

        val response = CarStatusTypeResponse
            .newBuilder()
            .setId(carStatusTypeEntity.id!!)
            .setValue(carStatusTypeEntity.value)
            .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun getAllCarStatusTypes(request: CarStatusTypeEmptyRequest, responseObserver: StreamObserver<ListCarStatusTypeResponse>) {
        val result: MutableList<CarStatusTypeResponse> = mutableListOf()

        carStatusTypeRepository.findAll().forEach { carStatusTypeEntity ->
            result.add(
                CarStatusTypeResponse
                    .newBuilder()
                    .setId(carStatusTypeEntity.id!!)
                    .setValue(carStatusTypeEntity.value)
                    .build()
            )
        }

        val response = ListCarStatusTypeResponse
            .newBuilder()
            .addAllCarStatusTypes(result)
            .build()
        
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}