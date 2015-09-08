/*
 * FILENAME
 *     MailUtils.java
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
 *     Copyright (C) 2010 HEB. All rights reserved.
 *     This software is the confidential and proprietary information of
 *     HEB ("Confidential Information"). You shall not
 *     disclose such Confidential Information and shall use it only in
 *     accordance with the terms of the licence agreement you entered into
 *     with HEB.
 */

package com.dell.enterprise.agenttool.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

public class MailUtils
{
    private static MailUtils instance = new MailUtils();
    private Properties config;

    private String host;
    private String from;
    private String authen_username;
    private String authen_password;
    private int smtp_port;
    
    private MailUtils()
    {

    }

    public static MailUtils getInstance() throws Exception
    {
        synchronized (MailUtils.class)
        {
            if (instance == null)
            {
                instance = new MailUtils();
            }
            instance.initLoadEmail();
        }
        return instance;
    }

    private void initLoadEmail() throws IOException
    {

        InputStream is = this.getClass().getClassLoader().getResourceAsStream("email.properties");
        config = new Properties();
        config.load(is);
        is.close();

        String env = System.getProperty(Constants.ENVIRONMENT);
        if (env == null)
        {
            env = Constants.ENVIRONMENT_DEV;
        }

        host = config.getProperty(env.toLowerCase() + ".host.name");
        from = config.getProperty(env.toLowerCase() + ".from.name");
        authen_username = config.getProperty(env.toLowerCase() + ".authen.username");
        authen_password = config.getProperty(env.toLowerCase() + ".authen.password");
        smtp_port = Integer.parseInt(config.getProperty(env.toLowerCase() + ".smtp.port"));
    }

    /**
     * @return the host
     */
    public String getHost()
    {
        return host;
    }

    /**
     * @param host
     *            the host to set
     */
    public void setHost(String host)
    {
        this.host = host;
    }

    /**
     * @return the from
     */
    public String getFrom()
    {
        return from;
    }

    /**
     * @param from
     *            the from to set
     */
    public void setFrom(String from)
    {
        this.from = from;
    }

    /**
     * @return the authen_username
     */
    public String getAuthen_username()
    {
        return authen_username;
    }

    /**
     * @param authenUsername the authen_username to set
     */
    public void setAuthen_username(String authenUsername)
    {
        authen_username = authenUsername;
    }

    /**
     * @return the authen_password
     */
    public String getAuthen_password()
    {
        return authen_password;
    }

    /**
     * @param authenPassword the authen_password to set
     */
    public void setAuthen_password(String authenPassword)
    {
        authen_password = authenPassword;
    }

    /**
     * @return the smtp_port
     */
    public int getSmtp_port()
    {
        return smtp_port;
    }

    /**
     * @param smtpPort the smtp_port to set
     */
    public void setSmtp_port(int smtpPort)
    {
        smtp_port = smtpPort;
    }

}
