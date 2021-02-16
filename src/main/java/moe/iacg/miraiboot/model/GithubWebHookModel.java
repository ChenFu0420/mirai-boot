package moe.iacg.miraiboot.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GithubWebHookModel implements Serializable {

    /**
     * ref : refs/heads/master
     * before : b41f9a1173b22a121251e7eda071787a6430f8d7
     * after : 82c561bf7e4f86d3096c1bcf913d259f9805043c
     * repository : {"id":328905693,"node_id":"MDEwOlJlcG9zaXRvcnkzMjg5MDU2OTM=","name":"mirai-boot","full_name":"ColorfulGhost/mirai-boot","private":false,"owner":{"name":"ColorfulGhost","email":"admin@iacg.moe","login":"ColorfulGhost","id":11071219,"node_id":"MDQ6VXNlcjExMDcxMjE5","avatar_url":"https://avatars.githubusercontent.com/u/11071219?v=4","gravatar_id":"","url":"https://api.github.com/users/ColorfulGhost","html_url":"https://github.com/ColorfulGhost","followers_url":"https://api.github.com/users/ColorfulGhost/followers","following_url":"https://api.github.com/users/ColorfulGhost/following{/other_user}","gists_url":"https://api.github.com/users/ColorfulGhost/gists{/gist_id}","starred_url":"https://api.github.com/users/ColorfulGhost/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/ColorfulGhost/subscriptions","organizations_url":"https://api.github.com/users/ColorfulGhost/orgs","repos_url":"https://api.github.com/users/ColorfulGhost/repos","events_url":"https://api.github.com/users/ColorfulGhost/events{/privacy}","received_events_url":"https://api.github.com/users/ColorfulGhost/received_events","type":"User","site_admin":false},"html_url":"https://github.com/ColorfulGhost/mirai-boot","description":"基于Go-Mirai-Client 与pbbot-spring-boot-starter 开发的QQBot.","fork":false,"url":"https://github.com/ColorfulGhost/mirai-boot","forks_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/forks","keys_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/keys{/key_id}","collaborators_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/collaborators{/collaborator}","teams_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/teams","hooks_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/hooks","issue_events_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/issues/events{/number}","events_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/events","assignees_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/assignees{/user}","branches_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/branches{/branch}","tags_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/tags","blobs_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/git/blobs{/sha}","git_tags_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/git/tags{/sha}","git_refs_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/git/refs{/sha}","trees_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/git/trees{/sha}","statuses_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/statuses/{sha}","languages_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/languages","stargazers_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/stargazers","contributors_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/contributors","subscribers_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/subscribers","subscription_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/subscription","commits_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/commits{/sha}","git_commits_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/git/commits{/sha}","comments_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/comments{/number}","issue_comment_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/issues/comments{/number}","contents_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/contents/{+path}","compare_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/compare/{base}...{head}","merges_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/merges","archive_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/{archive_format}{/ref}","downloads_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/downloads","issues_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/issues{/number}","pulls_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/pulls{/number}","milestones_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/milestones{/number}","notifications_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/notifications{?since,all,participating}","labels_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/labels{/name}","releases_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/releases{/id}","deployments_url":"https://api.github.com/repos/ColorfulGhost/mirai-boot/deployments","created_at":1610436343,"updated_at":"2021-02-15T12:40:55Z","pushed_at":1613484722,"git_url":"git://github.com/ColorfulGhost/mirai-boot.git","ssh_url":"git@github.com:ColorfulGhost/mirai-boot.git","clone_url":"https://github.com/ColorfulGhost/mirai-boot.git","svn_url":"https://github.com/ColorfulGhost/mirai-boot","homepage":"","size":1130,"stargazers_count":0,"watchers_count":0,"language":"Java","has_issues":true,"has_projects":true,"has_downloads":true,"has_wiki":true,"has_pages":false,"forks_count":0,"mirror_url":null,"archived":false,"disabled":false,"open_issues_count":0,"license":null,"forks":0,"open_issues":0,"watchers":0,"default_branch":"master","stargazers":0,"master_branch":"master"}
     * pusher : {"name":"ColorfulGhost","email":"admin@iacg.moe"}
     * sender : {"login":"ColorfulGhost","id":11071219,"node_id":"MDQ6VXNlcjExMDcxMjE5","avatar_url":"https://avatars.githubusercontent.com/u/11071219?v=4","gravatar_id":"","url":"https://api.github.com/users/ColorfulGhost","html_url":"https://github.com/ColorfulGhost","followers_url":"https://api.github.com/users/ColorfulGhost/followers","following_url":"https://api.github.com/users/ColorfulGhost/following{/other_user}","gists_url":"https://api.github.com/users/ColorfulGhost/gists{/gist_id}","starred_url":"https://api.github.com/users/ColorfulGhost/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/ColorfulGhost/subscriptions","organizations_url":"https://api.github.com/users/ColorfulGhost/orgs","repos_url":"https://api.github.com/users/ColorfulGhost/repos","events_url":"https://api.github.com/users/ColorfulGhost/events{/privacy}","received_events_url":"https://api.github.com/users/ColorfulGhost/received_events","type":"User","site_admin":false}
     * created : false
     * deleted : false
     * forced : false
     * base_ref : null
     * compare : https://github.com/ColorfulGhost/mirai-boot/compare/b41f9a1173b2...82c561bf7e4f
     * commits : [{"id":"82c561bf7e4f86d3096c1bcf913d259f9805043c","tree_id":"9d21a40ee98cc4f711c3feb6c5d85fa75a61bbae","distinct":true,"message":"[A]git web hook","timestamp":"2021-02-16T22:11:43+08:00","url":"https://github.com/ColorfulGhost/mirai-boot/commit/82c561bf7e4f86d3096c1bcf913d259f9805043c","author":{"name":"ColorfulGhost","email":"admin@iacg.moe","username":"ColorfulGhost"},"committer":{"name":"ColorfulGhost","email":"admin@iacg.moe","username":"ColorfulGhost"},"added":["src/main/java/moe/iacg/miraiboot/controller/GithubController.java"],"removed":[],"modified":[]}]
     * head_commit : {"id":"82c561bf7e4f86d3096c1bcf913d259f9805043c","tree_id":"9d21a40ee98cc4f711c3feb6c5d85fa75a61bbae","distinct":true,"message":"[A]git web hook","timestamp":"2021-02-16T22:11:43+08:00","url":"https://github.com/ColorfulGhost/mirai-boot/commit/82c561bf7e4f86d3096c1bcf913d259f9805043c","author":{"name":"ColorfulGhost","email":"admin@iacg.moe","username":"ColorfulGhost"},"committer":{"name":"ColorfulGhost","email":"admin@iacg.moe","username":"ColorfulGhost"},"added":["src/main/java/moe/iacg/miraiboot/controller/GithubController.java"],"removed":[],"modified":[]}
     */

    @JSONField(name = "ref")
    private String ref;
    @JSONField(name = "before")
    private String before;
    @JSONField(name = "after")
    private String after;
    @JSONField(name = "repository")
    private Repository repository;
    @JSONField(name = "pusher")
    private Pusher pusher;
    @JSONField(name = "sender")
    private Sender sender;
    @JSONField(name = "created")
    private boolean created;
    @JSONField(name = "deleted")
    private boolean deleted;
    @JSONField(name = "forced")
    private boolean forced;
    @JSONField(name = "base_ref")
    private Object baseRef;
    @JSONField(name = "compare")
    private String compare;
    @JSONField(name = "head_commit")
    private HeadCommit headCommit;
    @JSONField(name = "commits")
    private List<Commits> commits;

    @Data
    public static class Repository implements Serializable {
        /**
         * id : 328905693
         * node_id : MDEwOlJlcG9zaXRvcnkzMjg5MDU2OTM=
         * name : mirai-boot
         * full_name : ColorfulGhost/mirai-boot
         * private : false
         * owner : {"name":"ColorfulGhost","email":"admin@iacg.moe","login":"ColorfulGhost","id":11071219,"node_id":"MDQ6VXNlcjExMDcxMjE5","avatar_url":"https://avatars.githubusercontent.com/u/11071219?v=4","gravatar_id":"","url":"https://api.github.com/users/ColorfulGhost","html_url":"https://github.com/ColorfulGhost","followers_url":"https://api.github.com/users/ColorfulGhost/followers","following_url":"https://api.github.com/users/ColorfulGhost/following{/other_user}","gists_url":"https://api.github.com/users/ColorfulGhost/gists{/gist_id}","starred_url":"https://api.github.com/users/ColorfulGhost/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/ColorfulGhost/subscriptions","organizations_url":"https://api.github.com/users/ColorfulGhost/orgs","repos_url":"https://api.github.com/users/ColorfulGhost/repos","events_url":"https://api.github.com/users/ColorfulGhost/events{/privacy}","received_events_url":"https://api.github.com/users/ColorfulGhost/received_events","type":"User","site_admin":false}
         * html_url : https://github.com/ColorfulGhost/mirai-boot
         * description : 基于Go-Mirai-Client 与pbbot-spring-boot-starter 开发的QQBot.
         * fork : false
         * url : https://github.com/ColorfulGhost/mirai-boot
         * forks_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/forks
         * keys_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/keys{/key_id}
         * collaborators_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/collaborators{/collaborator}
         * teams_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/teams
         * hooks_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/hooks
         * issue_events_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/issues/events{/number}
         * events_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/events
         * assignees_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/assignees{/user}
         * branches_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/branches{/branch}
         * tags_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/tags
         * blobs_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/git/blobs{/sha}
         * git_tags_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/git/tags{/sha}
         * git_refs_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/git/refs{/sha}
         * trees_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/git/trees{/sha}
         * statuses_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/statuses/{sha}
         * languages_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/languages
         * stargazers_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/stargazers
         * contributors_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/contributors
         * subscribers_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/subscribers
         * subscription_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/subscription
         * commits_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/commits{/sha}
         * git_commits_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/git/commits{/sha}
         * comments_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/comments{/number}
         * issue_comment_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/issues/comments{/number}
         * contents_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/contents/{+path}
         * compare_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/compare/{base}...{head}
         * merges_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/merges
         * archive_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/{archive_format}{/ref}
         * downloads_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/downloads
         * issues_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/issues{/number}
         * pulls_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/pulls{/number}
         * milestones_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/milestones{/number}
         * notifications_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/notifications{?since,all,participating}
         * labels_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/labels{/name}
         * releases_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/releases{/id}
         * deployments_url : https://api.github.com/repos/ColorfulGhost/mirai-boot/deployments
         * created_at : 1610436343
         * updated_at : 2021-02-15T12:40:55Z
         * pushed_at : 1613484722
         * git_url : git://github.com/ColorfulGhost/mirai-boot.git
         * ssh_url : git@github.com:ColorfulGhost/mirai-boot.git
         * clone_url : https://github.com/ColorfulGhost/mirai-boot.git
         * svn_url : https://github.com/ColorfulGhost/mirai-boot
         * homepage :
         * size : 1130
         * stargazers_count : 0
         * watchers_count : 0
         * language : Java
         * has_issues : true
         * has_projects : true
         * has_downloads : true
         * has_wiki : true
         * has_pages : false
         * forks_count : 0
         * mirror_url : null
         * archived : false
         * disabled : false
         * open_issues_count : 0
         * license : null
         * forks : 0
         * open_issues : 0
         * watchers : 0
         * default_branch : master
         * stargazers : 0
         * master_branch : master
         */

        @JSONField(name = "id")
        private int id;
        @JSONField(name = "node_id")
        private String nodeId;
        @JSONField(name = "name")
        private String name;
        @JSONField(name = "full_name")
        private String fullName;
        @JSONField(name = "private")
        private boolean privateX;
        @JSONField(name = "owner")
        private Owner owner;
        @JSONField(name = "html_url")
        private String htmlUrl;
        @JSONField(name = "description")
        private String description;
        @JSONField(name = "fork")
        private boolean fork;
        @JSONField(name = "url")
        private String url;
        @JSONField(name = "forks_url")
        private String forksUrl;
        @JSONField(name = "keys_url")
        private String keysUrl;
        @JSONField(name = "collaborators_url")
        private String collaboratorsUrl;
        @JSONField(name = "teams_url")
        private String teamsUrl;
        @JSONField(name = "hooks_url")
        private String hooksUrl;
        @JSONField(name = "issue_events_url")
        private String issueEventsUrl;
        @JSONField(name = "events_url")
        private String eventsUrl;
        @JSONField(name = "assignees_url")
        private String assigneesUrl;
        @JSONField(name = "branches_url")
        private String branchesUrl;
        @JSONField(name = "tags_url")
        private String tagsUrl;
        @JSONField(name = "blobs_url")
        private String blobsUrl;
        @JSONField(name = "git_tags_url")
        private String gitTagsUrl;
        @JSONField(name = "git_refs_url")
        private String gitRefsUrl;
        @JSONField(name = "trees_url")
        private String treesUrl;
        @JSONField(name = "statuses_url")
        private String statusesUrl;
        @JSONField(name = "languages_url")
        private String languagesUrl;
        @JSONField(name = "stargazers_url")
        private String stargazersUrl;
        @JSONField(name = "contributors_url")
        private String contributorsUrl;
        @JSONField(name = "subscribers_url")
        private String subscribersUrl;
        @JSONField(name = "subscription_url")
        private String subscriptionUrl;
        @JSONField(name = "commits_url")
        private String commitsUrl;
        @JSONField(name = "git_commits_url")
        private String gitCommitsUrl;
        @JSONField(name = "comments_url")
        private String commentsUrl;
        @JSONField(name = "issue_comment_url")
        private String issueCommentUrl;
        @JSONField(name = "contents_url")
        private String contentsUrl;
        @JSONField(name = "compare_url")
        private String compareUrl;
        @JSONField(name = "merges_url")
        private String mergesUrl;
        @JSONField(name = "archive_url")
        private String archiveUrl;
        @JSONField(name = "downloads_url")
        private String downloadsUrl;
        @JSONField(name = "issues_url")
        private String issuesUrl;
        @JSONField(name = "pulls_url")
        private String pullsUrl;
        @JSONField(name = "milestones_url")
        private String milestonesUrl;
        @JSONField(name = "notifications_url")
        private String notificationsUrl;
        @JSONField(name = "labels_url")
        private String labelsUrl;
        @JSONField(name = "releases_url")
        private String releasesUrl;
        @JSONField(name = "deployments_url")
        private String deploymentsUrl;
        @JSONField(name = "created_at")
        private int createdAt;
        @JSONField(name = "updated_at")
        private String updatedAt;
        @JSONField(name = "pushed_at")
        private int pushedAt;
        @JSONField(name = "git_url")
        private String gitUrl;
        @JSONField(name = "ssh_url")
        private String sshUrl;
        @JSONField(name = "clone_url")
        private String cloneUrl;
        @JSONField(name = "svn_url")
        private String svnUrl;
        @JSONField(name = "homepage")
        private String homepage;
        @JSONField(name = "size")
        private int size;
        @JSONField(name = "stargazers_count")
        private int stargazersCount;
        @JSONField(name = "watchers_count")
        private int watchersCount;
        @JSONField(name = "language")
        private String language;
        @JSONField(name = "has_issues")
        private boolean hasIssues;
        @JSONField(name = "has_projects")
        private boolean hasProjects;
        @JSONField(name = "has_downloads")
        private boolean hasDownloads;
        @JSONField(name = "has_wiki")
        private boolean hasWiki;
        @JSONField(name = "has_pages")
        private boolean hasPages;
        @JSONField(name = "forks_count")
        private int forksCount;
        @JSONField(name = "mirror_url")
        private Object mirrorUrl;
        @JSONField(name = "archived")
        private boolean archived;
        @JSONField(name = "disabled")
        private boolean disabled;
        @JSONField(name = "open_issues_count")
        private int openIssuesCount;
        @JSONField(name = "license")
        private Object license;
        @JSONField(name = "forks")
        private int forks;
        @JSONField(name = "open_issues")
        private int openIssues;
        @JSONField(name = "watchers")
        private int watchers;
        @JSONField(name = "default_branch")
        private String defaultBranch;
        @JSONField(name = "stargazers")
        private int stargazers;
        @JSONField(name = "master_branch")
        private String masterBranch;

        @Data
        public static class Owner implements Serializable {
            /**
             * name : ColorfulGhost
             * email : admin@iacg.moe
             * login : ColorfulGhost
             * id : 11071219
             * node_id : MDQ6VXNlcjExMDcxMjE5
             * avatar_url : https://avatars.githubusercontent.com/u/11071219?v=4
             * gravatar_id :
             * url : https://api.github.com/users/ColorfulGhost
             * html_url : https://github.com/ColorfulGhost
             * followers_url : https://api.github.com/users/ColorfulGhost/followers
             * following_url : https://api.github.com/users/ColorfulGhost/following{/other_user}
             * gists_url : https://api.github.com/users/ColorfulGhost/gists{/gist_id}
             * starred_url : https://api.github.com/users/ColorfulGhost/starred{/owner}{/repo}
             * subscriptions_url : https://api.github.com/users/ColorfulGhost/subscriptions
             * organizations_url : https://api.github.com/users/ColorfulGhost/orgs
             * repos_url : https://api.github.com/users/ColorfulGhost/repos
             * events_url : https://api.github.com/users/ColorfulGhost/events{/privacy}
             * received_events_url : https://api.github.com/users/ColorfulGhost/received_events
             * type : User
             * site_admin : false
             */

            @JSONField(name = "name")
            private String name;
            @JSONField(name = "email")
            private String email;
            @JSONField(name = "login")
            private String login;
            @JSONField(name = "id")
            private int id;
            @JSONField(name = "node_id")
            private String nodeId;
            @JSONField(name = "avatar_url")
            private String avatarUrl;
            @JSONField(name = "gravatar_id")
            private String gravatarId;
            @JSONField(name = "url")
            private String url;
            @JSONField(name = "html_url")
            private String htmlUrl;
            @JSONField(name = "followers_url")
            private String followersUrl;
            @JSONField(name = "following_url")
            private String followingUrl;
            @JSONField(name = "gists_url")
            private String gistsUrl;
            @JSONField(name = "starred_url")
            private String starredUrl;
            @JSONField(name = "subscriptions_url")
            private String subscriptionsUrl;
            @JSONField(name = "organizations_url")
            private String organizationsUrl;
            @JSONField(name = "repos_url")
            private String reposUrl;
            @JSONField(name = "events_url")
            private String eventsUrl;
            @JSONField(name = "received_events_url")
            private String receivedEventsUrl;
            @JSONField(name = "type")
            private String type;
            @JSONField(name = "site_admin")
            private boolean siteAdmin;
        }
    }

    @Data
    public static class Pusher implements Serializable {
        /**
         * name : ColorfulGhost
         * email : admin@iacg.moe
         */

        @JSONField(name = "name")
        private String name;
        @JSONField(name = "email")
        private String email;
    }

    @Data
    public static class Sender implements Serializable {
        /**
         * login : ColorfulGhost
         * id : 11071219
         * node_id : MDQ6VXNlcjExMDcxMjE5
         * avatar_url : https://avatars.githubusercontent.com/u/11071219?v=4
         * gravatar_id :
         * url : https://api.github.com/users/ColorfulGhost
         * html_url : https://github.com/ColorfulGhost
         * followers_url : https://api.github.com/users/ColorfulGhost/followers
         * following_url : https://api.github.com/users/ColorfulGhost/following{/other_user}
         * gists_url : https://api.github.com/users/ColorfulGhost/gists{/gist_id}
         * starred_url : https://api.github.com/users/ColorfulGhost/starred{/owner}{/repo}
         * subscriptions_url : https://api.github.com/users/ColorfulGhost/subscriptions
         * organizations_url : https://api.github.com/users/ColorfulGhost/orgs
         * repos_url : https://api.github.com/users/ColorfulGhost/repos
         * events_url : https://api.github.com/users/ColorfulGhost/events{/privacy}
         * received_events_url : https://api.github.com/users/ColorfulGhost/received_events
         * type : User
         * site_admin : false
         */

        @JSONField(name = "login")
        private String login;
        @JSONField(name = "id")
        private int id;
        @JSONField(name = "node_id")
        private String nodeId;
        @JSONField(name = "avatar_url")
        private String avatarUrl;
        @JSONField(name = "gravatar_id")
        private String gravatarId;
        @JSONField(name = "url")
        private String url;
        @JSONField(name = "html_url")
        private String htmlUrl;
        @JSONField(name = "followers_url")
        private String followersUrl;
        @JSONField(name = "following_url")
        private String followingUrl;
        @JSONField(name = "gists_url")
        private String gistsUrl;
        @JSONField(name = "starred_url")
        private String starredUrl;
        @JSONField(name = "subscriptions_url")
        private String subscriptionsUrl;
        @JSONField(name = "organizations_url")
        private String organizationsUrl;
        @JSONField(name = "repos_url")
        private String reposUrl;
        @JSONField(name = "events_url")
        private String eventsUrl;
        @JSONField(name = "received_events_url")
        private String receivedEventsUrl;
        @JSONField(name = "type")
        private String type;
        @JSONField(name = "site_admin")
        private boolean siteAdmin;
    }

    @Data
    public static class HeadCommit implements Serializable {
        /**
         * id : 82c561bf7e4f86d3096c1bcf913d259f9805043c
         * tree_id : 9d21a40ee98cc4f711c3feb6c5d85fa75a61bbae
         * distinct : true
         * message : [A]git web hook
         * timestamp : 2021-02-16T22:11:43+08:00
         * url : https://github.com/ColorfulGhost/mirai-boot/commit/82c561bf7e4f86d3096c1bcf913d259f9805043c
         * author : {"name":"ColorfulGhost","email":"admin@iacg.moe","username":"ColorfulGhost"}
         * committer : {"name":"ColorfulGhost","email":"admin@iacg.moe","username":"ColorfulGhost"}
         * added : ["src/main/java/moe/iacg/miraiboot/controller/GithubController.java"]
         * removed : []
         * modified : []
         */

        @JSONField(name = "id")
        private String id;
        @JSONField(name = "tree_id")
        private String treeId;
        @JSONField(name = "distinct")
        private boolean distinct;
        @JSONField(name = "message")
        private String message;
        @JSONField(name = "timestamp")
        private String timestamp;
        @JSONField(name = "url")
        private String url;
        @JSONField(name = "author")
        private Author author;
        @JSONField(name = "committer")
        private Committer committer;
        @JSONField(name = "added")
        private List<String> added;
        @JSONField(name = "removed")
        private List<?> removed;
        @JSONField(name = "modified")
        private List<?> modified;

        @Data
        public static class Author implements Serializable {
            /**
             * name : ColorfulGhost
             * email : admin@iacg.moe
             * username : ColorfulGhost
             */

            @JSONField(name = "name")
            private String name;
            @JSONField(name = "email")
            private String email;
            @JSONField(name = "username")
            private String username;
        }

        @Data
        public static class Committer implements Serializable {
            /**
             * name : ColorfulGhost
             * email : admin@iacg.moe
             * username : ColorfulGhost
             */

            @JSONField(name = "name")
            private String name;
            @JSONField(name = "email")
            private String email;
            @JSONField(name = "username")
            private String username;
        }
    }

    @Data
    public static class Commits implements Serializable {
        /**
         * id : 82c561bf7e4f86d3096c1bcf913d259f9805043c
         * tree_id : 9d21a40ee98cc4f711c3feb6c5d85fa75a61bbae
         * distinct : true
         * message : [A]git web hook
         * timestamp : 2021-02-16T22:11:43+08:00
         * url : https://github.com/ColorfulGhost/mirai-boot/commit/82c561bf7e4f86d3096c1bcf913d259f9805043c
         * author : {"name":"ColorfulGhost","email":"admin@iacg.moe","username":"ColorfulGhost"}
         * committer : {"name":"ColorfulGhost","email":"admin@iacg.moe","username":"ColorfulGhost"}
         * added : ["src/main/java/moe/iacg/miraiboot/controller/GithubController.java"]
         * removed : []
         * modified : []
         */

        @JSONField(name = "id")
        private String id;
        @JSONField(name = "tree_id")
        private String treeId;
        @JSONField(name = "distinct")
        private boolean distinct;
        @JSONField(name = "message")
        private String message;
        @JSONField(name = "timestamp")
        private String timestamp;
        @JSONField(name = "url")
        private String url;
        @JSONField(name = "author")
        private AuthorX author;
        @JSONField(name = "committer")
        private CommitterX committer;
        @JSONField(name = "added")
        private List<String> added;
        @JSONField(name = "removed")
        private List<?> removed;
        @JSONField(name = "modified")
        private List<?> modified;

        @Data
        public static class AuthorX implements Serializable {
            /**
             * name : ColorfulGhost
             * email : admin@iacg.moe
             * username : ColorfulGhost
             */

            @JSONField(name = "name")
            private String name;
            @JSONField(name = "email")
            private String email;
            @JSONField(name = "username")
            private String username;
        }

        @Data
        public static class CommitterX implements Serializable {
            /**
             * name : ColorfulGhost
             * email : admin@iacg.moe
             * username : ColorfulGhost
             */

            @JSONField(name = "name")
            private String name;
            @JSONField(name = "email")
            private String email;
            @JSONField(name = "username")
            private String username;
        }
    }
}
