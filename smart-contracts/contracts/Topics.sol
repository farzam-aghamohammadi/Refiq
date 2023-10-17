// SPDX-License-Identifier: GPL-3.0
pragma solidity ^0.8.20;

import {ERC721} from "@openzeppelin/contracts/token/ERC721/ERC721.sol";
import {IRefiqTopics} from "./interfaces/ITopics.sol";

contract RefiqTopics is ERC721, IRefiqTopics {
    mapping(uint256 contentId => uint256 topicId) private _topics;
    mapping(uint256 contentId => address author) private _authors;
    mapping(uint256 contentId => string cid) private _cids;

    constructor() ERC721("RefiqTopics", "RTP") {}

    function createTopic(string calldata name) external {
        uint256 id = uint256(keccak256(abi.encodePacked(name)));
        emit TopicCreated(id, name);
        _safeMint(msg.sender, id);
    }

    function createPost(uint256 topicId, string calldata cid) external {
        uint256 id = uint256(keccak256(abi.encodePacked(topicId, cid)));
        if (_topics[id] != 0) {
            revert DuplicateContent(id);
        }
        _topics[id] = topicId;
        _authors[id] = msg.sender;
        _cids[id] = cid;
        emit PostCreated(id, topicId, msg.sender, cid);
    }

    function createComment(uint256 parentId, string calldata cid) external {
        uint256 id = uint256(keccak256(abi.encodePacked(parentId, cid)));
        if (_topics[id] != 0) {
            revert DuplicateContent(id);
        }
        _topics[id] = _topics[parentId];
        _authors[id] = msg.sender;
        _cids[id] = cid;
        emit CommentCreated(id, parentId, msg.sender, cid);
    }
}
