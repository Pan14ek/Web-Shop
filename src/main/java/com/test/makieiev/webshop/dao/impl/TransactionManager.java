package com.test.makieiev.webshop.dao.impl;

import com.test.makieiev.webshop.dao.Operation;
import com.test.makieiev.webshop.exception.InvalidConnectionException;
import com.test.makieiev.webshop.exception.InvalidRollbackException;
import com.test.makieiev.webshop.exception.InvalidTransactionException;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * TransactionManager is class which operate with transaction for database, get connection for connect with database
 *
 * @param <T> determines the type
 * @author Oleksii_Makieiev1
 */
public class TransactionManager<T> {

    private static final Logger LOGGER = Logger.getLogger(TransactionManager.class);

    private final DataSource dataSource;

    public TransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Get connection from data source
     *
     * @return connection
     */
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Cannot obtain a connection from the pool", e);
            throw new InvalidConnectionException("Cannot obtain a connection from the pool");
        }
    }

    /**
     * The method execute some operations and close
     *
     * @param operation  is interface which do operation
     * @param connection is connection with database
     * @return special object from operation method operate
     */
    public T executeAndClose(Operation<T> operation, Connection connection) {
        try {
            return operation.operate();
        } finally {
            close(connection);
        }
    }

    /**
     * The method control transactions , execute some operations and close
     *
     * @param operation  is interface which do operation
     * @param connection is connection with database
     * @return special object from operation method operate
     */
    public T doTransaction(Operation<T> operation, Connection connection) {
        try {
            setAutoCommit(connection, false);
            T returnValue = operation.operate();
            connection.commit();
            return returnValue;
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Cannot do transaction", e);
            throw new InvalidTransactionException("Cannot do transaction");
        } finally {
            setAutoCommit(connection, true);
            close(connection);
        }
    }

    /**
     * The method set auto commit
     *
     * @param connection is connection with database
     * @param autoCommit is a flag which change auto commit
     * @throws InvalidTransactionException if some trouble with transaction
     */
    private void setAutoCommit(Connection connection, boolean autoCommit) {
        try {
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            throw new InvalidTransactionException("Problem with auto commit", e);
        }
    }

    /**
     * The method rollback transaction if transaction is did some troubles with transaction
     *
     * @param connection is connection with database
     */
    private void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.error("Cannot rollback transaction", e);
            throw new InvalidRollbackException("Cannot rollback transaction", e);
        }
    }

    /**
     * The method close connection
     *
     * @param connection is connection with database
     */
    private void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Cannot close a connection", e);
            throw new InvalidConnectionException("Cannot close a connection", e);
        }
    }

}