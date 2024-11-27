package ru.xdragon.carsservicebackend.service

import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import ru.xdragon.carsservicebackend.entity.CarTypeEntity
import ru.xdragon.carsservicebackend.repository.CarTypeRepository

import CarTypeServiceOuterClass.CarTypeRequest as CarTypeRequest
import CarTypeServiceOuterClass.CarTypeResponse as CarTypeResponse
import CarTypeServiceGrpc.CarTypeServiceImplBase as CarTypeServiceBase

@GrpcService
class CarTypeService : CarTypeServiceBase() {
    @Autowired
    private lateinit var carTypeRepository: CarTypeRepository

    override fun addNewCarType(request: CarTypeRequest, responseObserver: StreamObserver<CarTypeResponse>) {
        carTypeRepository.save(
            CarTypeEntity(
                value = request.value,
            )
        )

        val response = CarTypeResponse
            .newBuilder()
            .setId(1)
            .setValue("carTypeEntity.value")
            .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}