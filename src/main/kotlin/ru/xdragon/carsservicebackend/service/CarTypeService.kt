package ru.xdragon.carsservicebackend.service

import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService

import CarTypeServiceOuterClass.CarTypeRequest as CarTypeRequest
import CarTypeServiceOuterClass.CarTypeResponse as CarTypeResponse
import CarTypeServiceGrpc.CarTypeServiceImplBase as CarTypeServiceBase

@GrpcService
class CarTypeService : CarTypeServiceBase() {
    override fun addNewCarType(request: CarTypeRequest, responseObserver: StreamObserver<CarTypeResponse>) {
        val response = CarTypeResponse
            .newBuilder()
            .setId(1)
            .setValue("Hello World")
            .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}