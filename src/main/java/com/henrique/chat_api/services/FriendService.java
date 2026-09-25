package com.henrique.chat_api.services;

import com.henrique.chat_api.dtos.friend.FriendRequestResponseDTO;
import com.henrique.chat_api.dtos.friend.FriendResponseDTO;
import com.henrique.chat_api.dtos.friend.SendFriendRequestDTO;
import com.henrique.chat_api.dtos.user.UserResponseDTO;
import com.henrique.chat_api.entities.Friend;
import com.henrique.chat_api.entities.FriendRequest;
import com.henrique.chat_api.entities.UserAccount;
import com.henrique.chat_api.exceptions.ResourceNotFoundException;
import com.henrique.chat_api.mappers.UserMapper;
import com.henrique.chat_api.repositories.IFriendRepository;
import com.henrique.chat_api.repositories.IFriendRequestRepository;
import com.henrique.chat_api.repositories.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final IFriendRequestRepository friendRequestRepository;
    private final IUserRepository userRepository;
    private final IFriendRepository friendRepository;


    public void sendFriendRequest(SendFriendRequestDTO request, UserAccount sender) {
        UserAccount recipient = userRepository.findByPublicID(request.publicID())
                .orElseThrow(ResourceNotFoundException::new);

        /*
        todo
        criar verificação caso usuário envie a mesma requisição de amizade, lançando erro ou deletando a antiga e mandando uma nova
         */

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setRecipient(recipient);

        friendRequestRepository.save(friendRequest);
    }

    public Set<FriendResponseDTO> loadFriendsListBy(UserAccount user) {
        Set<Friend> friends = friendRepository.findAllByUserAccount(user);
        Set<Friend> filteredFriends = this.filterFriends(friends, user);

        return filteredFriends.stream()
                .map(Friend::getFriendAccount)
                .map(UserMapper::toResponse)
                .map(FriendResponseDTO::new)
                .collect(Collectors.toSet());
    }

    public Set<Friend> filterFriends(Set<Friend> friends, UserAccount user) {
        Set<Friend> friendshipsAsUser = friends.stream()
                .filter(friendship -> friendship.getUserAccount().equals(user))
                .collect(Collectors.toSet());
        Set<Friend> friendShipsAsFriend = friends.stream()
                .filter(friendship -> friendship.getFriendAccount().equals(user))
                .collect(Collectors.toSet());

        for (Friend friendshipAsFriend : friendShipsAsFriend) {
            Friend friendshipAsUser = new Friend();

            friendshipAsUser.setId(friendshipAsFriend.getId());
            friendshipAsUser.setUserAccount(friendshipAsFriend.getFriendAccount());
            friendshipAsUser.setFriendAccount(friendshipAsFriend.getUserAccount());
            friendshipAsUser.setMessages(friendshipAsFriend.getMessages());
            friendshipsAsUser.add(friendshipAsUser);
        }

        return friendshipsAsUser;
    }

    public Set<FriendRequestResponseDTO> loadFriendRequestsBy(UserAccount user) {
        return friendRequestRepository.findAllByRecipient(user).stream()
                .map(request -> new FriendRequestResponseDTO(
                        request.getId(), UserMapper.toResponse(request.getSender())
                ))
                .collect(Collectors.toSet());
    }

    @Transactional(rollbackOn = Exception.class)
    public void acceptFriendRequest(Long friendRequestID) {
        FriendRequest request = friendRequestRepository.findById(friendRequestID)
                .orElseThrow(ResourceNotFoundException::new);

        Friend friend = new Friend();
        friend.setUserAccount(request.getSender());
        friend.setFriendAccount(request.getRecipient());


        friendRepository.save(friend);
        friendRequestRepository.delete(request);
    }

    public void rejectFriendRequest(Long friendRequestID) {
        FriendRequest request = friendRequestRepository.findById(friendRequestID)
                .orElseThrow(ResourceNotFoundException::new);

        friendRequestRepository.delete(request);
    }

    public void deleteFriendByID(UUID friendID) {
        Friend friend = friendRepository.findByFriendID(friendID)
                .orElseThrow(() -> new ResourceNotFoundException("Friend"));

        friendRepository.delete(friend);
    }
}
