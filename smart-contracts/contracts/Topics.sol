// SPDX-License-Identifier: GPL-3.0
pragma solidity ^0.8.20;

import {ERC721} from "@openzeppelin/contracts/token/ERC721/ERC721.sol";
import {AddressSet, AddressSetLib} from "./libraries/AddressSet.sol";
import {IRefiqTopics} from "./interfaces/ITopics.sol";

using AddressSetLib for AddressSet;

contract RefiqTopics is ERC721, IRefiqTopics {
    struct Content {
        address author;
        uint8 ownerShare;
        uint8 moderatorsShare;
        string cid;
    }

    mapping(uint256 topicId => uint8 share) private _ownerShare;
    mapping(uint256 topicId => uint8 share) private _moderatorsShare;
    mapping(uint256 topicId => AddressSet moderatorAddresses)
        private _moderators;
    mapping(uint256 contentId => uint256 topicId) private _topics;
    mapping(uint256 contentId => Content content) private _contents;

    constructor() ERC721("RefiqTopics", "RTP") {}

    function createTopic(
        string calldata name,
        string calldata infoCid
    ) external {
        uint256 topicId = uint256(keccak256(abi.encodePacked(name)));
        emit TopicCreated(topicId, name, infoCid);
        _safeMint(msg.sender, topicId);
    }

    function updateTopicPolicy(
        uint256 topicId,
        uint8 ownerShare,
        uint8 moderatorsShare
    ) external {
        if (msg.sender != ownerOf(topicId)) {
            revert Unauthorized();
        }
        _ownerShare[topicId] = ownerShare;
        _moderatorsShare[topicId] = moderatorsShare;
        emit TopicPolicyUpdated(topicId, ownerShare, moderatorsShare);
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
        uint256 contentId = _createContent(topicId, topicId, contentCid);
        emit PostCreated(contentId, topicId, msg.sender, contentCid);
    }

    function createComment(
        uint256 parentId,
        string calldata contentCid
    ) external {
        uint256 topicId = _topics[parentId];
        uint256 contentId = _createContent(topicId, parentId, contentCid);
        emit CommentCreated(contentId, parentId, msg.sender, contentCid);
    }

    function awardContent(uint256 contentId) external payable {
        uint256 amount = msg.value;
        uint256 topicId = _topics[contentId];
        address author = _contents[contentId].author;
        if (author == address(0)) {
            revert InvalidContent(contentId);
        }

        address owner = ownerOf(topicId);
        uint256 ownerShare = (_contents[contentId].ownerShare * amount) /
            type(uint8).max;
        {
            (bool succeed, ) = owner.call{value: ownerShare}("");
            if (!succeed) {
                revert FailedToSendAward();
            }
        }

        AddressSet storage moderators = _moderators[topicId];
        uint256 moderatorShare = (_contents[contentId].moderatorsShare *
            amount) /
            moderators.size() /
            type(uint8).max;
        {
            bool succeed = moderators.sendEth(moderatorShare);
            if (!succeed) {
                revert FailedToSendAward();
            }
        }

        uint256 authorShare = amount -
            ownerShare -
            (moderatorShare * moderators.size());
        {
            (bool succeed, ) = author.call{value: authorShare}("");
            if (!succeed) {
                revert FailedToSendAward();
            }
        }

        emit ContentAwarded(contentId, amount);
    }

    function removeContent(uint256 contentId) external {
        address author = _contents[contentId].author;
        if (author == address(0)) {
            revert InvalidContent(contentId);
        }

        address sender = msg.sender;
        uint256 topicId = _topics[contentId];
        if (
            author != sender &&
            !_moderators[topicId].contains(sender) &&
            sender != ownerOf(topicId)
        ) {
            revert Unauthorized();
        }
        delete _contents[contentId];
        emit ContentRemoved(contentId);
    }

    function _createContent(
        uint256 topicId,
        uint256 parentId,
        string calldata contentCid
    ) internal returns (uint256) {
        uint256 contentId = uint256(
            keccak256(abi.encodePacked(parentId, contentCid))
        );
        if (_topics[contentId] != 0) {
            revert DuplicateContent(contentId);
        }
        _topics[contentId] = topicId;
        _contents[contentId] = Content({
            author: msg.sender,
            ownerShare: _ownerShare[topicId],
            moderatorsShare: _moderatorsShare[topicId],
            cid: contentCid
        });
        return contentId;
    }
}
