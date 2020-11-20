package movierecsys.dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import movierecsys.dal.ObjectPool;

public class JDBCConnectionPool extends ObjectPool<Connection>
{

    private static JDBCConnectionPool INSTANCE;
    private final DbConnectionProvider connectionProvider;

    public synchronized static JDBCConnectionPool getInstance() throws IOException //I make the JDBC Connection Pool a Singleton.
    {
        if(INSTANCE == null)
            INSTANCE = new JDBCConnectionPool();
        return INSTANCE;
    }
    
    private JDBCConnectionPool() throws IOException
    {
        connectionProvider = new DbConnectionProvider(); 
    }

    @Override
    protected Connection create()
    {
        try
        {
            return connectionProvider.getConnection();
        } catch (SQLServerException ex)
        {
            ex.printStackTrace(); //Perfect exception handling... (NOT!)
            return null;
        }
    }

    @Override
    public boolean validate(Connection con)
    {
        try
        {
            return (!con.isClosed());
        } catch (SQLException e)
        {
            e.printStackTrace();
            return (false);
        }
    }

    @Override
    public void expire(Connection con)
    {
        try
        {
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
