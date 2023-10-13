const { ethers } = require("hardhat");

async function main() {
  const [signer] = await ethers.getSigners();
  const signerAddress = await signer.getAddress();
  console.log(`Deploying contracts using: ${signerAddress}`);

  const topics = await ethers.deployContract("RefiqTopics");
  await topics.waitForDeployment();
  const topicsAddress = await topics.getAddress();
  console.log(`topics address: ${topicsAddress}`);
}

main().catch((error) => {
  console.error(error);
  process.exitCode = 1;
});
