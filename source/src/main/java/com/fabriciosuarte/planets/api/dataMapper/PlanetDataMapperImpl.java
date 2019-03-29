package com.fabriciosuarte.planets.api.dataMapper;

import com.fabriciosuarte.planets.api.domain.Planet;

import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of {@link PlanetDataMapper}
 */
class PlanetDataMapperImpl implements PlanetDataMapper{

    private static final String COLLECTION_NAME = "planets";

    private Document toDBObject(Planet obj) {
        return new Document("_id", obj.getId())
                .append("name", obj.getName())
                .append("climate", obj.getClimate())
                .append("terrain", obj.getTerrain())
                .append("films", obj.getFilmsAppearances());
    }

    private Planet toPlanet(@Nullable Document obj) {

        if(obj == null)
            return null;

        Planet planet = Planet.create((long) obj.get("_id"),
                obj.getString("name"),
                obj.getString("climate"),
                obj.getString("terrain"));

        Integer films = obj.getInteger("films");
        if(films != null) {
            planet.setFilmsAppearances(films);
        }

        return planet;
    }

    @Override
    public void insert(Planet obj) {
        Document data = this.toDBObject(obj);
        MongoClientHelper.getInstance()
                .getCollection(COLLECTION_NAME)
                .insertOne(data);
    }

    @Override
    public void update(Planet obj) {
        Document data = this.toDBObject(obj);
        Document filter = new Document("_id", obj.getId());

        MongoClientHelper.getInstance()
                .getCollection(COLLECTION_NAME)
                .updateOne(filter, data);
    }

    @Override
    public void delete(long id) {

        Document filter = new Document("_id", id);

        MongoClientHelper.getInstance()
                .getCollection(COLLECTION_NAME)
                .deleteOne(filter);
    }

    @Override
    public Planet select(long id) {
        Document filter = new Document("_id", id);

        Document document = MongoClientHelper.getInstance()
                                .getCollection(COLLECTION_NAME)
                                .find(filter).first();

        return this.toPlanet(document);
    }

    @Override
    public Planet selectByName(String name) {
        Document filter = new Document("name", name);

        Document document = MongoClientHelper.getInstance()
                .getCollection(COLLECTION_NAME)
                .find(filter).first();

        return this.toPlanet(document);
    }

    @Override
    public List<Planet> selectAll() {
        FindIterable<Document> documents = MongoClientHelper.getInstance()
                .getCollection(COLLECTION_NAME)
                .find();

        List<Planet> list = new ArrayList<>();

        if(documents == null)
            return list;

        for(Document doc : documents) {
            list.add( this.toPlanet(doc));
        }

        return list;
    }

    @Override
    public boolean exists(long id) {
        Document filter = new Document("_id", id);

        Document document = MongoClientHelper.getInstance()
                .getCollection(COLLECTION_NAME)
                .find(filter)
                .projection(filter) //returns only the id
                .first();

        return document != null;
    }
}
