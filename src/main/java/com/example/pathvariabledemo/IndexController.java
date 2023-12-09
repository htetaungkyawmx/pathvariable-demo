package com.example.pathvariabledemo;


import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class IndexController {

    //curl "localhost:8080/actionA/cities/Yangon
    @ResponseBody
    @GetMapping("/actionA/cities/{city}")
    public String cityByCode(@PathVariable("city") String city1){
        return String.format("Retrieved city = [%s]\n",city1);
    }

    //curl "localhost:8080/actionB/countries/US/cities/DEN
    @GetMapping("/actionB/countries/{country}/cities/{city}")
    @ResponseBody
    public String countryAndCityByCode(@PathVariable("country")String country,@PathVariable(value = "city")String city){
        return String.format("Retrieved country =[%s] \n", country,city);
    }

    //curl "localhost:8080/actionC/countries/US/cities/DEN
    //curl "localhost:8080/actionC/countries/US
    @GetMapping({"/actionC/countries/{country}/cities/{city}","/actionC/countries/{country}"})
    @ResponseBody
    public String countryAndOptionalByCode(@PathVariable("country")String country
            ,@PathVariable(value = "city",required = false)String city){
        return String.format("Retrieved country =[%s] \n", country,city);
    }

    //curl localhost:8080/actionD/countries/US/cities/DEN
    //curl localhost:8080/actionD/countries/US
    @GetMapping({"/actionD/countries/{country}/cities/{city}","/actionD/countries/{country}"})
    @ResponseBody
    public String countryAndOptionalByCodeJava17(@PathVariable("country")String country,
                                                 @PathVariable("city")Optional<String> city){
        return String.format("Retrieved name= [%s] , city = [%s] \n", country, city.orElse("N/A"));
    }

    //curl localhost:8080/actionE/countries/US/cities/DFW/zip/75038
    @GetMapping("/actionE/countries/{country}/cities/{city}/zip/zip}")
    @ResponseBody
    public String actionE(@PathVariable Map<String, String> parameters){
         String parametersAsString = parameters.entrySet().stream()
                 .map(entry -> String.format("[%s] -> [%s]",entry.getKey(),entry.getValue()))
                 .collect(Collectors.joining(", "));

         return String.format("Retrieved parameters map = [%s]\n", parametersAsString);
    }

    //curl localhost:8080/actionF/countries/US,PL,UK
    @GetMapping("/actionF/countries/{countries}")
    @ResponseBody
    public String actionF(@PathVariable("countries")List<String> countries){
        return String.format("Retrieved cities identifiers = [%s]\n",String.join(", ",countries));
    }

}