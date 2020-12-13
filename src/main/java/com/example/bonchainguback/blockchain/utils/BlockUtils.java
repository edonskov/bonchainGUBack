package com.example.bonchainguback.blockchain.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BlockUtils {
    public static <T> T fromJSON(final TypeReference<T> type,
                                 final String jsonPacket) {
        T data = null;

        try {
            data = new ObjectMapper().readValue(jsonPacket, type);
        } catch (Exception e) {
            // Handle the problem
        }
        return data;
    }
}
