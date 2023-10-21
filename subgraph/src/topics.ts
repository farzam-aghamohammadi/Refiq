import { BigInt, store } from "@graphprotocol/graph-ts";
import {
  TopicCreated as TopicCreatedEvent,
  Transfer as TransferEvent,
  TopicPolicyUpdated as TopicPolicyUpdatedEvent,
  ModeratorAdded as ModeratorAddedEvent,
  ModeratorRemoved as ModeratorRemovedEvent,
  TopicInfoUpdated as TopicInfoUpdatedEvent,
  PostCreated as PostCreatedEvent,
  CommentCreated as CommentCreatedEvent,
  ContentAwarded as ContentAwardedEvent,
  ContentRemoved as ContentRemovedEvent,
} from "../generated/Topics/Topics";
import { Topic, Post, Comment } from "../generated/schema";

export function handleTopicCreated(event: TopicCreatedEvent): void {
  const id = event.params.topicId;
  const topic = new Topic(id.toString());
  topic.name = event.params.name;
  topic.owner = "0x0000000000000000000000000000000000000000";
  topic.moderators = [];
  topic.ownerShare = 0;
  topic.moderatorsShare = 0;
  topic.infoCid = event.params.infoCid;
  topic.save();
}

export function handleTransfer(event: TransferEvent): void {
  const id = event.params.tokenId;
  const topic = Topic.load(id.toString());
  if (topic === null) {
    throw new Error(`tokenId for handleTransfer is invalid: ${id}`);
  }
  topic.owner = event.params.to.toHexString();
  topic.save();
}

export function handleTopicPolicyUpdated(event: TopicPolicyUpdatedEvent): void {
  const id = event.params.topicId;
  const topic = Topic.load(id.toString());
  if (topic === null) {
    throw new Error(`tokenId for handleTransfer is invalid: ${id}`);
  }
  topic.ownerShare = event.params.ownerShare;
  topic.moderatorsShare = event.params.moderatorsShare;
  topic.save();
}

export function handleModeratorAdded(event: ModeratorAddedEvent): void {
  const id = event.params.topicId;
  const topic = Topic.load(id.toString());
  if (topic === null) {
    throw new Error(`tokenId for handleTransfer is invalid: ${id}`);
  }
  topic.moderators = topic.moderators.concat([
    event.params.moderator.toHexString(),
  ]);
  topic.save();
}

function findModeratorIndex(moderators: string[], moderator: string): i32 {
  for (let i: i32 = 0; i < moderators.length; i++) {
    if (moderators[i] == moderator) {
      return i;
    }
  }
  return -1;
}

export function handleModeratorRemoved(event: ModeratorRemovedEvent): void {
  const id = event.params.topicId;
  const topic = Topic.load(id.toString());
  if (topic === null) {
    throw new Error(`tokenId for handleTransfer is invalid: ${id}`);
  }
  const index = findModeratorIndex(
    topic.moderators,
    event.params.moderator.toHexString()
  );
  topic.moderators = topic.moderators
    .slice(0, index)
    .concat(topic.moderators.slice(index + 1));
  topic.save();
}

export function handleTopicInfoUpdated(event: TopicInfoUpdatedEvent): void {
  const id = event.params.topicId;
  const topic = Topic.load(id.toString());
  if (topic === null) {
    throw new Error(`id for handleTransfer is invalid: ${id}`);
  }
  topic.infoCid = event.params.infoCid;
  topic.save();
}

export function handlePostCreated(event: PostCreatedEvent): void {
  const id = event.params.contentId;
  const post = new Post(id.toString());
  post.author = event.params.author.toHexString();
  post.awards = BigInt.zero();
  post.contentCid = event.params.contentCid;
  post.topic = event.params.topicId.toString();
  post.save();
}

export function handleCommentCreated(event: CommentCreatedEvent): void {
  const id = event.params.contentId;
  const comment = new Comment(id.toString());
  comment.author = event.params.author.toHexString();
  comment.awards = BigInt.zero();
  comment.contentCid = event.params.contentCid;
  comment.parent = event.params.parentId.toString();
  comment.save();
}

export function handleContentAwarded(event: ContentAwardedEvent): void {
  const id = event.params.contentId;
  const post = Post.load(id.toString());
  const comment = Comment.load(id.toString());
  if (post) {
    post.awards = post.awards.plus(event.params.amount);
    post.save();
  } else if (comment) {
    comment.awards = comment.awards.plus(event.params.amount);
    comment.save();
  } else {
    throw new Error(`tokenId for handleContentAwarded is invalid: ${id}`);
  }
}

export function handleContentRemoved(event: ContentRemovedEvent): void {
  const id = event.params.contentId;
  if (Post.load(id.toString())) {
    store.remove("Post", id.toString());
  } else if (Comment.load(id.toString())) {
    store.remove("Comment", id.toString());
  }
}
