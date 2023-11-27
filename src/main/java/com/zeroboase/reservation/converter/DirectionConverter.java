package com.zeroboase.reservation.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Sort.Direction;

public class DirectionConverter implements Converter<String, Direction> {

    @Override
    public Direction convert(String value) {
        return Direction.valueOf(value.toUpperCase());
    }
}
