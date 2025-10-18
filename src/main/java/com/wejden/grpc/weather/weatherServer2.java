package com.wejden.grpc.weather;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import weather.Weather;
import weather.WeatherServiceGrpc;

public class weatherServer2 {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(50052)
                .addService(new weatherServer2.WeatherServiceImpl())
                .build();

        System.out.println("Server2 running on port 50052...");
        server.start();
        server.awaitTermination();
    }

    static class WeatherServiceImpl extends WeatherServiceGrpc.WeatherServiceImplBase {
        @Override
        public void getTemperature(Weather.CityRequest request, StreamObserver<Weather.TemperatureResponse> responseObserver) {
            System.out.println("Server2 received request for " + request.getCity());
            Weather.TemperatureResponse response = Weather.TemperatureResponse.newBuilder()
                    .setCity(request.getCity())
                    .setTemperature(26.0)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
