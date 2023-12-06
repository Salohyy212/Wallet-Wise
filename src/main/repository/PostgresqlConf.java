package main.repository;

public class PostgresqlConf {
    public final static String URL = System.getenv("DB_URL");
    public final static String USERNAME = System.getenv("DB_USERNAME") ;
    public final static String PASSWORD = System.getenv("DB_PASSWORD") ;
}
