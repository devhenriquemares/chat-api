package com.henrique.chat_api.services;

import com.henrique.chat_api.dtos.friend.FriendResponseDTO;
import com.henrique.chat_api.dtos.friend.SendFriendRequestDTO;
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

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setRecipient(recipient);

        friendRequestRepository.save(friendRequest);
    }

    public Set<FriendResponseDTO> loadFriendsListBy(UserAccount user) {
        Set<Friend> friends = friendRepository.findAllByUserAccount(user);

        return friends.stream()
                .map(Friend::getFriendAccount)
                .map(UserMapper::toResponse)
                .map(FriendResponseDTO::new)
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
