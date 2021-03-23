package cat.udl.eps.softarch.academicrecruit.repository;

import cat.udl.eps.softarch.academicrecruit.domain.SelectionProcess;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface SelectionProcessRepository extends PagingAndSortingRepository<SelectionProcess, Long> {

    List<SelectionProcess> findByVacancy(@Param("vacancy") String vacancy);

}