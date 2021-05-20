package cat.udl.eps.softarch.academicrecruit.repository;

import cat.udl.eps.softarch.academicrecruit.domain.Document;
import cat.udl.eps.softarch.academicrecruit.domain.Participant;
import cat.udl.eps.softarch.academicrecruit.domain.SelectionProcess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ParticipantRepository extends PagingAndSortingRepository<Participant, Long> {

  /* Interface provides automatically, as defined in https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html
   * count, delete, deleteAll, deleteById, existsById, findAll, findAllById, findById, save, saveAll
   *
   * Additional methods following the syntax defined in
   * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
   */

    Page<Participant> findBySelectionProcess(Pageable pageable, @Param("selectionProcess") SelectionProcess selectionProcess);
}
