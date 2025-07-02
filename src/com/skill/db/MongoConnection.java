package com.skill.db;

import com.mongodb.client.*;
import static com.mongodb.client.model.Filters.*;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.skill.model.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MongoConnection {
    private static final String URI; 
    private static MongoClient mongoClient;
    
   static {
	   try {
		   Properties props = new Properties();
		   FileInputStream fis = new FileInputStream("config.properties");
		   props.load(fis);
		   URI = props.getProperty("MONGO_URI");
	   }catch(IOException e){
		   throw new RuntimeException("Failed to load MongoDB URI from config.properties", e);
	   }
   }
   
    public static MongoDatabase getDatabase(String dbName) {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(URI);
        }
        return mongoClient.getDatabase(dbName);
    }

    
    public static boolean insertUser(User user) {
        try {
            MongoDatabase db = getDatabase("skillmate"); // your DB name
            MongoCollection<Document> users = db.getCollection("users"); // your collection name
            String hashedPassword = hashPassword(user.getPassword());
            Document doc = new Document()
                    .append("fullName", user.getFullName())
                    .append("email", user.getEmail())
                    .append("username", user.getUsername())
                    .append("password", hashedPassword);


            users.insertOne(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean validateUser(String username, String password) {
        try {
            MongoDatabase db = getDatabase("skillmate");
            MongoCollection<Document> users = db.getCollection("users");

            // Hash the entered password before comparison
            String hashedPassword = hashPassword(password);

            Document query = new Document("username", username)
                                .append("password", hashedPassword);
            Document userDoc = users.find(query).first();

            return userDoc != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    
    public static List<Document> findTutors(String skill, String availability) {
        List<Document> tutors = new ArrayList<>();
        try {
            if (skill == null || availability == null) return tutors;

            MongoDatabase db = getDatabase("skillmate");
            MongoCollection<Document> tutorsCollection = db.getCollection("tutors");

            Bson query = and(
                eq("skill", skill),
                eq("availability", availability)
            );
            tutorsCollection.find(query).into(tutors);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tutors;
    }
}

