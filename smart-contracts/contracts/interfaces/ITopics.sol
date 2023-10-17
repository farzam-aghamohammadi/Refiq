// SPDX-License-Identifier: GPL-3.0
pragma solidity ^0.8.20;

import {IERC721} from "@openzeppelin/contracts/token/ERC721/IERC721.sol";
import {IERC721Metadata} from "@openzeppelin/contracts/token/ERC721/extensions/IERC721Metadata.sol";

interface IRefiqTopics is IERC721, IERC721Metadata {
    event TopicCreated(uint256 id, string name, string infoCid);
    event TopicInfoUpdated(uint256 id, string infoCid);
    event PostCreated(
        uint256 id,
        uint256 topicId,
        address author,
        string contentCid
    );
    event CommentCreated(
        uint256 id,
        uint256 parentId,
        address author,
        string contentCid
    );

    error Unauthorized();
    error DuplicateContent(uint256 contentId);

    function createTopic(
        string calldata name,
        string calldata infoCid
    ) external;

    function updateTopicInfo(uint256 id, string calldata infoCid) external;

    function createPost(uint256 topicId, string calldata contentCid) external;

    function createComment(
        uint256 parentId,
        string calldata contentCid
    ) external;
}
