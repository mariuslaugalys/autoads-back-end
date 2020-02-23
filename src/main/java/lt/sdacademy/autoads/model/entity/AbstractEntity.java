package lt.sdacademy.autoads.model.entity;

import javax.persistence.*;
import lombok.NoArgsConstructor;

@MappedSuperclass
public abstract class AbstractEntity {

    private static final int ODD_PRIME = 31;

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntity)) return false;
        AbstractEntity that = (AbstractEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : ODD_PRIME * id.hashCode();
    }
}
