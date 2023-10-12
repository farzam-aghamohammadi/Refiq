package com.eth.refiq

import androidx.annotation.DrawableRes

fun getPersonalSignBody(account: String): String {
    val msg = "My email is john@doe.com - ${System.currentTimeMillis()}".encodeToByteArray()
        .joinToString(separator = "", prefix = "0x") { eachByte -> "%02x".format(eachByte) }
    return "[\"$msg\", \"$account\"]"
}

fun getEthSignBody(account: String): String {
    val msg = "My email is john@doe.com - ${System.currentTimeMillis()}".encodeToByteArray()
        .joinToString(separator = "", prefix = "0x") { eachByte -> "%02x".format(eachByte) }
    return "[\"$account\", \"$msg\"]"
}

fun getEthSendTransaction(account: String): String {
    return "[{\"from\":\"$account\",\"to\":\"0x70012948c348CBF00806A3C79E3c5DAdFaAa347B\",\"data\":\"0x\",\"gasLimit\":\"0x5208\",\"gasPrice\":\"0x0649534e00\",\"value\":\"0x01\",\"nonce\":\"0x07\"}]"
}

fun getEthSignTypedData(account: String): String {
    val stringifiedParams =
        """{\"types\":{\"EIP712Domain\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"version\",\"type\":\"string\"},{\"name\":\"chainId\",\"type\":\"uint256\"},{\"name\":\"verifyingContract\",\"type\":\"address\"}],\"Person\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"wallet\",\"type\":\"address\"}],\"Mail\":[{\"name\":\"from\",\"type\":\"Person\"},{\"name\": \"to\",\"type\":\"Person\"},{\"name\":\"contents\",\"type\":\"string\"}]},\"primaryType\":\"Mail\",\"domain\":{\"name\":\"Ether Mail\",\"version\":\"1\",\"chainId\":1,\"verifyingContract\":\"0xCcCCccccCCCCcCCCCCCcCcCccCcCCCcCcccccccC\"},\"message\":{\"from\": {\"name\":\"Cow\",\"wallet\":\"0xCD2a3d9F938E13CD947Ec05AbC7FE734Df8DD826\"},\"to\":{\"name\":\"Bob\",\"wallet\":\"0xbBbBBBBbbBBBbbbBbbBbbbbBBbBbbbbBbBbbBBbB\"},\"contents\":\"Hello, Bob!\"}}"""

    return "[\"$account\",\"$stringifiedParams\"]"
}

enum class Chains(
    val chainName: String,
    val chainNamespace: String,
    val chainReference: String,
    @DrawableRes val icon: Int,
    val color: String,
    val methods: List<String>,
    val events: List<String>,
    val chainId: String = "$chainNamespace:$chainReference"
) {

    ETHEREUM_MAIN(
        chainName = "Ethereum",
        chainNamespace = Info.Eth.chain,
        chainReference = "1",
        icon = R.drawable.ic_ethereum,
        color = "#617de8",
        methods = Info.Eth.defaultMethods,
        events = Info.Eth.defaultEvents,
    ),



    ETHEREUM_KOVAN(
        chainName = "Ethereum Kovan",
        chainNamespace = Info.Eth.chain,
        chainReference = "42",
        icon = R.drawable.ic_ethereum,
        color = "#617de8",
        methods = Info.Eth.defaultMethods,
        events = Info.Eth.defaultEvents,
    ),

    ;

    sealed class Info {
        abstract val chain: String
        abstract val defaultEvents: List<String>
        abstract val defaultMethods: List<String>

        object Eth : Info() {
            override val chain = "eip155"
            override val defaultEvents: List<String> = listOf("chainChanged", "accountsChanged")
            override val defaultMethods: List<String> = listOf(
                "eth_sendTransaction",
                "personal_sign",
                "eth_sign",
                "eth_signTypedData"
            )
        }

    }
}
