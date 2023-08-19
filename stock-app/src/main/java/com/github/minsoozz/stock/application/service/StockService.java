package com.github.minsoozz.stock.application.service;

import com.github.minsoozz.stock.application.port.in.StockUseCase;
import com.github.minsoozz.stock.application.port.out.StockPersistencePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StockService implements StockUseCase {

    private final Logger logger = LoggerFactory.getLogger(StockService.class);
    private final StockPersistencePort stockPersistencePort;

    public StockService(StockPersistencePort stockPersistencePort) {
        this.stockPersistencePort = stockPersistencePort;
    }

    @Override
    public void decreaseStock(String orderNumber) {
        try {
            logger.info("Stock decrease started: {}", orderNumber);
            stockPersistencePort.decreaseStock(orderNumber);
        } catch (Exception e) {
            logger.error("Stock decrease failed: {}", orderNumber);
            e.printStackTrace();
        }
    }

    @Override
    public void increaseStock(String orderNumber) {
        logger.info("Stock increase started: {}", orderNumber);
        stockPersistencePort.increaseStock(orderNumber);
        rollbackCreatedOrderEvent(orderNumber);
    }

    @Override
    public void rollbackCreatedOrderEvent(String orderNumber) {
        logger.info("Stock rollback started: {}", orderNumber);
    }
}
