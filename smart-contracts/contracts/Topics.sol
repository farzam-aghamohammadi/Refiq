// SPDX-License-Identifier: GPL-3.0
pragma solidity ^0.8.20;

import {ERC721} from "@openzeppelin/contracts/token/ERC721/ERC721.sol";
import {IRefiqTopics} from "./interfaces/ITopics.sol";

contract RefiqTopics is ERC721, IRefiqTopics {
    mapping(uint256 contentId => uint256 topicId) private _topics;
    mapping(uint256 contentId => address author) private _authors;
    mapping(uint256 contentId => string contentCid) private _contents;

    constructor() ERC721("RefiqTopics", "RTP") {}

    function createTopic(
        string calldata name,
        string calldata infoCid
    ) external {
        uint256 id = uint256(keccak256(abi.encodePacked(name)));
        emit TopicCreated(id, name, infoCid);
        _safeMint(msg.sender, id);
    }

    function updateTopicInfo(uint256 id, string calldata infoCid) external {
        if (msg.sender != ownerOf(id)) {
            revert Unauthorized();
        }
        emit TopicInfoUpdated(id, infoCid);
    }

    function createPost(uint256 topicId, string calldata contentCid) external {
        uint256 id = uint256(keccak256(abi.encodePacked(topicId, contentCid)));
        if (_topics[id] != 0) {
            revert DuplicateContent(id);
        }
        _topics[id] = topicId;
        _authors[id] = msg.sender;
        _contents[id] = contentCid;
        emit PostCreated(id, topicId, msg.sender, contentCid);
    }

    function createComment(
        uint256 parentId,
        string calldata contentCid
    ) external {
        uint256 id = uint256(keccak256(abi.encodePacked(parentId, contentCid)));
        if (_topics[id] != 0) {
            revert DuplicateContent(id);
        }
        _topics[id] = _topics[parentId];
        _authors[id] = msg.sender;
        _contents[id] = contentCid;
        emit CommentCreated(id, parentId, msg.sender, contentCid);
    }
}
