package bg.sofia.uni.fmi.mjt.socialnetwork.profile;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

public class DefaultUserProfile implements UserProfile, Comparable<UserProfile> {

    private final String username;
    private final Collection<Interest> interests;
    private final Collection<UserProfile> friends;

    public DefaultUserProfile(String username) {
        this.username = username;
        interests = new HashSet<>();
        friends = new HashSet<>();
    }

    @Override
    public int compareTo(UserProfile o) {
        if (o == null) {
            return -1;
        }
        if (this.equals(o)) {
            return 0;
        }
        int friendsSizeCompare = Integer.compare(o.getFriends().size(), this.getFriends().size());
        if (friendsSizeCompare != 0) {
            return friendsSizeCompare;
        }
        int interestSizeCompare = Integer.compare(o.getInterests().size(), this.getInterests().size());
        if (interestSizeCompare != 0) {
            return interestSizeCompare;
        }
        return this.getUsername().compareTo(o.getUsername());
    }

    @Override
    public String toString() {
        return "DefaultUserProfile{" +
                "username='" + username + '\'' +
                ", interests=" + interests.size() +
                ", friends=" + friends.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultUserProfile that = (DefaultUserProfile) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<Interest> getInterests() {
        return Collections.unmodifiableCollection(interests);
    }

    @Override
    public boolean addInterest(Interest interest) {
        if (interest == null) {
            throw new IllegalArgumentException("Interest is null");
        }

        if (interests.contains(interest)) {
            return false;
        } else {
            interests.add(interest);
            return true;
        }
    }

    @Override
    public boolean removeInterest(Interest interest) {
        if (interest == null) {
            throw new IllegalArgumentException("Interest is null");
        }

        if (interests.contains(interest)) {
            interests.remove(interest);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Collection<UserProfile> getFriends() {
        return Collections.unmodifiableCollection(friends);
    }

    @Override
    public boolean addFriend(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("User profile is null");
        } else if (this.equals(userProfile)) {
            throw new IllegalArgumentException("User profile is the same");
        }

        if (friends.contains(userProfile)) {
            return false;
        } else {
            friends.add(userProfile);
            userProfile.addFriend(this);
            return true;
        }
    }

    @Override
    public boolean unfriend(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("User profile is null");
        }

        if (!friends.contains(userProfile)) {
            return false;
        } else {
            friends.remove(userProfile);
            userProfile.unfriend(this);
            return true;
        }
    }

    @Override
    public boolean isFriend(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("User profile is null");
        }

        return friends.contains(userProfile);
    }

}
