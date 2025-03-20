package com.platform.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;

/**
 * @author Muhil
 */
@RestController
@RequestMapping("common")
public class CommonController
{
    // move to props
    private static final String BASE_URL = "https://api.postalpincode.in/pincode/";

    @GetMapping(value = "/pincode/{pincode}")
    public GenericResponse<PostalResponse> getPincodeDetails (@PathVariable String pincode)
        throws IOException,
        InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(
            URI.create(BASE_URL + pincode)).GET().build();
        HttpResponse<String> response = client.send(
            request,
            HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        if (response.statusCode() == 200) {
            List<PostalResponse> responseList = objectMapper.readValue(
                response.body(),
                new TypeReference<List<PostalResponse>>() {});
            return new GenericResponse<PostalResponse>().setDataList(responseList);
        }
        else {
            return new GenericResponse<PostalResponse>().setStatus(
                Response.Status.NO_CONTENT);
        }
    }

}

@JsonIgnoreProperties(ignoreUnknown = true)
class PostOffice
{
    @JsonProperty("Name")
    private String name;

    @JsonProperty("State")
    private String state;

    @JsonProperty("Pincode")
    private String pincode;

    @JsonProperty("Country")
    private String country;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getState ()
    {
        return state;
    }

    public void setState (String state)
    {
        this.state = state;
    }

    public String getPincode ()
    {
        return pincode;
    }

    public void setPincode (String pincode)
    {
        this.pincode = pincode;
    }

    public String getCountry ()
    {
        return country;
    }

    public void setCountry (String country)
    {
        this.country = country;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class PostalResponse
{

    @JsonProperty("PostOffice")
    private List<PostOffice> postOffice;

    public List<PostOffice> getPostOffice ()
    {
        return postOffice;
    }

    public void setPostOffice (List<PostOffice> postOffice)
    {
        this.postOffice = postOffice;
    }
}