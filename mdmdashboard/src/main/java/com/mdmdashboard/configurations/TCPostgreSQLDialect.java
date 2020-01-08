package com.mdmdashboard.configurations;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQL94Dialect;
//import org.hibernate.dialect.SQLServer2012Dialect;
//import org.hibernate.dialect.SQLServer2012Dialect;
import org.hibernate.type.StandardBasicTypes;

public class TCPostgreSQLDialect extends PostgreSQL94Dialect{
    public TCPostgreSQLDialect() {
        super();                
        registerHibernateType(Types.NCHAR, StandardBasicTypes.CHARACTER.getName());
        registerHibernateType(Types.NCHAR, 1, StandardBasicTypes.CHARACTER.getName());
        registerHibernateType(Types.NCHAR, 255, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.LONGNVARCHAR, StandardBasicTypes.TEXT.getName());
        registerHibernateType(Types.NCLOB, StandardBasicTypes.CLOB.getName());
    }
}
