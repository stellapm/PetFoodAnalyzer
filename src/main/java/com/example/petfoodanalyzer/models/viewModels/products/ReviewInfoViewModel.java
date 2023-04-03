package com.example.petfoodanalyzer.models.viewModels.products;

public class ReviewInfoViewModel {
    private Long id;

    private String authorUsername;

    private String authorProfilePic;

    private String content;

    private String created;

    private int likesCount;

    private boolean limitUserLike;

    public ReviewInfoViewModel() {
    }

    public Long getId() {
        return id;
    }

    public ReviewInfoViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public ReviewInfoViewModel setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
        return this;
    }

    public String getAuthorProfilePic() {
        return authorProfilePic;
    }

    public ReviewInfoViewModel setAuthorProfilePic(String authorProfilePic) {
        this.authorProfilePic = authorProfilePic;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ReviewInfoViewModel setContent(String content) {
        this.content = content;
        return this;
    }

    public String getCreated() {
        return created;
    }

    public ReviewInfoViewModel setCreated(String created) {
        this.created = created;
        return this;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public ReviewInfoViewModel setLikesCount(int likesCount) {
        this.likesCount = likesCount;
        return this;
    }

    public boolean getLimitUserLike() {
        return this.limitUserLike;
    }

    public ReviewInfoViewModel setLimitUserLike(boolean limitUserLike) {
        this.limitUserLike = limitUserLike;
        return this;
    }
}
