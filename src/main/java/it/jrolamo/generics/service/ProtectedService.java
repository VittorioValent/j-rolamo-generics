package it.jrolamo.generics.service;

import it.jrolamo.generics.annotations.IsOwnerPreAuth;
import it.jrolamo.generics.domain.AbstractDTO;
import it.jrolamo.generics.domain.AbstractModel;

/**
 * Read all, Write only owner
 *
 * @author Vittorio Valent
 * @param <Entity>
 * @param <DTO>
 * @since 0.0.1
 */
public abstract class ProtectedService<Entity extends AbstractModel, DTO extends AbstractDTO>
        extends PublicService<Entity, DTO> {

    /**
     *
     * @param id
     */
    @Override
    @IsOwnerPreAuth
    public void delete(Long id) {
        repository.deleteById(id);
    }

    /**
     *
     * @param dto
     * @return
     */
    @Override
    @IsOwnerPreAuth
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
    @IsOwnerPreAuth
    public DTO merge(Long id, DTO dto) {
        dto = (DTO) patchUtils.applyPatch(read(id), dto);
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }
}
