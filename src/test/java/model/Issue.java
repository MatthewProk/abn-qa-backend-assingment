package model;

import lombok.Data;

import java.util.List;

@Data
public class Issue {

    private int projectId;
    private int id;
    private int iid;
    private String createdAt;
    private String title;
    private String state;
    private String opened;
    private List<String> assignees;
    private String type;
    private List<String> labels;
    private int upvotes;
    private int downvotes;
    private int mergeRequestsCount;
    private Author author;
    private String description;
    private String updated_at;
    private String closed_at;
    private String webUrl;
    private String issueType;
    private String severity;


}
