package com.reactivedemo.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.concurrent.ThreadLocalRandom

@SpringBootApplication
class ReactiveDemoServerApplication

fun main(args: Array<String>) {
    runApplication<ReactiveDemoServerApplication>(*args)
}

@RestController
class RestController {
    @GetMapping("/stocks/{symbol}", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun prices(@PathVariable("symbol") symbol: String): Flux<StockPrice> {
        return Flux.interval(Duration.ofSeconds(1)).map {
            StockPrice(symbol, randomStockPrice(), now())
        }
    }

    private fun randomStockPrice(): Double {
        return ThreadLocalRandom.current().nextDouble(100.0)
    }
}

data class StockPrice(
    val symbol: String,
    val price: Double,
    val time: LocalDateTime
)