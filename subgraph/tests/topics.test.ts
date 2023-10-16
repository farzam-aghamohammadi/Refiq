import { Address, BigInt } from "@graphprotocol/graph-ts";
import {
  afterEach,
  assert,
  clearStore,
  describe,
  test,
} from "matchstick-as/assembly/index";

import { Topic } from "../generated/schema";
import { handleTopicCreated, handleTransfer } from "../src/topics";

import { createTopicCreatedEvent, createTransferEvent } from "./topics-utils";

describe("Topics", function () {
  afterEach(function () {
    clearStore();
  });

  test("handleTopicCreated", function () {
    const topicName = "test_topic";
    const topicId = BigInt.fromI32(420);
    const topicCreatedEvent = createTopicCreatedEvent(topicId, topicName);
    handleTopicCreated(topicCreatedEvent);

    assert.entityCount("Topic", 1);
    assert.fieldEquals("Topic", topicId.toString(), "name", topicName);
    assert.fieldEquals(
      "Topic",
      topicId.toString(),
      "owner",
      "0x0000000000000000000000000000000000000000"
    );
  });

  test("handleTransfer", function () {
    const topicName = "test_topic";
    const topicId = BigInt.fromI32(420);
    const topicOwner = Address.fromString(
      "0x0000000000000000000000000000000000000001"
    );
    const topic = new Topic(topicId.toString());
    topic.name = topicName;
    topic.owner = topicOwner.toHexString();
    topic.save();

    const topicNewOwner = Address.fromString(
      "0x0000000000000000000000000000000000000002"
    );
    const transferEvent = createTransferEvent(
      topicOwner,
      topicNewOwner,
      topicId
    );
    handleTransfer(transferEvent);

    assert.entityCount("Topic", 1);
    assert.fieldEquals("Topic", topicId.toString(), "name", topicName);
    assert.fieldEquals(
      "Topic",
      topicId.toString(),
      "owner",
      topicNewOwner.toHexString()
    );
  });
});
