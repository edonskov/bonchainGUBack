# Решение команды BonchainGU задачи №5
Данные документ содержит описание решения задачи №5 командой BonchainGU в рамках Blockchain Hackathon.

**Условия задачи №5:** 
Развернуть блокчейн сеть и реализовать механизм реализации сделки (поставка МТО), заключающийся в согласовании между покупателем и поставщиком условий ранней оплаты счетов, т.е. оплаты счетов ранее крайней даты платежа по договору в обмен на бонус (премия за раннюю оплату) от поставщика.
Адреса машин для настройки:
ssh user@178.154.249.143 
ssh user@84.201.169.77 
ssh user@178.154.232.233

Решение задачи было разбито на 4 этапа:

- Развертывание приватной блокчейн-сети
- Разработка смарт-контракта
- Разработка серверного приложения
- Разработка веб-приложения

Далее приведено более подробное описание реализации каждого этапа.

### Развертывание приватной блокчейн-сети
Для реализации данного этапа было рекомендовано несколько блокчейн-платформ: Masterchain, HLF, Waves ENT.  Наша команда сделала выбор в пользую блокчейн-платформы Waves. Обе платформы используют язык смарт-контрактов Ride. Выбор блокчейн-платформы Waves обусловлен ее развитостью с точки зрения разработки законченного решения, удобного для пользовательского взаимодействия. Наш проект может быть быстро адаптирован под блокчейн-платформу Waves ENT, где единственным отличием будет наличие пользовательских токенов вместо использования Waves Keeper для подписи транзакций. 
Участникам хакатона было выделено 3 виртуальные машины с ОС Ubuntu 20.04. Блокчейн-сеть настроена и синхронизирована на двух виртуальных машинах, поскольку это дает достаточную производительность и использование третьего узла уже является излишним. Ноды настроены на базе Java JDK 8 с помощью jar-ноды. Конфигурационные файлы были адаптированы под требования к блокчейн-сети команды.
**Файл genesis.conf
```Addresses:
c0:
 Seed text:           c0
 Seed:                8Yo
 Account seed:        Db4U5EaWicjrmZ343S65zJShJzVAA8pHdmAfLdePrkj5
 Private account key: CJpAR2kQ9Z2NQ9DZ7npCXvELVsugC73NdfSQmhHd6htk
 Public account key:  FeWKGP9PZaX5y5zwyogNUx5shdLxAyK1mNY33zTqBEiq
 Account address:     3Evs1avTscMTMNHthFwGLXos6dtYWbMY5ub

c1:
 Seed text:           c1
 Seed:                8Yp
 Account seed:        Ez6qzPLNPQhLAMCCccvcCv8tJVDn3vZNjW1pzfqgAomj
 Private account key: 13adG1bsitzzkMsFE5KCP1SjfcuSVy3XLeSVXRuUYzvs
 Public account key:  4QXjnkcn6tirmZcc9PBQRq9f6eQ2wZcXrSaxckK94QDD
 Account address:     3Exdg3T8rvooDkaGyViP8qUrLKPCxaLDmYL

Settings:
genesis {
  average-block-delay = 20000ms
  initial-base-target = 10
  timestamp = 1607808846573
  block-timestamp = 1607808846573
  signature = "gfwwe8HA3F7si26G4bLgh3adyJJY9rk38dkq5WmGhpXMRY2BVn4kiTrGLmZhyNiuQj2kcfpzopjH5rputMqww2B"
  initial-balance = 11000
  transactions = [
    {recipient = "3Evs1avTscMTMNHthFwGLXos6dtYWbMY5ub", amount = 1000},
    {recipient = "3Exdg3T8rvooDkaGyViP8qUrLKPCxaLDmYL", amount = 10000}
  ]
}
```
**Конфигурационный файл custom.conf**
```
waves {
  blockchain {
    type: CUSTOM
    custom {
      address-scheme-character: "B"
      functionality {
        feature-check-blocks-period = 5
        blocks-for-feature-activation = 5
        allow-temporary-negative-until: 0
        allow-invalid-payment-transactions-by-timestamp: 0
        require-sorted-transactions-after: 0
        generation-balance-depth-from-50-to-1000-after-height: 0
        minimal-generating-balance-after: 0
        allow-transactions-from-future-until: 0
        allow-unissued-assets-until: 0
        require-payment-unique-id-after: 0
        allow-invalid-reissue-in-same-block-until-timestamp: 0
        allow-multiple-lease-cancel-transaction-until-timestamp: 0
        reset-effective-balances-at-height: 1
        allow-leased-balance-transfer-until: 0
        block-version-3-after-height: 0
        pre-activated-features = {
          1 = 0
          2 = 0
          3 = 0
          4 = 0
          5 = 0
          6 = 0
          7 = -${waves.blockchain.custom.functionality.feature-check-blocks-period}
          8 = 0
          9 = 0
          10 = 0
          11 = 0
          12 = 0
          13 = 0
          14 = 1
          15 = 0
        }
        double-features-periods-after-height = 1000000000
        max-transaction-time-back-offset = 120m
        max-transaction-time-forward-offset = 90m
        min-asset-info-update-interval = 2
        min-block-time = 5s
      }
      genesis {
        average-block-delay = 20000ms
        initial-base-target = 10
        timestamp = 1607808846573
        block-timestamp = 1607808846573
        signature = "gfwwe8HA3F7si26G4bLgh3adyJJY9rk38dkq5WmGhpXMRY2BVn4kiTrGLmZhyNiuQj2kcfpzopjH5rputMqww2B"
        initial-balance = 11000
        transactions = [
          {recipient = "3Evs1avTscMTMNHthFwGLXos6dtYWbMY5ub", amount = 1000},
          {recipient = "3Exdg3T8rvooDkaGyViP8qUrLKPCxaLDmYL", amount = 10000}
        ]
      }
      rewards {
        term = 10
        initial = 600000000
        min-increment = 50000000
        voting-interval = 10
      }
    }
  }

  rewards.desired = 600000000

  network {
    bind-address = "0.0.0.0"
    port = 6860
    known-peers = ["10.128.0.55:6860"]
    node-name = "bonchain-1"
    declared-address = "10.128.0.42:6860"

    traffic-logger {
      ignore-tx-messages = [1, 2, 20, 21, 22, 24, 26, 27, 28]
      ignore-rx-messages = ${waves.network.traffic-logger.ignore-tx-messages}
    }
  }

  wallet {
    seed = "Db4U5EaWicjrmZ343S65zJShJzVAA8pHdmAfLdePrkj5"
    password = "bonchain"
  }

  rest-api {
    enable = yes
    bind-address = "0.0.0.0"
    port = 6869
    api-key-hash = "7XczD2t3pCQvPw1ubZufDsU4JVC4mqvncgCgaabdoZ9jpQYT"
  }

  miner {
    interval-after-last-block-then-generation-is-allowed = 999d
    max-transactions-in-micro-block = 500
    micro-block-interval = 1500ms
    min-micro-block-age = 0s
    quorum = 0
  }
}
```
Также подключены дополнительные сервисы: сервис данных (Data Service), dApp, Waves Explorer. Эти сервисы позволяют получать данные из блокчейн-сети посредством REST API запросов, просматривать данные из блокчейн-сети и работать с dApp.
Необходимо отметить, что параметр сложности в сети, а также другие параметры были выбраны с точки зрения эффективности работы в тестовом режиме, то есть быстрое формирование блоков и отработка.

