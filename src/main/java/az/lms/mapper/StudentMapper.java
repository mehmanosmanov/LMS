package az.lms.mapper;

import az.lms.model.dto.request.StudentRequest;
import az.lms.model.dto.response.StudentResponse;
import az.lms.model.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Mehman Osmanov on 12.08.23
 * @project LMS
 */
@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {
   Student requestToEntity(StudentRequest request);
   StudentResponse entityToResponse(Student student);

}
