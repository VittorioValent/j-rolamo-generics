package it.jrolamo.generics.repository;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import it.jrolamo.generics.domain.AbstractModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * This Repository groups other repositores we need:
 * <ul>
 * <li>{@link JpaRepository} for all Crud and Pageable methods,
 * <li>{@link QuerydslBinderCustomizer} for QueryDsl bindings,
 * <li>{@link QuerydslPredicateExecutor} to use URL predicates,
 * <li>{@link JpaSpecificationExecutor} to apply spec filters,
 * </ul>
 *
 * @author Vittorio Valent
 * @param <Entity>
 *
 * @since 0.0.1
 */
@NoRepositoryBean
public interface IRepository<Entity extends AbstractModel>
        extends JpaRepository<Entity, Long>, QuerydslBinderCustomizer<EntityPath<Entity>>,
        QuerydslPredicateExecutor<Entity>, JpaSpecificationExecutor<Entity> {

    /**
     *
     * @param bindings
     * @param qEntity
     */
    @Override
    default void customize(QuerydslBindings bindings, EntityPath<Entity> qEntity) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
