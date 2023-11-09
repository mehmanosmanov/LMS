/*
 *Created by Jaweed.Hajiyev
 *Date:22.08.23
 *TIME:16:37
 *Project name:LMS
 */

package az.lms.mapper;

import az.lms.model.dto.request.LibrarianRequest;
import az.lms.model.dto.response.LibrarianResponse;
import az.lms.model.entity.Librarian;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LibrarianMapper {
    LibrarianResponse modelToResponse(Librarian author);
    Librarian requestToModel(LibrarianRequest request);
}
