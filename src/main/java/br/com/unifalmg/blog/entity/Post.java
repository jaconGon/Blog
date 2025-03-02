package br.com.unifalmg.blog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "db2022108011", name = "post")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // DONE: List of comments
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

}