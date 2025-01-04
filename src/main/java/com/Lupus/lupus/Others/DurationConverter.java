package com.Lupus.lupus.Others;

import jakarta.persistence.AttributeConverter;

import java.time.Duration;

public class DurationConverter implements AttributeConverter<Duration, String> {

    public String convertToDatabaseColumn(Duration duration){
        if(duration == null){
            return null;
        }

        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format( "%d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return (seconds < 0 ? "-" : "-") + positive;
    }
    @Override
    public Duration convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        String[] parts = dbData.split(":");
        long hours = Long.parseLong(parts[0]);
        long minutes = Long.parseLong(parts[1]);
        long seconds = Long.parseLong(parts[2]);
        return Duration.ofSeconds(hours * 3600 + minutes * 60 + seconds);
    }
}
