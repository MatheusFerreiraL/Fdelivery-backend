package github.matheusferreiral.fdeliveryapi.infrastructure.repository;

import github.matheusferreiral.fdeliveryapi.domain.model.PaymentMethod;
import github.matheusferreiral.fdeliveryapi.domain.repository.PaymentMethodRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentMethodRepositoryImpl implements PaymentMethodRepository {

  @PersistenceContext private EntityManager manager;

  /**
   * @return result list with payment methods
   */
  @Override
  public List<PaymentMethod> list() {
    TypedQuery<PaymentMethod> query =
        manager.createQuery("from PaymentMethod ", PaymentMethod.class);
    return query.getResultList();
  }

  /**
   * @param id - payment method id
   * @return payment method - specif payment method searched
   */
  @Override
  public PaymentMethod find(Long id) {
    return manager.find(PaymentMethod.class, id);
  }

  /**
   * @param paymentMethod
   * @return updated payment method
   */
  @Transactional
  @Override
  public PaymentMethod save(PaymentMethod paymentMethod) {
    return manager.merge(paymentMethod);
  }

  /**
   * @param paymentMethod - payment method that will be removed
   */
  @Transactional
  @Override
  public void remove(PaymentMethod paymentMethod) {
    manager.remove(paymentMethod);
  }
}
