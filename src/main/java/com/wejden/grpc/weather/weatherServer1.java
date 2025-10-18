package com.wejden.grpc.weather;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import weather.Weather;
import weather.WeatherServiceGrpc;

public class weatherServer1 {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(50051)
                .addService((BindableService) new weatherServer2.WeatherServiceImpl())
                .build();

        System.out.println("Server1 running on port 50051...");
        server.start();
        server.awaitTermination();
    }

    static class WeatherServiceImpl extends WeatherServiceGrpc.WeatherServiceImplBase {
        @Override
        public void getTemperature(Weather.CityRequest request, StreamObserver<Weather.TemperatureResponse> responseObserver) {
            System.out.println("Server1 received request for " + request.getCity());
            Weather.TemperatureResponse response = Weather.TemperatureResponse.newBuilder()
                    .setCity(request.getCity())
                    .setTemperature(25.0)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
