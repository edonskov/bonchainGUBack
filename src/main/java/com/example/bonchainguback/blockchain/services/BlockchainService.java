package com.example.bonchainguback.blockchain.services;

import com.example.bonchainguback.blockchain.dao.BlockGU;
import com.wavesplatform.wavesj.Node;
import com.wavesplatform.wavesj.Profile;
import com.wavesplatform.wavesj.exceptions.NodeException;
import im.mak.waves.transactions.InvokeScriptTransaction;
import im.mak.waves.transactions.SetScriptTransaction;
import im.mak.waves.transactions.TransferTransaction;
import im.mak.waves.transactions.account.Address;
import im.mak.waves.transactions.account.PrivateKey;
import im.mak.waves.transactions.account.PublicKey;
import im.mak.waves.transactions.common.*;
import im.mak.waves.transactions.invocation.Function;
import im.mak.waves.transactions.invocation.StringArg;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BlockchainService {

    @SneakyThrows
    public String doWork(BlockGU blockGU) throws IOException, NodeException {
        PrivateKey privateKey = PrivateKey.as("4oHSZfpzwVQBR3yVhzu2MSVxkoDSV6XUYprXJWfF3EXH");
        PublicKey publicKey = PublicKey.from(privateKey);

        Node node = new Node(Profile.TESTNET);
        Address buddy = new Address("3NAjrUCA7omNC3y1cXqDzVPzLo3jZzmnoFv");
        return doWork3(
                privateKey,
                publicKey,
                node,
                buddy,
                Function.as(
                        "addData",
                        StringArg.as(blockGU.getKey()),
                        StringArg.as(blockGU.getValue())));
    }

    @SneakyThrows
    public String doWorkTest(BlockGU blockGU) throws IOException, NodeException {
        PrivateKey privateKey = PrivateKey.as("83M4HnCQxrDMzUQqwmxfTVJPTE9WdE7zjAooZZm2jCyV");
        PublicKey publicKey = PublicKey.from(privateKey);

        Node node = new Node(Profile.TESTNET);
        Address buddy = new Address("3NAjrUCA7omNC3y1cXqDzVPzLo3jZzmnoFv");
        return doWork2(
                privateKey,
                publicKey,
                node,
                buddy,
                Function.as(
                        "addData",
                        StringArg.as(blockGU.getKey()),
                        StringArg.as(blockGU.getValue())));
    }

    @SneakyThrows
    public String doWorkTestMoney() throws IOException, NodeException {
        System.out.println("trying...");
//        String seed = "jaguar govern around million shove antique curve cotton evil join public summer powder typical noise";//Crypto.getRandomSeedPhrase();
        PrivateKey privateKey = PrivateKey.as("83M4HnCQxrDMzUQqwmxfTVJPTE9WdE7zjAooZZm2jCyV");
        PublicKey publicKey = PublicKey.from(privateKey);
        Address address = Address.from((byte)'R', publicKey);

        Node node = new Node("http://178.154.249.143:6869");

        System.out.println("Current height is " + node.getHeight());
        System.out.println("My balance is " + node.getBalance(address));
        System.out.println("With 100 confirmations: " + node.getBalance(address, 100));

        return doWork1(privateKey, publicKey, node);


//        System.out.println("ура!!!!");
    }

    private String doWork1(PrivateKey privateKey, PublicKey publicKey, Node node) throws IOException, NodeException {
        Address buddy = new Address("3MGqWUDyZuENhGUK7MqcavBbgtTgfMeDzUW");
        TransferTransaction transferTransaction = new TransferTransaction(
                publicKey,
                buddy,
                Amount.of(1_00000000),
                Base58String.empty(),
                (byte)'R',
                Amount.of(0_10000000),
                System.currentTimeMillis(),
                2,
                Proof.emptyList()
        ).addProof(privateKey);

        return node.broadcast(transferTransaction).toJson();
    }

    private String doWork2(
            PrivateKey privateKey,
            PublicKey publicKey,
            Node node,
            Recipient dApp,
            Function function
    ) throws IOException, NodeException {
        InvokeScriptTransaction transferTransaction = new InvokeScriptTransaction (
                publicKey,
                dApp,
                function,
                null,
                (byte)'T',
                Amount.of(0_10000000),
                System.currentTimeMillis(),
                2,
                Proof.emptyList()
        ).addProof(privateKey);
        return node.broadcast(transferTransaction).toJson();
    }

    private String doWork3(
            PrivateKey privateKey,
            PublicKey publicKey,
            Node node,
            Recipient dApp,
            Function function
    ) throws IOException, NodeException {
        InvokeScriptTransaction transferTransaction = new InvokeScriptTransaction (
                publicKey,
                dApp,
                function,
                null,
                (byte)'T',
                Amount.of(0_10000000),
                System.currentTimeMillis(),
                2,
                Proof.emptyList()
        ).addProof(privateKey);
        return node.broadcast(transferTransaction).toJson();
    }


}