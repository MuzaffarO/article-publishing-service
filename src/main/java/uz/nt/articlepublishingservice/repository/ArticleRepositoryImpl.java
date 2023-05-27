package uz.nt.articlepublishingservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import uz.nt.articlepublishingservice.model.Articles;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl {
    private final EntityManager entityManager;
    public Page<Articles> articlesWithPagination(Integer limit, Integer offset){
        String stringQuery = "select a from Articles a where 1 = 1 ";
        String sqlCount = "select count(1) from Articles a where 1 = 1 ";
        Query query = entityManager.createQuery(stringQuery);
        Query cnt = entityManager.createQuery(sqlCount);

        long count = (long) cnt.getSingleResult();




        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return new PageImpl<>(query.getResultList(), PageRequest.of(offset / limit, limit), count);
    }
}
