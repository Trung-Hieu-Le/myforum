// package com.example.myforum.model;

// import java.time.LocalDateTime;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;

// @Entity
// public class Media {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "media_id")
//     private Long mediaId;

//     @Column(nullable = false, length = 500)
//     private String url;

//     @Column(nullable = false, length = 50)
//     private String type; // image, video, file, gif

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "post_id")
//     private Post post;

//     @Column(name = "created_at", updatable = false)
//     private LocalDateTime createdAt;

//     @Column(name = "updated_at")
//     private LocalDateTime updatedAt;

//     public Media() {
//         this.createdAt = LocalDateTime.now();
//         this.updatedAt = LocalDateTime.now();
//     }

//     public Media(String url, String type, Post post) {
//         this.url = url;
//         this.type = type;
//         this.post = post;
//         this.createdAt = LocalDateTime.now();
//         this.updatedAt = LocalDateTime.now();
//     }

//     public Long getMediaId() {
//         return mediaId;
//     }

//     public void setMediaId(Long mediaId) {
//         this.mediaId = mediaId;
//     }

//     public String getUrl() {
//         return url;
//     }

//     public void setUrl(String url) {
//         this.url = url;
//     }

//     public String getType() {
//         return type;
//     }

//     public void setType(String type) {
//         this.type = type;
//     }

//     public Post getPost() {
//         return post;
//     }

//     public void setPost(Post post) {
//         this.post = post;
//     }

//     public LocalDateTime getCreatedAt() {
//         return createdAt;
//     }

//     public void setCreatedAt(LocalDateTime createdAt) {
//         this.createdAt = createdAt;
//     }

//     public LocalDateTime getUpdatedAt() {
//         return updatedAt;
//     }

//     public void setUpdatedAt(LocalDateTime updatedAt) {
//         this.updatedAt = updatedAt;
//     }
// }
