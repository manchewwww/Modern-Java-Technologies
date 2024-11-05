package bg.sofia.uni.fmi.mjt.socialnetwork;

import bg.sofia.uni.fmi.mjt.socialnetwork.exception.UserRegistrationException;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.Post;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.ReactionType;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.SocialFeedPost;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.DefaultUserProfile;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.Interest;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.util.Set;

public class Main {

    public static void main(String[] args) throws UserRegistrationException {
        SocialNetwork socialNetwork = new SocialNetworkImpl();

        UserProfile userProfile = new DefaultUserProfile("Georgi");
        UserProfile userProfile1 = new DefaultUserProfile("Ivan");
        UserProfile userProfile2 = new DefaultUserProfile("Pesho");
        UserProfile userProfile3 = new DefaultUserProfile("Ivo");
        UserProfile userProfile4 = new DefaultUserProfile("Marto");

        Post post = new SocialFeedPost(userProfile, "New photo");

        userProfile.addInterest(Interest.FOOD);
        userProfile1.addInterest(Interest.FOOD);
        userProfile2.addInterest(Interest.FOOD);
        userProfile2.addInterest(Interest.MUSIC);
        userProfile2.addInterest(Interest.BOOKS);
        userProfile3.addInterest(Interest.GAMES);
        userProfile4.addInterest(Interest.FOOD);
        userProfile4.addInterest(Interest.MUSIC);

        userProfile1.addFriend(userProfile);
        userProfile1.addFriend(userProfile2);
        userProfile1.addFriend(userProfile3);
        userProfile1.addFriend(userProfile4);
        System.out.println("Friend with user 2: " + userProfile1.isFriend(userProfile2));
        userProfile1.unfriend(userProfile2);
        System.out.println("Friend with user 2: " + userProfile1.isFriend(userProfile2));

        post.addReaction(userProfile, ReactionType.LOVE);
        post.addReaction(userProfile1, ReactionType.LOVE);
        System.out.println("Post reactions: " + post.totalReactionsCount());
        post.removeReaction(userProfile);
        System.out.println("Post reactions: " + post.totalReactionsCount());
        System.out.println(post.getPublishedOn());

        socialNetwork.registerUser(userProfile);
        socialNetwork.registerUser(userProfile1);
        socialNetwork.registerUser(userProfile2);
        socialNetwork.registerUser(userProfile3);
        socialNetwork.registerUser(userProfile4);

        socialNetwork.post(userProfile, "TITLE");
        System.out.println(socialNetwork.getAllUsers());
        System.out.println(socialNetwork.getReachedUsers(post));
        System.out.println(socialNetwork.getAllProfilesSortedByFriendsCount());
        System.out.println(socialNetwork.getMutualFriends(userProfile, userProfile3));
    }

}
