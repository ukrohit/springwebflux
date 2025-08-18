package com.ukrohit.springwebflux.sec02;

import com.ukrohit.springwebflux.sec02.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.test.StepVerifier;

public class Lec05DataBaseClient extends AbstractTest{

    public static final Logger logger= LoggerFactory.getLogger(Lec05DataBaseClient.class);

    @Autowired
    DatabaseClient databaseClient;

    public void databaseClientTest()
    {
        RowsFetchSpec<Product> productRowsFetchSpec = this.databaseClient.sql("""
                        SELECT
                            p.*
                            FROM
                            customer c
                            INNER JOIN customer_order co ON c.id = co.customer_id
                            INNER JOIN product p ON co.product_id = p.id
                            WHERE
                            c.name = :name
                        """).bind("name", "sam")
                .mapProperties(Product.class);

        productRowsFetchSpec.all(); // This will return the flux stream 

    }
}
