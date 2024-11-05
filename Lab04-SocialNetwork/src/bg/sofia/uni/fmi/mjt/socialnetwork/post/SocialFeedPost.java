package bg.sofia.uni.fmi.mjt.socialnetwork.post;

import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.time.LocalDateTime;
import java.util.*;

public class SocialFeedPost implements Post {

    private static int uniqueId = 0;
    private final UserProfile author;
    private final String content;
    private final LocalDateTime publishedOn;
    private final Map<ReactionType, Set<UserProfile>> reactions;
    private final int id;

    public SocialFeedPost(UserProfile author, String content) {
        this.author = author;
        this.content = content;
        this.publishedOn = LocalDateTime.now();
        this.reactions = new HashMap<>();
        id = uniqueId++;
    }

    @Override
    public String getUniqueId() {
        return "ID = " + id;
    }

    @Override
    public UserProfile getAuthor() {
        return author;
    }

    @Override
    public LocalDateTime getPublishedOn() {
        return publishedOn;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public boolean addReaction(UserProfile userProfile, ReactionType reactionType) {
        if (userProfile == null) {
            throw new IllegalArgumentException("User profile is null");
        }
        else if (reactionType == null) {
            throw new IllegalArgumentException("Reaction type is null");
        }

        if (!reactions.containsKey(reactionType)) {
            reactions.put(reactionType, new HashSet<>());
        }

        for (Map.Entry<ReactionType, Set<UserProfile>> entry : reactions.entrySet()) {
            if (entry.getValue().contains(userProfile)) {
                entry.getValue().remove(userProfile);
                if (entry.getValue().isEmpty() && entry.getKey() != reactionType) {
                    reactions.remove(entry.getKey());
                }
                reactions.get(reactionType).add(userProfile);
                return false;
            }
        }

        reactions.get(reactionType).add(userProfile);
        return true;
    }

    @Override
    public boolean removeReaction(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("User profile is null");
        }

        for (Map.Entry<ReactionType, Set<UserProfile>> entry : reactions.entrySet()) {
            if (entry.getValue().contains(userProfile)) {
                entry.getValue().remove(userProfile);
                if (entry.getValue().isEmpty()) {
                    reactions.remove(entry.getKey());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<ReactionType, Set<UserProfile>> getAllReactions() {
        return Collections.unmodifiableMap(reactions);
    }

    @Override
    public int getReactionCount(ReactionType reactionType) {
        if (reactionType == null) {
            throw new IllegalArgumentException("Reaction type is null");
        }

        if (!reactions.containsKey(reactionType)) {
            return 0;
        }
        return reactions.get(reactionType).size();
    }

    @Override
    public int totalReactionsCount() {
        int sum = 0;

        for (Set<UserProfile> values : reactions.values()) {
            sum += values.size();
        }

        return sum;
    }

}
