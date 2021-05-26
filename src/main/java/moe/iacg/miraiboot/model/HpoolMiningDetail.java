package moe.iacg.miraiboot.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
public class HpoolMiningDetail implements Serializable {

    private Integer code;
    private Data data;

    @NoArgsConstructor
    @lombok.Data
    public static   class  Data  implements Serializable {
        private Integer page;
        private Integer total;
        private List<Settlement> list;

//        private java.util.List<MiningInComeRecord> miningInComeRecord;

        @NoArgsConstructor
        @lombok.Data
        public static class Settlement implements Serializable {
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
//        {
//            "amount": "0.01142881",
//                "coin": "chia",
//                "name": "CHIA",
//                "record_time": 1621827000,
//                "type": "chia"
//        }

        @NoArgsConstructor
        @lombok.Data
        public static class MiningInComeRecord implements Serializable {
            private String amount;
            private Date record_time;
        }


    }
}
