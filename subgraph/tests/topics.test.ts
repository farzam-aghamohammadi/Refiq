import { Address, BigInt } from "@graphprotocol/graph-ts";
import {
  afterEach,
  assert,
  clearStore,
  describe,
  test,
} from "matchstick-as/assembly/index";

import { Topic, Post } from "../generated/schema";
import {
  handleTopicCreated,
  handleTransfer,
  handleModeratorAdded,
  handleModeratorRemoved,
  handleTopicInfoUpdated,
  handlePostCreated,
  handleCommentCreated,
  handleContentRemoved,
} from "../src/topics";

import {
  createTopicCreatedEvent,
  createTransferEvent,
  createModeratorAddedEvent,
  createModeratorRemovedEvent,
  createTopicInfoUpdatedEvent,
  createPostCreatedEvent,
  createCommentCreatedEvent,
  createContentRemovedEvent,
} from "./topics-utils";

describe("Topics", function () {
  afterEach(function () {
    clearStore();
  });

  test("handleTopicCreated", function () {
    const topicName = "test_topic";
    const topicId = BigInt.fromI32(420);
    const topicInfoCid = "test_info_cid";
    const topicCreatedEvent = createTopicCreatedEvent(
      topicId,
      topicName,
      topicInfoCid
    );
    handleTopicCreated(topicCreatedEvent);

    assert.entityCount("Topic", 1);
    assert.fieldEquals("Topic", topicId.toString(), "name", topicName);
    assert.fieldEquals(
      "Topic",
      topicId.toString(),
      "owner",
      "0x0000000000000000000000000000000000000000"
    );
    assert.fieldEquals("Topic", topicId.toString(), "moderators", "[]");
    assert.fieldEquals("Topic", topicId.toString(), "ownerShare", "0");
    assert.fieldEquals("Topic", topicId.toString(), "moderatorsShare", "0");
    assert.fieldEquals("Topic", topicId.toString(), "infoCid", topicInfoCid);
  });

  test("handleTransfer", function () {
    const topicName = "test_topic";
    const topicId = BigInt.fromI32(420);
    const topicOwner = Address.fromString(
      "0x0000000000000000000000000000000000000001"
    );
    const topicInfoCid = "test_info_cid";
    const topic = new Topic(topicId.toString());
    topic.name = topicName;
    topic.owner = topicOwner.toHexString();
    topic.moderators = [];
    topic.ownerShare = 0;
    topic.moderatorsShare = 0;
    topic.infoCid = topicInfoCid;
    topic.save();

    const newTopicOwner = Address.fromString(
      "0x0000000000000000000000000000000000000002"
    );
    const transferEvent = createTransferEvent(
      topicOwner,
      newTopicOwner,
      topicId
    );
    handleTransfer(transferEvent);

    assert.entityCount("Topic", 1);
    assert.fieldEquals("Topic", topicId.toString(), "name", topicName);
    assert.fieldEquals(
      "Topic",
      topicId.toString(),
      "owner",
      newTopicOwner.toHexString()
    );
    assert.fieldEquals("Topic", topicId.toString(), "moderators", "[]");
    assert.fieldEquals("Topic", topicId.toString(), "ownerShare", "0");
    assert.fieldEquals("Topic", topicId.toString(), "moderatorsShare", "0");
    assert.fieldEquals("Topic", topicId.toString(), "infoCid", topicInfoCid);
  });

  test("handleModeratorAdded", function () {
    const topicName = "test_topic";
    const topicId = BigInt.fromI32(420);
    const topicOwner = Address.fromString(
      "0x0000000000000000000000000000000000000001"
    );
    const topicInfoCid = "test_info_cid";
    const topic = new Topic(topicId.toString());
    topic.name = topicName;
    topic.owner = topicOwner.toHexString();
    topic.moderators = [];
    topic.ownerShare = 0;
    topic.moderatorsShare = 0;
    topic.infoCid = topicInfoCid;
    topic.save();

    const newTopicModerator = Address.fromString(
      "0x0000000000000000000000000000000000000002"
    );
    const moderatorAddedEvent = createModeratorAddedEvent(
      topicId,
      newTopicModerator
    );
    handleModeratorAdded(moderatorAddedEvent);

    assert.entityCount("Topic", 1);
    assert.fieldEquals("Topic", topicId.toString(), "name", topicName);
    assert.fieldEquals(
      "Topic",
      topicId.toString(),
      "owner",
      topicOwner.toHexString()
    );
    assert.fieldEquals(
      "Topic",
      topicId.toString(),
      "moderators",
      "[" + newTopicModerator.toHexString() + "]"
    );
    assert.fieldEquals("Topic", topicId.toString(), "ownerShare", "0");
    assert.fieldEquals("Topic", topicId.toString(), "moderatorsShare", "0");
    assert.fieldEquals("Topic", topicId.toString(), "infoCid", topicInfoCid);
  });

  test("handleModeratorRemoved", function () {
    const topicName = "test_topic";
    const topicId = BigInt.fromI32(420);
    const topicOwner = Address.fromString(
      "0x0000000000000000000000000000000000000001"
    );
    const topicModerator = Address.fromString(
      "0x0000000000000000000000000000000000000002"
    );
    const topicInfoCid = "test_info_cid";
    const topic = new Topic(topicId.toString());
    topic.name = topicName;
    topic.owner = topicOwner.toHexString();
    topic.moderators = [topicModerator.toHexString()];
    topic.ownerShare = 0;
    topic.moderatorsShare = 0;
    topic.infoCid = topicInfoCid;
    topic.save();

    const moderatorRemovedEvent = createModeratorRemovedEvent(
      topicId,
      topicModerator
    );
    handleModeratorRemoved(moderatorRemovedEvent);

    assert.entityCount("Topic", 1);
    assert.fieldEquals("Topic", topicId.toString(), "name", topicName);
    assert.fieldEquals(
      "Topic",
      topicId.toString(),
      "owner",
      topicOwner.toHexString()
    );
    assert.fieldEquals("Topic", topicId.toString(), "moderators", "[]");
    assert.fieldEquals("Topic", topicId.toString(), "ownerShare", "0");
    assert.fieldEquals("Topic", topicId.toString(), "moderatorsShare", "0");
    assert.fieldEquals("Topic", topicId.toString(), "infoCid", topicInfoCid);
  });

  test("handleTopicInfoUpdated", function () {
    const topicName = "test_topic";
    const topicId = BigInt.fromI32(420);
    const topicOwner = Address.fromString(
      "0x0000000000000000000000000000000000000001"
    );
    const topicInfoCid = "test_info_cid";
    const topic = new Topic(topicId.toString());
    topic.name = topicName;
    topic.owner = topicOwner.toHexString();
    topic.moderators = [];
    topic.ownerShare = 0;
    topic.moderatorsShare = 0;
    topic.infoCid = topicInfoCid;
    topic.save();

    const newTopicInfoCid = "new_test_info_cid";
    const topicInfoUpdatedEvent = createTopicInfoUpdatedEvent(
      topicId,
      newTopicInfoCid
    );
    handleTopicInfoUpdated(topicInfoUpdatedEvent);

    assert.entityCount("Topic", 1);
    assert.fieldEquals("Topic", topicId.toString(), "name", topicName);
    assert.fieldEquals(
      "Topic",
      topicId.toString(),
      "owner",
      topicOwner.toHexString()
    );
    assert.fieldEquals("Topic", topicId.toString(), "moderators", "[]");
    assert.fieldEquals("Topic", topicId.toString(), "ownerShare", "0");
    assert.fieldEquals("Topic", topicId.toString(), "moderatorsShare", "0");
    assert.fieldEquals("Topic", topicId.toString(), "infoCid", newTopicInfoCid);
  });

  test("handlePostCreated", function () {
    const topicId = BigInt.fromI32(420);
    const topic = new Topic(topicId.toString());
    topic.name = "test_topic";
    topic.owner = "0x0000000000000000000000000000000000000001";
    topic.moderators = [];
    topic.ownerShare = 0;
    topic.moderatorsShare = 0;
    topic.infoCid = "test_info_cid";
    topic.save();

    const postId = BigInt.fromI64(64);
    const postAuthor = Address.fromString(
      "0x0000000000000000000000000000000000000002"
    );
    const postContentCid = "test_post_content_cid";
    const postCreatedEvent = createPostCreatedEvent(
      postId,
      topicId,
      postAuthor,
      postContentCid
    );
    handlePostCreated(postCreatedEvent);

    assert.entityCount("Post", 1);
    assert.fieldEquals(
      "Post",
      postId.toString(),
      "author",
      postAuthor.toHexString()
    );
    assert.fieldEquals("Post", postId.toString(), "awards", "0");
    assert.fieldEquals("Post", postId.toString(), "contentCid", postContentCid);
    assert.fieldEquals("Post", postId.toString(), "topic", topicId.toString());
  });

  test("handleCommentCreated", function () {
    const topicId = BigInt.fromI32(420);
    const topic = new Topic(topicId.toString());
    topic.name = "test_topic";
    topic.owner = "0x0000000000000000000000000000000000000001";
    topic.moderators = [];
    topic.ownerShare = 0;
    topic.moderatorsShare = 0;
    topic.infoCid = "test_info_cid";
    topic.save();

    const postId = BigInt.fromI64(64);
    const post = new Post(postId.toString());
    post.author = "0x0000000000000000000000000000000000000002";
    post.awards = BigInt.zero();
    post.contentCid = "test_post_content_cid";
    post.topic = topicId.toString();
    post.save();

    const commentId = BigInt.fromI64(128);
    const commentAuthor = Address.fromString(
      "0x0000000000000000000000000000000000000002"
    );
    const commentContentCid = "test_comment_content_cid";
    const commentCreatedEvent = createCommentCreatedEvent(
      commentId,
      postId,
      commentAuthor,
      commentContentCid
    );
    handleCommentCreated(commentCreatedEvent);

    assert.entityCount("Comment", 1);
    assert.fieldEquals(
      "Comment",
      commentId.toString(),
      "author",
      commentAuthor.toHexString()
    );
    assert.fieldEquals("Comment", commentId.toString(), "awards", "0");
    assert.fieldEquals(
      "Comment",
      commentId.toString(),
      "contentCid",
      commentContentCid
    );
    assert.fieldEquals(
      "Comment",
      commentId.toString(),
      "parent",
      postId.toString()
    );

    const nestedCommentId = BigInt.fromI64(256);
    const nestedCommentAuthor = Address.fromString(
      "0x0000000000000000000000000000000000000003"
    );
    const nestedCommentContentCid = "test_nested_comment_content_cid";
    const nestedCommentCreatedEvent = createCommentCreatedEvent(
      nestedCommentId,
      commentId,
      nestedCommentAuthor,
      nestedCommentContentCid
    );
    handleCommentCreated(nestedCommentCreatedEvent);

    assert.entityCount("Comment", 2);
    assert.fieldEquals(
      "Comment",
      nestedCommentId.toString(),
      "author",
      nestedCommentAuthor.toHexString()
    );
    assert.fieldEquals("Comment", nestedCommentId.toString(), "awards", "0");
    assert.fieldEquals(
      "Comment",
      nestedCommentId.toString(),
      "contentCid",
      nestedCommentContentCid
    );
    assert.fieldEquals(
      "Comment",
      nestedCommentId.toString(),
      "parent",
      commentId.toString()
    );
  });

  test("handleContentRemoved", function () {
    const topicId = BigInt.fromI32(420);
    const topic = new Topic(topicId.toString());
    topic.name = "test_topic";
    topic.owner = "0x0000000000000000000000000000000000000001";
    topic.moderators = [];
    topic.ownerShare = 0;
    topic.moderatorsShare = 0;
    topic.infoCid = "test_info_cid";
    topic.save();

    const postId = BigInt.fromI64(64);
    const post = new Post(postId.toString());
    post.author = "0x0000000000000000000000000000000000000002";
    post.awards = BigInt.zero();
    post.contentCid = "test_post_content_cid";
    post.topic = topicId.toString();
    post.save();

    const contentRemovedEvent = createContentRemovedEvent(postId);
    handleContentRemoved(contentRemovedEvent);

    assert.entityCount("Post", 0);
  });
});
