package com.siziba.zim_news.zim_news.service.common;


import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.siziba.zim_news.zim_news.library.CommonFunctions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommonService {
    public String getApproxLocationFromIp(String stringIpAddress) {
        CommonFunctions.validateIpAddress(stringIpAddress);
        try {
            File cityDatabase = new File("src/main/resources/GeoLite2-City.mmdb");
            DatabaseReader cityDatabaseReader = new DatabaseReader.Builder(cityDatabase).build();
            InetAddress ipAddress = InetAddress.getByName(stringIpAddress);
            CityResponse response = cityDatabaseReader.city(ipAddress);
            String location = response.getCity().getName() + ", " + response.getCountry().getName();
            if (location.contains("null") && location.indexOf("null") != location.lastIndexOf("null")) {
                return "Unknown";
            }
            if (location.contains("null")){
                return location.replaceAll("null", "Unknown");
            }
            return location;
        } catch (IOException | GeoIp2Exception e) {
            log.error("Error reading GeoLite2-City.mmdb file");
            log.error(e.getMessage());
            return "Unknown";
        }
    }
}
