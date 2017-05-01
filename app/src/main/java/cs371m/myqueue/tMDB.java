package cs371m.myqueue;

/**
 * Created by Kaivan on 5/1/2017.
 */


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "overview"
})

public class tMDB {
    @JsonProperty("overview")
    private String overview;

    @JsonProperty("vote_average")
    private double rating;

    @JsonProperty("overview")
    public String getOverview() {
        return overview;
    }

    @JsonProperty("vote_average")
    public double getRating() {
        return rating;
    }
}
