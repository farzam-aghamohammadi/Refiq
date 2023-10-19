import {
  TopicCreated as TopicCreatedEvent,
  Transfer as TransferEvent,
  TopicInfoUpdated as TopicInfoUpdatedEvent,
  PostCreated as PostCreatedEvent,
  CommentCreated as CommentCreatedEvent,
} from "../generated/Topics/Topics";
import { Topic, Post, Comment } from "../generated/schema";

export function handleTopicCreated(event: TopicCreatedEvent): void {
  const id = event.params.id;
  const topic = new Topic(id.toString());
  topic.name = event.params.name;
  topic.owner = "0x0000000000000000000000000000000000000000";
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

export function handleTopicInfoUpdated(event: TopicInfoUpdatedEvent): void {
  const id = event.params.id;
  const topic = Topic.load(id.toString());
  if (topic === null) {
    throw new Error(`id for handleTransfer is invalid: ${id}`);
  }
  topic.infoCid = event.params.infoCid;
  topic.save();
}

export function handlePostCreated(event: PostCreatedEvent): void {
  const id = event.params.id;
  const post = new Post(id.toString());
  post.author = event.params.author.toHexString();
  post.contentCid = event.params.contentCid;
  post.topic = event.params.topicId.toString();
  post.save();
}

export function handleCommentCreated(event: CommentCreatedEvent): void {
  const id = event.params.id;
  const comment = new Comment(id.toString());
  comment.author = event.params.author.toHexString();
  comment.contentCid = event.params.contentCid;
  comment.parent = event.params.parentId.toString();
  comment.save();
}
