package ru.koryakin.service;

import org.springframework.stereotype.Service;
import ru.koryakin.exception.NotFoundException;
import ru.koryakin.model.Post;
import ru.koryakin.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
  // сервис завязан на интерфейс, а не на конкретную реализацию
  private final PostRepository repository;

  public PostService(PostRepository repository) {
    this.repository = repository;
  }

  public List<Post> all() {
    return repository.all();
  }

  public Post getById(long id) {
    return repository.getById(id).orElseThrow(NotFoundException::new);
  }

  public Post save(Post post) {
    return repository.save(post).orElseThrow(NotFoundException::new);
  }

  public void removeById(long id) {
    repository.removeById(id);
  }
}

