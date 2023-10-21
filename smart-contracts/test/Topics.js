const { ethers } = require("hardhat");
const {
  loadFixture,
} = require("@nomicfoundation/hardhat-toolbox/network-helpers");
const { expect } = require("chai");

describe("RefiqTopics", function () {
  async function deployFixture() {
    const Topics = await ethers.getContractFactory("RefiqTopics");
    const topics = await Topics.deploy();
    const [, owner1, moderator1, moderator2, author1, author2, nobody] =
      await ethers.getSigners();
    return { topics, owner1, moderator1, moderator2, author1, author2, nobody };
  }

  async function deployFixtureWithTopic() {
    const result = await loadFixture(deployFixture);
    const { topics, owner1 } = result;
    const topicName = "test_topic";
    const topicId =
      "27804793435090195748552391999461632910659124896759557810118903205356103197977";
    const topicInfoCid = "randoc-cid-for-info";
    await topics.connect(owner1).createTopic(topicName, topicInfoCid);
    return { ...result, topicName, topicId, topicInfoCid };
  }

  async function deployFixtureWithModerator() {
    const result = await loadFixture(deployFixtureWithTopic);
    const { topics, topicId, owner1, moderator1 } = result;
    await topics.connect(owner1).addModerator(topicId, moderator1.address);
    return result;
  }

  async function deployFixtureWithPost() {
    const result = await loadFixture(deployFixtureWithModerator);
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
      const infoCid = "randoc-cid-for-info";
      await expect(topics.connect(owner1).createTopic(name, infoCid))
        .to.emit(topics, "Transfer")
        .withArgs(ethers.ZeroAddress, owner1.address, id);
    });

    it("Should emit TopicCreated event with correct args", async function () {
      const { topics, owner1 } = await loadFixture(deployFixture);
      const name = "test_topic";
      const id =
        "27804793435090195748552391999461632910659124896759557810118903205356103197977";
      const infoCid = "randoc-cid-for-info";
      await expect(topics.connect(owner1).createTopic(name, infoCid))
        .to.emit(topics, "TopicCreated")
        .withArgs(id, name, infoCid);
    });

    it("Should topic owner have been set correctly", async function () {
      const { topics, owner1 } = await loadFixture(deployFixture);
      const name = "test_topic";
      const id =
        "27804793435090195748552391999461632910659124896759557810118903205356103197977";
      const infoCid = "randoc-cid-for-info";
      await topics.connect(owner1).createTopic(name, infoCid);
      await expect(topics.ownerOf(id)).to.eventually.equal(owner1.address);
    });

    it("Should revert on duplicate topic", async function () {
      const { topics, owner1 } = await loadFixture(deployFixture);
      const name = "test_topic";
      const infoCid = "randoc-cid-for-info";
      await topics.connect(owner1).createTopic(name, infoCid);
      await expect(topics.connect(owner1).createTopic(name, infoCid)).to
        .reverted;
    });
  });

  describe("updateTopicPolicy", async function () {
    it("Should emit topicPolicyUpdated event with correct args", async function () {
      const { topics, topicId, owner1 } = await loadFixture(
        deployFixtureWithTopic,
      );
      const ownerShare = 16;
      const moderatorsShare = 32;
      await expect(
        topics
          .connect(owner1)
          .updateTopicPolicy(topicId, ownerShare, moderatorsShare),
      )
        .to.emit(topics, "TopicPolicyUpdated")
        .withArgs(topicId, ownerShare, moderatorsShare);
    });

    it("Should revert on unauthorized access", async function () {
      const { topics, topicId, nobody } = await loadFixture(
        deployFixtureWithTopic,
      );
      await expect(
        topics.connect(nobody).updateTopicPolicy(topicId, 16, 32),
      ).to.revertedWithCustomError(topics, "Unauthorized");
    });
  });

  describe("addModerator", async function () {
    it("Should emit ModeratorAdded event with correct args", async function () {
      const { topics, topicId, owner1, moderator2 } = await loadFixture(
        deployFixtureWithTopic,
      );
      await expect(
        topics.connect(owner1).addModerator(topicId, moderator2.address),
      )
        .to.emit(topics, "ModeratorAdded")
        .withArgs(topicId, moderator2.address);
    });

    it("Should not emit anything on already add moderator", async function () {
      const { topics, topicId, owner1, moderator1 } = await loadFixture(
        deployFixtureWithModerator,
      );
      await expect(
        topics.connect(owner1).addModerator(topicId, moderator1.address),
      ).to.not.emit(topics, "ModeratorAdded");
    });

    it("Should revert on unauthorized access", async function () {
      const { topics, topicId, nobody, moderator2 } = await loadFixture(
        deployFixtureWithTopic,
      );
      await expect(
        topics.connect(nobody).addModerator(topicId, moderator2.address),
      ).to.revertedWithCustomError(topics, "Unauthorized");
    });
  });

  describe("removeModerator", async function () {
    it("Should emit ModeratorRemoved event with correct args", async function () {
      const { topics, topicId, owner1, moderator1 } = await loadFixture(
        deployFixtureWithModerator,
      );
      await expect(
        topics.connect(owner1).removeModerator(topicId, moderator1.address),
      )
        .to.emit(topics, "ModeratorRemoved")
        .withArgs(topicId, moderator1.address);
    });

    it("Should not emit anything on not a moderator", async function () {
      const { topics, topicId, owner1, moderator2 } = await loadFixture(
        deployFixtureWithTopic,
      );
      await expect(
        topics.connect(owner1).removeModerator(topicId, moderator2.address),
      ).to.not.emit(topics, "ModeratorRemoved");
    });

    it("Should revert on unauthorized access", async function () {
      const { topics, topicId, nobody, moderator1 } = await loadFixture(
        deployFixtureWithModerator,
      );
      await expect(
        topics.connect(nobody).removeModerator(topicId, moderator1.address),
      ).to.revertedWithCustomError(topics, "Unauthorized");
    });
  });

  describe("updateTopicInfo", async function () {
    it("Should emit TopicInfoUpdated event with correct args", async function () {
      const { topics, topicId, owner1 } = await loadFixture(
        deployFixtureWithTopic,
      );
      const newInfoCid = "new-cid-for-info";
      await expect(topics.connect(owner1).updateTopicInfo(topicId, newInfoCid))
        .to.emit(topics, "TopicInfoUpdated")
        .withArgs(topicId, newInfoCid);
    });

    it("Moderators should be able to update topic info", async function () {
      const { topics, topicId, moderator1 } = await loadFixture(
        deployFixtureWithModerator,
      );
      const newInfoCid = "new-cid-for-info";
      await expect(
        topics.connect(moderator1).updateTopicInfo(topicId, newInfoCid),
      )
        .to.emit(topics, "TopicInfoUpdated")
        .withArgs(topicId, newInfoCid);
    });

    it("Should revert on unauthorized access", async function () {
      const { topics, topicId, nobody } = await loadFixture(
        deployFixtureWithTopic,
      );
      const newInfoCid = "new-cid-for-info";
      await expect(
        topics.connect(nobody).updateTopicInfo(topicId, newInfoCid),
      ).to.revertedWithCustomError(topics, "Unauthorized");
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

  describe("awardContent", async function () {
    it("Should emit ContentAwarded event with correct args", async function () {
      const { topics, postId, nobody } = await loadFixture(
        deployFixtureWithPost,
      );
      const amount = ethers.parseEther("0.01");
      await expect(
        topics.connect(nobody).awardContent(postId, { value: amount }),
      )
        .to.emit(topics, "ContentAwarded")
        .withArgs(postId, amount);
    });

    it("Should revert on invalid content id with InvalidContent", async function () {
      const { topics, nobody } = await loadFixture(deployFixtureWithTopic);
      const amount = ethers.parseEther("0.01");
      await expect(
        topics.connect(nobody).awardContent(10, { value: amount }),
      ).to.revertedWithCustomError(topics, "InvalidContent");
    });
  });

  describe("removeContent", async function () {
    it("Should emit contentRemoved event with correct args for owner", async function () {
      const { topics, postId, owner1 } = await loadFixture(
        deployFixtureWithPost,
      );
      await expect(topics.connect(owner1).removeContent(postId))
        .to.emit(topics, "ContentRemoved")
        .withArgs(postId);
    });

    it("Should emit contentRemoved event with correct args for moderator", async function () {
      const { topics, postId, moderator1 } = await loadFixture(
        deployFixtureWithPost,
      );
      await expect(topics.connect(moderator1).removeContent(postId))
        .to.emit(topics, "ContentRemoved")
        .withArgs(postId);
    });

    it("Should emit contentRemoved event with correct args for author", async function () {
      const { topics, postId, author1 } = await loadFixture(
        deployFixtureWithPost,
      );
      await expect(topics.connect(author1).removeContent(postId))
        .to.emit(topics, "ContentRemoved")
        .withArgs(postId);
    });

    it("Should revert on Unauthorized", async function () {
      const { topics, postId, nobody } = await loadFixture(
        deployFixtureWithPost,
      );
      await expect(
        topics.connect(nobody).removeContent(postId),
      ).to.revertedWithCustomError(topics, "Unauthorized");
    });

    it("Should revert on invalid content id with InvalidContent", async function () {
      const { topics, owner1 } = await loadFixture(deployFixtureWithPost);
      await expect(
        topics.connect(owner1).removeContent(10),
      ).to.revertedWithCustomError(topics, "InvalidContent");
    });
  });
});
