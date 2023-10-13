const { ethers } = require("hardhat");
const {
  loadFixture,
} = require("@nomicfoundation/hardhat-toolbox/network-helpers");
const { expect } = require("chai");

describe("RefiqTopics", function () {
  async function deployFixture() {
    const Topics = await ethers.getContractFactory("RefiqTopics");
    const topics = await Topics.deploy();

    const [, owner1] = await ethers.getSigners();
    return { topics, owner1 };
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

  describe("mintToken", async function () {
    it("Should emit Transfer from zero_address to to_address", async function () {
      const { topics, owner1 } = await loadFixture(deployFixture);
      const name = "test_topic";
      const id =
        "27804793435090195748552391999461632910659124896759557810118903205356103197977";
      await expect(topics.connect(owner1).createTopic(name))
        .to.emit(topics, "Transfer")
        .withArgs(ethers.ZeroAddress, owner1.address, id);
    });

    it("Should emit TopicCreated event with correct id", async function () {
      const { topics, owner1 } = await loadFixture(deployFixture);
      const name = "test_topic";
      const id =
        "27804793435090195748552391999461632910659124896759557810118903205356103197977";
      await expect(topics.connect(owner1).createTopic(name))
        .to.emit(topics, "TopicCreated")
        .withArgs(id, name);
    });

    it("Should station owner have been set correctly", async function () {
      const { topics, owner1 } = await loadFixture(deployFixture);
      const name = "test_topic";
      const id =
        "27804793435090195748552391999461632910659124896759557810118903205356103197977";
      const tx = await topics.connect(owner1).createTopic(name);
      await expect(topics.ownerOf(id)).to.eventually.equal(owner1.address);
    });

    it("Should revert on duplicate topic", async function () {
      const { topics, owner1 } = await loadFixture(deployFixture);
      const name = "test_topic";
      const id =
        "27804793435090195748552391999461632910659124896759557810118903205356103197977";
      await topics.connect(owner1).createTopic(name);
      await expect(topics.connect(owner1).createTopic(name)).to.reverted;
    });
  });
});
