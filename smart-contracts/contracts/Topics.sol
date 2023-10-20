// SPDX-License-Identifier: GPL-3.0
pragma solidity ^0.8.20;

import {ERC721} from "@openzeppelin/contracts/token/ERC721/ERC721.sol";
import {AddressSet, AddressSetLib} from "./libraries/AddressSet.sol";
import {IRefiqTopics} from "./interfaces/ITopics.sol";

using AddressSetLib for AddressSet;

contract RefiqTopics is ERC721, IRefiqTopics {
    mapping(uint256 topicId => AddressSet moderatorAddresses)
        private _moderators;
    mapping(uint256 contentId => uint256 topicId) private _topics;
    mapping(uint256 contentId => address author) private _authors;
    mapping(uint256 contentId => string contentCid) private _contents;

    constructor() ERC721("RefiqTopics", "RTP") {}

    function createTopic(
        string calldata name,
        string calldata infoCid
    ) external {
        uint256 topicId = uint256(keccak256(abi.encodePacked(name)));
        emit TopicCreated(topicId, name, infoCid);
        _safeMint(msg.sender, topicId);
    }

    function addModerator(uint256 topicId, address moderator) external {
        if (msg.sender != ownerOf(topicId)) {
            revert Unauthorized();
        }
        if (_moderators[topicId].insert(moderator)) {
            emit ModeratorAdded(topicId, moderator);
        }
    }

    function removeModerator(uint256 topicId, address moderator) external {
        if (msg.sender != ownerOf(topicId)) {
            revert Unauthorized();
        }
        if (_moderators[topicId].remove(moderator)) {
            emit ModeratorRemoved(topicId, moderator);
        }
    }

    function updateTopicInfo(
        uint256 topicId,
        string calldata infoCid
    ) external {
        address sender = msg.sender;
        if (
            !_moderators[topicId].contains(sender) && sender != ownerOf(topicId)
        ) {
            revert Unauthorized();
        }
        emit TopicInfoUpdated(topicId, infoCid);
    }

    function createPost(uint256 topicId, string calldata contentCid) external {
        uint256 contentId = uint256(
            keccak256(abi.encodePacked(topicId, contentCid))
        );
        if (_topics[contentId] != 0) {
            revert DuplicateContent(contentId);
        }
        _topics[contentId] = topicId;
        _authors[contentId] = msg.sender;
        _contents[contentId] = contentCid;
        emit PostCreated(contentId, topicId, msg.sender, contentCid);
    }

    function createComment(
        uint256 parentId,
        string calldata contentCid
    ) external {
        uint256 contentId = uint256(
            keccak256(abi.encodePacked(parentId, contentCid))
        );
        if (_topics[contentId] != 0) {
            revert DuplicateContent(contentId);
        }
        _topics[contentId] = _topics[parentId];
        _authors[contentId] = msg.sender;
        _contents[contentId] = contentCid;
        emit CommentCreated(contentId, parentId, msg.sender, contentCid);
    }

    function removeContent(uint256 contentId) external {
        uint256 topicId = _topics[contentId];
        address sender = msg.sender;
        if (
            _authors[contentId] != sender &&
            !_moderators[topicId].contains(sender) &&
            sender != ownerOf(topicId)
        ) {
            revert Unauthorized();
        }
        delete _authors[contentId];
        delete _contents[contentId];
        emit ContentRemoved(contentId);
    }
}
