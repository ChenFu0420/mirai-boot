package moe.iacg.miraiboot.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class AnimeModel {

    @JSONField(name = "RawDocsCount")
    private int rawDocsCount;

    @JSONField(name = "CacheHit")
    private boolean cacheHit;

    @JSONField(name = "docs")
    private List<DocsItem> docs;

    @JSONField(name = "limit_ttl")
    private int limitTtl;

    @JSONField(name = "RawDocsSearchTime")
    private long rawDocsSearchTime;

    @JSONField(name = "quota")
    private int quota;

    @JSONField(name = "limit")
    private int limit;

    @JSONField(name = "ReRankSearchTime")
    private long reRankSearchTime;

    @JSONField(name = "quota_ttl")
    private int quotaTtl;

    @JSONField(name = "trial")
    private int trial;

    @Data
    public static class DocsItem {

        @JSONField(name = "title_chinese")
        private String titleChinese;

        @JSONField(name = "title_native")
        private String titleNative;

        @JSONField(name = "synonyms")
        private List<String> synonyms;

        @JSONField(name = "title_romaji")
        private String titleRomaji;

        @JSONField(name = "episode")
        private int episode;

        @JSONField(name = "mal_id")
        private int malId;

        @JSONField(name = "title")
        private String title;

        @JSONField(name = "anilist_id")
        private int anilistId;

        @JSONField(name = "is_adult")
        private boolean isAdult;

        @JSONField(name = "tokenthumb")
        private String tokenthumb;

        @JSONField(name = "synonyms_chinese")
        private List<Object> synonymsChinese;

        @JSONField(name = "at")
        private double at;

        @JSONField(name = "filename")
        private String filename;

        @JSONField(name = "similarity")
        private double similarity;

        @JSONField(name = "season")
        private String season;

        @JSONField(name = "title_english")
        private String titleEnglish;

        @JSONField(name = "from")
        private double from;

        @JSONField(name = "to")
        private double to;

        @JSONField(name = "anime")
        private String anime;
    }
}