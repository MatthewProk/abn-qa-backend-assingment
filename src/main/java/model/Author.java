package model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Author {

    private String name;
    @SerializedName("avatar_url")
    private String avatarUrl;
    private String state;
    @SerializedName("web_url")
    private String webUrl;
    private String username;
}