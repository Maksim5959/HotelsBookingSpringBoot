package com.chuyashkou.hotels_booking.util;

public enum DataSourceMappingConstant {

    DB_URL("spring.datasource.url"),
    DB_USER_NAME("spring.datasource.username"),
    DB_USER_PASSWORD("spring.datasource.password");

    private final String key;

    DataSourceMappingConstant(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
