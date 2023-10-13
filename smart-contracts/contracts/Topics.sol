// SPDX-License-Identifier: GPL-3.0
pragma solidity ^0.8.20;

import {ERC721} from "@openzeppelin/contracts/token/ERC721/ERC721.sol";
import {IRefiqTopics} from "./interfaces/ITopics.sol";

contract RefiqTopics is ERC721, IRefiqTopics {
    constructor() ERC721("RefiqTopics", "RTP") {}

    function createTopic(string calldata name) external {
        uint256 id = uint256(keccak256(abi.encodePacked(name)));
        emit TopicCreated(id, name);
        _safeMint(msg.sender, id);
    }
}
