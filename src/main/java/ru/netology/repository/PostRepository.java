package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {
    private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong postId = new AtomicLong();

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Post save(Post post) {
        Post savingPost;
        int id = (int) post.getId();
        if (post.getId() == 0) {
            postId.incrementAndGet();
            savingPost = new Post(postId.get(), post.getContent());
            posts.put(postId.get(), savingPost);
        } else if (posts.containsKey(id)) {
            savingPost = new Post(id, post.getContent());
            posts.replace((long) id, post);
        } else {
            throw new NotFoundException("Post with id(" + id + ") not found");
        }
        return savingPost;
    }

    public void removeById(long id) {
        if (posts.containsKey(id)) {
            posts.remove(id);
        } else {
            throw new NotFoundException("Post with id(" + id + ") not found");
        }
    }
}