Но для конечного взаимодействия команда реализовала закрытую блокчейн-сеть на базе Docker контейнеров.

Ссылки на REST-API, Waves Explorer
bonchain-1 rest point: http://178.154.249.143:6869/
bonchain-2 rest point: http://84.201.169.77:6869/
waves explorer: http://178.154.249.143:3000/custom


### Разработка смарт-контракта
Для реализации данного этапа было принято решение разрабатывать смарт-контракт, который будет размещать в своем хранилище данных всю ключевую информацию об аукционах, ставках на аукционах и заключенных в последствии договорах.
В итоге был разработан смарт-контракт на языке Ride, задеплоенную версию которого можно посмотреть в тестовой сети Waves по ссылке: 
```
https://testnet.wavesexplorer.com/address/3NAjrUCA7omNC3y1cXqDzVPzLo3jZzmnoFv/tx
```
***Смарт-контракт addData***
```
{-# STDLIB_VERSION 4 #-}
{-# SCRIPT_TYPE ACCOUNT #-}
{-# CONTENT_TYPE DAPP #-}
let adminPubKey = "3NAjrUCA7omNC3y1cXqDzVPzLo3jZzmnoFv"

@Callable(i)
func addData (key,data) = {
    let callerStr = toBase58String(i.caller.bytes)
    if ((callerStr != adminPubKey))
        then throw("Only admin can add entries")
        else [StringEntry(key, data)]
    }


@Verifier(tx)
func verify () = sigVerify(tx.bodyBytes, tx.proofs[0], tx.senderPublicKey)
```
### Разработка серверного приложения
В основные функции серверного приложения входят основная логика аукционов, взаимодействия заказчиков и поставщиков, формирования и согласования документов, а также взаимодействие с сетью Waves. В ходе планирования было принято решение отделить все взаимодействие с блокчейном в отдельное приложение - оракул сети Waves. Все оставшаяся логика будет сформирована в рамках другого серверного приложения.

***Оракул взаимодействия с сетью Waves***
Для реализации оракула была выбрана платформа Java, с использованием библиотеки WavesJ, которая позволяет напрямую взаимодействовать с программным интерфейсом интерфейсом Waves. Подробную реализацию можно посмотреть в этом репозитории или по ссылке:
https://github.com/edonskov/bonchainGUBack

***Основное серверное приложение***
Основное серверное приложение было реализовано на языке C#. Данное серверное приложение включает в себя логику проведения аукционов, распределения средств между победителями и формирование документа. Все взаимодействие с сетью блокчейн происходит через оракула. Посмотреть реализацию можно в другом репозитории по ссылке:
https://github.com/vladg2e/bonchainGU-api

### Разработка веб-приложения 
В качестве веб приложения на стадии пре-релиза было выбран простейший инструмент для взаимеодействия с серверным приложением - Swagger. 
Подробнее о Swagger можно почитать здесь: https://swagger.io/
