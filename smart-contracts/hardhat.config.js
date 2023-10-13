require("dotenv").config();

require("@nomicfoundation/hardhat-toolbox");
require("@nomiclabs/hardhat-solhint");

const privateKey = process.env.PRIVATE_KEY;
const accounts = privateKey !== undefined ? [privateKey] : [];

module.exports = {
  solidity: {
    version: "0.8.21",
    settings: {
      optimizer: {
        enabled: true,
        runs: 200,
      },
      evmVersion: "london",
    },
  },
  networks: {
    Sepolia: {
      url: "https://eth-sepolia-public.unifra.io/",
      accounts,
    },
    ScrollSepolia: {
      url: "https://1rpc.io/scroll/sepolia",
      accounts,
    },
  },
};
