// SPDX-License-Identifier: GPL-3.0
pragma solidity ^0.8.20;

import {IERC721} from "@openzeppelin/contracts/token/ERC721/IERC721.sol";
import {IERC721Metadata} from "@openzeppelin/contracts/token/ERC721/extensions/IERC721Metadata.sol";

interface IRefiqTopics is IERC721, IERC721Metadata {
    event TopicCreated(uint256 id, string name);

    function createTopic(string calldata name) external;
}
