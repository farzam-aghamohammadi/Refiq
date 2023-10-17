const { ethers } = require("hardhat");
const {
  loadFixture,
} = require("@nomicfoundation/hardhat-toolbox/network-helpers");
const { expect } = require("chai");

describe("RefiqTopics", function () {
  async function deployFixture() {
    const Topics = await ethers.getContractFactory("RefiqTopics");
    const topics = await Topics.deploy();
    const [, owner1, author1, author2] = await ethers.getSigners();
    return { topics, owner1, author1, author2 };
  }

  async function deployFixtureWithTopic() {
    const result = await loadFixture(deployFixture);
    const { topics, owner1 } = result;
    const topicName = "test_topic";
    const topicId =
      "27804793435090195748552391999461632910659124896759557810118903205356103197977";
    await topics.connect(owner1).createTopic(topicName);
    return { ...result, topicName, topicId };
  }

  async function deployFixtureWithPost() {
    const result = await loadFixture(deployFixtureWithTopic);
    const { topics, topicId, author1 } = result;
    const postId =
      "108355461378729987362265230430483131655744934225951456976902664848123781277909";
    const postCid =
      "bafybeigdyrzt5sfp7udm7hu76uh7y26nf3efuylqabf3oclgtqy55fbzdi";

    await topics.connect(author1).createPost(topicId, postCid);
    return { ...result, postId, postCid };
  }

  describe("Deployment", async function () {
    it("Should have right name", async function () {
      const { topics } = await loadFixture(deployFixture);
      const name = await topics.name();
      expect(name).to.equal("RefiqTopics");
    });

    it("Should have right symbol", async function () {
      const { topics } = await loadFixture(deployFixture);
      const symbol = await topics.symbol();
      expect(symbol).to.equal("RTP");
    });
  });

  describe("createTopic", async function () {
    it("Should emit Transfer from zero_address to owner", async function () {
      const { topics, owner1 } = await loadFixture(deployFixture);
      const name = "test_topic";
      const id =
        "27804793435090195748552391999461632910659124896759557810118903205356103197977";
      await expect(topics.connect(owner1).createTopic(name))
        .to.emit(topics, "Transfer")
        .withArgs(ethers.ZeroAddress, owner1.address, id);
    });

    it("Should emit TopicCreated event with correct args", async function () {
      const { topics, owner1 } = await loadFixture(deployFixture);
      const name = "test_topic";
      const id =
        "27804793435090195748552391999461632910659124896759557810118903205356103197977";
      await expect(topics.connect(owner1).createTopic(name))
        .to.emit(topics, "TopicCreated")
        .withArgs(id, name);
    });

    it("Should topic owner have been set correctly", async function () {
      const { topics, owner1 } = await loadFixture(deployFixture);
      const name = "test_topic";
      const id =
        "27804793435090195748552391999461632910659124896759557810118903205356103197977";
      await topics.connect(owner1).createTopic(name);
      await expect(topics.ownerOf(id)).to.eventually.equal(owner1.address);
    });

    it("Should revert on duplicate topic", async function () {
      const { topics, owner1 } = await loadFixture(deployFixture);
      const name = "test_topic";
      await topics.connect(owner1).createTopic(name);
      await expect(topics.connect(owner1).createTopic(name)).to.reverted;
    });
  });

  describe("createPost", async function () {
    it("Should emit PostCreated event with correct args", async function () {
      const { topics, topicId, author1 } = await loadFixture(
        deployFixtureWithTopic,
      );
      const cid = "bafybeigdyrzt5sfp7udm7hu76uh7y26nf3efuylqabf3oclgtqy55fbzdi";
      const id =
        "108355461378729987362265230430483131655744934225951456976902664848123781277909";
      await expect(topics.connect(author1).createPost(topicId, cid))
        .to.emit(topics, "PostCreated")
        .withArgs(id, topicId, author1.address, cid);
    });

    it("Should revert on duplicate content", async function () {
      const { topics, topicId, author1 } = await loadFixture(
        deployFixtureWithTopic,
      );
      const cid = "bafybeigdyrzt5sfp7udm7hu76uh7y26nf3efuylqabf3oclgtqy55fbzdi";
      const id =
        "108355461378729987362265230430483131655744934225951456976902664848123781277909";
      await topics.connect(author1).createPost(topicId, cid);
      await expect(topics.connect(author1).createPost(topicId, cid))
        .to.revertedWithCustomError(topics, "DuplicateContent")
        .withArgs(id);
    });
  });

  describe("createComment", async function () {
    it("Should emit CommentCreated event with correct args", async function () {
      const { topics, postId, author2 } = await loadFixture(
        deployFixtureWithPost,
      );
      const cid = "bafybeigdyrzt5sfp7udm7hu76uh7y26nf3efuylqabf3oclgtqy55fbzdi";
      const id =
        "58093008259506952688163896443918611580339215765731669007314335606848863452838";
      await expect(topics.connect(author2).createComment(postId, cid))
        .to.emit(topics, "CommentCreated")
        .withArgs(id, postId, author2.address, cid);
    });

    it("Should revert on duplicate content", async function () {
      const { topics, postId, author2 } = await loadFixture(
        deployFixtureWithPost,
      );
      const cid = "bafybeigdyrzt5sfp7udm7hu76uh7y26nf3efuylqabf3oclgtqy55fbzdi";
      const id =
        "58093008259506952688163896443918611580339215765731669007314335606848863452838";
      await topics.connect(author2).createComment(postId, cid);
      await expect(topics.connect(author2).createComment(postId, cid))
        .to.revertedWithCustomError(topics, "DuplicateContent")
        .withArgs(id);
    });
  });
});
