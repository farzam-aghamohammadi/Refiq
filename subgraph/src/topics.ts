import {
  TopicCreated as TopicCreatedEvent,
  Transfer as TransferEvent,
} from "../generated/Topics/Topics";
import { Topic } from "../generated/schema";

export function handleTopicCreated(event: TopicCreatedEvent): void {
  const id = event.params.id;
  const topic = new Topic(id.toString());
  topic.name = event.params.name;
  topic.owner = "0x0000000000000000000000000000000000000000";
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
