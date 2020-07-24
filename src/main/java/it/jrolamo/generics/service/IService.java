package it.jrolamo.generics.service;

import it.jrolamo.generics.domain.AbstractDTO;
import it.jrolamo.generics.domain.AbstractModel;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * All CRUD methods and buisness helpers can be found in this interface.
 *
 * @author Vittorio Valent
 * @param <Entity>
 * @param <DTO>
 *
 * @see ICrudService
 * @since 0.0.1
 */
@Service
public interface IService<Entity extends AbstractModel, DTO extends AbstractDTO> extends ICrudService<DTO> {

    /**
     * Buisness-only method: supports filtering by specification instead of QDSDL
     * Predicate
     *
     * @param spec
     * @return List<DTO>
     */
    public List<DTO> find(Specification<Entity> spec);
}
