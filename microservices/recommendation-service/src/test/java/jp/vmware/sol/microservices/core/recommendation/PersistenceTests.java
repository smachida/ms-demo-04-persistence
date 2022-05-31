package jp.vmware.sol.microservices.core.recommendation;

import jp.vmware.sol.microservices.core.recommendation.persistence.RecommendationEntity;
import jp.vmware.sol.microservices.core.recommendation.persistence.RecommendationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class PersistenceTests {

    @Autowired
    private RecommendationRepository repository;

    private RecommendationEntity savedEntity;

    @Before
    public void setupDb() {
        repository.deleteAll();

        RecommendationEntity entity = new RecommendationEntity(1, 2, "a", 3, "c");
        savedEntity = repository.save(entity);
        assertEqualsRecommendation(entity, savedEntity);
    }

    @Test
    public void create() {
        RecommendationEntity entity = new RecommendationEntity(1, 3, "a", 3, "c" );
        repository.save(entity);
        RecommendationEntity foundEntity = repository.findById(entity.getId()).get();
        assertEqualsRecommendation(entity, foundEntity);
        assertEquals(2, repository.count());
    }

    @Test
    public void update() {
        savedEntity.setAuthor("a2");
        repository.save(savedEntity);
        RecommendationEntity foundEntity = repository.findById(savedEntity.getId()).get();
        assertEquals(1, (long)foundEntity.getVersion());
        assertEquals("a2", foundEntity.getAuthor());
    }

    @Test
    public void delete() {
        repository.delete(savedEntity);
        assertFalse(repository.existsById(savedEntity.getId()));
    }

    @Test
    public void getByProductId() {
        List<RecommendationEntity> entityList = repository.findByProductId(savedEntity.getProductId());
        assertThat(entityList, hasSize(1));
    }

    @Test(expected = DuplicateKeyException.class)
    public void duplicateError() {
        RecommendationEntity entity = new RecommendationEntity(1, 2, "a", 3, "c");
        repository.save(entity);
    }

    @Test
    public void optimisticLockError() {
        RecommendationEntity entity1 = repository.findById(savedEntity.getId()).get();
        RecommendationEntity entity2 = repository.findById(savedEntity.getId()).get();

        entity1.setAuthor("a1");
        repository.save(entity1);

        try {
            entity2.setAuthor("a2");
            repository.save(entity2);

            fail("Expected an OptimisticLockingFailureException");
        } catch (OptimisticLockingFailureException ex) {}

        RecommendationEntity updatedEntity = repository.findById(savedEntity.getId()).get();
        assertEquals(1, (int)updatedEntity.getVersion());
        assertEquals("a1", updatedEntity.getAuthor());
    }

    private void assertEqualsRecommendation(RecommendationEntity expectedEntityy, RecommendationEntity actualEntity) {
        assertEquals(expectedEntityy.getId(), actualEntity.getId());
        assertEquals(expectedEntityy.getVersion(), actualEntity.getVersion());
        assertEquals(expectedEntityy.getProductId(), actualEntity.getProductId());
        assertEquals(expectedEntityy.getRecommendationId(), actualEntity.getRecommendationId());
        assertEquals(expectedEntityy.getAuthor(), actualEntity.getAuthor());
        assertEquals(expectedEntityy.getRating(), actualEntity.getRating());
        assertEquals(expectedEntityy.getContent(), actualEntity.getContent());
    }
}
