package com.example.petfoodanalyzer.models.dtos.products;

public class ReviewOverviewDTO {
    private String authorDisplayName;

    private String authorProfilePic;

    private String content;

    private String productName;

    private String productPicUrl;

    private long productId;

    public ReviewOverviewDTO() {
    }

    public ReviewOverviewDTO(String authorDisplayName, String authorProfilePic, String content, String productName, String productPicUrl, long productId) {
        this.authorDisplayName = authorDisplayName;
        this.authorProfilePic = authorProfilePic;
        this.content = content;
        this.productName = productName;
        this.productPicUrl = productPicUrl;
        this.productId = productId;
    }

    public String getAuthorDisplayName() {
        return authorDisplayName;
    }

    public ReviewOverviewDTO setAuthorDisplayName(String authorDisplayName) {
        this.authorDisplayName = authorDisplayName;
        return this;
    }

    public String getAuthorProfilePic() {
        return authorProfilePic;
    }

    public ReviewOverviewDTO setAuthorProfilePic(String authorProfilePic) {
        this.authorProfilePic = authorProfilePic;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ReviewOverviewDTO setContent(String content) {
        this.content = content;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public ReviewOverviewDTO setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getProductPicUrl() {
        return productPicUrl;
    }

    public ReviewOverviewDTO setProductPicUrl(String productPicUrl) {
        this.productPicUrl = productPicUrl;
        return this;
    }

    public long getProductId() {
        return productId;
    }

    public ReviewOverviewDTO setProductId(long productId) {
        this.productId = productId;
        return this;
    }
}
