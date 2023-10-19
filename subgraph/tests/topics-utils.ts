import { newMockEvent } from "matchstick-as";
import { ethereum, Address, BigInt } from "@graphprotocol/graph-ts";
import {
  TopicCreated,
  Transfer,
  TopicInfoUpdated,
  PostCreated,
  CommentCreated,
} from "../generated/Topics/Topics";

export function createTopicCreatedEvent(
  id: BigInt,
  name: string,
  infoCid: string
): TopicCreated {
  let topicCreatedEvent = changetype<TopicCreated>(newMockEvent());

  topicCreatedEvent.parameters = new Array();

  topicCreatedEvent.parameters.push(
    new ethereum.EventParam("id", ethereum.Value.fromUnsignedBigInt(id))
  );
  topicCreatedEvent.parameters.push(
    new ethereum.EventParam("name", ethereum.Value.fromString(name))
  );
  topicCreatedEvent.parameters.push(
    new ethereum.EventParam("infoCid", ethereum.Value.fromString(infoCid))
  );

  return topicCreatedEvent;
}

export function createTransferEvent(
  from: Address,
  to: Address,
  tokenId: BigInt
): Transfer {
  let transferEvent = changetype<Transfer>(newMockEvent());

  transferEvent.parameters = new Array();

  transferEvent.parameters.push(
    new ethereum.EventParam("from", ethereum.Value.fromAddress(from))
  );
  transferEvent.parameters.push(
    new ethereum.EventParam("to", ethereum.Value.fromAddress(to))
  );
  transferEvent.parameters.push(
    new ethereum.EventParam(
      "tokenId",
      ethereum.Value.fromUnsignedBigInt(tokenId)
    )
  );

  return transferEvent;
}

export function createTopicInfoUpdatedEvent(
  id: BigInt,
  infoCid: string
): TopicInfoUpdated {
  let topicInfoUpdatedEvent = changetype<TopicInfoUpdated>(newMockEvent());

  topicInfoUpdatedEvent.parameters = new Array();

  topicInfoUpdatedEvent.parameters.push(
    new ethereum.EventParam("id", ethereum.Value.fromUnsignedBigInt(id))
  );
  topicInfoUpdatedEvent.parameters.push(
    new ethereum.EventParam("infoCid", ethereum.Value.fromString(infoCid))
  );

  return topicInfoUpdatedEvent;
}

export function createPostCreatedEvent(
  id: BigInt,
  topicId: BigInt,
  author: Address,
  contentCid: string
): PostCreated {
  let postCreatedEvent = changetype<PostCreated>(newMockEvent());

  postCreatedEvent.parameters = new Array();

  postCreatedEvent.parameters.push(
    new ethereum.EventParam("id", ethereum.Value.fromUnsignedBigInt(id))
  );
  postCreatedEvent.parameters.push(
    new ethereum.EventParam(
      "topicId",
      ethereum.Value.fromUnsignedBigInt(topicId)
    )
  );
  postCreatedEvent.parameters.push(
    new ethereum.EventParam("author", ethereum.Value.fromAddress(author))
  );
  postCreatedEvent.parameters.push(
    new ethereum.EventParam("contentCid", ethereum.Value.fromString(contentCid))
  );

  return postCreatedEvent;
}

export function createCommentCreatedEvent(
  id: BigInt,
  parentId: BigInt,
  author: Address,
  contentCid: string
): CommentCreated {
  let commentCreatedEvent = changetype<CommentCreated>(newMockEvent());

  commentCreatedEvent.parameters = new Array();

  commentCreatedEvent.parameters.push(
    new ethereum.EventParam("id", ethereum.Value.fromUnsignedBigInt(id))
  );
  commentCreatedEvent.parameters.push(
    new ethereum.EventParam(
      "topicId",
      ethereum.Value.fromUnsignedBigInt(parentId)
    )
  );
  commentCreatedEvent.parameters.push(
    new ethereum.EventParam("author", ethereum.Value.fromAddress(author))
  );
  commentCreatedEvent.parameters.push(
    new ethereum.EventParam("contentCid", ethereum.Value.fromString(contentCid))
  );

  return commentCreatedEvent;
}
