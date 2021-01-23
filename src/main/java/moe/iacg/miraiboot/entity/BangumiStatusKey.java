package moe.iacg.miraiboot.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class BangumiStatusKey implements Serializable {
    Long qq;
    Long qqGroup;
}
