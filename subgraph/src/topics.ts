import { store } from "@graphprotocol/graph-ts";
import {
  TopicCreated as TopicCreatedEvent,
  Transfer as TransferEvent,
  ModeratorAdded as ModeratorAddedEvent,
  ModeratorRemoved as ModeratorRemovedEvent,
  TopicInfoUpdated as TopicInfoUpdatedEvent,
  PostCreated as PostCreatedEvent,
  CommentCreated as CommentCreatedEvent,
  ContentRemoved as ContentRemovedEvent,
} from "../generated/Topics/Topics";
import { Topic, Post, Comment } from "../generated/schema";

export function handleTopicCreated(event: TopicCreatedEvent): void {
  const id = event.params.topicId;
  const topic = new Topic(id.toString());
  topic.name = event.params.name;
  topic.owner = "0x0000000000000000000000000000000000000000";
  topic.moderators = [];
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
  post.contentCid = event.params.contentCid;
  post.topic = event.params.topicId.toString();
  post.save();
}

export function handleCommentCreated(event: CommentCreatedEvent): void {
  const id = event.params.contentId;
  const comment = new Comment(id.toString());
  comment.author = event.params.author.toHexString();
  comment.contentCid = event.params.contentCid;
  comment.parent = event.params.parentId.toString();
  comment.save();
}

export function handleContentRemoved(event: ContentRemovedEvent): void {
  const id = event.params.contentId;
  if (Post.load(id.toString())) {
    store.remove("Post", id.toString());
  } else if (Comment.load(id.toString())) {
    store.remove("Comment", id.toString());
  }
}
