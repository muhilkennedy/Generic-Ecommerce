package com.user.model;

import java.io.Serializable;

/**
 * @author muhil
 */
public class LoginUser implements Serializable
{

    private static final long serialVersionUID = 1L;

    private String emailid;
    private String password;
    private String mobile;
    private boolean rememberMe;

    public String getEmailid ()
    {
        return emailid;
    }

    public void setEmailid (String emailid)
    {
        this.emailid = emailid;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getMobile ()
    {
        return mobile;
    }

    public void setMobile (String mobile)
    {
        this.mobile = mobile;
    }

    public boolean isRememberMe ()
    {
        return rememberMe;
    }

    public void setRememberMe (boolean rememberMe)
    {
        this.rememberMe = rememberMe;
    }

}
