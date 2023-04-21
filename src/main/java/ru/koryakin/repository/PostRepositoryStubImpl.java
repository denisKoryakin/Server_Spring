package ru.koryakin.repository;

import org.springframework.stereotype.Repository;
import ru.koryakin.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepositoryStubImpl implements PostRepository {

    private static AtomicLong counter = new AtomicLong(1);
    private static ConcurrentHashMap<Long, Post> postsMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Long, Post> removedPostsMap = new ConcurrentHashMap<>();

    public List<Post> all() {
        return new ArrayList<>(postsMap.values());
    }

    public Optional<Post> getById(long id) {
        // Optional чтоб не ронять программу при отсутствии Post
        return Optional.ofNullable(postsMap.get(id));
    }

    public Optional<Post> save(Post post) {
        long id = 0;
        // если номер id не задан
        if (post.getId() == 0) {
            id = counter.getAndIncrement();
            post.setId(id);
            postsMap.put(id, post);
        } else {
            // Если id задан
            // Если post с данным id уже имеется
            if (postsMap.containsKey(post.getId())) {
                postsMap.put(post.getId(), post);
            }
        }
        return Optional.ofNullable(postsMap.get(id));
    }

    public void removeById(long id) {
        if (postsMap.containsKey(id)) {
            Post removedPost = getById(id).get();
            postsMap.remove(id);
            removedPostsMap.put(removedPost.getId(), removedPost);
        }
    }
}