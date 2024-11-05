package bg.sofia.uni.fmi.mjt.socialnetwork;

import bg.sofia.uni.fmi.mjt.socialnetwork.exception.UserRegistrationException;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.Post;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.SocialFeedPost;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.Interest;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class SocialNetworkImpl implements SocialNetwork {

    private final Set<UserProfile> users;
    private final Collection<Post> posts;

    public SocialNetworkImpl() {
        this.users = new HashSet<>();
        this.posts = new HashSet<>();
    }

    @Override
    public void registerUser(UserProfile userProfile) throws UserRegistrationException {
        if (userProfile == null) {
            throw new IllegalArgumentException("User profile is null");
        }
        if (users.contains(userProfile)) {
            throw new UserRegistrationException("User already exists");
        }

        users.add(userProfile);
    }

    @Override
    public Set<UserProfile> getAllUsers() {
        return Collections.unmodifiableSet(users);
    }

    @Override
    public Post post(UserProfile userProfile, String content) throws UserRegistrationException {
        if (userProfile == null) {
            throw new IllegalArgumentException("User profile is null");
        }
        if (content == null) {
            throw new IllegalArgumentException("Content is null");
        }
        if (content.isEmpty()) {
            throw new IllegalArgumentException("Content is empty");
        }
        if (!users.contains(userProfile)) {
            throw new UserRegistrationException("User does not exist");
        }

        Post createdPost = new SocialFeedPost(userProfile, content);
        posts.add(createdPost);
        return createdPost;
    }

    @Override
    public Collection<Post> getPosts() {
        return Collections.unmodifiableCollection(posts);
    }

    @Override
    public Set<UserProfile> getReachedUsers(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("Post is null");
        }

        UserProfile author = post.getAuthor();
        Set<UserProfile> reachedUsers = new HashSet<>();
        Set<UserProfile> oneNetworkFriends = getOneNetworkUsers(author);

        for (UserProfile user : oneNetworkFriends) {
            if (!user.equals(author) && commonInterest(author.getInterests(), user.getInterests())) {
                reachedUsers.add(user);
            }
        }

        return reachedUsers;
    }

    private boolean commonInterest(Collection<Interest> authorInterests, Collection<Interest> otherInterests) {
        Collection<Interest> commonInterests = new HashSet<>(authorInterests);
        commonInterests.retainAll(otherInterests);
        return !commonInterests.isEmpty();
    }

    private Set<UserProfile> getOneNetworkUsers(UserProfile author) {
        Set<UserProfile> visited = new HashSet<>();
        Queue<UserProfile> queue = new LinkedList<>();
        queue.add(author);
        visited.add(author);

        while (!queue.isEmpty()) {
            UserProfile user = queue.poll();
            for (UserProfile friend : user.getFriends()) {
                if (!visited.contains(friend)) {
                    queue.add(friend);
                    visited.add(friend);
                }
            }
        }

        return visited;
    }

    @Override
    public Set<UserProfile> getMutualFriends(UserProfile firstProfile, UserProfile secondProfile)
            throws UserRegistrationException {
        if (firstProfile == null) {
            throw new IllegalArgumentException("First user is null.");
        }
        if (secondProfile == null) {
            throw new IllegalArgumentException("Second user is null.");
        }

        if (!users.contains(firstProfile)) {
            throw new UserRegistrationException("First user does not exist");
        }
        if (!users.contains(secondProfile)) {
            throw new UserRegistrationException("Second user does not exist");
        }

        Set<UserProfile> intersection = new HashSet<>(firstProfile.getFriends());
        intersection.retainAll(secondProfile.getFriends());
        return intersection;
    }

    @Override
    public SortedSet<UserProfile> getAllProfilesSortedByFriendsCount() {
        return new TreeSet<>(users);
    }

}
