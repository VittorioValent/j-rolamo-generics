package it.jrolamo.generics.service;

import com.querydsl.core.types.Predicate;
import it.jrolamo.generics.domain.AbstractDTO;
import it.jrolamo.generics.domain.AbstractModel;
import it.jrolamo.generics.mapper.IMapper;
import it.jrolamo.generics.repository.IRepository;
import it.jrolamo.generics.utils.PatchUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * This class implements all CRUD and common buisness methods from
 * {@link IService} except those which need authentication to be invoked.
 *
 * @author Vittorio Valent
 * @param <Entity>
 * @param <DTO>
 *
 * @see PrivateService
 * @since 0.0.1
 */
public abstract class PublicService<Entity extends AbstractModel, DTO extends AbstractDTO>
        implements IService<Entity, DTO> {

    /**
     *
     */
    @Autowired
    protected IMapper<Entity, DTO> mapper;

    /**
     *
     */
    @Autowired
    protected IRepository<Entity> repository;

    @Autowired
    PatchUtils<DTO> patchUtils;

    /**
     *
     * @param dto
     * @return
     */
    @Override
    public DTO create(DTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public List<DTO> find(Specification<Entity> spec) {
        return mapper.toDTO(repository.findAll(spec));
    }

    /**
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public DTO read(Long id) {
        // return mapper.toDTO(repository.findById(id).orElse(null));
        return mapper.toDTO(repository.findById(id).get());
    }

    /**
     *
     * @param dto
     * @return
     */
    @Override
    public DTO update(DTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    /**
     *
     * @param id
     * @param dto
     * @return
     */
    @Override
    public DTO merge(Long id, DTO dto) {
        dto = patchUtils.applyPatch(read(id), dto);
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    /**
     *
     * @param predicate
     * @param pageable
     * @return
     */
    @Override
    public Page<DTO> getAll(Predicate predicate, Pageable pageable) {
        if (predicate == null) {
            return mapper.toDTO(repository.findAll(pageable));
        } else {
            return mapper.toDTO(repository.findAll(predicate, pageable));
        }
    }
}
