package model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import util.Util;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import static config.Config.getProject;

@Data
public class Issue {

    @SerializedName("project_id")
    private int projectId;
    private int id;
    private int iid;
    @SerializedName("created_at")
    private String createdAt;
    private String title;
    private String state;
    private String opened;
    private List<String> assignees;
    private String type;
    private List<String> labels;
    private int upvotes;
    private int downvotes;
    @SerializedName("merge_requests_count")
    private int mergeRequestsCount;
    private Author author;
    private String description;
    private String updated_at;
    private String closed_at;
    @SerializedName("web_url")
    private String webUrl;
    @SerializedName("issue_type")
    private String issueType;
    private String severity;

    public Issue(Random random){
        this.setIid(random.nextInt(1000));
        this.setProjectId(getProject());
        this.setDescription("Issue description: " + Util.generateRandomString(20, random));
        this.setTitle("Issue title: " + Util.generateRandomString(8, random));
        this.setType("ISSUE");
    }

    /**
     * Let's assume that we need to check the similarity of Issues by 4 parameters:
     * iid, title, description and type.
     * This equal method is overriden for this checking.
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Issue other = (Issue) obj;
        return this.iid == other.iid &&
                Objects.equals(this.title, other.title) &&
                Objects.equals(this.description, other.description) &&
                Objects.equals(this.type, other.type);    }

}