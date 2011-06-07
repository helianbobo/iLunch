dataSource {
    pooled = true
    driverClassName = "org.hsqldb.jdbcDriver"
    username = "sa"
    password = ""
    showSql= true
}
hibernate {

    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
            url = "jdbc:hsqldb:mem:devDB"
        }
    }
    test {
        dataSource {
            dbCreate = "create-drop"
            url = "jdbc:hsqldb:mem:testDb"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:mysql://112.125.57.131:3306/ilunch"
            driverClassName = "com.mysql.jdbc.Driver"
            username = "root"
            password = "5q7v6g9t8f"
        }
    }
    leo {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
            url = "jdbc:mysql://localhost:3306/prototype"
            driverClassName = "com.mysql.jdbc.Driver"
            username = "root"
            password = "root"
        }
    }

    liuchao {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
            url = "jdbc:mysql://localhost:3306/ilunch"
            driverClassName = "com.mysql.jdbc.Driver"
            username = "root"
            password = ""
        }
    }
}
