/*
 *Created by Jaweed.Hajiyev
 *Date:12.08.23
 *TIME:01:54
 *Project name:LMS
 */

package az.lms.mapper;

import az.lms.model.dto.request.AuthorRequest;
import az.lms.model.dto.response.AuthorResponse;
import az.lms.model.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorMapper {
    AuthorResponse modelToResponse(Author author);

    Author requestToModel(AuthorRequest request);
}
