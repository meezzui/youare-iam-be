package com.letter.security;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;


@Converter
public class CryptoStringConverter implements AttributeConverter<String,String> {

    private final CryptoHelper cryptoHelper = new CryptoHelper();

    @Override
    public String convertToDatabaseColumn(String plainText) {
        return Optional.ofNullable(plainText)
                .map(cryptoHelper::encryptToString)
                .orElse(null);
    }

    @Override
    public String convertToEntityAttribute(String encrypted) {
        return Optional.ofNullable(encrypted)
                .map(cryptoHelper::decryptToString)
                .orElse(null);
    }
}
