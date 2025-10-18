package com.wejden.grpc.weather;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import weather.Weather;
import weather.WeatherServiceGrpc;

import java.util.Arrays;
import java.util.List;

public class weatherClient {
    public static void main(String[] args) {
        List<String> servers = Arrays.asList("localhost:50051", "localhost:50052");
        String city = "Tunis";

        for (int i = 0; i < 6; i++) {
            String target = servers.get(i % servers.size());
            ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                    .usePlaintext()
                    .build();

            WeatherServiceGrpc.WeatherServiceBlockingStub stub = WeatherServiceGrpc.newBlockingStub(channel);
            Weather.TemperatureResponse response = stub.getTemperature(
                    Weather.CityRequest.newBuilder().setCity(city).build()
            );

            System.out.printf("Request %d → %s → %s %.1f°C%n",
                    i + 1, target, response.getCity(), response.getTemperature());

            channel.shutdown();
        }
    }
}

