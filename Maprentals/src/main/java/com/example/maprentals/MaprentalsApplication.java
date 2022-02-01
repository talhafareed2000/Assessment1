package com.example.maprentals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;



import java.sql.Connection;
import java.util.Map;

@SpringBootApplication
@RestController


public class  MaprentalsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaprentalsApplication.class, args);
    }

    //@RequestMapping(value = "/getAvg/{location}", method = RequestMethod.GET)
    @PostMapping(value = "/getAvg/location", produces = {"application/json"})
    //@RequestMapping(value = "/getAvg/location", method = RequestMethod.POST)
    public double Rent(){
        Db database = new Db();
        System.out.println();
        Connection conn=database.connect_to_db("gis","guest","U8OPtddp");
       // return database.GetAvg(conn,(double)payLoad.get("lat"), (double) payLoad.get("lng"));
        System.out.println(payLoad);
      return (double) payLoad.get("lat");

    }
    public static class Location {

        private double lat;
        private double lng;

        public Location() {}

        public Location(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }

    }


}
