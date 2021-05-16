package moe.iacg.miraiboot.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class HpoolMiningDetail implements Serializable {

    private Integer code;
    private Data data;

    @NoArgsConstructor
    @lombok.Data
    public static class Data implements Serializable {
        private Integer page;
        private Integer total;
        private java.util.List<List> list;

        @NoArgsConstructor
        @lombok.Data
        public static class List implements Serializable {
            private BigDecimal block_reward;
            private String coin;
            private String height;
            private String huge_reward;
            private Integer mortgage_rate_k;
            private String name;
            private Integer record_time;
            private Integer status;
            private String status_str;
            private String type;
        }


    }
}
