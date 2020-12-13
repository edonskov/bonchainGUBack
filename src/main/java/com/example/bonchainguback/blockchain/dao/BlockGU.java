package com.example.bonchainguback.blockchain.dao;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@JsonDeserialize
@Data
public class BlockGU {
    String key;
    String value;
}
