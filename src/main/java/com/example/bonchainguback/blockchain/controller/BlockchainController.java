package com.example.bonchainguback.blockchain.controller;

import com.example.bonchainguback.blockchain.client.BlockchainClient;
import com.example.bonchainguback.blockchain.dao.BlockGU;
import com.example.bonchainguback.blockchain.services.BlockchainService;
import com.wavesplatform.wavesj.exceptions.NodeException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/blockchain")
@Slf4j
public class BlockchainController {

    final BlockchainService blockchainService;
    final BlockchainClient blockchainClient;

    public BlockchainController(BlockchainService blockchainService, BlockchainClient blockchainClient) {
        this.blockchainService = blockchainService;
        this.blockchainClient = blockchainClient;
    }

    @RequestMapping(value = "/setData", method = { RequestMethod.POST }
    )
    public ResponseEntity onSetData(
            @RequestBody BlockGU blockGU
    ) {
        try {
            return new ResponseEntity(blockchainService.doWork(blockGU), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NodeException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @SneakyThrows
    @RequestMapping(value = "/getData", method = { RequestMethod.GET }
    )
    public String onGetData(
            @RequestParam(defaultValue = "", required = false) String key
    ) {
        return BlockchainClient.getData(key);
    }

    @SneakyThrows
    @RequestMapping(value = "/setTest", method = { RequestMethod.GET }
    )
    public ResponseEntity onSetDataTest() {
        try {
            return new ResponseEntity(blockchainService.doWorkTestMoney(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NodeException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @SneakyThrows
    @RequestMapping(value = "/getTest", method = { RequestMethod.GET }
    )
    public String onGetDataTest(
            @RequestParam(defaultValue = "", required = false) String key
    ) {
        return BlockchainClient.getDataTest(key);
    }

    @SneakyThrows
    @RequestMapping(value = "/getTestMoney", method = { RequestMethod.GET }
    )
    public String onGetDataTestMoney(
            @RequestParam(defaultValue = "", required = false) String key
    ) {
        return BlockchainClient.getDataTest(key);
    }

}
