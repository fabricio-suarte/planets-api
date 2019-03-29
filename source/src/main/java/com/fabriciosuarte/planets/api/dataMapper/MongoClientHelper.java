package com.fabriciosuarte.planets.api.dataMapper;

import com.fabriciosuarte.planets.api.common.PropertyBag;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * A singleton helper for the MongoDB client
 */
final class MongoClientHelper {

    private static MongoClientHelper instance;

    private MongoClient client;

    private MongoClientHelper() {

        String connect = PropertyBag.getInstance().getString("dbConnectionString");
        this.client = MongoClients.create(connect);
    }

    static MongoClientHelper getInstance() {

        if( instance == null) {

            synchronized (MongoClientHelper.class) {

                //double check...
                if(instance == null) {
                    instance = new MongoClientHelper();
                }
            }
        }

        return instance;
    }

    String getDatabaseName() {

        String dbName = PropertyBag.getInstance().getString("databaseName");
        return dbName;
    }

    MongoDatabase getDataBase() {
        String databaseName = this.getDatabaseName();
        return this.client.getDatabase(databaseName);
    }

    MongoCollection<Document> getCollection(String name) {
        return this.getDataBase()
                .getCollection(name);
    }

}
