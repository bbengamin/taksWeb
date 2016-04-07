package com.epam.bohdanov.service.transaction;

import java.sql.Connection;

import com.epam.bohdanov.controller.exception.DataException;
import com.epam.bohdanov.utils.DBUtils;

public class JdbcTransactionManager implements TransactionManager {
    private DBUtils dbUtils;

    public JdbcTransactionManager(DBUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    public <T> T execute(TransactionOperation<T> op) {

        Connection con = dbUtils.getConnection();
        T res = null;
        try {
            con.setAutoCommit(false);
            res = op.execute(con);
            con.commit();
        } catch (Exception e) {
            dbUtils.rollback(con);
            throw new DataException(e);
        } finally {
            dbUtils.closeConnection(con);
        }
        return res;
    }

}
