/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;

/**
 *
 * @author pgn
 */
public class DbConnectionProvider
{

    private SQLServerDataSource ds;

    public DbConnectionProvider()
    {
        ds = new SQLServerDataSource();
        ds.setServerName("10.176.111.31");
        ds.setDatabaseName("mrs");
        ds.setUser("CS2018A_40");
        ds.setPassword("CS2018A_40");
    }
    
    public Connection getConnection() throws SQLServerException
    {
        return ds.getConnection();
    }

}
