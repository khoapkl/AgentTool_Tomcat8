/*
 * FILENAME
 *     DAOUtils.java
 *
 * FILE LOCATION
 *     $Source$
 *
 * VERSION
 *     $Id$
 *         @version       $Revision$
 *         Check-Out Tag: $Name$
 *         Locked By:     $Lockers$
 *
 * FORMATTING NOTES
 *     * Lines should be limited to 120 characters.
 *     * Files should contain no tabs.
 *     * Indent code using four-character increments.
 *
 * COPYRIGHT
 *     Copyright (C) 2010 DELL. All rights reserved.
 *     This software is the confidential and proprietary information of
 *     DELL ("Confidential Information"). You shall not
 *     disclose such Confidential Information and shall use it only in
 *     accordance with the terms of the licence agreement you entered into
 *     with DELL.
 */

package com.dell.enterprise.agenttool.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.util.logging.Logger;

/**
 * @author BINHHT
 */
public final class DAOUtils
{

    private static DAOUtils instance = new DAOUtils();

    private Properties services;
    private Properties config;
    private DataSource ds;
    private String schema;
    private String dsname;


    // Private constructor prevents instantiation from other classes
    private DAOUtils()
    {
    }

    /**
     * Get the Instance.
     * 
     * @return BaseDAO
     * @throws Exception
     *             when errors
     */
    public static DAOUtils getInstance() throws Exception
    {
        synchronized (DAOUtils.class)
        {
            if (instance == null)
            {
                instance = new DAOUtils();
            }
            instance.initLoadSQL();
            instance.initDS();
        }
        return instance;
    }

    /**
     * Load SQL properties.
     * 
     * @return void
     * @throws IOException
     */
    private void initLoadSQL() throws IOException
    {
        try
        {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("sql.properties");
            services = new Properties();
            services.load(is);
            is.close();
        }
        catch (IOException ioe)
        {
            throw new IOException("CANNOT LOAD SQL");
        }
    }

    /**
     * Load SQL properties.
     * 
     * @return void
     * @throws Exception
     */
    private void initDS() throws Exception
    {
        try
        {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("schema.properties");
            config = new Properties();
            config.load(is);
            is.close();

            String env = System.getProperty(Constants.ENVIRONMENT);
            if (env == null)
            {
                env = Constants.ENVIRONMENT_DEV;
                
                
            }

            schema = config.getProperty(env.toLowerCase() + ".schema.name");
            dsname = config.getProperty(env.toLowerCase() + ".datasource.name");
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    /**
     * Get connection from Datasource.
     * 
     * @return Connection
     * @throws Exception
     *             when errors
     */
    public Connection getConnection() throws Exception
    {
        Connection conn = null;
        try
        {
            Context context = new InitialContext();
            
            ds = (DataSource) context.lookup("java:/comp/env/" + dsname);

            conn = ds.getConnection();
        }
        catch (Exception e)
        {  
            throw new Exception("CANNOT CONNECT TO DATABASE:" + e.toString());
        }

        return conn;
    }

    /**
     * Get message by key.
     * 
     * @param key
     *            : key in properties
     * @return String
     */
    public String getString(final String key)
    {
        String sql = services.getProperty(key);
        sql = sql.replace("[schema]", schema);

        return sql;
    }

 
}
