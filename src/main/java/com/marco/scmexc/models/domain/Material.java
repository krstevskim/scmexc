package com.marco.scmexc.models.domain;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(schema = "public", name = "materials")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private SmxUser createdBy;

    @Column(name = "date_created")
    private ZonedDateTime dateCreated;

    @Column(name = "is_published")
    private boolean published;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private SmxUser approvedBy;

    @Column(name = "up_votes")
    private Integer upVotes;

    @Column(name = "down_votes")
    private Integer downVotes;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;


    @OneToMany(mappedBy = "material")
    private List<Comment> comments;

    @OneToMany(mappedBy = "material")
    private List<Item> items;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SmxUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(SmxUser createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SmxUser getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(SmxUser approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Integer getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(Integer upvotes) {
        this.upVotes = upvotes;
    }

    public Integer getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(Integer downVotes) {
        this.downVotes = downVotes;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
