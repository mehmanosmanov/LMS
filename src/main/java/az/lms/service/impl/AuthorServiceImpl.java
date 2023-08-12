/*
 *Created by Jaweed.Hajiyev
 *Date:12.08.23
 *TIME:01:22
 *Project name:LMS
 */

package az.lms.service.impl;

import az.lms.dto.request.AuthorRequest;
import az.lms.dto.response.AuthorResponse;
import az.lms.exception.NotFoundException;
import az.lms.mapper.AuthorMapper;
import az.lms.model.Author;
import az.lms.repository.AuthorRepository;
import az.lms.service.AuthorService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;
    private final AuthorMapper mapper;

    @Override
    public void createAuthor(AuthorRequest request) {
        repository.save(mapper.requestToModel(request));
    }

    @Override
    public List<AuthorResponse> getAllAuthors() {
        List<Author> authors = repository.findAll();
        List<AuthorResponse> responses = new ArrayList<>();
        for (var a : authors) {
            responses.add(mapper.modelToResponse(a));
        }
        return responses;
    }

    @Override
    public void deleteAuthor(Long id) {
        repository.deleteById(id);
    }

    @Override
    public AuthorResponse getAuthorById(Long id) {
        Author author = repository.findById(id).orElseThrow(()
                -> new NotFoundException("Author not found!"));
        return mapper.modelToResponse(author);
    }

    @Override
    public void updateAuthors(Long id, AuthorRequest request) {

    }
}
