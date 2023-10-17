// SPDX-License-Identifier: GPL-3.0
pragma solidity ^0.8.20;

import {IERC721} from "@openzeppelin/contracts/token/ERC721/IERC721.sol";
import {IERC721Metadata} from "@openzeppelin/contracts/token/ERC721/extensions/IERC721Metadata.sol";

interface IRefiqTopics is IERC721, IERC721Metadata {
    event TopicCreated(uint256 id, string name);
    event PostCreated(uint256 id, uint256 topicId, address author, string cid);
    event CommentCreated(
        uint256 id,
        uint256 parentId,
        address author,
        string cid
    );

    error DuplicateContent(uint256 contentId);

    function createTopic(string calldata name) external;

    function createPost(uint256 topicId, string calldata cid) external;

    function createComment(uint256 parentId, string calldata cid) external;
}
