package com.marco.scmexc.models.domain;

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

    @Column(name = "is_parent")
    private boolean is_parent;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Material parent;

    @Enumerated(EnumType.ORDINAL)
    private Type type;

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

    @Column(name = "upvotes")
    private Integer upvotes;

    @Column(name = "downvotes")
    private Integer downvotes;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private SmxFile file;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @OneToMany(mappedBy = "material")
    private List<Comment> comments;


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

    public boolean isIs_parent() {
        return is_parent;
    }

    public void setIs_parent(boolean is_parent) {
        this.is_parent = is_parent;
    }

    public Material getParent() {
        return parent;
    }

    public void setParent(Material parent) {
        this.parent = parent;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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

    public SmxFile getFile() {
        return file;
    }

    public void setFile(SmxFile file) {
        this.file = file;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    public Integer getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(Integer downvotes) {
        this.downvotes = downvotes;
    }
}
