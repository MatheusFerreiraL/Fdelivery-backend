package github.matheusferreiral.fdeliveryapi.domain.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column private BigDecimal shippingFee;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Kitchen kitchen;
}
